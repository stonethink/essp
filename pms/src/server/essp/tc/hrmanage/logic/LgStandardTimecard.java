package server.essp.tc.hrmanage.logic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import db.essp.tc.TcStandardTimecard;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.dto.IDto;
import java.util.Calendar;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.math.BigDecimal;
import javax.sql.RowSet;
import com.wits.util.comDate;
import server.essp.tc.common.LgTcCommon;
import itf.hr.LgHrUtilImpl;

public class LgStandardTimecard extends AbstractBusinessLogic {
    WorkCalendar workCalendar = WrokCalendarFactory.serverCreate();

    public void update(List tcList) {
        try {
            for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
                DtoWeeklyReportSumByHr dto = (DtoWeeklyReportSumByHr) iter.next();

                //界面上用户修改过的标准工时才会保存到数据库中
                if (dto.isModify()) {
                    Date resetBeginPeriod = LgTcCommon.resetBeginDate(dto.getBeginPeriod());
                    //将结束时间的设置为0
                    Date resetEndPeriod = LgTcCommon.resetBeginDate(dto.getEndPeriod());

                    List l = getByUserId(dto.getUserId(), resetBeginPeriod, resetEndPeriod);
                    Iterator it = l.iterator();
                    if (it.hasNext()) {
                        //update real work period and work hours
                         TcStandardTimecard standardTimecard = (TcStandardTimecard) it.next();
                         standardTimecard.setRealBeginPeriod(dto.getRealBeginPeriod());
                         standardTimecard.setRealEndPeriod(dto.getRealEndPeriod());
                         standardTimecard.setTimecardNum(dto.getStandardHour());
                         getDbAccessor().update(standardTimecard);
                    }else{
                        TcStandardTimecard standardTimecard = new TcStandardTimecard();
                        standardTimecard.setBeginPeriod(resetBeginPeriod);
                        standardTimecard.setEndPeriod(resetEndPeriod);
                        standardTimecard.setRealBeginPeriod(dto.getRealBeginPeriod());
                        standardTimecard.setRealEndPeriod(dto.getRealEndPeriod());
                        standardTimecard.setTimecardNum(dto.getStandardHour());
                        standardTimecard.setUserId(dto.getUserId());
                        getDbAccessor().save(standardTimecard);
                    }
                }
                dto.setOp(IDto.OP_NOCHANGE);
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when update the standard time card.", ex);
        }
    }
    /**
     * 获得用户周的标准工时,若数据库中没有记录时,则根据离职日计算,但不用保存到数据库中
     * modify by XR 2006/09/14
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return TcStandardTimecard
     */
    public TcStandardTimecard getByUserIdOnWeek(String userId, Date beginPeriod, Date endPeriod) {
            //将开始时间的时分秒都设为0
            Date resetBeginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
            //将结束时间的设置为0
            //System.out.println("Before reset end:"+endPeriod.getTime());
            Date resetEndPeriod = LgTcCommon.resetBeginDate(endPeriod);
            //System.out.println("After reset end:"+resetEndPeriod.getTime());
        List tcList = getByUserId(userId, resetBeginPeriod, resetEndPeriod);
        Iterator it = tcList.iterator();
        if (it.hasNext()) {
            return (TcStandardTimecard) it.next();
        } else {
            //add one
            TcStandardTimecard standardTimecard = new TcStandardTimecard();
            standardTimecard.setBeginPeriod(resetBeginPeriod);
            standardTimecard.setEndPeriod(resetEndPeriod);

            Date[] inOutDate = getInOutDate(userId);
            Calendar c = Calendar.getInstance();
            if (inOutDate != null) {
                if (inOutDate[0] != null) {
                    c.setTime(inOutDate[0]);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    Date inDate = c.getTime();

                    if( comDate.compareDate(inDate, resetBeginPeriod) > 0 ){
                        resetBeginPeriod = inDate;
                    }
                }

                if (inOutDate[1] != null) {
                    c.setTime(inOutDate[1]);
                    c.set(Calendar.HOUR_OF_DAY, 0);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);
                    Date outDate = c.getTime();

                    if( comDate.compareDate(outDate, resetEndPeriod) < 0 ){
                        resetEndPeriod = outDate;
                    }
                }
            }

            if( comDate.compareDate(resetBeginPeriod, resetEndPeriod) > 0 ){
                standardTimecard.setRealBeginPeriod(null);
                standardTimecard.setRealEndPeriod(null);
                standardTimecard.setTimecardNum(new BigDecimal(0));
            }else{
                standardTimecard.setRealBeginPeriod(resetBeginPeriod);
                standardTimecard.setRealEndPeriod(resetEndPeriod);
                standardTimecard.setTimecardNum(caculateStandardTc(resetBeginPeriod, resetEndPeriod));
            }

            standardTimecard.setUserId(userId);
            //romoved by XR 2006/09/14
//            try {
//                getDbAccessor().save(standardTimecard);
//
//            } catch (Exception ex) {
//                throw new BusinessException("E0000", "Error when get the standard timecard of user - " + userId, ex);
//            }
            return standardTimecard;
        }
    }

    public List getByUserId(String userId, Date beginPeriod, Date endPeriod) {
        String sql = "from TcStandardTimecard t where t.userId =:userId"
                     + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod";
        try {
            return getDbAccessor().getSession().createQuery(sql)
                    .setString("userId", userId)
                    .setDate("beginPeriod", beginPeriod)
                    .setDate("endPeriod", endPeriod)
                    .list();
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when get the standard timecard of user - " + userId, ex);
        }
    }

    private Date[] getInOutDate(String userId){
        String sql = "select hr.INDATE as inDate, hr.OUTDATE as outDate from essp_hr_employee_main_t hr,"+LgHrUtilImpl.LOGIN_TABLE+" login " +
                     "where hr.code=login.user_id and login.login_id='"+userId+"' ";
        try {
            RowSet rset = getDbAccessor().executeQuery(sql);
            if (rset.next()) {
                Date inDate = rset.getDate("inDate");
                Date outDate = rset.getDate("outDate");
                return new Date[] {
                        inDate, outDate};
            }else{
                return null;
            }
        } catch (Exception ex) {
            throw new BusinessException("E00000","Error when get the inDate and outDate of user - " + userId,ex);
        }
    }

    private BigDecimal caculateStandardTc(Date beginPeriod, Date endPeriod){
        if( beginPeriod != null && endPeriod != null ){
            Calendar c = Calendar.getInstance();
            c.setTime(beginPeriod);

            Calendar d = Calendar.getInstance();
            d.setTime(endPeriod);


            int numOfDay = workCalendar.getWorkDayNum(c, d);
            return new BigDecimal( numOfDay * 8 );
        }else{
            return new BigDecimal( 0 );
        }
    }

}
