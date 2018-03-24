package server.essp.tc.common;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.wits.util.comDate;
import db.essp.pms.ActivityPK;
import db.essp.pms.ActivityWorker;
import db.essp.pms.LaborResource;
import db.essp.tc.TcWeeklyReport;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import server.framework.logic.AbstractBusinessLogic;
import db.essp.pms.Activity;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import itf.hr.IHrUtil;
import c2s.essp.common.user.DtoUser;
import itf.hr.HrFactory;
import db.essp.pms.Account;

public class LgAcntActivity extends AbstractBusinessLogic{
    public final static int ADD = 0;
    public final static int SUBTRACT = 1;

    //改变周报的状态时，调用本函数更新人在项目/活动中的时间
    public void updateByUserAcnt(String userId ,Long acntRid, Date beginOfWeek, Date endOfWeek, int op){
        Date[] ds = LgTcCommon.resetBeginDate(beginOfWeek, endOfWeek);
        beginOfWeek = ds[0];
        endOfWeek = ds[1];

        Map hourInActivities = new HashMap();

        AccountLabor accountLabor = new AccountLabor(userId, acntRid, new BigDecimal(0), null, null);

        String sql = "select t from TcWeeklyReport t where t.acntRid =:acntRid and t.userId =:userId"
                     + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod"
                     ;
        try {
            Iterator iter = getDbAccessor().getSession().createQuery(sql)
                            .setLong("acntRid", acntRid.longValue())
                            .setString("userId", userId)
                            .setDate("beginPeriod", beginOfWeek)
                            .setDate("endPeriod", endOfWeek)
                            .iterate();
            while(iter.hasNext()) {
                TcWeeklyReport db = (TcWeeklyReport) iter.next();
                BigDecimal sumHour = getSumHour(db);
                Date[] actualPeriod = getActualPeriod(db);
                Date actualBeginOfWeek = actualPeriod[0];
                Date actualEndOfWeek = actualPeriod[1];

                //account
                accountLabor.setWorkhour(accountLabor.getWorkhour().add(sumHour));
                if( actualBeginOfWeek != null ){
                    if( accountLabor.getBeginTime() == null
                       || comDate.compareDate(actualBeginOfWeek, accountLabor.getBeginTime()) < 0 ){
                        accountLabor.setBeginTime(actualBeginOfWeek);
                    }
                }
                if( actualEndOfWeek != null ){
                    if( accountLabor.getEndTime() == null
                       || comDate.compareDate(actualEndOfWeek, accountLabor.getEndTime()) > 0 ){
                        accountLabor.setEndTime(actualEndOfWeek);
                    }
                }

                //activity
                Long activityRid = db.getActivityRid();
                ActivityPK pk = new ActivityPK(acntRid, activityRid);
                if (activityRid != null) {
                    ActivityLabor activityLabor = (ActivityLabor) hourInActivities.get(pk);
                    if (activityLabor == null) {
                        activityLabor = new ActivityLabor(userId, acntRid, activityRid, new BigDecimal(0)
                                                          , null, null);
                        hourInActivities.put(pk, activityLabor);
                    }
                    activityLabor.setWorkhour(activityLabor.getWorkhour().add(sumHour));
                    if (actualBeginOfWeek != null) {
                        if (activityLabor.getBeginTime() == null
                            || comDate.compareDate(actualBeginOfWeek, activityLabor.getBeginTime()) < 0) {
                            activityLabor.setBeginTime(actualBeginOfWeek);
                        }
                    }
                    if (actualEndOfWeek != null) {
                        if (activityLabor.getEndTime() == null
                            || comDate.compareDate(actualEndOfWeek, activityLabor.getEndTime()) > 0) {
                            activityLabor.setEndTime(actualEndOfWeek);
                        }
                    }
                }
            }

            //更新account的时间
            updateHourOfUserInAcnt(accountLabor, op);

            //更新activity的时间
            for (Iterator iterActivity = hourInActivities.values().iterator(); iterActivity.hasNext(); ) {
                ActivityLabor activityLabor = (ActivityLabor) iterActivity.next();
                updateHourOfUserInActivity(activityLabor, op);
            }
        } catch (Exception ex) {

            throw new BusinessException("E000","Error when update actual work time of worker in the account.", ex );
        }

    }

    private void updateHourOfUserInAcnt(AccountLabor accountLabor, int op)throws Exception{
        String userId = accountLabor.getUserId();
        Long acntRid = accountLabor.getAcntRid();
        Date beginTime = accountLabor.getBeginTime();
        Date endTime = accountLabor.getEndTime();
        BigDecimal workHour = accountLabor.getWorkhour();
        //add by xr 2006/03/17
        //若workHour为0,则直接返回
        if(workHour.compareTo(new BigDecimal(0)) == 0){
            return;
        }
        String sql = "from LaborResource t where t.loginId =:userId and t.account.rid =:acntRid";
        Iterator it = getDbAccessor().getSession().createQuery(sql)
                      .setString("userId", userId)
                      .setLong("acntRid", acntRid.longValue())
                      .iterate();
        if (it.hasNext()) {
            LaborResource labor = (LaborResource) it.next();

            //work time
            BigDecimal actualHour = getHour( labor.getActualWorkTime());
            if( op == SUBTRACT){
                actualHour = actualHour.subtract(workHour);
            }else if( op == ADD ){
                actualHour = actualHour.add(workHour);

                //start - finish
                if (labor.getActualStart() == null
                    || (comDate.compareDate(labor.getActualStart(), beginTime) > 0)) {
                    labor.setActualStart(beginTime);
                }
                if (labor.getActualFinish() == null
                    || (comDate.compareDate(labor.getActualFinish(), endTime) < 0)) {
                    labor.setActualFinish(endTime);
                }
            }else{
                throw new RuntimeException("Error argument.");
            }
            labor.setActualWorkTime(new Double( actualHour.doubleValue() ));

            getDbAccessor().update(labor);
        }else{
            if( op == ADD ){
                //新增一个
                LaborResource labor = createLaborResource(acntRid, userId);
                if( labor != null ){
                    labor.setActualStart(beginTime);
                    labor.setActualFinish(endTime);
                    labor.setActualWorkTime(new Double(workHour.longValue()));
                    labor.setPlanStart(beginTime);
                    labor.setPlanFinish(endTime);
                    labor.setPlanWorkTime(new Double(workHour.longValue()));

                    getDbAccessor().save(labor);
                }
            }
        }
    }

    private void updateHourOfUserInActivity(ActivityLabor activityLabor, int op) throws Exception {
        String userId = activityLabor.getUserId();
        Long activityRid = activityLabor.getActivityRid();
        Long acntRid = activityLabor.getAcntRid();
        Date beginTime = activityLabor.getBeginTime();
        Date endTime = activityLabor.getEndTime();
        BigDecimal workHour = activityLabor.getWorkhour();
        //add by xr 2006/03/17
        //若workHour为0,则直接返回
        if(workHour.compareTo(new BigDecimal(0)) == 0){
            return;
        }

        String sql = "from ActivityWorker t where t.loginId =:userId and t.activity.pk.activityId =:activityRid and t.activity.pk.acntRid =:acntRid";
        Iterator it = getDbAccessor().getSession().createQuery(sql)
                      .setString("userId", userId)
                      .setLong("activityRid", activityRid.longValue())
                      .setLong("acntRid", acntRid.longValue())
                      .iterate();
        if (it.hasNext()) {
            ActivityWorker labor = (ActivityWorker) it.next();

            //work time
            BigDecimal actualHour = getHour(labor.getActualWorkTime());
            if (op == SUBTRACT) {
                actualHour = actualHour.subtract(workHour);
            } else if (op == ADD) {
                actualHour = actualHour.add(workHour);
            } else {
                throw new RuntimeException("Error argument.");
            }
            labor.setActualWorkTime(new Long(actualHour.longValue()));

            //start - finish
            if (labor.getActualStart() == null
                || (comDate.compareDate(labor.getActualStart(), beginTime) > 0)) {
                labor.setActualStart(beginTime);
            }
            if (labor.getActualFinish() == null
                || (comDate.compareDate(labor.getActualFinish(), endTime) < 0)) {
                labor.setActualFinish(endTime);
            }

            getDbAccessor().update(labor);
        } else {
            if (op == ADD) {
                //新增一个
                ActivityWorker labor = createActivityWorker(acntRid, activityRid, userId);
                if (labor != null) {
                    labor.setActualStart(beginTime);
                    labor.setActualFinish(endTime);
                    labor.setActualWorkTime(new Long(workHour.longValue()));
                    labor.setPlanStart(beginTime);
                    labor.setPlanFinish(endTime);
                    labor.setPlanWorkTime(new Long(workHour.longValue()));

                    getDbAccessor().save(labor);
                }
            }
        }
    }

    private ActivityWorker createActivityWorker(Long acntRid, Long activityRid, String userId) {
        LgAccountLaborRes logic = new LgAccountLaborRes();
        DtoAcntLoaborRes dto = logic.findResourceDto(acntRid, userId);

        if (dto != null) {
            ActivityWorker labor = new ActivityWorker();
            labor.setLoginId(userId);

            ActivityPK pk = new ActivityPK();
            pk.setAcntRid(acntRid);
            pk.setActivityId(activityRid);
            Activity activity = new Activity();
            activity.setPk(pk);
            labor.setActivity(activity);

            labor.setEmpName(dto.getEmpName());
            labor.setJobcodeId(dto.getJobcodeId());
            labor.setRoleName(dto.getRoleName());

            return labor;
        }else{
           return null;
       }
    }

    private LaborResource createLaborResource(Long acntRid, String userId){
       IHrUtil hrUtil = HrFactory.create();
       DtoUser user = hrUtil.findResouce(userId);

       if (user != null) {
           LaborResource labor = new LaborResource();
           labor.setLoginId(userId);

           Account acnt = new Account();
           acnt.setRid(acntRid);
           labor.setAccount(acnt);

           labor.setLoginidStatus(DtoAcntLoaborRes.LOGINID_STATUS_IN);
           labor.setResStatus(DtoAcntLoaborRes.RESOURCE_STATUS_IN);

           labor.setEmpName(user.getUserName());
           labor.setJobcodeId(user.getJobCode());
           labor.setLoginId(userId);
           return labor;
       }else{
           return null;
       }
    }

    private BigDecimal getHour(Double hour){
        if( hour == null ){
            return new BigDecimal(0);
        }else{
            return new BigDecimal(hour.doubleValue());
        }
    }

    private BigDecimal getHour(Long hour){
        if( hour == null ){
            return new BigDecimal(0);
        }else{
            return new BigDecimal(hour.doubleValue());
        }
    }

    private BigDecimal getSumHour(TcWeeklyReport db){
        BigDecimal sum = new BigDecimal(0);
        BigDecimal hourArray[] = new BigDecimal[] {
                                 db.getSatHour(), db.getSunHour(), db.getMonHour()
                                 , db.getTueHour(), db.getWedHour(), db.getThuHour(), db.getFriHour()};
        for (int i = 0; i < hourArray.length; i++) {
            if( hourArray[i] != null ){
                sum = sum.add(hourArray[i]);
            }
        }

        return sum;
    }

    private Date[] getActualPeriod(TcWeeklyReport db){
        Date begin = null;
        Date end = null;

        Date beginOfWeek = db.getBeginPeriod();
        Calendar day = Calendar.getInstance();
        day.setTime(beginOfWeek);
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        int dayOfYear = day.get(Calendar.DAY_OF_YEAR);
        BigDecimal hourArray[] = new BigDecimal[] {
                                 db.getSatHour(), db.getSunHour(), db.getMonHour()
                                 , db.getTueHour(), db.getWedHour(), db.getThuHour(), db.getFriHour()};

        for (int i = 0; i < hourArray.length; i++) {
            if( hourArray[i] != null ){
                //edit by xr
//                String hourStr = hourArray[i].setScale(1).toString();
//                if( "0".equals(hourStr) || "0.0".equals(hourStr) ){
//                    continue;
//                }
                if(hourArray[i] == null || hourArray[i].doubleValue() == 0D)
                    continue;
                day.set(Calendar.DAY_OF_YEAR, dayOfYear+i);
                if( begin == null ){
                    begin = day.getTime();
                }
                end = day.getTime();
            }
        }

        return new Date[]{begin, end};
    }

    private static class AccountLabor {
        String userId;
        Long acntRid;
        BigDecimal workhour;
        Date beginTime;
        Date endTime;
        public AccountLabor(String userId, Long acntRid, BigDecimal workhour
                            , Date beginTime, Date endTime) {
            this.userId = userId;
            this.acntRid = acntRid;
            this.workhour = workhour;
            this.beginTime = beginTime;
            this.endTime = endTime;
        }

        public Long getAcntRid() {
            return acntRid;
        }

        public Date getBeginTime() {
            return beginTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public String getUserId() {
            return userId;
        }

        public BigDecimal getWorkhour() {
            return workhour;
        }

        public void setWorkhour(BigDecimal workhour) {
            this.workhour = workhour;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public void setBeginTime(Date beginTime) {
            this.beginTime = beginTime;
        }

        public void setAcntRid(Long acntRid) {
            this.acntRid = acntRid;
        }
    }


    private static class ActivityLabor extends AccountLabor {
        Long activityRid;

        public ActivityLabor(String userId, Long acntRid, Long activityRid, BigDecimal workhour
                             , Date beginTime, Date endTime) {
            super(userId, acntRid, workhour, beginTime, endTime);
            this.activityRid = activityRid;
        }

        public Long getActivityRid() {
            return activityRid;
        }

        public void setActivityRid(Long activityRid) {
            this.activityRid = activityRid;
        }
    }


    public static void main(String args[]) {
        try {
            LgAcntActivity logic = new LgAcntActivity();
            HBComAccess hbComAccess = new HBComAccess();
            hbComAccess.newTx();
            logic.setDbAccessor(hbComAccess);

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            c.set(2000,12,3);
            Date b = c.getTime();
            c.set(2009,12,9);
            Date e = c.getTime();

            logic.updateByUserAcnt("labor", new Long(1), b, e, LgAcntActivity.ADD);
            hbComAccess.endTxCommit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
