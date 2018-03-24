package server.essp.attendance.leave.logic;

import java.util.*;

import c2s.dto.*;
import c2s.essp.common.calendar.*;
import com.wits.util.*;
import db.essp.attendance.*;
import net.sf.hibernate.*;
import c2s.essp.attendance.Constant;
import server.essp.attendance.leave.form.*;
import server.essp.attendance.leave.viewbean.*;
import server.essp.attendance.overtime.logic.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.logic.*;
import server.framework.common.*;
import c2s.essp.common.user.DtoUser;
import itf.hr.HrFactory;
import server.workflow.wfengine.WfProcess;
import server.workflow.wfengine.WfException;
import server.workflow.wfengine.WfEngine;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;
import server.essp.common.mail.LgMailSend;
import itf.account.AccountFactory;
import c2s.essp.common.account.IDtoAccount;
import server.essp.attendance.common.LgTimeCardFreeze;
import javax.sql.RowSet;

public class LgLeave extends AbstractESSPLogic {
    private WorkTime wt;

    /**
     * ������ֹ���ڼ�ʱ���ڵĹ���ʱ��
     * 1.�г����е���,����ÿ��,������칤ʱ,�ۼ����
     * 2 if ����һ��,���칤��ʱ��ΪTimeFrom��TimeTo��
     *   else ������������,
     *           ��һ�칤��ʱ��ΪTimeFrom��24:00:00��,
     *           �м�ÿ��Ϊ00:00:00��24:00:00��Ӱ�ʱ��
     *           ���һ�칤��ʱ��Ϊ00:00:00��TimeTo��
     * @param datatimeStart Date
     * @param datetimeFinish Date
     * @return float
     */
    public Map caculateWorkHours(String dateFromStr,String dateToStr,String timeFrom,String timeTo){
        if(dateFromStr == null || dateToStr == null || timeFrom == null || timeTo == null){
            throw new BusinessException("TC_LV0001","can not calucate work hours of null period!");
        }
        Map dayHoursMap = new HashMap();
        Date dateFrom = comDate.toDate(dateFromStr);
        Date dateTo = comDate.toDate(dateToStr);
        Calendar calFrom = Calendar.getInstance();
        Calendar calTo = Calendar.getInstance();
        calFrom.setTime(dateFrom);
        calTo.setTime(dateTo);
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        List days = wc.getDays(calFrom, calTo); //���е���

        if(days == null || days.size() <= 0)
            return dayHoursMap;
        else if(days.size() == 1){//ֻ��һ��
            Calendar day = (Calendar) days.get(0);
            boolean isWorkDay = wc.isWorkDay(day);
            if(!isWorkDay){
                return dayHoursMap;
            }else{
                float dayHour = (float)StringUtil.trunNum(
                        getWorkHours(timeFrom,timeTo),Constant.DECIMAL_TRUN);
                dayHoursMap.put(day,new Float(dayHour));
                return dayHoursMap;
            }
        }else{//�������������
            float perDayWorkHour=0.0f;

            Calendar firstDay = (Calendar) days.get(0);//��һ��
            boolean isWorkDay = wc.isWorkDay(firstDay);
            if(isWorkDay){
                perDayWorkHour = getWorkHours(timeFrom,
                                              Constant.DAY_END_TIME);
                dayHoursMap.put(firstDay,new Float(perDayWorkHour));
            }

            Calendar lastDay = (Calendar) days.get(days.size() - 1);//���һ��
            isWorkDay = wc.isWorkDay(lastDay);
            if(isWorkDay){
                perDayWorkHour = getWorkHours(Constant.DAY_BEGIN_TIME, timeTo);
                dayHoursMap.put(lastDay,new Float(perDayWorkHour));
            }
            days.remove(firstDay);
            days.remove(lastDay);

            Iterator it = days.iterator();//�����м����
            while(it.hasNext()){
                Calendar day = (Calendar) it.next();
                isWorkDay = wc.isWorkDay(day);
                if(isWorkDay){
                    perDayWorkHour = getWorkHours(Constant.DAY_BEGIN_TIME, Constant.DAY_END_TIME);
                    dayHoursMap.put(day,new Float(perDayWorkHour));
                }
            }
            return dayHoursMap;
        }
    }
    /**
     * ͳ����Աĳ���ݼٵ�״��:�����깲ʣ����ݼ�Сʱ��,�������õ�Сʱ��
     * 1.�г����еļٱ�,����ÿһ���ٱ�
     * 2.���ٱ�ͳ�Ƹ����¸�������ĸüٱ��ʱ��
     * 3.���ٱ��ǵ���(��Ӱ�ʱ�����),��ͳ�Ƹ���Ա�������мӰ�״��
     * @see LgOverTime.getOverTimeSum()
     * 4.���ٱ������(������ʱ�����),���Ҹ���Ա�ٱ��ʹ��״��,
     *   ��������,�����Ա�ٿ���ʱ��Ϊ0
     * ע:����ʱ�� = ����ʱ�� + ��֧��ʱ��;����ʱ�� = ����ʱ�� - ����ʱ��
     * 4.�����ٱ���ݼ�Сʱ��Ϊ0
     * @param loginId String
     * @param yearStr String
     * @return server.essp.attendance.leave.viewbean.VbLeavePersonalStatus
     */
    public VbLeavePersonalStatus getUserLeaveStatus(String loginId,String yearStr){
        VbLeavePersonalStatus webVo = new VbLeavePersonalStatus();
        webVo.setLoginId(loginId);
        int selectedYear = 0;
        if(yearStr != null){
            selectedYear = Integer.parseInt(yearStr);
            webVo.setSelectedYear(selectedYear);
        }
        LgLeaveType lgLeaveType = new LgLeaveType();
        List leaveTypeList = lgLeaveType.listEnableLeaveType();
        if(leaveTypeList == null || leaveTypeList.size() <= 0)
            throw new BusinessException("TC_LV0004","there is not leave type in system!");
        List usedList = new ArrayList();//ÿ�ּٱ���ʹ�õ�Сʱ��
        List usableList = new ArrayList();//ÿ�ּٱ��ʹ�õ�Сʱ��(δʹ��)
        List maxHoursList = new ArrayList();//ÿ�ּٱ�������Сʱ��
        //���������û�ÿ���ٱ�Ŀ���ʹ�õļ�
        Object usableHours = null,usedHours = null;
        for(int i = 0;i < leaveTypeList.size();i ++){
            VbLeaveType leaveType = (VbLeaveType) leaveTypeList.get(i);
            double maxHours = leaveType.getMaxDay() == null ? 0D : leaveType.getMaxDay().longValue() * getDailyWorkHours();
            usedHours = getUserLeaveSum(loginId,leaveType.getLeaveName(),webVo.getSelectedYear());
            //���ݼ�
            if(Constant.LEAVE_RELATION_SHIFT.equals(leaveType.getRelation())){
                VbOverTimeSum overTimeSum = getOverTimeSum(loginId);
                usableHours = overTimeSum.getUsableHours() ;
            }//���
            else if(Constant.LEAVE_RELATION_ANNU_LEAVE.equals(leaveType.getRelation())){
                TcUserLeave userLeave = getUserLeave(loginId,leaveType.getLeaveName());
                if(userLeave == null){
                    usableHours = new Double(0);
                }else{
                    usableHours = userLeave.getUsableHours();
                }
            }else{//�����ٱ�
                usableHours = new Double(0);
            }
            usableList.add(i,usableHours);
            usedList.add(i,usedHours);
            maxHoursList.add(i,new Double(maxHours));
        }
        webVo.setLeaveTypeList(leaveTypeList);
        webVo.setUsableList(usableList);
        webVo.setUsedList(usedList);
        webVo.setMaxHoursList(maxHoursList);
        return webVo;
    }
    /**
     * ͳ����Ա�ݼٵ�״��:��ʣ����ݼ�Сʱ���͸üٱ��������Сʱ��
     * @param loginId String
     * @return VbLeavePersonalStatus
     */
    public VbLeavePersonalStatus getUserLeaveStatus(String loginId){
        VbLeavePersonalStatus webVo = new VbLeavePersonalStatus();
        webVo.setLoginId(loginId);
        LgLeaveType lgLeaveType = new LgLeaveType();
        List leaveTypeList = lgLeaveType.listEnableLeaveType();
        if(leaveTypeList == null || leaveTypeList.size() <= 0)
            throw new BusinessException("TC_LV0004","there is not leave type in system!");
        List usableList = new ArrayList();//ÿ�ּٱ��ʹ�õ�Сʱ��(δʹ��)
        List maxHoursList = new ArrayList();//ÿ�ּٱ�������Сʱ��
        //���������û�ÿ���ٱ�Ŀ���ʹ�õļ�
        Object usableHours = null;
        for(int i = 0;i < leaveTypeList.size();i ++){
            VbLeaveType leaveType = (VbLeaveType) leaveTypeList.get(i);
            double maxHours = leaveType.getMaxDay() == null ? 0D : leaveType.getMaxDay().longValue() * getDailyWorkHours();
            //���ݼ�
            if(Constant.LEAVE_RELATION_SHIFT.equals(leaveType.getRelation())){
                VbOverTimeSum overTimeSum = getOverTimeSum(loginId);
                usableHours = overTimeSum.getUsableHours() ;
            }//���
            else if(Constant.LEAVE_RELATION_ANNU_LEAVE.equals(leaveType.getRelation())){
                TcUserLeave userLeave = getUserLeave(loginId,leaveType.getLeaveName());
//                boolean canLeave = canUserLeaveAnnual(loginId);
                if(userLeave == null ) {
//                   || canLeave == false){
                    usableHours = new Double(0);
                }else{
                    usableHours = userLeave.getUsableHours();
                }
            }else{//�����ٱ�
                if(maxHours == 0D)
                    usableHours = Constant.INFINITE_USABLE_HOURS;
                else
                    usableHours = new Double(maxHours);
            }
            usableList.add(i,usableHours);
            maxHoursList.add(i,new Double(maxHours));
        }
        webVo.setLeaveTypeList(leaveTypeList);
        webVo.setUsableList(usableList);
        webVo.setMaxHoursList(maxHoursList);
        return webVo;
    }
    //������Աĳ����ĳ�ٱ����ʱ��
    private Double getUserLeaveSum(String loginId,String leaveName,int year){
        Date start = getYearStart(year);
        Date end = getYearEnd(year);
        try{
            Session s = this.getDbAccessor().getSession();
            String query = "select sum(detail.hours) " +
                           "from TcLeaveDetail detail " +
                           "where detail.leaveDay >=:start and " +
                           "detail.leaveDay <=:end and  " +
                           "detail.tcLeave.loginId=:loginId and  " +
                           "detail.tcLeave.status=:status and  " +
                           "detail.tcLeave.leaveName=:leaveName ";
            Double hours = (Double)s.createQuery(query)
                           .setParameter("start",start)
                           .setParameter("end",end)
                           .setParameter("loginId",loginId)
                           .setParameter("leaveName",leaveName)
                           .setParameter("status",Constant.STATUS_FINISHED)
                           .uniqueResult();
            if(hours == null)
                return new Double(0);
            return hours;
        }catch(Exception ex){
            throw new BusinessException(ex);
        }
    }
    //������Աĳ�ٱ����ʱ��
    private Double getUserLeaveSum(String loginId,String leaveName){
            try{
                Session s = this.getDbAccessor().getSession();
                String query = "select sum(leave.actualTotalHours) " +
                               "from TcLeaveMain leave " +
                               "where leave.loginId=:loginId and  " +
                               "leave.status=:status and  " +
                               "leave.leaveName=:leaveName ";
                Double hours = (Double)s.createQuery(query)
                               .setParameter("loginId",loginId)
                               .setParameter("leaveName",leaveName)
                               .setParameter("status",Constant.STATUS_FINISHED)
                               .uniqueResult();
                if(hours == null)
                    return new Double(0);
                return hours;
            }catch(Exception ex){
                throw new BusinessException(ex);
            }
    }
    /**
     * �г�ĳ����������Ա���ݼ�״��
     * 1.���ݲ���ID���Ƿ�����Ӳ��Ų������е���Ա
     * 2.����ÿ����Ա������ݼ�״��,����ݼ���������minUsableHours����Ӹ��˵���ʾ���б�
     * @param loginId String
     * @return VbLeaveOrgStatus
     */
    public VbLeaveOrgStatus getOrgLeaveStatus(String orgId,boolean includeSub,double minUsableHours){
        VbLeaveOrgStatus result = new VbLeaveOrgStatus();
        IOrgnizationUtil orgUtil = OrgnizationFactory.create();
        //�������е���
        List usersLoginId = null;
        if(includeSub){
            String orgs = orgUtil.getSubOrgs(orgId);
            usersLoginId = orgUtil.getUserListInOrgs(orgs);
        }else{
            usersLoginId = orgUtil.getUserListInOrgs("'"+orgId+"'");
        }

        LgLeaveType lgLeaveType = new LgLeaveType();
        List leaveTypeList = lgLeaveType.listEnableLeaveType();
        if(leaveTypeList == null || leaveTypeList.size() <= 0)
            throw new BusinessException("TC_LV0004","there is not leave type in system!");
        result.setLeaveType(leaveTypeList);

        List workersStatus = new ArrayList();
        if(usersLoginId != null && usersLoginId.size() != 0)
            for(int i = 0;i < usersLoginId.size() ;i ++){
                String loginId = (String) usersLoginId.get(i);
                //�������˵�LoginIdΪ��
                if(loginId == null || "".equals(StringUtil.nvl(loginId))){
                    continue;
                }
                VbLeavePersonalStatus personal = new VbLeavePersonalStatus();
                personal.setLoginId(loginId);
                List usedList = new ArrayList();//ÿ�ּٱ���ʹ�õ�Сʱ��
                List usableList = new ArrayList();//ÿ�ּٱ��ʹ�õ�Сʱ��(δʹ��)
                for(int j = 0;j < leaveTypeList.size();j ++){
                    VbLeaveType leaveType = (VbLeaveType) leaveTypeList.get(j);
                    Double usableHours = null,usedHours = null;
                    usedHours = getUserLeaveSum(loginId,leaveType.getLeaveName());
                    //���ݼ�
                    if(Constant.LEAVE_RELATION_SHIFT.equals(leaveType.getRelation())){
                        VbOverTimeSum overTimeSum = getOverTimeSum(loginId);
                        usableHours = overTimeSum.getUsableHours() ;
                    }else if(Constant.LEAVE_RELATION_ANNU_LEAVE.equals(leaveType.getRelation())){//���
                        TcUserLeave userLeave = getUserLeave(loginId,
                                leaveType.getLeaveName());
                        if (userLeave == null) {
                            usableHours = new Double(0);
                        } else {
                            usableHours = userLeave.getUsableHours();
                        }
                    }else{
                        usableHours = new Double(0);
                    }
                    usedList.add(usedHours);
                    usableList.add(usableHours);
                }
                personal.setUsableList(usableList);
                personal.setUsedList(usedList);
                if(personal.getSumUsaleHours() >= minUsableHours)
                    workersStatus.add(personal);
            }
        result.setWorkersStatus(workersStatus);
        return result;
    }
    /**
     * �г�ĳ��ĳ���������е���ټ�¼
     * @param loginId String
     * @param startDate Date
     * @param finishDate Date
     * @return List
     */
    public List listPeriodAllLeave(String loginId, Date startDate, Date finishDate) {
        if(loginId == null)
            throw new BusinessException("TC_LV_0011", "login id can not be null!");
        if(startDate == null || finishDate == null)
            throw new BusinessException("TC_LV_0017", "The period can not be null!");
        if(startDate.getTime() > finishDate.getTime())
            throw new BusinessException("TC_LV_0018", "The start date can not be larger than finish date!");
        List l = listPeriodStatusLeave(loginId, startDate, finishDate,
                                          new String[]{Constant.STATUS_APPLYING,
                                          Constant.STATUS_DISAGREED,
                                          Constant.STATUS_FINISHED,
                                          Constant.STATUS_REVIEWING,
                                          Constant.STATUS_ABORTED});
        return l;
    }
    /**
     * �г�ĳ��ĳ���������е���ټ�¼
     * @return List
     */
    public List listPeriodFinishedLeave(String loginId, Date startDate, Date finishDate) {
        if(loginId == null)
            throw new BusinessException("TC_LV_0011", "login id can not be null!");
        if(startDate == null || finishDate == null)
            throw new BusinessException("TC_LV_0017", "The period can not be null!");
        if(startDate.getTime() > finishDate.getTime())
            throw new BusinessException("TC_LV_0018", "The start date can not be larger than finish date!");
        List l = listPeriodStatusLeave(loginId, startDate, finishDate,
                                       new String[]{Constant.STATUS_FINISHED});
        return l;
    }

    /**
     * �г�ĳ��ĳ�����еļӰ��¼
     * @param loginId String
     * @param year int
     * @return List
     */
    public List listLeave(String loginId,int year){
        if(loginId == null)
            throw new BusinessException("TC_LV_0011", "login id can not be null!");
        Date startDate = getYearStart(year);//���꿪ʼʱ��
        Date finishDate = getYearEnd(year);//�������ʱ��
        try {
            List result = new ArrayList();
            List l = listPeriodAllLeave(loginId, startDate, finishDate);
            for(int i = 0;i < l.size() ;i ++){
                TcLeaveMain leave = (TcLeaveMain) l.get(i);
                VbLeave vb = new VbLeave();
                DtoUtil.copyBeanToBean(vb,leave);
                Date start = leave.getActualDatetimeStart();
                Date finish = leave.getActualDatetimeFinish();
                vb.setDateFrom(comDate.dateToString(start));
                vb.setDateTo(comDate.dateToString(finish));
                vb.setTimeFrom(comDate.dateToString(start,Constant.TIME_FORMAT));
                vb.setTimeTo(comDate.dateToString(finish,Constant.TIME_FORMAT));
                result.add(vb);
            }
            return result;
        } catch (Exception ex) {
            throw new BusinessException("TC_LV_0010", "error list overtime log!",ex);
        }

    }
    /**
     * ������Ա��ĳ���ٱ�ʹ��״��(���ݳ���)
     * @param loginId String
     * @param leaveType String
     * @return TcUserLeave
     */
    public TcUserLeave getUserLeave(String loginId,String leaveType){
        if(loginId == null || leaveType == null )
            throw new BusinessException("TC_LV0002","can not null user leave!");
        TcUserLeavePK pk = new TcUserLeavePK(loginId,leaveType);
        try {
            Session s = this.getDbAccessor().getSession();
            TcUserLeave result =  (TcUserLeave) s.get(TcUserLeave.class,pk);
            return result;
        } catch (Exception ex) {
            throw new BusinessException("TC_LV0003","error get user leave!",ex);
        }
    }

    /**
     * can user use annual
     * @param loginId String
     * @return boolean
     */
    public boolean canUserLeaveAnnual(String loginId) {
        try{
                Session s = this.getDbAccessor().getSession();
                String query = "select t.indate from essp_hr_employee_main_t t "
                               +"join upms_loginuser l on t.code = l.user_id "
                               +"where l.login_id = '"+loginId+"'";
                RowSet r =this.getDbAccessor().executeQuery(query);
                Date inDate = null;
                if(r.next()) {
                    inDate = r.getDate(1);
                }
                if(inDate == null){
                    return false;
                }
                Calendar inday = Calendar.getInstance();
                inday.setTime(inDate);
                inday.set(inday.get(inday.YEAR)+1,inday.get(inday.MONTH),inday.get(inday.DATE));
                //when join over one year return true
                return !inday.after(Calendar.getInstance());
            }catch(Exception ex){
                throw new BusinessException(ex);
            }
    }
    /**
     * �����������
     * 1.��������ʱ�������Ƿ��Ѵ���,�Ҵ��ڵĸ�����¼״̬��Ϊ��ͬ��
     * 2.���������¼��ʵ��ʱ��Ϊ�����ʱ��,״̬ΪApplying
     * 3.�۳�������ٱ�Ŀ���ʱ��,���ݲ�ͬ�ٱ�����ֿ���:
     * (1)�ٱ�Ϊ����ʱ:��ʱ��˳��۳��Ӱ�ʱ��,���ۼӼӰ�ÿ����ϸ���ѵ���ʱ��
     * (2)�ٱ��Ӧ���ݼټ�¼������ʱ,��������
     * (3)�ٱ��Ӧ���ݼټ�¼����(TcUserLeave),
     *     ���ݼٴ���ÿ�����ϸ,�����Ⱥ�۳�����ʱ��,���ۼ�����ʱ��
     *     ��������ϸ��۳�����ֵ
     * 4.��������ÿ����ϸ
     * 5.������ٹ�����ʵ��������,��ù�����ʵ��RID,���õ���������¼��Ӧ�ֶβ�����
     * 6.�����ʼ������������ڵ�������Ŀ��PM
     * ע:��������뱻�޸�ʱ,Ӧ���ݸı��Сʱ�����¼���,���뱻�ܾ�ʱ,Ӧ�������Сʱ���ۼӻ�ȥ
     * @param form AfLeaveApp
     */
    public void addLeaveApp(AfLeaveApp form){
        if(form == null || form.getLoginId() == null || form.getOrgId() == null ||
           form.getDateFrom() == null || form.getDateTo() == null || form.getAcntRid() == null ||
           form.getLeaveName() == null || form.getTimeFrom() == null || form.getTimeTo() == null){
            throw new BusinessException("TC_LV0004","can not add null leave application");
        }
        Date datetimeStart = Constant.parseDate(form.getDateFrom(),form.getTimeFrom());
        Date datetimeFinish = Constant.parseDate(form.getDateTo(),form.getTimeTo());
        //����ʼʱ�����΢������Ϊ���,����ʱ������Ϊ��С
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetimeStart);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,999);
        datetimeStart = cal.getTime();
        cal.setTime(datetimeFinish);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        datetimeFinish = cal.getTime();
        checkPeriod(datetimeStart,datetimeFinish,form.getLoginId());

        TcLeaveMain leave = new TcLeaveMain();
        DtoUtil.copyBeanToBean(leave,form);
        leave.setDatetimeStart(datetimeStart);
        leave.setDatetimeFinish(datetimeFinish);
        leave.setActualDatetimeStart(datetimeStart);
        leave.setActualDatetimeFinish(datetimeFinish);
        leave.setActualTotalHours(leave.getTotalHours());
        leave.setStatus(Constant.STATUS_APPLYING);

        addUseLeaveHours(form.getLoginId(),
                         form.getLeaveName(),
                         leave.getActualTotalHours().doubleValue());
        try {
                //�����������������
                DtoUser user = this.getUser();
                WfEngine engine = new WfEngine();
                WfProcess  prcoess = engine.createProcessInstance(Constant.WF_PACKAGE_ID,
                        Constant.WF_LEAVE_PROCESS_ID,
                        user.getUserLoginId());
                String wfDesc = leave.getWFDescription();
                prcoess.getDtoWorkingProcess().setDescription(wfDesc);
                prcoess.getDtoWorkingProcess().setCurrActivityDescription(wfDesc);
                prcoess.start();
                leave.setWkId(prcoess.getDtoWorkingProcess().getRid());
                this.getDbAccessor().save(leave);
                prcoess.finishActivity();
            }catch (WfException ex1) {
                throw new BusinessException("TC_OVER_0003", "error create new overtime work flow!",ex1);
            }
            catch (Exception ex) {
                throw new BusinessException("TC_LV_0007","error add leave application!",ex);
            }
        addLeaveDetail(form.getDateFrom(),form.getDateTo(),form.getTimeFrom(),
                       form.getTimeTo(), leave);
//        sendLeaveMail(leave);
    }
    //�����ʼ����������ڵ���Ŀ����Ŀ�������ž���
    private void sendLeaveMail(TcLeaveMain leave){
        String appLoginId = leave.getLoginId();
        List accounts = AccountFactory.create().listAccountsByLoginID(appLoginId);
        StringBuffer mailList = new StringBuffer();
        if(accounts != null && accounts.size() != 0){
            for(int i =0 ;i < accounts.size() ;i ++){
                IDtoAccount account = (IDtoAccount) accounts.get(i);
                String managmer = account.getManager();
                String maildAdd = HrFactory.create().getUserEmail(managmer);
                if(mailList.length() == 0)
                    mailList.append(maildAdd);
                else
                    mailList.append("," + maildAdd);
            }
        }
        log.info("mail list:" + mailList);
        LgMailSend lg = new LgMailSend();
        String mailTitle = getMailTitle(leave);
        lg.setTitle(mailTitle);
        String content = getMailConent(leave);
        lg.setContent(content);
        lg.setToAddress(mailList.toString());
//        lg.setToAddress("rongxiao@wistronits.com");
        try{
            lg.send();
        }catch(Throwable t){
            log.warn("send mail error!",t);
        }
    }
    private String getMailTitle(TcLeaveMain leave){
        StringBuffer title = new StringBuffer();
        title.append(leave.getLoginId());
        title.append(" applys ");
        title.append(leave.getLeaveName());
        return title.toString();
    }
    private String getMailConent(TcLeaveMain leave){
        StringBuffer content = new StringBuffer();
        content.append(getMailTitle(leave));
        content.append(" : \n\t ");
        content.append(leave.getWFDescription());
        content.append(" \t ");
        String appLoginId = leave.getLoginId();
        DtoUser apper = HrFactory.create().findResouce(appLoginId);
        String managerLoginId = OrgnizationFactory.create().getOrgManagerLoginId(apper.getOrgId());
        DtoUser manager = HrFactory.create().findResouce(managerLoginId);
        content.append("If your have any questions,please contact the OBS Manager:");
        content.append(manager.getUserName());
        content.append(" ( ");
        content.append(manager.getEmail());
        content.append(" ). ");
        return content.toString();
    }

    private void addLeaveDetail(String dateFrom,String dateTo,
                                String timeFrom,String timeTo, TcLeaveMain leave) throws BusinessException {
        Map dayHoursMap = caculateWorkHours(dateFrom,dateTo,timeFrom,timeTo);
        Iterator it = dayHoursMap.keySet().iterator();
        while(it.hasNext()){
          Calendar day = (Calendar) it.next();
          TcLeaveDetail leaveDetail = new TcLeaveDetail();
          leaveDetail.setLeaveDay(day.getTime());
          Float hours = (Float) dayHoursMap.get(day);
          leaveDetail.setHours(new Double(hours.floatValue()));
          leaveDetail.setTcLeave(leave);
          try {
              this.getDbAccessor().save(leaveDetail);
          } catch (Exception ex1) {
              throw new BusinessException("TC_LV_0007","error add leave application!",ex1);
          }
        }
    }
    //������Աĳ�ٱ������ʱ��
    public void addUseLeaveHours(String loginId, String leaveName,double hours)  {
        LgLeaveType lg = new LgLeaveType();
        TcLeaveType leaveType = lg.getLeaveType(leaveName);
        if(leaveType == null)
            throw new BusinessException("TC_LV0005","can not add leave application!The leave type ["+leaveName+"] is not defined!");

        if(Constant.LEAVE_RELATION_SHIFT.equals(leaveType.getRelation())){//����,�ۼӰ�ʱ��
            LgOverTime lgOverTime = new LgOverTime();
            lgOverTime.addShiftHours(loginId,hours);
        }else if(Constant.LEAVE_RELATION_ANNU_LEAVE.equals(leaveType.getRelation())){//�����,�ۿ������ʱ��
            TcUserLeave userLeave = this.getUserLeave(loginId,leaveName);
            if(userLeave == null){
                throw new BusinessException("TC_LV_0008","There is not enought hours for annual leave!");
            }else if(userLeave != null){
                userLeave.addUseLeaveHours(hours);
            }
        }
    }
    //������Աĳ�ٱ������ʱ��
    public void returnUseLeaveHours(String loginId, String leaveName,double hours)  {
        LgLeaveType lg = new LgLeaveType();
        TcLeaveType leaveType = lg.getLeaveType(leaveName);
        if(leaveType == null)
            throw new BusinessException("TC_LV0005","The leave type ["+leaveName+"] is not defined!");

        if(Constant.LEAVE_RELATION_SHIFT.equals(leaveType.getRelation())){//����
            LgOverTime lgOverTime = new LgOverTime();
            lgOverTime.returnShiftHours(loginId,hours);
        }else {
            TcUserLeave userLeave = this.getUserLeave(loginId,leaveName);
            if(userLeave != null)
                userLeave.returnUseLeaveHours(hours);
        }
    }
    /**
     * ����������
     * ���ݵ�ǰʵ��ֵ������־,���ü�¼״̬����Disagreed,����
     * ����������Ĳ�ͬ�������¸����̵�״̬.����Ӧ����
     * 1.��������ͬ��:��������,�������ʱ�䷵�ص��û��Ŀ���ʱ����;
     * 2.������ͬ��:�޸�״̬
     * 3.������Ϊ�޸�:��Client�������ݸ��µ�ǰʵ��ֵ,�������û����ݼ�״��(ɾ���ɵ���ϸ,�����µ���ϸ);
     * �޸�ʱ����ڵ�ǰʵ��ʱ��,������û��Ŀ��ü�,С���򷵻ز�ֵ���û��Ŀ��ü�
     * @param form AfLeaveReview
     */
    public TcLeaveMain reviewLeave(AfLeaveReview form){
        if(form == null || form.getRid() == null || form.getDecision() == null){
            throw new BusinessException("TC_LV_0011","review null leave!");
        }
        TcLeaveMain leave = getLeave(new Long(form.getRid()));
        if(Constant.STATUS_DISAGREED.equals(leave.getStatus()) ||
           Constant.STATUS_FINISHED.equals(leave.getStatus()))
            throw new BusinessException("TC_LV_1000","This leave applying has been finished!");
        String decision = form.getDecision();

        if(Constant.DECISION_DISAGREE.equals(decision)){
                double hours = leave.getActualTotalHours().doubleValue();
                returnUseLeaveHours(leave.getLoginId(),leave.getLeaveName(),hours);
//                leave.setStatus(Constant.STATUS_DISAGREED);
                leave.setComments(form.getComments());
        }else if(Constant.DECISION_AGREE.equals(decision)){
//            leave.setStatus(Constant.STATUS_REVIEWING);
            leave.setComments(form.getComments());
        }else if(Constant.DECISION_MODIFY.equals(decision)){
                updateLeave(leave,form.getActualDateFrom(),form.getActualDateTo(),
                            form.getActualTimeFrom(),form.getActualTimeTo(),
                            form.getComments(),Double.parseDouble(form.getActualTotalHours()));
        }else{
            throw new BusinessException("Unknown","illegal review decision ["+decision+"]");
        }
        return leave;
    }

    public void updateLeave(TcLeaveMain leave,String dateFrom,String dateTo,
                             String timeFrom,String timeTo,
                             String comments,double actualHours) throws BusinessException {
        double oldActualHours = leave.getActualTotalHours().doubleValue();
        if (actualHours > oldActualHours) {
            addUseLeaveHours(leave.getLoginId(), leave.getLeaveName(),
                             actualHours - oldActualHours);
        } else if (actualHours < oldActualHours) {
            returnUseLeaveHours(leave.getLoginId(), leave.getLeaveName(),
                                oldActualHours - actualHours);
        }
        leave.setComments(comments);
        leave.setActualDatetimeFinish(
                Constant.parseDate(dateTo,timeTo));
        leave.setActualDatetimeStart(
                Constant.parseDate(dateFrom,timeFrom));
        leave.setActualTotalHours(new Double(actualHours));
        try {
            this.getDbAccessor().delete(leave.getTcLeaveDetails());
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        addLeaveDetail(dateFrom,dateTo,timeFrom,timeTo,leave);
    }
    //������ٵ������־
    public TcLeaveLog addReviewLog(TcLeaveMain leave,String decision,String comments)  {
        TcLeaveLog log = new TcLeaveLog();
        DtoUser user = getUser();
        if(user == null)
            throw new BusinessException("unknown","The user does not login in!");
        log.setDecision(decision);
        log.setTotalHours(leave.getActualTotalHours());
        log.setLoginId(user.getUserLoginId());
        log.setLogDate(new Date());
        log.setTcLeaveMain(leave);
        log.setDatetimeFinish(leave.getActualDatetimeFinish());
        log.setDatetimeStart(leave.getActualDatetimeStart());
        log.setComments(comments);
        try {
            this.getDbAccessor().save(log);
        } catch (Exception ex) {
            throw new BusinessException("TC_LV_0012","error add leave review log!",ex);
        }
        return log;
    }
    /**
     * ���ݹ�����ʵ��Rid������ټ�¼
     * @param wkRid Long
     * @return VbLeave
     */
    public TcLeaveMain getLeaveByWkRid(Long wkRid){
        try {
            Session s = this.getDbAccessor().getSession();
            TcLeaveMain leave = (TcLeaveMain) s.createQuery(
                    "from TcLeaveMain leave " +
                    "where leave.wkId=:wkRid")
                                .setParameter("wkRid", wkRid)
                                .uniqueResult();
            return leave;
        } catch (Exception ex) {
            throw new BusinessException("TC_LEAVE_10000","error get leave by work flow!",ex);
        }

    }
    public VbLeave getLeaveVBByWkRid(Long wkRid){
        try {
            TcLeaveMain leave = getLeaveByWkRid(wkRid);
            VbLeave webVo = leave2WebVo(leave);
            return webVo;
        } catch (Exception ex) {
            throw new BusinessException("TC_LEAVE_10000","error get leave by work flow!",ex);
        }
    }
    /**
     * ������ټ�¼
     * @param rid Long
     * @return TcLeaveMain
     */
    public VbLeave getLeaveByRid(Long rid){
        if(rid == null)
            throw new BusinessException("TC_LV_0009","can not get null leave!");
        TcLeaveMain leave = getLeave(rid);
        VbLeave webVo = leave2WebVo(leave);
        return webVo;
    }

    private VbLeave leave2WebVo(TcLeaveMain leave) throws BusinessException {
        VbLeave webVo = new VbLeave();
        DtoUtil.copyBeanToBean(webVo,leave);
        if(leave.getUsableHours() == null)
            webVo.setUsableHours(Constant.INFINITE_USABLE_HOURS);
        Date start = leave.getDatetimeStart();
        Date finish = leave.getDatetimeFinish();
        webVo.setDateFrom(comDate.dateToString(start));
        webVo.setDateTo(comDate.dateToString(finish));
        webVo.setTimeFrom(comDate.dateToString(start,comDate.pattenTimeHM));
        webVo.setTimeTo(comDate.dateToString(finish,comDate.pattenTimeHM));

        Date actualStart = leave.getActualDatetimeStart();
        Date actualFinish = leave.getActualDatetimeFinish();
        webVo.setActualDateFrom(comDate.dateToString(actualStart));
        webVo.setActualDateTo(comDate.dateToString(actualFinish));
        webVo.setActualTimeFrom(comDate.dateToString(actualStart,comDate.pattenTimeHM));
        webVo.setActualTimeTo(comDate.dateToString(actualFinish,comDate.pattenTimeHM));

        String org = HrFactory.create().findResouce(webVo.getLoginId()).getOrganization();
        webVo.setOrganization(org);

        Long acntRid = webVo.getAcntRid();
        if(acntRid != null){
            webVo.setAcntRid(acntRid);
            IDtoAccount account = AccountFactory.create().getAccountByRID(acntRid);
            webVo.setAccountName(account.getId() + " -- " + account.getName());
        }
        String leaveName = leave.getLeaveName();
        LgLeaveType lg = new LgLeaveType();
        TcLeaveType leaveType = lg.getLeaveType(leaveName);
        if(leaveType != null){
            webVo.setSettlementWay(leaveType.getSettlementWay());
            Long maxDays = leaveType.getMaxDay();

            double maxHours = maxDays == null ? 0D :
                              maxDays.longValue() * getDailyWorkHours();
            webVo.setMaxHours(new Double(maxHours));
            Object currUsableHours = null;
            String loginId = leave.getLoginId();
            //���ݼ�
            if (Constant.LEAVE_RELATION_SHIFT.equals(leaveType.getRelation())) {
                VbOverTimeSum overTimeSum = getOverTimeSum(loginId);
                currUsableHours = overTimeSum.getUsableHours();
            } else if (Constant.LEAVE_RELATION_ANNU_LEAVE.equals(leaveType.
                    getRelation())) {
                //���
                TcUserLeave userLeave = getUserLeave(loginId, leaveName);
                if (userLeave == null) {
                    currUsableHours = new Double(0);
                } else {
                    currUsableHours = userLeave.getUsableHours();
                }
            } else { //�����ٱ�
                if (maxHours > 0D)
                    currUsableHours = new Double(maxHours);
                else
                    currUsableHours = Constant.INFINITE_USABLE_HOURS;
            }
            webVo.setCurrUsableHours(currUsableHours);
        }
        return webVo;
    }
    public TcLeaveMain getLeave(Long rid){
        try {
            TcLeaveMain leave = (TcLeaveMain)this.getDbAccessor().load(
                    TcLeaveMain.class, rid);
            return leave;
        } catch (Exception ex) {
            throw new BusinessException("TC_LV_0010","error get leave!",ex);
        }
    }
    /**
     * �г�����������˼�¼
     * @param rid Long
     * @return List
     */
    public List listLeaveReviewLog(Long rid){
        if(rid == null)
            throw new BusinessException("TC_LV_0009","can not get null leave!");
        try {
            TcLeaveMain leave = (TcLeaveMain)this.getDbAccessor().load(
                    TcLeaveMain.class, rid);
            Set logs = leave.getTcLeaveLogs();
            List reviewLogs = new ArrayList(logs.size());
            Iterator it = logs.iterator();
            while(it.hasNext()){
                TcLeaveLog log = (TcLeaveLog) it.next();
                VbLeaveReviewLog reviewLog = new VbLeaveReviewLog();
                DtoUtil.copyBeanToBean(reviewLog,log);
                reviewLogs.add(reviewLog);
            }
            return reviewLogs;
        } catch (Exception ex) {
            throw new BusinessException("TC_LV_0010","error get leave!",ex);
        }
    }
    /**
     * �����������
     * @param rid Long
     */
    public void finish(Long rid){
        TcLeaveMain leave = getLeave(rid);
        finish(leave);
    }
    public void finish(TcLeaveMain leave){
        leave.setStatus(Constant.STATUS_FINISHED);
    }
    /**�ж�������������Ƿ��Ѵ�������״̬��Ϊ��ͬ���
     * �жϸ������Ƿ��ѱ�������
     */
    private void checkPeriod(Date datetimeStart, Date datetimeFinish,String loginId) {
        List l = listPeriodStatusLeave(loginId, datetimeStart, datetimeFinish,
                                       new String[]{Constant.STATUS_APPLYING,
                                       Constant.STATUS_FINISHED,
                                       Constant.STATUS_REVIEWING});
        if(l != null && l.size() > 0)
            throw new BusinessException("TC_LV_0005","!The period has been applied!");
        LgTimeCardFreeze lgTCFreeze = new LgTimeCardFreeze();
        boolean isFrozen = lgTCFreeze.isFrozen(loginId,datetimeStart,datetimeFinish);
        if(isFrozen)
            throw new BusinessException("TC_OVER_0013","The period has been frozen by HR!Please ask HR for help!");

    }
    /**
     *  �г���Աĳ������ĳ״̬��������ټ�¼
     */
    private List listPeriodStatusLeave(String loginId, Date datetimeStart,Date datetimeFinish,String[] status) {
        try{
            StringBuffer statusQuery = new StringBuffer();
            for(int i = 0;i < status.length;i ++){
                statusQuery.append(" leave.status='" + status[i] + "' or ");
            }                //ɾ�����һ��or
            statusQuery.delete(statusQuery.length() - 3,statusQuery.length() - 1);
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from TcLeaveMain leave " +
                                   "where leave.loginId=:loginId " +
                                   "and( leave.actualDatetimeFinish >= :start " +
                                   "and leave.actualDatetimeStart <= :finish )" +
                                   "and ( "+statusQuery+" )" +
                                   "order by leave.datetimeStart desc"
                     )
                     .setParameter("loginId", loginId)
                     .setParameter("start", datetimeStart)
                     .setParameter("finish", datetimeFinish)
                     .list();
            return l;
        }catch(Exception ex){
            throw new BusinessException("TC_LV_0006","error list leave of period!",ex);
        }
    }
    private VbOverTimeSum getOverTimeSum(String loginId){
        LgOverTime lgOverTime = new LgOverTime();
        VbOverTimeSum overTimeSum = lgOverTime.getOverTimeSum(loginId);
        return overTimeSum;
    }
    //������ֹʱ��Ĺ�ʱ
    private float getWorkHours(String timeFrom,String timeTo){
        if(wt == null)
            wt = WrokTimeFactory.serverCreate();
        float result = wt.getWorkHours(timeFrom,timeTo);
        return result;
    }
    private float getDailyWorkHours(){
        if(wt == null)
            wt = WrokTimeFactory.serverCreate();
        return wt.getDailyWorkHours();
    }
    private Date getYearStart(int year){
        Calendar start = Calendar.getInstance();
        start.set(year,0,1,0,0,0);//���꿪ʼʱ��
        start.set(Calendar.MILLISECOND,0);
        return start.getTime();
    }
    private Date getYearEnd(int year){
        Calendar finish = Calendar.getInstance();
        finish.set(year,11,31,23,59,59);//�������ʱ��
        finish.set(Calendar.MILLISECOND,999);
        return finish.getTime();
    }

    public static void main(String args[]){
        LgLeave lg = new LgLeave();
        System.out.println(lg.canUserLeaveAnnual("WH0302008"));
    }
}
