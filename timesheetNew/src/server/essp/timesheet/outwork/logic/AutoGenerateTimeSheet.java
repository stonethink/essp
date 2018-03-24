package server.essp.timesheet.outwork.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.hibernate.Session;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wits.util.ThreadVariant;

import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.timesheet.approval.service.IApprovalAssistService;
import server.essp.timesheet.code.codevalue.service.ICodeValueService;
import server.essp.timesheet.period.service.IPeriodService;
import server.essp.timesheet.preference.service.IPreferenceService;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;
import server.essp.timesheet.workscope.service.IWorkScopeService;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.framework.primavera.IPrimaveraApi;
import c2s.dto.IDto;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.code.DtoCodeValue;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDay;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetDetail;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import db.essp.timesheet.TsHumanBase;
import db.essp.timesheet.TsOutWorker;

/**
 *
 * <p>Title: ������Ա�Զ���д��ʱ��</p>
 * <p>Description: ���������Ա��¼�Զ����ɹ�ʱ��
 * 		1.ʱ�䣺��Ҫ������ʱ�����������ʱ��Ľ���
 * 		2.��ʱ��ȡ�����Ա�ڵ���ı�׼��ʱ
 * 		3.���ݣ���ʱ����Ŀ�е���Ŀ��Code��Activityȡ�������Ա��¼
 * 		4.״̬��Approved������Ҫ��������
 * 		5.���ͬһ��ʱ�������ж��������¼����������ʱ����Ŀ����
 * </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: WistronITS</p>
 * @author lipengxu
 * @version 1.0
 */
public class AutoGenerateTimeSheet extends AbstractESSPLogic {
	
	private static ApplicationContext context = null;
    private Date today = WorkCalendar.resetBeginDate(new Date());
    private DtoTimeSheetPeriod period;
    private ITimeSheetService timeSheetService;
    private IWorkScopeService workScopeService;
    private ICodeValueService codeValueService;
    private IApprovalAssistService approvalAssistService;
    private IPreferenceService preferenceService;
    private IRmMaintDao rmMaintDao;
    private Date pBegin;
    private Date pEnd;
    private Long pId;
    
    /**
     * ���ж�����ڵ����ɴ���
     * @param offset
     * @param times
     */
    public int runMultiPeriod(int offset, int times) {
    	int count = 0;
    	for(int i = 0; i < times; i ++) {
    		count += runOnePeriod(offset + i);
    	}
    	return count;
    }
    
    /**
     * ����һ�����ڵ����ɴ���
     * @param offset
     */
    public int runOnePeriod(int offset) {
    	initPeriod(offset);
    	return run();
    }
    
    /**
     * ��ʼ����ǰ��ʱ������
     * @param weekOffset
     */
    protected void initPeriod(int weekOffset) {
    	IPeriodService periodService = (IPeriodService)this.getBean("periodService");
        try {
			period = periodService.getNextPeriod(today, weekOffset);
		} catch (Exception e) {
			log.error("getNextPeriod error offset:" 
					+ weekOffset + "  today:" + today, e);
		}
    }
    
    /**
     * �������ɳ���
     *
     */
    private int run() {
    	int count = 0;
    	
    	//���period��Ч��ֱ�ӷ���
    	if(validatePeriod() == false) {
    		return count;
    	}
    	
        //���г��������г����¼
        List outWorkerRecords = getOutWorkersRecordsInPeriod(pBegin, pEnd);
        if(outWorkerRecords == null || outWorkerRecords.size() <= 0) {
        	log.info("no out worker between " + pBegin + " and " + pEnd);
        	return count;
        }
        //�ٶԳ����¼����Ա����Ŀ����
        Map tsMap = generateTimeSheet(outWorkerRecords);
        if (tsMap == null || tsMap.size() == 0) {
        	log.warn("no timesheet need generate between " + pBegin + " and " + pEnd);
            return count;
        }
        //���湤ʱ��
        Iterator<DtoTimeSheet> it = tsMap.values().iterator();
        while (it.hasNext()) {
        	saveWeeklyReport(it.next());
        	count ++;
        }
        return count;
    }
    
    /**
     * ��֤period�Ƿ���Ч
     * @return
     */
    private boolean validatePeriod() {
    	if(period == null) {
    		log.error("period is null, auto generate abort!");
    		return false;
    	}
    	pId = period.getTsId();
    	if(pId == null) {
    		log.error("period id is null, auto generate abort!");
    		return false;
    	}
    	pBegin = period.getBeginDate();
    	if(pBegin == null) {
    		log.error("period begin date is null, auto generate abort!");
    		return false;
    	}
    	pEnd = period.getEndDate();
    	if(pEnd == null) {
    		log.error("period end date is null, auto generate abort!");
    		return false;
    	}
    	return true;
    }
    
    /**
     * �жϹ�ʱ���Ƿ��Ѿ�����
     * @param loginId
     * @param tsId
     * @return boolean
     */
    private boolean isTimeSheetExist(String loginId, Long tsId) {
    	return getTimeSheetService().isTimeSheetExist(tsId, loginId);
    }
    
    private boolean isNeedApproval(String loginId) {
    	TsHumanBase hm = getRmMaintDao().loadHumanById(loginId);
    	if(hm == null) {
    		return false;
    	} else {
    		return getPreferenceService().getLoadPreferenceBySite(hm.getSite()).getGenTsNeedApproval();
    	}
    }
    
    /**
     * ��ȡITimeSheetService
     * @return ITimeSheetService
     */
    private ITimeSheetService getTimeSheetService() {
    	if(timeSheetService == null) {
    		timeSheetService = (ITimeSheetService) getBean("timeSheetService");
    	}
    	return timeSheetService;
    }
    
    /**
     * ��ȡICodeValueService
     * @return ICodeValueService
     */
    private ICodeValueService getCodeValueService() {
    	if(codeValueService == null) {
    		codeValueService = (ICodeValueService) getBean("codeValueService");
    	}
    	return codeValueService;
    }
    
    /**
     * ��ȡIWorkScopeService
     * @return IWorkScopeService
     */
    private IWorkScopeService getWorkScopeService() {
    	if(workScopeService == null) {
    		workScopeService = (IWorkScopeService) getBean("iWorkScopeService");
    	}
    	return workScopeService;
    }
    
    /**
     * ��ȡIApprovalAssistService
     * @return IApprovalAssistService
     */
    public IApprovalAssistService getApprovalAssistService() {
		if(approvalAssistService == null) {
			approvalAssistService = (IApprovalAssistService) getBean("approvalAssistService");
    	}
    	return approvalAssistService;
	}
    
    /**
     * ��ȡIApprovalAssistService
     * @return IApprovalAssistService
     */
    public IPreferenceService getPreferenceService() {
		if(preferenceService == null) {
			preferenceService = (IPreferenceService) getBean("preferenceService");
    	}
    	return preferenceService;
	}

    /**
     * ��ȡIRmMaintDao
     * @return IRmMaintDao
     */
    public IRmMaintDao getRmMaintDao() {
		if(rmMaintDao == null) {
			rmMaintDao = (IRmMaintDao) getBean("rmMaintDao");
    	}
    	return rmMaintDao;
	}
    
    /**
     * ���������ɵĹ�ʱ��
     * @param dto DtoTimeSheet
     */
    private void saveWeeklyReport(DtoTimeSheet dto) {
    	getTimeSheetService().saveTimeSheet(dto);
    	if(isNeedApproval(dto.getLoginId())) {
    		getApprovalAssistService().writeBackToP3(dto.getRid());
    	}
    }

    /**
     * ���ɹ�ʱ��
     *   �����ͬһ��ʱ���������ж��������¼����ÿ�������¼����һ����ʱ����Ŀ
     * @param outWorkerRecords
     * @return Map<loginId, DtoTimeSheet>
     */
    protected Map generateTimeSheet(List outWorkerRecords) {
        Map result = new HashMap();
        if (outWorkerRecords == null) {
        	return result;
        }
        for (int i = 0; i < outWorkerRecords.size(); i++) {
            TsOutWorker outWorker = (TsOutWorker) outWorkerRecords.get(i);
            String loginId = outWorker.getLoginId();
            //������û��ڵ�ǰ��ʱ�����Ѿ����ڹ�ʱ���������Զ���д
            if(isTimeSheetExist(loginId, pId)) {
            	//��¼����Ҫ���ɹ�ʱ��
            	printGenerateLog(loginId, true);
            	continue;
            }
            
            DtoTimeSheet dtoTs = (DtoTimeSheet) result.get(loginId);
            List detailList;
            //���֮ǰû�м�¼��������һ���µ�DtoTimeSheet���µ�detailList
            //���֮ǰ�м�¼�����ڴ˻���������һ��DtoTimeSheetDetail
            if (dtoTs == null) {
            	dtoTs = newDtoTimeSheet(loginId);
            	//��¼Ҫ���ɹ�ʱ��
            	printGenerateLog(loginId, false);
            	result.put(loginId, dtoTs);
            	detailList = new ArrayList();
            } else {
            	detailList = dtoTs.getTsDetails();
            }
            //���������Ա��¼������DtoTimeSheetDetail����ÿ���newDtoTimeSheetDay
            DtoTimeSheetDetail dtoDetail = newDtoTimeSheetDetail(outWorker);
            dtoDetail.setTsDays(getTimeSheetDays(outWorker));
            detailList.add(dtoDetail);
            dtoTs.setTsDetails(detailList);
        }
        return result;
    }
    
    /**
     * Ϊ��Ҫ����Ҫ���ɹ�ʱ�������ݼ�¼
     * @param loginId
     */
    private void printGenerateLog(String loginId, boolean exist) {
    	String info = today + " " + loginId
    						+ (exist ? " a timesheet exist" 
    								:" generate a timesheet")
    						+ " at\n\t tsId:[" + pId + "]\n\t begin:[" + pBegin 
    						+ "] \n\t end:[" + pEnd + "]" ;
    	log.info(info);
    }
    
    /**
     * ����TsOutWorker����ÿ��Ĺ�ʱ���ݡ�
     * 		ʱ�����䣺��ǰ��ʱ�������TsOutWorker������ڽ���
     * @param outWorker
     * @return
     */
    private HashMap getTimeSheetDays(TsOutWorker outWorker) {
    	HashMap dayMap = new HashMap();
    	Date begin = outWorker.getBeginDate();
    	Date end = outWorker.getEndDate();
    	String loginId = outWorker.getLoginId();
    	if(begin == null) {
    		log.warn("begin date is null, TsOutWorker rid=" + outWorker.getRid());
    		return dayMap;
    	}
    	if(end == null) {
    		log.warn("end date is null, TsOutWorker rid=" + outWorker.getRid());
    		return dayMap;
    	}
    	//����ʼ����������ת���������б�
    	List<Date> dateList = getDateList(getBegin(begin), getEnd(end));
    	if(dateList == null || dateList.size() == 0) {
    		log.warn("is no date between " 
    				+ getBegin(begin) + " and " + getEnd(end)
    				+ " (" + begin + " and " + end + ")");
    		return dayMap;
    	}
    	//���������б��ȡÿ��ı�׼��ʱ
    	List<Double> hourList = getHourList(loginId, dateList);
    	if(hourList == null || hourList.size() == 0) {
    		log.warn("is no hour data between " 
    				+ getBegin(begin) + " and " + getEnd(end)
    				+ " (" + loginId + ")");
    		return dayMap;
    	}
    	//��׼��ʱ������������ڻ���ڣ��л��ܼ�¼����������
    	if(hourList.size() < dateList.size()) {
    		log.warn("hourList length is not enough -- " 
    				+ getBegin(begin) + " and " + getEnd(end)
    				+ " (" + loginId + ")");
    		return dayMap;
    	}
    	//���������б�͹�ʱ�б����� HashMap<Date, DtoTimeSheetDay>
    	for(int i = 0; i < dateList.size(); i ++) {
    		Date d = dateList.get(i);
    		Double h = hourList.get(i);
    		dayMap.put(d, newDtoTimeSheetDay(d, h));
    	}
    	return dayMap;
    }
    
    /**
     * ��ȡ����ʱ��͹�ʱ����ʼʱ��ϴ��һ��
     * @param begin
     * @return Date
     */
    private Date getBegin(Date begin) {
    	if(begin.after(pBegin)) {
    		return begin;
    	} else {
    		return pBegin;
    	}
    }
    
    /**
     * ��ȡ����ʱ��͹�ʱ������ʱ���С��һ��
     * @param end
     * @return Date
     */
    private Date getEnd(Date end) {
    	if(end.before(pEnd)) {
    		return end;
    	} else {
    		return pEnd;
    	}
    }
    
    /**
     * ��ȡָ��ʱ������ÿ���ʱ������
     * @param begin
     * @param end
     * @return List<Date>
     */
    private List getDateList(Date begin, Date end) {
    	return DtoTimeSheetPeriod.period2DateList(begin, end);
    }
    
    
    /**
     * ��ȡָ���û���ָ��ʱ���ڵ�ÿ��ı�׼��ʱ
     * @param loginId
     * @param dateList
     * @return List<Double>
     */
    private List getHourList(String loginId, List dateList) {
    	return getTimeSheetService().listStandarHours(loginId, dateList);
    }
    
    /**
     * ��ȡ��DtoTimeSheet����
     * @param loginId
     * @return DtoTimeSheet
     */
    private DtoTimeSheet newDtoTimeSheet(String loginId) {
    	DtoTimeSheet dtoTs = new DtoTimeSheet();
        dtoTs.setLoginId(loginId);
        dtoTs.setTsId(pId);
        dtoTs.setBeginDate(pBegin);
        dtoTs.setEndDate(pEnd);
        dtoTs.setStatus(getTsStatus(loginId));
        dtoTs.setStatusDate(today);
        dtoTs.setNotes(getTsNodes());
        dtoTs.setOp(IDto.OP_INSERT);
        return dtoTs;
    }
    
    /**
     * ��ȡ��DtoTimeSheetDetail����
     * @param outWorker
     * @return DtoTimeSheetDetail
     */
    private DtoTimeSheetDetail newDtoTimeSheetDetail(TsOutWorker outWorker) {
    	DtoTimeSheetDetail dtoDetail = new DtoTimeSheetDetail();
    	dtoDetail.setAccountRid(outWorker.getAcntRid());
    	dtoDetail.setAccountName(getAccountName(outWorker.getAcntRid()));
    	dtoDetail.setCodeValueRid(outWorker.getCodeRid());
    	setCodeValue(dtoDetail, outWorker.getCodeRid());
    	dtoDetail.setActivityId(outWorker.getActivityRid());
    	dtoDetail.setActivityName(getActivityName(outWorker.getActivityRid()));
    	dtoDetail.setStatus(getTsStatus(outWorker.getLoginId()));
    	dtoDetail.setJobDescription(getDescription(outWorker.getDestAddress()));
    	dtoDetail.setOp(IDto.OP_INSERT);
    	return dtoDetail;
    }
    
    private String getAccountName(Long acntRid) {
    	if(acntRid == null) {
    		return "";
    	}
    	return getWorkScopeService().getAccountShowName(acntRid);
    }
    
    private void setCodeValue(DtoTimeSheetDetail dtoDetail, Long codeRid) {
    	if(codeRid == null || dtoDetail == null) {
    		return ;
    	}
    	DtoCodeValue dto = getCodeValueService().getCodeValue(codeRid);
    	if(dto != null) {
    		dtoDetail.setCodeValueName(dto.getShowLeaveCodeName());
    		dtoDetail.setIsLeaveType(dto.getIsLeaveType());
    	}
    }
    
    /**
     * ����ActivityId ��ActivityShowName
     * @param activityId Long
     * @return String
     */
    private String getActivityName(Long activityId) {
    	if(activityId == null) {
    		return "";
    	}
    	return getWorkScopeService().getActivityShowName(activityId);
    }
    
    /**
     * ��ȡ��DtoTimeSheetDay����
     * @param workDate
     * @param hours
     * @return DtoTimeSheetDay
     */
    private DtoTimeSheetDay newDtoTimeSheetDay(Date workDate, Double hours) {
    	DtoTimeSheetDay dtoDay = new DtoTimeSheetDay();
    	dtoDay.setWorkDate(workDate);
    	dtoDay.setWorkHours(hours);
    	dtoDay.setOp(IDto.OP_INSERT);
    	return dtoDay;
    }
    
    
    /**
     * ��ʱ�����ɺ��״̬
     * @return
     */
    protected String getTsStatus(String loginId) {
    	if(isNeedApproval(loginId)) {
    		return DtoTimeSheet.STATUS_SUBMITTED;
    	} else {
    		return DtoTimeSheet.STATUS_APPROVED;
    	}
    }
    
    /**
     * ��ÿ���Զ����ɵĹ�ʱ��Notes�м������ڣ�������������
     * @return String
     */
    protected String getTsNodes() {
    	return today + "\n\t This timesheet is auto generated by ESSP Out Worker Management Module";
    }
    
    /**
     * ���ɹ�ʱ����Ŀ��������Ϣ
     *    ��������¼��Ŀ�ĵأ�����������С�
     * @param destAddr
     * @return String
     */
    protected String getDescription(String destAddr) {
    	String desc = "Generated by ESSP";
    	if(destAddr != null && "".equals(destAddr) == false) {
    		desc += ", destination [" + destAddr + "]";
    	}
    	return desc;
    }


    /**
     * �����������н��������Զ���д�ܱ��ĳ����¼
     * @param begin Date
     * @param end Date
     * @return List
     */
    private List getOutWorkersRecordsInPeriod(Date beginPeriod, Date endPeriod) {
        String hql = "from TsOutWorker worker " +
                     "where worker.beginDate <= :endPeriod " +
                     "and worker.endDate >= :beginPeriod " +
                     "and worker.isAutoWeeklyReport = :isAutoWeeklyReport";
        Date resetBeginPeriod = WorkCalendar.resetBeginDate(beginPeriod);
        Date resetEndPeriod = WorkCalendar.resetEndDate(endPeriod);
        try {
            Session s = this.getDbAccessor().getSession();
            return s.createQuery(hql)
                    .setDate("beginPeriod", resetBeginPeriod)
                    .setDate("endPeriod", resetEndPeriod)
                    .setBoolean("isAutoWeeklyReport", true)
                    .list();
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }
    
    /**
     * context ����
     * @return ApplicationContext
     * @throws BeansException
     */
    private ApplicationContext getContext() throws BeansException {
        if(context == null) {
            context = new ClassPathXmlApplicationContext(AbstractBusinessAction.SPRING_CONFIG_MATCH);
        }
        return context;
    }
    
    /**
     * ΪAction�����ṩ�ӿڷ����ѳ�ʼ���õ�ApplicationContext
     * @param context
     */
    public void setContext(ApplicationContext context) {
    	this.context = context;
    }
    
    /**
     * ��Spring��������ȡ��Service Bean
     *
     * @param beanName
     * @return Object
     * @author Robin
     */
    protected Object getBean(String beanName) {
        return getContext().getBean(beanName);
    }

    public static void main(String[] args) {
        int offset = 0;
        int times = 1;
        if(args.length > 0) {
        	try {
        		offset = Integer.parseInt(args[0]);
            } catch (NumberFormatException tx) {
            	log.warn("offset Integer parse error [" + times + "], it's will be default 0", tx);
            }
        }
        if (args.length > 1) {
            try {
            	times = Integer.parseInt(args[1]);
            } catch (NumberFormatException tx) {
                log.warn("times Integer parse error [" + times + "], it's will be default 1", tx);
            }
        }
        AutoGenerateTimeSheet generater = new AutoGenerateTimeSheet();
        int count = 0;
        try {
        	count = generater.runMultiPeriod(offset, times);
        } catch (Exception ex) {
           log.error("Generate failed!", ex);
        }
        log.info(count + " timesheets have(has) successfully generated!");
        System.out.println(count + " timesheets have(has) successfully generated!");
        logoutPrimaveraApi();
        System.exit(0);
    }
    
    /**
     * logout primavera api in current thread
     */
    private static void logoutPrimaveraApi() {
        ThreadVariant thread = ThreadVariant.getInstance();
        com.primavera.integration.client.Session apiSession =  
        	(com.primavera.integration.client.Session) thread.get(IPrimaveraApi.PRIMAVERA_API_SESSION);
        if(apiSession != null) {
            apiSession.logout();
            thread.remove(IPrimaveraApi.PRIMAVERA_API_SESSION);
        }
    }

}
