package server.essp.tc.pmmanage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.dto.IDto;
import c2s.essp.tc.weeklyreport.DtoAllocateHour;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumOnMonthByPm;
import com.wits.util.StringUtil;
import db.essp.tc.TcByWorkerAccount;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import server.essp.tc.common.LgOvertimeLeaveExtend;
import server.essp.tc.common.LgWeeklyReportLock;
import server.essp.tc.common.LgWeeklyReportSum;
import server.framework.common.BusinessException;
import java.util.Calendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import java.math.BigDecimal;
import server.essp.tc.common.LgAcntActivity;
import com.wits.util.comDate;
import c2s.essp.tc.weeklyreport.IDtoAllocateHourInTheAcnt;
import server.essp.tc.common.LgTcCommon;
import java.util.*;
import server.essp.common.mail.SendHastenMail;
import itf.hr.LgHrUtilImpl;
import server.essp.common.mail.ContentBean;
import server.essp.tc.mail.genmail.contbean.PMConfirmBean;
import server.essp.pms.account.logic.LgAccount;

public class LgWeeklyReportSumByPm extends LgWeeklyReportSum {
    IHrUtil hrUtil = HrFactory.create();
    LgWeeklyReportLock lgWeeklyReportLock = new LgWeeklyReportLock();
    LgOvertimeLeaveExtend lgOvertimeLeave = new LgOvertimeLeaveExtend();
    LgAcntActivity lgAcntActivity = new LgAcntActivity();
    WorkCalendar workCalendar = WrokCalendarFactory.serverCreate();
    public static final String vmFile = "mail/template/tc/PmWeeklyReport.htm";
    public static final String title = "Your weekly report has been rejected!";
    /**
     * 适用：按“周”列出项目acntRid中每个人的weeklyReportSum信息，含每天的时间信息
     * “周”内，一个项目中的一个人只会有一条weeklyReportSum信息
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByAcntOnWeek(Long acntRid, Date beginOfWeek, Date endOfWeek) {
        List tcList = getByAcnt(acntRid, beginOfWeek, endOfWeek);
        List dtoList = dbList2DtoListOnWeek(tcList, beginOfWeek, endOfWeek);

        /**
         * dtoList的元素类型为DtoWeeklyReportSumByPm，上面函数dbList2DtoListOnWeek
         * ，计算actual hour/actual confirmed hour，包括所有项目的
         */
        for (Iterator iter = dtoList.iterator(); iter.hasNext(); ) {
            DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) iter.next();

            getActualHourByUser(dto);
        }

        return dtoList;
    }

    //查询userId在项目中的周报汇总信息
    public DtoWeeklyReportSumByPm listByUserAcntOnWeek(String userId, Long acntRid, Date beginOfWeek, Date endOfWeek) {
        List tcList = getByUserAcnt(userId, acntRid, beginOfWeek, endOfWeek);
        List dtoList = dbList2DtoListOnWeek(tcList, beginOfWeek, endOfWeek);

        Iterator iter = dtoList.iterator();
        if (iter.hasNext()) {
            DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) iter.next();

            getActualHourByUser(dto);

            return dto;
        } else {
            return null;
        }
    }

    /**
     * 适用：按“月”列出项目acntRid中每个人的weeklyReportSum信息，含汇总时间信息，不含每天的时间信息
     * “月”内，一个项目中的一个人会有多条weeklyReportSum信息
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByAcntOnMonth(Long acntRid, Date beginOfMonth, Date endOfMonth) {
        List tcList = getByAcnt(acntRid, beginOfMonth, endOfMonth);
        List dtoList = dbList2DtoListOnMonth(tcList, beginOfMonth, endOfMonth);

        /**
         * dtoList的元素类型为DtoWeeklyReportSumByPm，上面函数dbList2DtoListOnWeek
         * 汇总的actual hour数据实际上是在项目中的部分,要重求
         * ，这里再处理一下：重新计算actual hour/actual confirmed hour
         */
        for (Iterator iter = dtoList.iterator(); iter.hasNext(); ) {
            DtoWeeklyReportSumOnMonthByPm dto = (DtoWeeklyReportSumOnMonthByPm) iter.next();
            dto.setSumHour(dto.getActualHour());
            dto.setActualHour(new BigDecimal(0));

            getActualHourByUser(dto);
        }

        return dtoList;
    }

    //查询userId在项目中的周报汇总信息
    public DtoWeeklyReportSumOnMonthByPm listByUserAcntOnMonth(String userId, Long acntRid, Date beginPeriod, Date endPeriod) {
        List tcList = getByUserAcnt(userId, acntRid, beginPeriod, endPeriod);
        List dtoList = dbList2DtoListOnMonth(tcList, beginPeriod, endPeriod);

        Iterator iter = dtoList.iterator();
        if (iter.hasNext()) {
            DtoWeeklyReportSumOnMonthByPm dto = (DtoWeeklyReportSumOnMonthByPm) iter.next();

            getActualHourByUser(dto);

            return dto;
        } else {
            DtoWeeklyReportSumOnMonthByPm dto = new DtoWeeklyReportSumOnMonthByPm();
            dto.setUserId(userId);
            dto.setBeginPeriod(beginPeriod);
            dto.setEndPeriod(endPeriod);
            dto.setActualHour(new BigDecimal(0));
            dto.setActualHourConfirmed(new BigDecimal(0));
            dto.setLeaveSum(new BigDecimal(0));
            dto.setLeaveSumConfirmed(new BigDecimal(0));
            dto.setOvertimeSum(new BigDecimal(0));
            dto.setOvertimeSumConfirmed(new BigDecimal(0));
            dto.setOvertimeSumConfirmedInTheAcnt(new BigDecimal(0));
            dto.setOvertimeSumInTheAcnt(new BigDecimal(0));
            dto.setSumHour(new BigDecimal(0));
            return dto;
        }
    }

    /**
     * 查询出userId在除pm的项目acntRid之外的项目的工时信息，作为pm的参考
     * 返回list的元素的类型为DtoWeeklyReport
     * @param userId String
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByUserExcludeAcnt(String userId, Long acntRid, Date beginPeriod, Date endPeriod) {
        Calendar bc = Calendar.getInstance();
         Calendar ec = Calendar.getInstance();
         bc.setTime(beginPeriod);
         ec.setTime(endPeriod);

         List weeklyReportList = new ArrayList();
         List weekList = workCalendar.getBEWeekListMax(bc, ec);
         for (Iterator iter = weekList.iterator(); iter.hasNext(); ) {
             Calendar[] weekPeriod = (Calendar[]) iter.next();
             if( weekPeriod != null ){
                 List weeklyReportListOnWeek = listByUserExcludeAcntOnWeek(
                         userId, acntRid, weekPeriod[0].getTime(), weekPeriod[1].getTime());

                 weeklyReportList.addAll(weeklyReportListOnWeek);
             }
         }

        return weeklyReportList;
    }

    public List listByUserExcludeAcntOnWeek(String userId, Long acntRid, Date beginOfWeek, Date endOfWeek) {
            List dtoList = new ArrayList();
            List tcList = getByUser(userId, beginOfWeek, endOfWeek);
            for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
                TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();
                if( tc.getAcntRid().equals(acntRid) == false ){
                    DtoWeeklyReport dto = new DtoWeeklyReport();
                    if( tc.getAccount() != null ){
                        dto.setAcntRid(tc.getAcntRid());
                        dto.setAcntName( tc.getAccount().getId() + " -- " + tc.getAccount().getName() );
                    }
                    dto.setBeginPeriod(tc.getBeginPeriod());
                    dto.setEndPeriod(tc.getEndPeriod());
                    dto.setComments(tc.getConfirmStatus());
                    dto.setSatHour(tc.getSatHour());
                    dto.setSunHour(tc.getSunHour());
                    dto.setMonHour(tc.getMonHour());
                    dto.setTueHour(tc.getTueHour());
                    dto.setWedHour(tc.getWedHour());
                    dto.setThuHour(tc.getThuHour());
                    dto.setFriHour(tc.getFriHour());
                    dto.setWeeklyReportSum(true);
                    dtoList.add(dto);
                }
            }

            return dtoList;
    }

    /**修改周报信息*/
    public void update(List sumList) {
        try {
            for (int i = 0; i < sumList.size(); i++) {
                DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) sumList.get(i);

                if (dto.isModify()) {
                    TcByWorkerAccount db = (TcByWorkerAccount) getDbAccessor().getSession()
                                           .get(TcByWorkerAccount.class, dto.getRid());
                    if (db != null) {
                        if (StringUtil.nvl(dto.getComments()).equals(db.getComments()) == false) {
                            //只会修改comments信息
                            db.setComments(dto.getComments());
                            getDbAccessor().update(db);
                        }
                    }

                    dto.setOp(IDto.OP_NOCHANGE);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update TcWeeklyReport error.", ex);
        }
    }

    /**PM发送邮件  */
    /**
     * 当PM将其成员的confirmStatu改为Rejected时,需要发送邮件通知该成员修改他(她)的周报并且抄送一份给自己
     * @param dto DtoWeeklyReportSumByPm
     * @param loginName String
     */
    public void send (DtoWeeklyReportSumByPm dto,String loginName){
        try{
            TcByWorkerAccount db = (TcByWorkerAccount) getDbAccessor().
                                   getSession()
                                   .get(TcByWorkerAccount.class, dto.getRid());
            LgAccount lgAccount = new LgAccount();
            String manager =(String)lgAccount.load(db.getAcntRid()).getManager();
            if(db != null){
                SendHastenMail shm = new SendHastenMail();
                HashMap hm = new HashMap();
                LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
                ArrayList al = new ArrayList();
                ContentBean cb = new ContentBean();
                String name = ihui.getChineseName(db.getUserId());
                if(name != null) {
                    cb.setUser(name);
                } else {
                    cb.setUser(db.getUserId());
                }

                cb.setEmail(ihui.getUserEmail(db.getUserId()));
                cb.setCcAddress(ihui.getUserEmail(manager));
                PMConfirmBean pm = new PMConfirmBean();
                pm.setSubmiter(loginName);
                pm.setBeginDate(comDate.dateToString(db.getBeginPeriod(),"yyyy-MM-dd"));
                pm.setEndDate(comDate.dateToString(db.getEndPeriod(),"yyyy-MM-dd"));
                pm.setAccountInfo(db.getAccount().getId()+"-"+db.getAccount().getName());
                al.add(pm);
                cb.setMailcontent(al);
                hm.put(db.getUserId(),cb);
                shm.sendHastenMail(vmFile, title, hm);
            }
        }catch(BusinessException ex){
            throw ex;
        }catch (Exception ex) {
            throw new BusinessException("E000000", "send TcWeeklyReport error.", ex);
        }

    }

//    /**pm confirm a weeklyReport*/
//    public String confirm(DtoWeeklyReportSumByPm dto) {
//        String msg = null;
//
//        List weeklyrptList = getByUserAcnt(dto.getUserId(), dto.getAcntRid()
//                                           , dto.getBeginPeriod(), dto.getEndPeriod());
//        if (weeklyrptList.iterator().hasNext()) {
//            TcByWorkerAccount db = (TcByWorkerAccount) weeklyrptList.iterator().next();
//
//            if (DtoWeeklyReport.STATUS_REJECTED.equals(dto.getConfirmStatus())) {
//                //pm拒绝周报将会为该人解锁
//                lgWeeklyReportLock.setLockOff(dto.getUserId(), dto.getBeginPeriod(), dto.getEndPeriod());
//            }
//
//            if (dto.getConfirmStatus().equals(db.getConfirmStatus()) == false) {
//                if( DtoWeeklyReport.STATUS_CONFIRMED.equals( db.getConfirmStatus() ) ){
//                    //从"Confirmed"状态转为其他状态，要更新account/activity的时间
//                    lgAcntActivity.updateByUserAcnt(db.getUserId(), db.getAcntRid()
//                            ,dto.getBeginPeriod(), dto.getEndPeriod(), lgAcntActivity.SUBTRACT);
//
//                }else{
//                    if( DtoWeeklyReport.STATUS_CONFIRMED.equals( dto.getConfirmStatus() ) ){
//                        //从其他状态转为"Confirmed"状态，要更新account/activity的时间
//                        lgAcntActivity.updateByUserAcnt(db.getUserId(), db.getAcntRid()
//                            ,dto.getBeginPeriod(), dto.getEndPeriod(), lgAcntActivity.ADD);
//                    }
//                }
//
//                try {
//                    db.setConfirmStatus(dto.getConfirmStatus());
//                    getDbAccessor().save(db);
//                } catch (Exception ex) {
//                    throw new BusinessException("E00000", "Error when " + dto.getConfirmStatus() + ".", ex);
//                }
//            }
//        }
//
//        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(dto.getConfirmStatus())) {
//            msg = "Confirmed Ok.";
//        } else if (DtoWeeklyReport.STATUS_REJECTED.equals(dto.getConfirmStatus())) {
//            msg = "Rejected Ok.";
//        } else if (DtoWeeklyReport.STATUS_LOCK.equals(dto.getConfirmStatus())) {
//            msg = "Lock Ok.";
//        }
//
//        return msg;
    //    }

    /**pm confirm a weeklyReport*/
    public String confirm(DtoWeeklyReportSumByPm dto) {
        return super.confirm(dto.getUserId(), dto.getAcntRid()
                , dto.getBeginPeriod(), dto.getEndPeriod(), dto.getConfirmStatus());
    }

    /**
     * pm confirm all the weeklyReport in one account
     * note: 如果周报还没有被lock，则不能做修改，也不能被pm confirm
     */
    public String confirmAllByPm(Long acntRid, Date beginPeriod, Date endPeriod, String status) {
        String msg = null;
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        try {

            //更新account/activity的时间
            List weeklyrptList = getByAcnt(acntRid, beginPeriod, endPeriod);
            for (Iterator iter = weeklyrptList.iterator(); iter.hasNext(); ) {

                TcByWorkerAccount db = (TcByWorkerAccount) iter.next();

                if (DtoWeeklyReport.STATUS_REJECTED.equals(status)) {
                    //pm拒绝周报将会为该人解锁
                    lgWeeklyReportLock.setLockOff(db.getUserId(), db.getBeginPeriod(), db.getEndPeriod());
                }
                //removed by xr 2006/03/27
//                else if (DtoWeeklyReport.STATUS_CONFIRMED.equals(status)) {
//                    //pm confirm周报将会为该人加锁
//                    lgWeeklyReportLock.setLockOn(db.getUserId(), db.getBeginPeriod(), db.getEndPeriod());
//                }

                if (status.equals(db.getConfirmStatus()) == false) {
                    if (DtoWeeklyReport.STATUS_CONFIRMED.equals(db.getConfirmStatus())) {
                        //从"Confirmed"状态转为其他状态，要更新account/activity的时间
                        lgAcntActivity.updateByUserAcnt(db.getUserId(), db.getAcntRid()
                                , beginPeriod, endPeriod, lgAcntActivity.SUBTRACT);

                    } else if(DtoWeeklyReport.STATUS_REJECTED.equals(db.getConfirmStatus())){
                        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(status)) {
                            //从其他状态转为"Confirmed"状态，要更新account/activity的时间
                            lgAcntActivity.updateByUserAcnt(db.getUserId(), db.getAcntRid()
                                    , beginPeriod, endPeriod, lgAcntActivity.ADD);
                        }
                    }
                }
            }

            //update TC_WEEKLY_REPORT_SUM
            List paramList = new ArrayList();
            List paramTypeList = new ArrayList();
            paramList.add(acntRid);
            paramTypeList.add("long");
            paramList.add(new java.sql.Timestamp(beginPeriod.getTime()));
            paramTypeList.add("date");
            paramList.add(new java.sql.Timestamp(endPeriod.getTime()));
            paramTypeList.add("date");

            String sql = "update TC_WEEKLY_REPORT_SUM t set t.CONFIRM_STATUS = '" + status + "'"
                         + " where t.ACNT_RID = ?" + " and t.BEGIN_PERIOD >= ? and t.END_PERIOD <= ?"
                         + " and t.CONFIRM_STATUS !='" + DtoWeeklyReport.STATUS_UNLOCK + "'"
                         ;
            log.info(sql);
            getDbAccessor().executeUpdate(sql, paramList, paramTypeList);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E00000", "Error when confirm all.", ex);
        }

        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(status)) {
            msg = "Confirmed Ok.";
        } else if (DtoWeeklyReport.STATUS_REJECTED.equals(status)) {
            msg = "Rejected Ok.";
        } else if (DtoWeeklyReport.STATUS_LOCK.equals(status)) {
            msg = "Lock Ok.";
        }

        return msg;
    }


    protected DtoHoursOnWeek createDtoHoursOnWeek(TcByWorkerAccount db, Date beginOfWeek, Date endOfWeek) {
        DtoWeeklyReportSumByPm dto = new DtoWeeklyReportSumByPm();
        dto.setRid(db.getRid());
        dto.setUserId(db.getUserId());
        dto.setBeginPeriod(db.getBeginPeriod());
        dto.setEndPeriod(db.getEndPeriod());

        dto.setAcntRid(db.getAcntRid());
        dto.setComments(db.getComments());
        dto.setConfirmStatus(db.getConfirmStatus());

        //job code of user
        String jobCode = hrUtil.getUserJobCode(db.getUserId());
        dto.setJobCode(hrUtil.getJobCodeById(jobCode));

        //overtime hours
        lgOvertimeLeave.getOvertimeInTheAcnt(dto);

        //leave hours
        lgOvertimeLeave.getLeave(dto);

        return dto;
    }

    protected DtoAllocateHour createDtoAllocateHour(TcByWorkerAccount db, Date beginPeriod, Date endPeriod) {
        DtoWeeklyReportSumOnMonthByPm dto = new DtoWeeklyReportSumOnMonthByPm();
        dto.setUserId(db.getUserId());
        dto.setBeginPeriod(db.getBeginPeriod());
        dto.setEndPeriod(db.getEndPeriod());

        dto.setAcntRid(db.getAcntRid());

        //init hours
        dto.setActualHour(new BigDecimal(0));
        dto.setActualHourConfirmed(new BigDecimal(0));
        dto.setActualHourConfirmedInTheAcnt(new BigDecimal(0));
        dto.setActualHourInTheAcnt(new BigDecimal(0));

        //job code of user
        String jobCode = hrUtil.getUserJobCode(db.getUserId());
        dto.setJobCode(hrUtil.getJobCodeById(jobCode));

        //overtime hours
        lgOvertimeLeave.getOvertimeInTheAcnt(dto);

        //leave hours
        lgOvertimeLeave.getLeave(dto);

        return dto;
    }


    protected void mergeDtoAndDbOnMonth(DtoAllocateHour dto, TcByWorkerAccount db, Date beginOfMonth, Date endOfMonth){
        //计算beginPeriod和endPeriod
        if (comDate.compareDate(dto.getBeginPeriod(), db.getBeginPeriod()) > 0) {
            dto.setBeginPeriod(db.getBeginPeriod());
        }

        if (comDate.compareDate(dto.getEndPeriod(), db.getEndPeriod()) < 0) {
            dto.setEndPeriod(db.getEndPeriod());
        }

        //将所有的时间汇总
        BigDecimal sum = getSumHour(db);

        IDtoAllocateHourInTheAcnt dtoInAcnt = (IDtoAllocateHourInTheAcnt)dto;
        dtoInAcnt.setActualHourInTheAcnt( dtoInAcnt.getActualHourInTheAcnt().add(sum));
        if( DtoWeeklyReport.STATUS_CONFIRMED.equals(db.getConfirmStatus()) ){
            dtoInAcnt.setActualHourConfirmedInTheAcnt( dtoInAcnt.getActualHourConfirmedInTheAcnt().add(sum));
        }
    }
}
