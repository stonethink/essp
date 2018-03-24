package server.essp.attendance.leave.logic;

import server.framework.logic.*;
import java.util.Calendar;
import java.util.List;
import itf.hr.HrFactory;
import c2s.essp.common.user.DtoUser;
import java.util.Date;
import c2s.essp.common.calendar.WorkCalendarBase;
import server.framework.common.BusinessException;
import server.essp.attendance.leave.viewbean.VbLeaveType;
import c2s.essp.attendance.Constant;
import db.essp.attendance.TcUserLeavePK;
import db.essp.attendance.TcUserLeave;
import db.essp.attendance.TcUserLeaveDetailPK;
import db.essp.attendance.TcUserLeaveDetail;
import c2s.essp.common.calendar.WrokTimeFactory;
import java.util.Set;
import java.util.HashSet;

/**
 * 年休假维护,每年的1月1日执行一次,遍历所有的人员按其年资增加其年休假时间
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class AnnualLeaveManager extends AbstractBusinessLogic {
    //该程序执行的时间:1月1日
    public static final int RUN_MONTH = 1;
    public static final int RUN_DAY = 1;

    private Calendar today = Calendar.getInstance();
    private String annualLeaveType = null;
    private float dailyWorkHours = 0F;
    public void run(){
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DATE);
        if(month != (RUN_MONTH - 1) || day != RUN_DAY){
            throw new BusinessException("","This program must be run at "+RUN_MONTH+"/"+RUN_DAY+"!");
        }
        annualLeaveType = getAnnualLeaveType();
        dailyWorkHours = getDailyWorkHours();
        List allUser = listAllUser();
        for(int i = 0;i < allUser.size();i ++){
            DtoUser user = (DtoUser) allUser.get(i);
            increAnnualLeave(user);
        }
    }
    private float getDailyWorkHours(){
        return WrokTimeFactory.serverCreate().getDailyWorkHours();
    }
    //查找年修对应的假别
    private String getAnnualLeaveType(){
        LgLeaveType lg = new LgLeaveType();
        List l = lg.listLeaveType();
        for(int i = 0;i < l.size() ;i ++){
            VbLeaveType leaveType = (VbLeaveType) l.get(i);
            if(Constant.LEAVE_RELATION_ANNU_LEAVE.equals(leaveType.getRelation())){
                if(Constant.LEAVE_TYPE_STATUS_DIS.equals(leaveType.getStatus())){
                    throw new BusinessException("","The annual leave type is disable!");
                }else{
                    return leaveType.getLeaveName();
                }
            }
        }
        throw new BusinessException("","Can not find type for annual leave !");
    }
    //列出系统中所有的人
    private List listAllUser(){
        return HrFactory.create().listAllUser();
    }
    /**
     * 增加某人的年休假
     * 1.若该人员到职日为空,无法计算其年资,返回
     * 2.若该人员离职日为空,说明其已离职,返回
     * 3.已到职日为起,当前日期为止,计算其在职的年数
     * 4.根据在职年数计算其年休假天数,并新增到
     * @param user DtoUser
     */
    private void increAnnualLeave(DtoUser user){
        if(user == null)
            return;
        String loginId = user.getUserLoginId();
        Date inDate = user.getInDate();
        Date outDate = user.getOutDate();
        if(inDate == null){
            log.warn("[" + loginId +
                     "] 's in date is null!Can not add his annual leave!");
            return;
        }
//        if(outDate == null){
//            log.warn("[" + loginId +
//                     "] 's out date is not null!He has left!Can not add his annual leave!");
//           return;
//        }
        int yearNum = caculateInYearNum(inDate);
        int leaveDays = caculateAnnualLeave(yearNum);
        int thisYear = today.get(Calendar.YEAR);
        addUserAnnualLeave(loginId,thisYear,leaveDays);
    }
    /**
     * 新增用户该年的年休,保存数据库
     * @param loginId String
     * @param year int
     * @param leaveDays int
     */
    private void addUserAnnualLeave(String loginId,int year,int leaveDays){
        TcUserLeavePK pk = new TcUserLeavePK(loginId,annualLeaveType);
        try {
            this.getDbAccessor().followTx();
            TcUserLeave userLeave = (TcUserLeave)this.getDbAccessor().get(
                    TcUserLeave.class, pk);
            if(userLeave == null){
                userLeave = new TcUserLeave();
                userLeave.setComp_id(pk);
                this.getDbAccessor().save(userLeave);
            }
            TcUserLeaveDetailPK detailPk = new TcUserLeaveDetailPK(loginId,annualLeaveType,new Long(year));
            TcUserLeaveDetail detail = new TcUserLeaveDetail();
            detail.setComp_id(detailPk);
            detail.setTcUserLeave(userLeave);
            float hours = leaveDays * dailyWorkHours;
            detail.setCanLeaveHours(new Double(hours));

            Set detailSet = userLeave.getTcUserLeaveDetails();
            if(detailSet == null){
                detailSet = new HashSet();
                userLeave.setTcUserLeaveDetails(detailSet);
            }
            detailSet.add(detail);
            this.getDbAccessor().save(detail);
            this.getDbAccessor().update(userLeave);
            this.getDbAccessor().endTxCommit();
        } catch (Exception ex) {
            try {
                this.getDbAccessor().rollback();
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
            throw new BusinessException("","error save annual leave of ["+loginId+"]!",ex);
        }
    }
    //计算到职年数
    public int caculateInYearNum(Date inDate){
        Calendar begin = Calendar.getInstance();
        begin.setTime(inDate);
        int days = WorkCalendarBase.getDayNum(begin,today);
        int year = days / 365;
        return year;
    }
    //根据年数计算休假天数
    public static final int BASE_DAY = 3;//年休的基本天数
    public static final int INCRE_DAY = 1;//每增加1年应增加的年休天数
    public static final int MIN_YEAR = 1;//开始算年休的最少年数
    public static final int MAX_YEAR = 5;//算年休的最大年数
    //年休天数 = 年休的基本天数 + (当前年数 - 起休年数) * 每年增量
    public int caculateAnnualLeave(int year){
        if(year < MIN_YEAR)
            return 0 ;
        else if(year >= MIN_YEAR && year <= MAX_YEAR){
            return BASE_DAY + (year - MIN_YEAR) * INCRE_DAY;
        }else
            return BASE_DAY + (MAX_YEAR - MIN_YEAR) * INCRE_DAY;
    }
    public static void main(String[] args){
        AnnualLeaveManager manager = new AnnualLeaveManager();
        try{
            manager.run();
        }catch(Throwable tx){
            log.error("error incre annual leave!",tx);
        }
    }
}
