package server.essp.attendance.overtime.logic;

import java.util.*;

import c2s.dto.*;
import c2s.essp.common.calendar.*;
import com.wits.util.*;
import db.essp.attendance.*;
import itf.account.*;
import net.sf.hibernate.*;
import c2s.essp.attendance.Constant;
import server.essp.attendance.overtime.form.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.logic.*;
import server.framework.common.*;
import server.workflow.wfengine.WfProcess;
import server.workflow.wfengine.WfEngine;
import c2s.essp.common.user.DtoUser;
import server.workflow.wfengine.*;
import c2s.essp.common.account.IDtoAccount;
import server.essp.attendance.common.LgTimeCardFreeze;
import java.sql.ResultSet;


public class LgOverTime extends AbstractESSPLogic {
    private WorkTime wt;
    /**
     * 根据起止日期和时间及isEachDay计算每天的加班时间
     * 1.列出所有的天
     * 2.if isEachDay,遍历每天,计算该天TimeFrom到TimeTo间加班时间,
     * 3 if isNotEachDay,
     *      if 仅有一天,该天加班时间为TimeFrom到TimeTo间加班时间
     *      else 两天或大于两天,
     *           第一天加班时间为TimeFrom到24:00:00间加班时间,
     *           中间每天为00:00:00到24:00:00间加班时间
     *           最后一天加班时间为00:00:00到TimeTo间加班时间
     * @param form AfOverTimeAppCaculate
     * @return VbOverTimeDetail
     */
    public VbOverTimeDetail caculateOverTime(String dateFrom,String dateTo,
                                             String timeFrom,String timeTo,boolean isEachDay){
        AfOverTimeAppCaculate form = new AfOverTimeAppCaculate();
        form.setDateFrom(dateFrom);
        form.setDateTo(dateTo);
        form.setTimeFrom(timeFrom);
        form.setTimeTo(timeTo);
        form.setIsEachDay(isEachDay + "");
        return cacualteOverTime(form);
    }
    public VbOverTimeDetail cacualteOverTime(AfOverTimeAppCaculate form) {
        VbOverTimeDetail webVo = new VbOverTimeDetail();
        float totalHours = 0.0f;
        List perDays = new ArrayList();//每天加班时间

        String dateFromStr = form.getDateFrom();
        String dateToStr = form.getDateTo();
        if (dateFromStr == null || dateToStr == null) {
            throw new BusinessException("TC_OVER_0001","date period can not be null");
        }
        Date dateFrom = comDate.toDate(dateFromStr);
        Date dateTo = comDate.toDate(dateToStr);
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTime(dateFrom);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dateTo);
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        List days = wc.getDays(calFrom, calEnd); //所有的天
        Boolean isEachDay = new Boolean(form.getIsEachDay());
        float perDayWorkHour = 0.0f;
        if (isEachDay.booleanValue()) { //is Each Day
            for (int i = 0; i < days.size(); i++) {
                Calendar day = (Calendar) days.get(i);
                boolean isWorkDay = wc.isWorkDay(day);
                perDayWorkHour = getOverTimeHours(form.getTimeFrom(), form.getTimeTo(), isWorkDay);
                VbOverTimePerDay overTimePerday = new VbOverTimePerDay();
                overTimePerday.setOvertimeDay(day.getTime());
                overTimePerday.setHours(perDayWorkHour + "");
                perDays.add(overTimePerday);
                totalHours += perDayWorkHour;
            }
        } else { // is not each day
            if(days.size() == 1){//只有一天
                Calendar day = (Calendar) days.get(0);
                boolean isWorkDay = wc.isWorkDay(day);
                perDayWorkHour = getOverTimeHours(form.getTimeFrom(), form.getTimeTo(), isWorkDay);
                VbOverTimePerDay overTimePerday = new VbOverTimePerDay();
                overTimePerday.setOvertimeDay(day.getTime());
                overTimePerday.setHours(perDayWorkHour + "");
                perDays.add(overTimePerday);
                totalHours += perDayWorkHour;
            }else{//两天或两天以上
                Calendar firstDay = (Calendar) days.get(0);//第一天
                boolean isWorkDay = wc.isWorkDay(firstDay);
                perDayWorkHour = getOverTimeHours(form.getTimeFrom(),Constant.DAY_END_TIME, isWorkDay);
                VbOverTimePerDay overTimeFirstDay = new VbOverTimePerDay();
                overTimeFirstDay.setOvertimeDay(firstDay.getTime());
                overTimeFirstDay.setHours(perDayWorkHour + "");
                perDays.add(overTimeFirstDay);
                totalHours += perDayWorkHour;

                Calendar lastDay = (Calendar) days.get(days.size() - 1);//最后一天
                isWorkDay = wc.isWorkDay(lastDay);
                perDayWorkHour = getOverTimeHours(Constant.DAY_BEGIN_TIME,form.getTimeTo(), isWorkDay);
                VbOverTimePerDay overTimeLastDay = new VbOverTimePerDay();
                overTimeLastDay.setOvertimeDay(lastDay.getTime());
                overTimeLastDay.setHours(perDayWorkHour + "");
                totalHours += perDayWorkHour;

                days.remove(firstDay);
                days.remove(lastDay);
                Iterator it = days.iterator();//遍历中间的天
                while(it.hasNext()){
                    Calendar day = (Calendar) it.next();
                    isWorkDay = wc.isWorkDay(day);
                    perDayWorkHour = getOverTimeHours(Constant.DAY_BEGIN_TIME,Constant.DAY_END_TIME, isWorkDay);
                    VbOverTimePerDay overTimePerDay = new VbOverTimePerDay();
                    overTimePerDay.setOvertimeDay(day.getTime());
                    overTimePerDay.setHours(perDayWorkHour + "");
                    perDays.add(overTimePerDay);
                    totalHours += perDayWorkHour;
                }

                perDays.add(overTimeLastDay);
            }
        }
        webVo.setPerDay(perDays);
        webVo.setTotalHours(totalHours + "");
        webVo.setIsEachDay(isEachDay.toString());

        return webVo;
    }
    /**
     * 新增加班申请记录
     * 1.判断申请的区间内和项目Rid是否已存在流程未完成的记录
     * 2.判断申请区间是否已被HR锁住了
     * 2.加班记录的实际值设置为申请值,状态为申请中ing
     * 3.增加加班工作流实例,并启动该流程
     * @param form AfOverTimeApp
     * @return TcOvertime
     */
    public TcOvertime addOverTimeApp(AfOverTimeApp form){
        if(  form == null || form.getAcntRid() == null || form.getLoginId() == null
          || form.getDateFrom() == null || form.getDateTo() == null
          || form.getTimeFrom() == null || form.getTimeTo() == null )
            throw new BusinessException("TC_OVER_0002", "can not add null overtime");
        Date datetimeStart = Constant.parseDate(form.getDateFrom(),form.getTimeFrom());
        Date datetimeFinish = Constant.parseDate(form.getDateTo(),form.getTimeTo());
        //将开始时间秒和微秒设置为最大,结束时间设置为最小
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetimeStart);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,999);
        datetimeStart = cal.getTime();
        cal.setTime(datetimeFinish);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        datetimeFinish = cal.getTime();
        checkPeriod(datetimeStart,datetimeFinish,form.getLoginId(),new Long(form.getAcntRid()));
        TcOvertime overTime = new TcOvertime();
        DtoUtil.copyBeanToBean(overTime,form);
        overTime.setAcntRid(new Long(form.getAcntRid()));
        overTime.setDatetimeStart(datetimeStart);
        overTime.setDatetimeFinish(datetimeFinish);
        Boolean isEachDay = new Boolean(form.getIsEachDay());
        overTime.setIsEachDay(isEachDay);
        //加班记录实际时间为当前申请时间
        overTime.setActualDatetimeStart(overTime.getDatetimeStart());
        overTime.setActualDatetimeFinish(overTime.getDatetimeFinish());
        overTime.setActualIsEachDay(overTime.getIsEachDay());
        overTime.setActualTotalHours(overTime.getTotalHours());
        overTime.setStatus(Constant.STATUS_APPLYING);
        //创建并启动加班流程
        DtoUser user = this.getUser();
        WfProcess prcoess = null;
        try {
            WfEngine engine = new WfEngine();
            prcoess = engine.createProcessInstance(Constant.WF_PACKAGE_ID,
                    Constant.WF_OVERTIME_PROCESS_ID,
                    user.getUserLoginId());
            String wfDesc = getWFDescription(overTime);
            prcoess.getDtoWorkingProcess().setDescription(wfDesc);
            prcoess.getDtoWorkingProcess().setCurrActivityDescription(wfDesc);
            prcoess.start();
            overTime.setWkId(prcoess.getDtoWorkingProcess().getRid());
            this.getDbAccessor().save(overTime);
            prcoess.finishActivity();
        } catch (WfException ex1) {
            throw new BusinessException("TC_OVER_0003", "error create new overtime work flow!",ex1);
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0003", "error add overtime!",ex);
        }
        return overTime;
    }
    //合并生成加班工作流Description
    private String getWFDescription(TcOvertime overTime){
        StringBuffer buf = new StringBuffer();
        String acntName = AccountFactory.create().
                          getAccountByRID(overTime.getAcntRid()).getName();
        buf.append(acntName);
        buf.append(" ( ");
        buf.append(comDate.dateToString(overTime.getDatetimeStart(),Constant.DATE_TIME_FORMAT));
        buf.append(" ~ ");
        buf.append(comDate.dateToString(overTime.getDatetimeFinish(),Constant.DATE_TIME_FORMAT));
        buf.append(" , ");
        buf.append(overTime.getTotalHours());
        buf.append(" hours )");
        return buf.toString();
    }
    /**
     * 判断申请的区间内是否已存在
     * 判断申请区间是否已被HR锁住了
     */
    private void checkPeriod(Date datetimeStart,Date datetimeFinish,String loginId,Long acntRid) {
        LgTimeCardFreeze lgTCFreeze = new LgTimeCardFreeze();
        boolean isFrozen = lgTCFreeze.isFrozen(loginId,datetimeStart,datetimeFinish);
        if(isFrozen)
            throw new BusinessException("TC_OVER_0013","The period has been frozen by HR!Please ask HR for help!");
        List l = listPeriodStatusOverTime(loginId, datetimeStart, datetimeFinish
                                          ,new String[]{Constant.STATUS_APPLYING,
                                          Constant.STATUS_FINISHED,
                                          Constant.STATUS_REVIEWING});
        if(l == null || l.size() ==0)
            return;
        for(int i = 0;i < l.size() ;i ++){
            TcOvertime overTime = (TcOvertime) l.get(i);
            if(overTime.getAcntRid().longValue() == acntRid.longValue()){
                throw new BusinessException("TC_OVER_0013","The period has been applied!");
            }
        }

    }

    /**
     * 新增加班申请每天的记录
     * @param detail TcOvertimeDetail
     */
    public void addOverTimeDetail(TcOvertimeDetail detail){
        if(detail == null || detail.getOvertimeDay() == null || detail.getTcOvertime() == null)
            throw new BusinessException("TC_OVER_0003", "can not add null overtime detail!");
        try {
            this.getDbAccessor().save(detail);
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0004", "error add overtime detail!",ex);
        }
    }
    /**
     * 根据工作流实例Rid查找加班记录
     * @param wkRid Long
     * @return VbOverTime
     */
    public VbOverTime getOverTimeVBByWkRid(Long wkRid){
        TcOvertime overTime = getOverTimeByWkRid(wkRid);
        VbOverTime webVo = overTime2WebVo(overTime);
        return webVo;
    }
    public TcOvertime getOverTimeByWkRid(Long wkRid){
        try {
           Session s = this.getDbAccessor().getSession();
           TcOvertime overTime = (TcOvertime)
                                 s.createQuery("from TcOvertime overtime " +
                                               "where overtime.wkId=:wkRid")
                                 .setParameter("wkRid",wkRid)
                                 .uniqueResult();
           return overTime;
       } catch (Exception ex) {
           throw new BusinessException("TC_OVER_1000","error get over time by work flow!",ex);
       }

    }
    /**
     * 获得加班记录
     */
    public VbOverTime getOverTimeByRid(Long rid){
            TcOvertime overTime =  getOverTime(rid);
            VbOverTime webVo = overTime2WebVo(overTime);
            return webVo;
    }
    private VbOverTime overTime2WebVo(TcOvertime overTime) {
        VbOverTime webVo = new VbOverTime();

        DtoUtil.copyBeanToBean(webVo,overTime);
        Date datetimeStart = overTime.getDatetimeStart();
        Date datetimeFinish = overTime.getDatetimeFinish();
        webVo.setDateFrom(comDate.dateToString(datetimeStart));
        webVo.setDateTo(comDate.dateToString(datetimeFinish));
        webVo.setTimeFrom(comDate.dateToString(datetimeStart,comDate.pattenTimeHM));
        webVo.setTimeTo(comDate.dateToString(datetimeFinish,comDate.pattenTimeHM));

        Date acutalDatetimeStart = overTime.getActualDatetimeStart();
        Date actualDatetimeFinish = overTime.getActualDatetimeFinish();
        webVo.setActualDateFrom(comDate.dateToString(acutalDatetimeStart));
        webVo.setActualDateTo(comDate.dateToString(actualDatetimeFinish));
        webVo.setActualTimeFrom(comDate.dateToString(acutalDatetimeStart,comDate.pattenTimeHM));
        webVo.setActualTimeTo(comDate.dateToString(actualDatetimeFinish,comDate.pattenTimeHM));

        IDtoAccount account = AccountFactory.create().getAccountByRID(overTime.getAcntRid());
        String accountName = account.getName();
        String accountCode = account.getId();
        webVo.setAccountName(accountName + " -- " + accountCode);
        return webVo;
    }
    public TcOvertime getOverTime(Long rid){
        if(rid == null)
            throw new BusinessException("TC_OVER_0005", "can not get null overtime!");
        try {
            TcOvertime overTime = (TcOvertime)this.getDbAccessor().load(
                    TcOvertime.class, rid);
            return overTime;
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0006", "error get overtime!",ex);
        }
    }
    /**
     * 获得加班记录明细
     */
    public VbOverTimeDetail getOverTimeDetail(Long rid){
        if(rid == null)
            throw new BusinessException("TC_OVER_0005", "can not get null overtime detail!");
        try {
            VbOverTimeDetail webVo = new VbOverTimeDetail();
            TcOvertime overTime = (TcOvertime)this.getDbAccessor().load(
                    TcOvertime.class, rid);
            DtoUtil.copyBeanToBean(webVo,overTime);
            Set details = overTime.getTcOvertimeDetails();
            Iterator it = details.iterator();
            List perDay = new ArrayList(details.size());
            while(it.hasNext()){
                TcOvertimeDetail detail = (TcOvertimeDetail) it.next();
                VbOverTimePerDay viewBean = new VbOverTimePerDay();
                DtoUtil.copyBeanToBean(viewBean,detail);
                viewBean.setOvertimeRid(overTime.getRid());
                perDay.add(viewBean);
            }
            webVo.setPerDay(perDay);
            webVo.setTotalHours(overTime.getActualTotalHours() + "");
            return webVo;
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0007", "error get overtime!",ex);
        }
    }
    /**
     * 列出加班审核记录
     * @param rid Long
     * @return List
     */
    public List listOverTimeReviewLog(Long rid){
        if(rid == null)
            throw new BusinessException("TC_OVER_0008", "can not get null overtime log!");
        List result = new ArrayList();
        try {
            TcOvertime overTime = (TcOvertime)this.getDbAccessor().load(
                    TcOvertime.class, rid);
            Set logs = overTime.getTcOvertimeLogs();
            Iterator it = logs.iterator();
            while(it.hasNext()){
                TcOvertimeLog log = (TcOvertimeLog) it.next();
                VbOverTimeReviewLog viewBean = new VbOverTimeReviewLog();
                DtoUtil.copyBeanToBean(viewBean,log);
                viewBean.setDeal(log.getDeal());
                result.add(viewBean);
            }
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0007", "error get overtime!",ex);
        }
        return result;
    }
    /**
     * 添加OverTime审核的记录
     * @param log TcOvertimeLog
     */
    public void addOverTimeReviewLog(TcOvertimeLog log){
        if( log == null || log.getLoginId() == null || log.getTcOvertime() == null
            || log.getDatetimeStart() == null || log.getDatetimeFinish() == null
            || log.getDecision() == null ){
            throw new BusinessException("TC_OVER_0008", "can not add null overtime log!");
        }
        try {
            this.getDbAccessor().save(log);
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0009", "errort add overtime log!",ex);
        }
    }
    /**
     * 列出人员某年份所有的加班记录
     * @param loginId String
     * @param year int
     * @return List
     */
    public List listOverTime(String loginId,int year){
        if(loginId == null)
            throw new BusinessException("TC_OVER_0011", "login id can not be null!");
        Date startDate = getYearStart(year);//该年开始时间
        Date finishDate = getYearEnd(year);//该年结束时间
        try {
            List result = new ArrayList();
            List l = listPeriodAllOverTime(loginId, startDate, finishDate);
            for(int i = 0 ;i < l.size(); i ++){
                TcOvertime overTime = (TcOvertime) l.get(i);
                VbOverTime viewBean = new VbOverTime();
                DtoUtil.copyBeanToBean(viewBean,overTime);
                Long acntRid = overTime.getAcntRid();
                String accountName = AccountFactory.create().getAccountByRID(acntRid).getName();
                viewBean.setAccountName(accountName);
                viewBean.setActualDateFrom(comDate.dateToString(overTime.getActualDatetimeStart()));
                viewBean.setActualDateTo(comDate.dateToString(overTime.getActualDatetimeFinish()));
                viewBean.setActualTimeFrom(comDate.dateToString(overTime.getActualDatetimeStart(),Constant.TIME_FORMAT));
                viewBean.setActualTimeTo(comDate.dateToString(overTime.getActualDatetimeFinish(),Constant.TIME_FORMAT));

                Set detailSet = overTime.getTcOvertimeDetails();
                if(detailSet != null && detailSet.size() != 0){
                    double totalShiftHours = 0D, totalPayedHours = 0D;
                    for(Iterator it = detailSet.iterator(); it.hasNext();){
                        TcOvertimeDetail detail = (TcOvertimeDetail) it.next();
                        Double shiftHours = detail.getShiftHours();
                        totalShiftHours += (shiftHours == null ? 0D : shiftHours.doubleValue() );
                        Double payedHours = detail.getPayedHours();
                        totalPayedHours += (payedHours == null ? 0D : payedHours.doubleValue() );
                    }
                    viewBean.setShiftHours( new Double(totalShiftHours) );
                    viewBean.setPayedHours( new Double(totalPayedHours) );
                }
                result.add(viewBean);
            }
            return result;
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0010", "error list overtime log!",ex);
        }
    }
    /**
     * 列出某区间内某人所有的加班记录
     * @param loginId String
     * @param startDate Date
     * @param finishDate Date
     * @return List
     */
    public List listPeriodAllOverTime(String loginId, Date startDate, Date finishDate) {
        if(loginId == null)
            throw new BusinessException("TC_OVER_0011", "login id can not be null!");
        if(startDate == null || finishDate == null)
            throw new BusinessException("TC_OVER_0017", "The period can not be null!");
        if(startDate.getTime() > finishDate.getTime())
            throw new BusinessException("TC_OVER_0018", "The start date can not be larger than finish date!");
        List l = listPeriodStatusOverTime(loginId, startDate, finishDate,
                                    new String[]{Constant.STATUS_APPLYING,
                                    Constant.STATUS_DISAGREED,
                                    Constant.STATUS_FINISHED,
                                    Constant.STATUS_REVIEWING,
                                    Constant.STATUS_ABORTED});
        return l;
    }
    /**
     * 列出某区间内某人已批准的加班记录
     */
    public List listPeriodFinishedOverTime(String loginId, Date startDate, Date finishDate) {
        if(loginId == null)
            throw new BusinessException("TC_OVER_0011", "login id can not be null!");
        if(startDate == null || finishDate == null)
            throw new BusinessException("TC_OVER_0017", "The period can not be null!");
        if(startDate.getTime() > finishDate.getTime())
            throw new BusinessException("TC_OVER_0018", "The start date can not be larger than finish date!");
        List l = listPeriodStatusOverTime(loginId, startDate, finishDate,
                                    new String[]{Constant.STATUS_FINISHED});
        return l;
    }
    /**
     * 列出人员某区间内某状态的所有加班记录
     */
    private List listPeriodStatusOverTime(String loginId, Date startDate, Date finishDate,String[] status) {
        try{
            StringBuffer statusQuery = new StringBuffer();
            for(int i = 0;i < status.length;i ++){
                statusQuery.append(" overtime.status='"+status[i]+"' or ");
            }
            //删除最后一个or
            statusQuery.delete(statusQuery.length()-3,statusQuery.length()-1);
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from TcOvertime overtime " +
                                   "where overtime.loginId=:loginId " +
                                   "and( overtime.actualDatetimeFinish >= :start " +
                                   "and overtime.actualDatetimeStart <= :finish )" +
                                   "and ("+statusQuery+") " +
                                   "order by overtime.actualDatetimeStart desc"
                     )
                     .setParameter("loginId", loginId)
                     .setParameter("start", startDate)
                     .setParameter("finish", finishDate)
                     .list();
            return l;
        }catch(Exception ex){
            throw new BusinessException("TC_OVER_0013","error list over time of period!",ex);
        }
    }
    /**
     * 返回某人加班的统计(已通过审核的),包括到现在为止总加班时间,已调休的时间,已支付的时间(计算总的可用时间);
     * @return VbOverTimeSum
     */
    public VbOverTimeSum getOverTimeSum(String loginId){
        if(loginId == null)
            throw new BusinessException("TC_OVER_0011", "login id can not be null!");
        String hql = "select new server.essp.attendance.overtime.viewbean.VbOverTimeSum(sum(ot.hours),sum(ot.shiftHours),sum(ot.payedHours)) " +
                     "from TcOvertimeDetail ot " +
                     "where ot.tcOvertime.loginId=:loginId " +
                     "and ot.tcOvertime.status =:status ";
        try {
            Session s = this.getDbAccessor().getSession();
            VbOverTimeSum result = (VbOverTimeSum) s.createQuery(hql)
                                   .setParameter("loginId", loginId)
                                   .setParameter("status",
                                                 Constant.STATUS_FINISHED)
                                   .uniqueResult();
            result.setLoginId(loginId);
            return result;
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0012", "error summing the overtime of ["+loginId+"]!",ex);
        }

    }
    /**
     * 新增调休时间
     * 1.列出状态为申请加班成功且可用时间大于0的加班记录,按可用时间对加班记录从小到大排序
     * 2.算法与@see TcUserLeave.addUseLeaveHours类似.
     * @param hours double
     */
    public void addShiftHours(String loginId,double hours){
        String hql = "from TcOvertimeDetail ot " +
                      "where ot.tcOvertime.loginId=:loginId " +
                      "and ot.tcOvertime.status =:status " +
                      "and ot.hours - ot.shiftHours - ot.payedHours > 0 " +
                      "order by ot.overtimeDay";
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery(hql)
                     .setParameter("loginId",loginId)
                     .setParameter("status",Constant.STATUS_FINISHED)
                     .list();
            if(l == null || l.size() <=0 )
                throw new BusinessException("TC_OVER_0014", "there is not enough hours to shift!");
            for(int i = 0;i < l.size() ;i ++){
                TcOvertimeDetail detail = (TcOvertimeDetail) l.get(i);
                double usable = detail.getUsableHours().doubleValue();
                if( usable > 0){
                    if(usable >= hours){
                        detail.addShiftHours(hours);
                        return;
                    }else{
                        hours -= usable;
                        detail.addShiftHours(usable);
                        continue;
                    }
                }
            }
        }catch(BusinessException ex){
            throw ex;
        }
        catch (Exception ex) {
            throw new BusinessException("TC_OVER_0013", "error add the shift hours of ["+loginId+"]!",ex);
        }
    }
    /**
     * 减去调休时间
     * 1.列出状态为申请加班成功,已调休时间大于0的加班记录,按已调休时间对加班记录从小到大排序
     * 2.遍历每条记录,在其已调休时间减去要返还的时间,至返还时间为0
     * @param loginId String
     * @param hours double
     */
    public void returnShiftHours(String loginId,double hours){
        String hql = "from TcOvertimeDetail ot " +
                      "where ot.tcOvertime.loginId=:loginId " +
                      "and ot.tcOvertime.status =:status " +
                      "and ot.shiftHours > 0 " +
                      "order by ot.overtimeDay desc";
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery(hql)
                     .setParameter("loginId", loginId)
                     .setParameter("status", Constant.STATUS_FINISHED)
                     .list();
            for(int i = 0;i < l.size() ;i ++){
                TcOvertimeDetail detail = (TcOvertimeDetail) l.get(i);
                double shiftHours = detail.getShiftHours().doubleValue();
                if(shiftHours >= hours){
                    detail.returnShiftHours(hours);
                    return;
                }else{
                    hours -= shiftHours;
                    detail.returnShiftHours(shiftHours);
                }
            }
            if(hours > 0){
                throw new BusinessException("TC_OVER_0017", "no enough shifted hours to return!");
            }
        } catch (Exception ex) {
            throw new BusinessException("TC_OVER_0015", "error return the shift hours of ["+loginId+"]!",ex);
        }
    }
    /**
     * 结束加班流程
     * @param rid Long
     */
    public void finish(Long rid){
        TcOvertime overTime = getOverTime(rid);
        finish(overTime);
    }
    public void finish(TcOvertime overTime){
        overTime.setStatus(Constant.STATUS_FINISHED);
    }
    private Date getYearStart(int year){
        Calendar start = Calendar.getInstance();
        start.set(year,0,1,0,0,0);//该年开始时间
        start.set(Calendar.MILLISECOND,0);
        return start.getTime();
    }
    private Date getYearEnd(int year){
        Calendar finish = Calendar.getInstance();
        finish.set(year,11,31,23,59,59);//该年结束时间
        finish.set(Calendar.MILLISECOND,999);
        return finish.getTime();
    }
    //默认每天最多只能加8小时班
    private float getOverTimeHours(String timeFrom,String timeTo,boolean isWorkDay){
        if(wt == null)
            wt =  WrokTimeFactory.serverCreate();
        float result = wt.getOverTimeHours(timeFrom,timeTo,isWorkDay);
        result = (float)StringUtil.trunNum(result,Constant.DECIMAL_TRUN);
        return result > 8F ? 8F : result;
    }
    public static void main(String[] args){
        LgOverTime lg = new LgOverTime();
    }
}
