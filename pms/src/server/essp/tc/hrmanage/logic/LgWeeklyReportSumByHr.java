
package server.essp.tc.hrmanage.logic;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;

import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import com.wits.util.comDate;
import db.essp.tc.TcByWorkerAccount;
import db.essp.tc.TcStandardTimecard;
import server.essp.tc.common.LgAcntActivity;
import server.essp.tc.common.LgWeeklyReportSum;
import server.framework.common.BusinessException;
import itf.account.AccountFactory;
import server.essp.tc.common.LgTcCommon;
import itf.hr.HrFactory;
import itf.hr.LgHrUtilImpl;
import server.essp.common.mail.ContentBean;
import server.essp.tc.mail.genmail.contbean.PMConfirmBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.pms.account.logic.LgAccount;

public class LgWeeklyReportSumByHr extends LgWeeklyReportSum {
    final static String HR_ACCOUNT_SCOPE_TABLE = "essp_hr_account_scope_t";
    /** 用户登录表*/
    public static final String LOGIN_TABLE = LgHrUtilImpl.LOGIN_TABLE;

    LgStandardTimecard lgStandardTimecard = new LgStandardTimecard();
    WorkCalendar workCalendar = WrokCalendarFactory.serverCreate();
    LgAcntActivity lgAcntActivity = new LgAcntActivity();
    public static final String vmFile = "mail/template/tc/HrWeeklyReport.htm";
    public static final String title = "Your weekly report has been unlocked!";
    /**
     * acntRid为一个hr性质的项目，它下面有些人
     * 适用：按“周”列出hr下的每个userId的weeklyReportSum信息，含每天的时间信息
     * “周”内，userId对每个参加的项目都会有一条weeklyReportSum信息，这里会把它们汇总成一条
     * @param orgId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByUserInHrOnWeek(Long acntRid, Date beginPeriod, Date endPeriod) {
        beginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
        endPeriod = LgTcCommon.resetEndDate(endPeriod);

        List result = getTcDtoByUserInHr(acntRid, beginPeriod, endPeriod);

        //取出除了周报之外的信息
        for (Iterator iterResult = result.iterator(); iterResult.hasNext(); ) {
            DtoWeeklyReportSumByHr dtoByHr = (DtoWeeklyReportSumByHr) iterResult.next();

            //lock flag
            Boolean lockFlag = lgWeeklyReportLock.getLock(dtoByHr.getUserId(),
                                                          beginPeriod, endPeriod);
            dtoByHr.setLocked(lockFlag.toString());

            //standard timecard
            getStandardTcOnWeek(dtoByHr, beginPeriod, endPeriod);

            //overtime
            lgOvertimeLeave.getOvertime(dtoByHr);

            //leave detail
            lgOvertimeLeave.getLeaveDetail(dtoByHr);

            //absent
            getAbsentHour(dtoByHr);

            //违规
            getViolat(dtoByHr);
        }

        return result;
    }

    /**
     * acntRid为一个hr性质的项目，它下面有些人
     * 适用：按“月”列出hr下的每个userId的weeklyReportSum信息，含每天的时间信息
     * “月”内，每周userId对每个参加的项目都会有一条weeklyReportSum信息，这里会把它们汇总成一条
     * @param orgId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByUserInHrOnMonth(Long acntRid, Date beginPeriod, Date endPeriod) {
        beginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
        endPeriod = LgTcCommon.resetEndDate(endPeriod);

        List result = getTcDtoByUserInHr(acntRid, beginPeriod, endPeriod);

        //取出除了周报之外的信息
        for (Iterator iterResult = result.iterator(); iterResult.hasNext(); ) {
            DtoWeeklyReportSumByHr dtoByHr = (DtoWeeklyReportSumByHr) iterResult.next();

            //standard timecard
            getStandardTcOnMonth(dtoByHr, beginPeriod, endPeriod);

            //overtime
            lgOvertimeLeave.getOvertime(dtoByHr);

            //leave detail
            lgOvertimeLeave.getLeaveDetail(dtoByHr);

            //absent
            getAbsentHour(dtoByHr);

            //违规
            getViolat(dtoByHr);
        }

        return result;
    }


    public List getTcDtoByUserInHr(Long acntRid, Date beginPeriod, Date endPeriod) {
        List allUserList = getUserListInHrAcnt(acntRid);
        return getTcDtoByUsers(beginPeriod, endPeriod,  allUserList);
    }

    public List getTcDtoByUsers(Date beginPeriod, Date endPeriod, List allUserList) {
        List result = new ArrayList();
        String userStr = getUserStrInHrAcnt(allUserList);
        log.info(userStr);
        List tcList = getByUsers(userStr, beginPeriod, endPeriod);

        Map tcMapByUser = new HashMap();
        List userListHasTc = new ArrayList();

        //取出allUserList这些人的周报信息
        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();

            String userId = tc.getUserId();
            DtoWeeklyReportSumByHr dtoByHr = (DtoWeeklyReportSumByHr) tcMapByUser.get(userId);
            if (dtoByHr == null) {
                dtoByHr = createDto(userId);
                dtoByHr.setBeginPeriod(beginPeriod);
                dtoByHr.setEndPeriod(endPeriod);
                dtoByHr.setActualHour(getSumHour(tc));
                if( DtoWeeklyReport.STATUS_CONFIRMED.equals( tc.getConfirmStatus() ) ){
                    dtoByHr.setActualHourConfirmed(getSumHour(tc));
                }else{
                    dtoByHr.setActualHourConfirmed(new BigDecimal(0));
                }

                userListHasTc.add(userId);
                tcMapByUser.put(userId, dtoByHr);
                result.add(dtoByHr);
            }else{
                dtoByHr.setActualHour( dtoByHr.getActualHour().add( getSumHour(tc)) );
                if( DtoWeeklyReport.STATUS_CONFIRMED.equals( tc.getConfirmStatus() ) ){

                    dtoByHr.setActualHourConfirmed( dtoByHr.getActualHourConfirmed().add( getSumHour(tc)) );
                }
            }
        }

        //有些人没有周报
        for (int i = 0; i < allUserList.size(); i++) {
            String userId = (String) allUserList.get(i);
            if( userListHasTc.contains(userId) == false){
                DtoWeeklyReportSumByHr dtoByHr = createDto(userId);
                dtoByHr.setBeginPeriod(beginPeriod);
                dtoByHr.setEndPeriod(endPeriod);
                dtoByHr.setActualHour(new BigDecimal(0));
                dtoByHr.setActualHourConfirmed(new BigDecimal(0));
                result.add(i, dtoByHr);
            }
        }
        return result;
    }

    /**Hr替userId修改周报时，这些周报的状态将会自动置为'confirmed'*/
    public void setConfirmStatusForUser(String userId, Date beginPeriod, Date endPeriod) {
        try {

            //update TC_WEEKLY_REPORT_SUM
            List paramList = new ArrayList();
            List paramTypeList = new ArrayList();
            paramList.add(userId);
            paramTypeList.add("string");

            paramList.add(new java.sql.Timestamp(LgTcCommon.resetBeginDate(beginPeriod).getTime()));
            paramTypeList.add("date");
            paramList.add(new java.sql.Timestamp(LgTcCommon.resetBeginDate(endPeriod).getTime()));
            paramTypeList.add("date");

            String sql = "update TC_WEEKLY_REPORT_SUM t set t.CONFIRM_STATUS = '" + DtoWeeklyReport.STATUS_CONFIRMED + "'"
                         + " where t.USER_ID = ?" + " and t.BEGIN_PERIOD = ? and t.END_PERIOD = ?"
                         ;
            log.info(sql);
            getDbAccessor().executeUpdate(sql, paramList, paramTypeList);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E00000", "Error when set the confirm status.", ex);
        }

    }

    protected DtoWeeklyReportSumByHr createDto(String userId){
        DtoWeeklyReportSumByHr dtoByHr = new DtoWeeklyReportSumByHr();
        String chineseName = HrFactory.create().getChineseName(userId);
        dtoByHr.setUserId(userId);
        dtoByHr.setChineseName(chineseName);
        return dtoByHr;
    }


    /**acntRid为hr性质的项目，这里列出其下所属的人*/
    private List getUserListInHrAcnt(Long acntRid) {
        List userList = AccountFactory.create().getUserListInHrAcnt(acntRid);
//        String sql = "select login.loginname as loginid " +
//                     "from " + LOGIN_TABLE + " login " +
//                     "left join " + HR_ACCOUNT_SCOPE_TABLE + " acntScope on login.user_id = acntScope.SCOPE_CODE " +
//                     "where acntScope.ACCOUNT_ID = " + acntRid.longValue() + " order by lower(loginid)";
//
//        try {
//            RowSet rset = getDbAccessor().executeQuery(sql);
//            while (rset.next()) {
//                String loginId = rset.getString("loginid");
//                userList.add(loginId);
//            }
//        } catch (Exception ex) {
//            throw new BusinessException("E000", "Error when get user in account - " + acntRid, ex);
//        }

        return userList;
    }

    private String getUserStrInHrAcnt(List userList) {
        StringBuffer userSb = new StringBuffer("''");

        Iterator rset = userList.iterator();
        while (rset.hasNext()) {
            String loginId = (String) rset.next();
            userSb.append(",");
            userSb.append("'");
            userSb.append(loginId);
            userSb.append("'");
        }

        if (userSb.length() > 0) {
            return userSb.toString();
        } else {
            return null;
        }
    }

    private void getStandardTcOnMonth(DtoWeeklyReportSumByHr dtoByHr, Date beginOfMonth, Date endOfMonth) {
         Calendar bc = Calendar.getInstance();
         Calendar ec = Calendar.getInstance();
         bc.setTime(beginOfMonth);
         ec.setTime(endOfMonth);

//         List weekList = workCalendar.getBEWeekListMax(bc, ec);
         List weekList = workCalendar.getBEWeekListMin(bc, ec);
         for (Iterator iter = weekList.iterator(); iter.hasNext(); ) {
             Calendar[] weekPeriod = (Calendar[]) iter.next();
             if( weekPeriod != null ){
                 getStandardTcOnWeek(dtoByHr, weekPeriod[0].getTime(), weekPeriod[1].getTime());
             }
         }
    }

    //note: 从数据库中查出标准工时,然后将工时“累加”到dtoByHr中
    private void getStandardTcOnWeek(DtoWeeklyReportSumByHr dtoByHr, Date beginOfWeek, Date endOfWeek) {
        //standard timecard
        String userId = dtoByHr.getUserId();
        TcStandardTimecard db = (TcStandardTimecard) lgStandardTimecard.getByUserIdOnWeek(
                                userId, beginOfWeek, endOfWeek);

        //real begin period
        if (dtoByHr.getRealBeginPeriod() != null) {
            if (db.getRealBeginPeriod() != null) {
                if (comDate.compareDate(dtoByHr.getRealBeginPeriod(), db.getRealBeginPeriod()) > 0) {
                    dtoByHr.setRealBeginPeriod(db.getRealBeginPeriod());
                }
            }
        } else {
            if (db.getRealBeginPeriod() != null) {
                dtoByHr.setRealBeginPeriod(db.getRealBeginPeriod());
            }
        }

        //real end period
        if (dtoByHr.getRealEndPeriod() != null) {
            if (db.getRealEndPeriod() != null) {
                if (comDate.compareDate(dtoByHr.getRealEndPeriod(), db.getRealEndPeriod()) < 0) {
                    dtoByHr.setRealEndPeriod(db.getRealEndPeriod());
                }
            }
        } else {
            if (db.getRealEndPeriod() != null) {
                dtoByHr.setRealEndPeriod(db.getRealEndPeriod());
            }
        }

        //standard hour
        if (dtoByHr.getStandardHour() != null) {
            if (db.getTimecardNum() != null) {
                BigDecimal sum = dtoByHr.getStandardHour().add(db.getTimecardNum());
                dtoByHr.setStandardHour(sum);
            }
        } else {
            if (db.getTimecardNum() != null) {
                dtoByHr.setStandardHour(db.getTimecardNum());
            }
        }
    }


    private void getAbsentHour(DtoWeeklyReportSumByHr dtoByHr){
        String userId = dtoByHr.getUserId();
        Date beginPeriod = dtoByHr.getBeginPeriod();
        Date endPeriod = dtoByHr.getEndPeriod();
        Calendar c = Calendar.getInstance();
        c.setTime(endPeriod);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        endPeriod = c.getTime();

        List paramList = new ArrayList();
        List paramTypeList = new ArrayList();
        paramList.add(new java.sql.Timestamp(beginPeriod.getTime()));
        paramTypeList.add("date");
        paramList.add(new java.sql.Timestamp(endPeriod.getTime()));
        paramTypeList.add("date");

        try {
            String sql = ("select sum(t.TOTAL_HOURS) as sumHour" +
                          " from TC_NONATTEN  t where t.LOGINNAME ='" + userId + "'" +
                          " and t.TIMEFROM >=? and t.TIMETO <=?");
            RowSet rset = getDbAccessor().executeQuery(sql, paramList, paramTypeList);
            if (rset.next()) {
                double sumHour = rset.getDouble("sumHour");
                dtoByHr.setAbsent(new BigDecimal(sumHour));
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000","Error when get the absent hour of user - " + userId, ex);
        }
    }

    private void getViolat(DtoWeeklyReportSumByHr dtoByHr){
        String userId = dtoByHr.getUserId();
        Date beginPeriod = dtoByHr.getBeginPeriod();
        Date endPeriod = dtoByHr.getEndPeriod();
        Calendar c = Calendar.getInstance();
        c.setTime(endPeriod);
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        endPeriod = c.getTime();

        List paramList = new ArrayList();
        List paramTypeList = new ArrayList();
        paramList.add(new java.sql.Timestamp(beginPeriod.getTime()));
        paramTypeList.add("date");
        paramList.add(new java.sql.Timestamp(endPeriod.getTime()));
        paramTypeList.add("date");

        try {
            String sql = ("select count(*) as num" +
                          " from TC_ATTENDANCE  t where t.LOGINNAME ='" + userId + "'" +
                          " and t.ATTEN_DATE  >=? and t.ATTEN_DATE  <=?");
            RowSet rset = getDbAccessor().executeQuery(sql, paramList, paramTypeList);
            if (rset.next()) {
                int num = rset.getInt("num");
                dtoByHr.setViolat(new Long(num));
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000","Error when get the Violat Number of user - " + userId, ex);
        }
    }




    /**
     * worker lock the weeklyreport
     * change "Locked" --> "UnLocked"
     */
    public void updateConfirmStatusByHr(String userId, Date beginPeriod, Date endPeriod) {

        List tcList = getByUser(userId, beginPeriod, endPeriod);
        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();
            if (DtoWeeklyReport.STATUS_CONFIRMED.equals(tc.getConfirmStatus())) {
                //从"Confirmed"状态转为其他状态，要更新account/activity的时间
                lgAcntActivity.updateByUserAcnt(tc.getUserId(), tc.getAcntRid()
                                                , tc.getBeginPeriod(), tc.getEndPeriod(), lgAcntActivity.SUBTRACT);
            }
        }

        String sqlUpdate = "update TC_WEEKLY_REPORT_SUM t"
                           + " set t.CONFIRM_STATUS='" + DtoWeeklyReport.STATUS_UNLOCK + "'"
                           + " where t.USER_ID ='" + userId + "'"
                           + " and t.BEGIN_PERIOD =? AND t.END_PERIOD =?"
                           ;
        List paramList = new ArrayList();
        List paramTypeList = new ArrayList();
        paramTypeList.add("date");
        paramList.add(new Timestamp(beginPeriod.getTime()));
        paramTypeList.add("date");
        paramList.add(new Timestamp(endPeriod.getTime()));

        try {
            getDbAccessor().executeUpdate(sqlUpdate, paramList, paramTypeList);
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when update confirm status of weekly report summary.", ex);
        }
    }

    /**HR发送邮件  */
    /**
     * 当HR对某个用户解锁时,需要发送邮件通知该用户修改自己的周报以及抄送一份给该用户的PM
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @param loginName String
     */
    public void send(String userId, Date beginPeriod, Date endPeriod,String loginName){
        List tcList = getByUser(userId, beginPeriod, endPeriod);
        for (Iterator iter = tcList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();
            //LgAccount lgAccount = new LgAccount();
            //String manager =(String)lgAccount.load(tc.getAcntRid()).getManager();
            if(tc!=null){
                SendHastenMail shm = new SendHastenMail();
                HashMap hm = new HashMap();
                LgHrUtilImpl ihui = (LgHrUtilImpl) HrFactory.create();
                ArrayList al = new ArrayList();
                ContentBean cb = new ContentBean();
                String name = ihui.getChineseName(tc.getUserId());
                if(name != null) {
                    cb.setUser(name);
                } else {
                    cb.setUser(tc.getUserId());
                }
                cb.setEmail(ihui.getUserEmail(tc.getUserId()));
                //cb.setCcAddress(ihui.getUserEmail(manager));
                PMConfirmBean pm = new PMConfirmBean();
                pm.setSubmiter(loginName);
                pm.setBeginDate(comDate.dateToString(tc.getBeginPeriod(),"yyyy-MM-dd"));
                pm.setEndDate(comDate.dateToString(tc.getEndPeriod(),"yyyy-MM-dd"));
                //pm.setAccountInfo(tc.getAccount().getId()+"-"+tc.getAccount().getName());
                al.add(pm);
                cb.setMailcontent(al);
                hm.put(tc.getUserId(),cb);
                shm.sendHastenMail(vmFile, title, hm);
            }
        }
    }

}
