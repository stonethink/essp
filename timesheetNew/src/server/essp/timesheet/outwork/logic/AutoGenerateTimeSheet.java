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
 * <p>Title: 出差人员自动填写工时单</p>
 * <p>Description: 根据外出人员记录自动生成工时单
 * 		1.时间：需要产生工时单区间与外出时间的交集
 * 		2.工时：取外出人员在当天的标准工时
 * 		3.数据：工时单条目中的项目、Code、Activity取自外出人员记录
 * 		4.状态：Approved，不需要另行审批
 * 		5.如果同一工时区间内有多条外出记录，按多条工时单条目处理。
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
     * 运行多个周期的生成处理
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
     * 运行一个周期的生成处理
     * @param offset
     */
    public int runOnePeriod(int offset) {
    	initPeriod(offset);
    	return run();
    }
    
    /**
     * 初始化当前工时单周期
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
     * 运行生成程序
     *
     */
    private int run() {
    	int count = 0;
    	
    	//如果period无效，直接返回
    	if(validatePeriod() == false) {
    		return count;
    	}
    	
        //先列出区间所有出差记录
        List outWorkerRecords = getOutWorkersRecordsInPeriod(pBegin, pEnd);
        if(outWorkerRecords == null || outWorkerRecords.size() <= 0) {
        	log.info("no out worker between " + pBegin + " and " + pEnd);
        	return count;
        }
        //再对出差记录按人员和项目分组
        Map tsMap = generateTimeSheet(outWorkerRecords);
        if (tsMap == null || tsMap.size() == 0) {
        	log.warn("no timesheet need generate between " + pBegin + " and " + pEnd);
            return count;
        }
        //保存工时单
        Iterator<DtoTimeSheet> it = tsMap.values().iterator();
        while (it.hasNext()) {
        	saveWeeklyReport(it.next());
        	count ++;
        }
        return count;
    }
    
    /**
     * 验证period是否有效
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
     * 判断工时单是否已经存在
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
     * 获取ITimeSheetService
     * @return ITimeSheetService
     */
    private ITimeSheetService getTimeSheetService() {
    	if(timeSheetService == null) {
    		timeSheetService = (ITimeSheetService) getBean("timeSheetService");
    	}
    	return timeSheetService;
    }
    
    /**
     * 获取ICodeValueService
     * @return ICodeValueService
     */
    private ICodeValueService getCodeValueService() {
    	if(codeValueService == null) {
    		codeValueService = (ICodeValueService) getBean("codeValueService");
    	}
    	return codeValueService;
    }
    
    /**
     * 获取IWorkScopeService
     * @return IWorkScopeService
     */
    private IWorkScopeService getWorkScopeService() {
    	if(workScopeService == null) {
    		workScopeService = (IWorkScopeService) getBean("iWorkScopeService");
    	}
    	return workScopeService;
    }
    
    /**
     * 获取IApprovalAssistService
     * @return IApprovalAssistService
     */
    public IApprovalAssistService getApprovalAssistService() {
		if(approvalAssistService == null) {
			approvalAssistService = (IApprovalAssistService) getBean("approvalAssistService");
    	}
    	return approvalAssistService;
	}
    
    /**
     * 获取IApprovalAssistService
     * @return IApprovalAssistService
     */
    public IPreferenceService getPreferenceService() {
		if(preferenceService == null) {
			preferenceService = (IPreferenceService) getBean("preferenceService");
    	}
    	return preferenceService;
	}

    /**
     * 获取IRmMaintDao
     * @return IRmMaintDao
     */
    public IRmMaintDao getRmMaintDao() {
		if(rmMaintDao == null) {
			rmMaintDao = (IRmMaintDao) getBean("rmMaintDao");
    	}
    	return rmMaintDao;
	}
    
    /**
     * 保存新生成的工时单
     * @param dto DtoTimeSheet
     */
    private void saveWeeklyReport(DtoTimeSheet dto) {
    	getTimeSheetService().saveTimeSheet(dto);
    	if(isNeedApproval(dto.getLoginId())) {
    		getApprovalAssistService().writeBackToP3(dto.getRid());
    	}
    }

    /**
     * 生成工时单
     *   如果在同一工时单区间内有多条外出记录，则每条外出记录生成一个工时单条目
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
            //如果此用户在当前工时区间已经存在工时单，不做自动填写
            if(isTimeSheetExist(loginId, pId)) {
            	//记录不需要生成工时单
            	printGenerateLog(loginId, true);
            	continue;
            }
            
            DtoTimeSheet dtoTs = (DtoTimeSheet) result.get(loginId);
            List detailList;
            //如果之前没有记录，就生成一个新的DtoTimeSheet和新的detailList
            //如果之前有记录，就在此基础上新增一条DtoTimeSheetDetail
            if (dtoTs == null) {
            	dtoTs = newDtoTimeSheet(loginId);
            	//记录要生成工时单
            	printGenerateLog(loginId, false);
            	result.put(loginId, dtoTs);
            	detailList = new ArrayList();
            } else {
            	detailList = dtoTs.getTsDetails();
            }
            //根据外出人员记录，生成DtoTimeSheetDetail，及每天的newDtoTimeSheetDay
            DtoTimeSheetDetail dtoDetail = newDtoTimeSheetDetail(outWorker);
            dtoDetail.setTsDays(getTimeSheetDays(outWorker));
            detailList.add(dtoDetail);
            dtoTs.setTsDetails(detailList);
        }
        return result;
    }
    
    /**
     * 为需要或不需要生成工时单的数据记录
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
     * 根据TsOutWorker产生每天的工时数据。
     * 		时间区间：当前工时单区间和TsOutWorker外出周期交集
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
    	//将开始、结束日期转换成日期列表
    	List<Date> dateList = getDateList(getBegin(begin), getEnd(end));
    	if(dateList == null || dateList.size() == 0) {
    		log.warn("is no date between " 
    				+ getBegin(begin) + " and " + getEnd(end)
    				+ " (" + begin + " and " + end + ")");
    		return dayMap;
    	}
    	//根据日期列表获取每天的标准工时
    	List<Double> hourList = getHourList(loginId, dateList);
    	if(hourList == null || hourList.size() == 0) {
    		log.warn("is no hour data between " 
    				+ getBegin(begin) + " and " + getEnd(end)
    				+ " (" + loginId + ")");
    		return dayMap;
    	}
    	//标准工时的数量必须等于或大于（有汇总记录）日期数量
    	if(hourList.size() < dateList.size()) {
    		log.warn("hourList length is not enough -- " 
    				+ getBegin(begin) + " and " + getEnd(end)
    				+ " (" + loginId + ")");
    		return dayMap;
    	}
    	//根据日期列表和工时列表生成 HashMap<Date, DtoTimeSheetDay>
    	for(int i = 0; i < dateList.size(); i ++) {
    		Date d = dateList.get(i);
    		Double h = hourList.get(i);
    		dayMap.put(d, newDtoTimeSheetDay(d, h));
    	}
    	return dayMap;
    }
    
    /**
     * 获取传入时间和工时单开始时间较大的一个
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
     * 获取传入时间和工时单结束时间较小的一个
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
     * 获取指定时间区间每天的时间数据
     * @param begin
     * @param end
     * @return List<Date>
     */
    private List getDateList(Date begin, Date end) {
    	return DtoTimeSheetPeriod.period2DateList(begin, end);
    }
    
    
    /**
     * 获取指定用户在指定时间内的每天的标准工时
     * @param loginId
     * @param dateList
     * @return List<Double>
     */
    private List getHourList(String loginId, List dateList) {
    	return getTimeSheetService().listStandarHours(loginId, dateList);
    }
    
    /**
     * 获取新DtoTimeSheet对象
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
     * 获取新DtoTimeSheetDetail对象
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
     * 根据ActivityId 找ActivityShowName
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
     * 获取新DtoTimeSheetDay对象
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
     * 工时单生成后的状态
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
     * 给每条自动生成的工时单Notes中加入日期，和生成描述。
     * @return String
     */
    protected String getTsNodes() {
    	return today + "\n\t This timesheet is auto generated by ESSP Out Worker Management Module";
    }
    
    /**
     * 生成工时单条目的描述信息
     *    如果外出记录有目的地，则加入描述中。
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
     * 查找与区间有交集且需自动填写周报的出差记录
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
     * context 单例
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
     * 为Action调用提供接口放入已初始化好的ApplicationContext
     * @param context
     */
    public void setContext(ApplicationContext context) {
    	this.context = context;
    }
    
    /**
     * 从Spring的配置中取得Service Bean
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
