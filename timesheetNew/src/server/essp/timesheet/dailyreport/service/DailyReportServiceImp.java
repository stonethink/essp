package server.essp.timesheet.dailyreport.service;

import java.math.BigDecimal;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import server.essp.common.ldap.LDAPUtil;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.account.labor.dao.ILaborDao;
import server.essp.timesheet.dailyreport.dao.IDailyReportDao;
import server.essp.timesheet.dailyreport.exporter.DailyReportExporter;
import server.essp.timesheet.step.management.dao.IStepManagementDao;
import server.essp.timesheet.step.management.dao.IStepManagementP3ApiDao;
import server.essp.timesheet.step.monitoring.dao.IMonitoringP3ApiDao;
import server.essp.timesheet.workscope.dao.IWorkScopeP3ApiDao;
import c2s.dto.*;
import c2s.essp.common.user.DtoUserInfo;
import c2s.essp.timesheet.dailyreport.*;
import c2s.essp.timesheet.step.management.DtoStepBase;

import com.primavera.integration.client.bo.object.Activity;
import com.wits.excel.ICellStyleSwitch;
import com.wits.util.Parameter;
import com.wits.util.comDate;

import db.essp.timesheet.*;

public class DailyReportServiceImp implements IDailyReportService {
	private static final String vmFile = "mail/template/ts/DailyReportMail.htm";
	private IStepManagementDao stepDao;
	private IDailyReportDao dailyReportDao;
	private IMonitoringP3ApiDao monitoringP3ApiDao;
	private IStepManagementP3ApiDao stepManagementP3ApiDao;
	private IAccountDao accountDao;
	private IWorkScopeP3ApiDao workScopeP3ApiDao;
	private ILaborDao laborDao;
	

	public void setLaborDao(ILaborDao laborDao) {
		this.laborDao = laborDao;
	}


	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setDailyReportDao(IDailyReportDao dailyReportDao) {
		this.dailyReportDao = dailyReportDao;
	}


	public List listByActivityId(String loginId, Long activityId, Long accountRid) {
		List resultList = new ArrayList();
		List list = null;
		//查询默认的step信息
		list = stepDao.listDeaultStepsByLongId(loginId, activityId);
		DtoDrStep dto = null;
		TsStep tsStep = null;
		for (int i = 0; i < list.size(); i++) {
			tsStep = (TsStep) list.get(i);
			dto = new DtoDrStep();
			DtoUtil.copyBeanToBean(dto, tsStep, new String[]{"rid"});
			dto.setStepRid(tsStep.getRid());
			dto.setIsFinish(tsStep.getCompleteFlag());
			dto.setItem(tsStep.getProcName());
			dto.setIsDBData(false);
			dto.setIsEditable(false);
			if(loginId.equalsIgnoreCase(tsStep.getResourceId())) {
				dto.setIsAssigned(true);
			} else {
				dto.setIsAssigned(false);
			}
			dto.setAccountRid(accountRid);
			resultList.add(dto);
		}
		return resultList;
	}


	public void setStepDao(IStepManagementDao stepDao) {
		this.stepDao = stepDao;
	}


	public void saveDailyReport(List<DtoDrActivity> list, String loginId, Date day) {
		List<DtoDrStep> stepList = null;
		TsStep tsStep = new TsStep();
		TsDailyReport tsDailyReport = null;
		TsDailyReport lastRecord = null;
		//循环所填的Activity
		for(DtoDrActivity dtoDrActivity : list) {
			stepList = dtoDrActivity.getStepList();
			if(stepList == null || stepList.size() == 0) {
				continue;
			}
			//循环所填工作项
			for(DtoDrStep dtoDrStep : stepList) {
				tsStep = new TsStep();
				//查询step
				if(dtoDrStep.getStepRid() != null) {
					tsStep = stepDao.loadByRid(dtoDrStep.getStepRid());
				}
				//判断是否是数据库已存在数据
				if(dtoDrStep.getIsDBData()) {
					//如果存在则更新相关信息
					tsDailyReport = dailyReportDao.loadDailtReportByRid(dtoDrStep.getRid());
					if(dtoDrStep.getWorkTime() == 0) {
						dailyReportDao.delete(tsDailyReport);
						continue;
					}
					tsDailyReport.setItem(dtoDrStep.getItem());
					tsDailyReport.setWorkTime(dtoDrStep.getWorkTime());
					tsDailyReport.setDescription(dtoDrStep.getDescription());
					tsDailyReport.setCodeValueRid(dtoDrActivity.getCodeValueRid());
					tsDailyReport.setCodeValueName(dtoDrActivity.getCodeValueName());
					dailyReportDao.updateDailyReport(tsDailyReport);
				} else {
					//如果是新增数据库
					if(dtoDrStep.getWorkTime()== null || dtoDrStep.getWorkTime() == 0) {
						//所填写工时为0或没有填工时时只设置其相应的完成状态
						if(tsStep.getRid() != null){
							//如果完成设置step的实际完成日期
							if(dtoDrStep.getIsFinish() != null && dtoDrStep.getIsFinish()) {
								//如果钩选完成设置完成日期
								//查询数据库中该step已经填写的日报信息查出日期最晚的那天记录
								//并设置实际完成日期为最晚的那个日期
								lastRecord = dailyReportDao.loadLastReport(tsStep.getRid());
								//如果没有查询到则设置结束日期为填写周报的日期
								if(lastRecord != null) {
									tsStep.setActualFinish(comDate.toDate(comDate.dateToString(lastRecord.getReportDate(), "yyyy-MM-dd")));
								} else {
									tsStep.setActualFinish(comDate.toDate(comDate.dateToString(day, "yyyy-MM-dd")));
								}
								//如果实际开始为空设置实际开始为实际结束日期
								if(tsStep.getActualStart() == null) {
									tsStep.setActualStart(tsStep.getActualFinish());
								}
							} else {
								tsStep.setActualFinish(null);
							}
							tsStep.setCompleteFlag(dtoDrStep.getIsFinish());
							
							stepDao.updateStep(tsStep);
						}
						continue;
					}
					tsDailyReport = new TsDailyReport();
					tsDailyReport.setItem(dtoDrStep.getItem());
					tsDailyReport.setWorkTime(dtoDrStep.getWorkTime());
					//如果第一次填写设置step的实际开始日期
					//如果数据库中已有实际开始日期与填写日报日期比较选取较小的日期作为实际开始日期
					//如果没有实际开始日期则将填写日报日期设置为实际开始日期
					if(tsStep.getRid() != null) {
						tsDailyReport.setProjId(tsStep.getProjId());
						if(tsStep.getActualStart() != null && day.before(tsStep.getActualStart())) {
							tsStep.setActualStart(comDate.toDate(comDate.dateToString(day, "yyyy-MM-dd")));
						}
						if(tsStep.getActualStart() == null) {
							tsStep.setActualStart(comDate.toDate(comDate.dateToString(day, "yyyy-MM-dd")));
						}
					}
					tsDailyReport.setProjName(dtoDrActivity.getAccountName());
					tsDailyReport.setTaskId(dtoDrStep.getTaskId());
					tsDailyReport.setTaskName(dtoDrActivity.getActivityName());
					tsDailyReport.setStepRid(dtoDrStep.getStepRid());
					tsDailyReport.setCodeValueRid(dtoDrActivity.getCodeValueRid());
					tsDailyReport.setCodeValueName(dtoDrActivity.getCodeValueName());
					tsDailyReport.setReportDate(comDate.toDate(comDate.dateToString(day, "yyyy-MM-dd")));
					tsDailyReport.setDescription(dtoDrStep.getDescription());
					tsDailyReport.setResourceId(loginId);
					tsDailyReport.setAccountRid(dtoDrStep.getAccountRid());
					tsDailyReport.setRst("N");
					dtoDrStep.setIsDBData(true);
					dailyReportDao.addDailyReport(tsDailyReport);
				}
				//对于数据库中已存在的信息更新完成日期
				if(tsStep.getRid() != null){
					//如果完成设置step的实际完成日期
					if(dtoDrStep.getIsFinish() != null && dtoDrStep.getIsFinish()) {
						lastRecord = dailyReportDao.loadLastReport(tsStep.getRid());
						if(lastRecord != null) {
							tsStep.setActualFinish(comDate.toDate(comDate.dateToString(lastRecord.getReportDate(), "yyyy-MM-dd")));
						} else {
							tsStep.setActualFinish(comDate.toDate(comDate.dateToString(day, "yyyy-MM-dd")));
						}
						if(tsStep.getActualStart() == null) {
							tsStep.setActualStart(tsStep.getActualFinish());
						}
					} else {
						tsStep.setActualFinish(null);
					}
					tsStep.setCompleteFlag(dtoDrStep.getIsFinish());
					
					stepDao.updateStep(tsStep);
				}
				
			}
		}
		
	}

	public List listActivityInDB(String loginId, Date day) {
		List resultList = new ArrayList();
		if(day == null) {
			return resultList;
		}
		List<TsDailyReport> dailyReportList = dailyReportDao.listTodayDailyReportByLoginId(loginId, day);
		DtoDrActivity dto = null;
		
		Map activityMap = new HashMap();
		DtoDrStep dtoDrStep = null;
		TsStep tsStep = null;
		List stepList = null;
		Map codeMap = new HashMap();
		for(TsDailyReport tsDailyReport : dailyReportList) {
			dtoDrStep = new DtoDrStep();
			dtoDrStep.setIsDBData(true);
			dtoDrStep.setIsAssigned(false);
			if(tsDailyReport.getStepRid() != null){
				tsStep = stepDao.loadByRid(tsDailyReport.getStepRid());
				dtoDrStep.setIsFinish(tsStep.getCompleteFlag());
				if(loginId.equalsIgnoreCase(tsStep.getResourceId())) {
					dtoDrStep.setIsAssigned(true);
				}
			}
			dtoDrStep.setIsEditable(false);
			DtoUtil.copyBeanToBean(dtoDrStep, tsDailyReport);
			//如果是Activity
			if(tsDailyReport.getTaskId() != null && activityMap.containsKey(tsDailyReport.getTaskId())) {
				dto = (DtoDrActivity) activityMap.get(tsDailyReport.getTaskId());
				dto.getStepList().add(dtoDrStep);
				dto.setWorkTime(dto.getWorkTime() + tsDailyReport.getWorkTime());
			} else if(tsDailyReport.getTaskId() != null && !activityMap.containsKey(tsDailyReport.getTaskId())){
				dto = new DtoDrActivity();
				stepList = new ArrayList();
				stepList.add(dtoDrStep);
				dto.setStepList(stepList);
				dto.setActivityId(tsDailyReport.getTaskId());
				dto.setActivityName(tsDailyReport.getTaskName());
				dto.setCodeValueRid(tsDailyReport.getCodeValueRid());
				dto.setCodeValueName(tsDailyReport.getCodeValueName());
				dto.setAccountRid(tsDailyReport.getAccountRid());
				dto.setWorkTime(tsDailyReport.getWorkTime());
				activityMap.put(tsDailyReport.getTaskId(), dto);
			}
			//如果是Code
			if(tsDailyReport.getTaskId() == null && codeMap.containsKey(tsDailyReport.getCodeValueRid())) {
				dto = (DtoDrActivity) codeMap.get(tsDailyReport.getCodeValueRid());
				dto.getStepList().add(dtoDrStep);
				dto.setWorkTime(dto.getWorkTime() + tsDailyReport.getWorkTime());
			} else if(tsDailyReport.getTaskId() == null && !codeMap.containsKey(tsDailyReport.getCodeValueRid())) {
				dto = new DtoDrActivity();
				stepList = new ArrayList();
				stepList.add(dtoDrStep);
				dto.setStepList(stepList);
				dto.setActivityId(tsDailyReport.getTaskId());
				dto.setActivityName(tsDailyReport.getTaskName());
				dto.setCodeValueRid(tsDailyReport.getCodeValueRid());
				dto.setCodeValueName(tsDailyReport.getCodeValueName());
				dto.setAccountRid(tsDailyReport.getAccountRid());
				dto.setWorkTime(tsDailyReport.getWorkTime());
				codeMap.put(tsDailyReport.getCodeValueRid(), dto);
			}
			dto.setAccountName(tsDailyReport.getProjName());
			if(tsDailyReport.getTaskId() != null){
				dto.setActivity(true);
			} else {
				dto.setActivity(false);
			}
		}
		resultList.addAll(new ArrayList(activityMap.values()));
		resultList.addAll(new ArrayList(codeMap.values()));
		return resultList;
	}


	public List showAllSteps(DtoDrActivity dto, String loginId) {
		List<TsStep> stepList = stepDao.listStepByActivityId(dto.getActivityId());
		if(stepList == null || stepList.size() == 0) {
			return dto.getStepList();
		}
		List<DtoDrStep> list = dto.getStepList();
		int size = list.size();
		boolean isExist = false;
		for(TsStep step : stepList) {
			isExist = false;
			if (list != null && size > 0) {
				for (DtoDrStep dtoDrStep : list) {
					if (step.getRid().equals(dtoDrStep.getStepRid())) {
						isExist = true;
						break;
					}
				}
			}
			if(isExist) {
				continue;
			}
			DtoDrStep dtoStep = new DtoDrStep();
			dtoStep.setCodeValueRid(dto.getCodeValueRid());
			dtoStep.setIsDBData(false);
			dtoStep.setIsFinish(step.getCompleteFlag());
			dtoStep.setItem(step.getProcName());
			dtoStep.setProjId(step.getProjId());
			dtoStep.setProjName(dto.getAccountName());
			dtoStep.setResourceId(step.getResourceId());
			dtoStep.setStepRid(step.getRid());
			dtoStep.setTaskId(step.getTaskId());
			dtoStep.setTaskName(dto.getActivityName());
			dtoStep.setAccountRid(dto.getAccountRid());
			dtoStep.setIsDBData(false);
			dtoStep.setIsEditable(false);
			if(loginId.equalsIgnoreCase(step.getResourceId())) {
				dtoStep.setIsAssigned(true);
			} else {
				dtoStep.setIsAssigned(false);
			}
			dto.getStepList().add(dtoStep);
		}
		return dto.getStepList();
	}


	public void delDailyReportInAct(Date day, DtoDrActivity dto, String loginId) {
		dailyReportDao.delDailyReport(day, dto, loginId);
	}


	public List displayAllData(Date day, String loginId) {
		List resultList = new ArrayList();
		if(day == null) {
			return resultList;
		}
		List<TsDailyReport> dailyReportList = dailyReportDao.listTodayDailyReportByLoginId(loginId, day);
		if(dailyReportList == null || dailyReportList.size() == 0) {
			return resultList;
		}
		DtoAll dtoAll = null;
		
		TsStep tsStep = null;
		TsDailyReport tsDailyReport = null;
		int size = dailyReportList.size();
		Map dtoMap = new HashMap();
		Map codeMap = new HashMap();
		for(int i = 0;i<size;i++) {
			dtoAll = new DtoAll();
			tsDailyReport = dailyReportList.get(i);
			dtoAll.setItem(tsDailyReport.getItem());
			dtoAll.setStepWorkTime(tsDailyReport.getWorkTime());
			if(tsDailyReport.getStepRid() != null){
				tsStep = stepDao.loadByRid(tsDailyReport.getStepRid());
				dtoAll.setIsFinish(tsStep.getCompleteFlag());
			}
			if(tsDailyReport.getTaskId() == null && codeMap.containsKey(tsDailyReport.getCodeValueRid())) {
				dtoAll = (DtoAll) codeMap.get(tsDailyReport.getCodeValueRid());
				dtoAll.setActivityWorkTime(dtoAll.getActivityWorkTime() + tsDailyReport.getWorkTime());
				dtoAll = new DtoAll();
				dtoAll.setItem(tsDailyReport.getItem());
				dtoAll.setStepWorkTime(tsDailyReport.getWorkTime());
				resultList.add(dtoAll);
			} else if(tsDailyReport.getTaskId() == null && !codeMap.containsKey(tsDailyReport.getCodeValueRid())) {
				dtoAll.setAccountName(tsDailyReport.getProjName());
				dtoAll.setActivityName(tsDailyReport.getTaskName());
				dtoAll.setCodeValueName(tsDailyReport.getCodeValueName());
				dtoAll.setActivityWorkTime(tsDailyReport.getWorkTime());
				codeMap.put(tsDailyReport.getCodeValueRid(), dtoAll);
				resultList.add(dtoAll);
			}
			if(tsDailyReport.getTaskId() != null && dtoMap.containsKey(tsDailyReport.getTaskId())) {
				dtoAll = (DtoAll) dtoMap.get(tsDailyReport.getTaskId());
				dtoAll.setActivityWorkTime(dtoAll.getActivityWorkTime() + tsDailyReport.getWorkTime());
				dtoAll = new DtoAll();
				dtoAll.setItem(tsDailyReport.getItem());
				dtoAll.setStepWorkTime(tsDailyReport.getWorkTime());
				if(tsDailyReport.getStepRid() != null){
					tsStep = stepDao.loadByRid(tsDailyReport.getStepRid());
					dtoAll.setIsFinish(tsStep.getCompleteFlag());
				}
				resultList.add(dtoAll);
			} else if(tsDailyReport.getTaskId() != null && !dtoMap.containsKey(tsDailyReport.getTaskId())){
				dtoAll.setAccountName(tsDailyReport.getProjName());
				dtoAll.setActivityName(tsDailyReport.getTaskName());
				dtoAll.setCodeValueName(tsDailyReport.getCodeValueName());
				dtoAll.setActivityWorkTime(tsDailyReport.getWorkTime());
				dtoMap.put(tsDailyReport.getTaskId(), dtoAll);
				resultList.add(dtoAll);
			}
		}
		return resultList;
	}


	public void deleteDailyReport(DtoDrStep dtoDrStep) {
		if(dtoDrStep.getRid() == null) {
			return;
		}
		TsDailyReport tsDailyReport = dailyReportDao.loadDailtReportByRid(dtoDrStep.getRid());
		if(tsDailyReport != null) {
			dailyReportDao.delete(tsDailyReport);
		}
		
	}
	private DtoDailyExport getDtoExportInstance() {
		return new StyledDtoExport();
	}
	
	public class StyledDtoExport extends DtoDailyExport implements
			ICellStyleSwitch {
		protected static final String STYLE_NAME_ACTIVITY = "Style_Activity";
		protected static final String STYLE_NAME_STEP = "Style_Step";
		protected static final String STYLE_NAME_DELAY = "Style_Delay";
		protected static final String STYLE_NAME_NORMAL = "Style_Normal";
		protected static final String STYLE_NAME_AHEAD = "Style_Ahead";
		public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
			if (styleName != null && !"".equals(styleName)) {
		         cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
		         if(STYLE_NAME_ACTIVITY.equals(styleName)) {
		        	 cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		         } else if(STYLE_NAME_STEP.equals(styleName)) {
		        	 cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		         } else if(STYLE_NAME_DELAY.equals(styleName)) {
		        	 cellStyle.setFillForegroundColor(HSSFColor.RED.index);
		         } else if(STYLE_NAME_NORMAL.equals(styleName)) {
		        	 cellStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
		         } else if(STYLE_NAME_AHEAD.equals(styleName)) {
		        	 cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
		         }
		     }
			return cellStyle;
		}

		public String getStyleName(String propertyName) {
			if("statusIndicator".equals(propertyName)) {
				 if(DtoStepBase.DELAY.equals(this.getStatusIndicator())) {
					 return STYLE_NAME_DELAY;
				 } else if(DtoStepBase.AHEAD.equals(this.getStatusIndicator())) {
					 return STYLE_NAME_AHEAD;
				 } else {
					 return STYLE_NAME_NORMAL;
				 }
			} else if(this.getIsAcitvity()){
				return STYLE_NAME_ACTIVITY;
			} else {
				return STYLE_NAME_STEP;
			}
		}
	}
	/**
	 * 查询日报信息并发送邮件
	 */
	public void exportAndSendMail(Date today, Date yesterday) throws Exception{
		List<String> accountIdList = accountDao.listMailProject();
		if(accountIdList == null || accountIdList.size() == 0) {
			return;
		}
    	List<String> persons = null;
    	Map dataMap = null;
    	DailyReportExporter exporter = null;
    	SendHastenMail shMail = new SendHastenMail();
    	Parameter inputData = new Parameter();
    	ContentBean cb = null;
		String userMail = null;
		String userName = null;
		DtoUserInfo dtoUser = null;
		HashMap hm = null;
		LDAPUtil ldapUtil = new LDAPUtil();
		ArrayList al = null;
		HashMap attachments = new HashMap();
		for(String accountId : accountIdList) {
			persons = laborDao.listPersonInProject(accountId);
			if(persons == null || persons.size() == 0) {
				continue;
			}
			//查数据
			dataMap = queryForExport(accountId, today, yesterday);
			for(String resourceId : persons) {
				exporter = new DailyReportExporter();
				al = new ArrayList();
				cb = new ContentBean();
				hm = new HashMap();
				inputData.put(DtoDailyExport.DTO_RESULTMAP, dataMap);
				inputData.put("resourceId", resourceId);
				inputData.put("today", today);
				inputData.put("yesterday", yesterday);
		        dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, resourceId);
				if (dtoUser != null) {
	                userMail = dtoUser.getEmail();
	                userName = dtoUser.getUserName();
	                cb.setUser(userName);
	                cb.setEmail(userMail);
	                cb.setMailcontent(al);
	                attachments.put("DailyReport_"+comDate.dateToString(today)+".xls", exporter.getExportFile(inputData));
	                cb.setAttachments(attachments);
	                hm.put(userName, cb);
				}
		        shMail.sendHastenMail(vmFile, "Daily Report "+comDate.dateToString(today), hm);
			}
		}
	}
	private Map queryForExport(String accountId, Date today, Date yesterday) 
	                       throws Exception {
		Map yesterdayDataMap = new HashMap();
		Map todayDataMap = new HashMap();
		ITreeNode projectTree = new DtoTreeNode(null);
		Map resultMap = new HashMap();
		resultMap.put("yesterday", yesterdayDataMap);
		resultMap.put("today", todayDataMap);
		resultMap.put("name", "");
		TsAccount account = accountDao.loadByAccountId(accountId);
		if(account == null) {
			return resultMap;
		}
		String accountName = accountId + " -- " + account.getAccountName();
		resultMap.put("name", accountName);
		//查询与昨天相关的Activity资料
		List<Activity> activityList = monitoringP3ApiDao.listActivityByAcntId(accountId, yesterday, DtoDailyExport.DTO_YESTERDAY);
		if(activityList != null || activityList.size() >= 0) {
			//查询昨天相关Step信息
			yesterdayDataMap = getTreesMap(activityList, yesterday, DtoDailyExport.DTO_YESTERDAY);
		}
		//查询与今天相关的Activity资料
		activityList = monitoringP3ApiDao.listActivityByAcntId(accountId, today, DtoDailyExport.DTO_TODAY);
		if(activityList != null || activityList.size() >= 0) {
			//查询今天相关Step信息
			todayDataMap = getTreesMap(activityList, today, DtoDailyExport.DTO_TODAY);
		}
		
		resultMap.put("yesterday", yesterdayDataMap);
		resultMap.put("today", todayDataMap);
		return resultMap;
	}
	private String getStepStatus(boolean isFinish) {
		if(isFinish) {
			return DtoDailyExport.DTO_STATUS_FINISH;
		}
		return DtoDailyExport.DTO_STATUS_NONFINISH;
	}

	public void setMonitoringP3ApiDao(IMonitoringP3ApiDao monitoringP3ApiDao) {
		this.monitoringP3ApiDao = monitoringP3ApiDao;
	}


	public void setWorkScopeP3ApiDao(IWorkScopeP3ApiDao workScopeP3ApiDao) {
		this.workScopeP3ApiDao = workScopeP3ApiDao;
	}
	private BigDecimal setScale(Double wPercent){
        BigDecimal percent =  BigDecimal.valueOf(wPercent);
        percent = percent.setScale(0,BigDecimal.ROUND_HALF_UP);
        return percent;
 }
	private Map getTreesMap(List<Activity> activityList, Date day, String date) throws Exception {
		Map personMap = new HashMap();//人员对应Activity和Step信息Map
		Map resultMap = new HashMap();
		DtoDailyExport dtoExport = null;//Step资料结点
		DtoDailyExport dtoExportAct = null;//Activity资料结点
		DtoTreeNode child = null;
		ITreeNode personRoot = null;//人员资料根结点
		ITreeNode root=new DtoTreeNode(null);//专案资料根结点
		ITreeNode actNode = null;//专案Activity结点
		ITreeNode personActNode = null;//人员Activity结点
		Map personActMap = new HashMap();
		Long taskId = null;
		List<TsStep> stepList = null;
		Map relationMap = new HashMap();
		Map actMap = null;
		Map resourceMap = new HashMap();
		BigDecimal percent = null;
		for(Activity act : activityList) {
			taskId = Long.valueOf(act.getObjectId().toString());
			dtoExportAct = getDtoExportInstance();
			dtoExportAct.setActivityName(workScopeP3ApiDao.getActivityShowName(taskId));
			dtoExportAct.setPlanStart(act.getPlannedStartDate());
			dtoExportAct.setPlanFinish(act.getPlannedFinishDate());
			dtoExportAct.setActualStart(act.getActualStartDate());
			dtoExportAct.setActualFinish(act.getActualFinishDate());
			//获取Activity中的资源0
			resourceMap = stepManagementP3ApiDao.getActivityPResourcesId(act);
			dtoExportAct.setResourceId((String)resourceMap.get("ID"));
			dtoExportAct.setResourceName((String)resourceMap.get("NAME"));
			percent =  setScale(nvl(act.getPercentComplete().doubleValue())*100);
			dtoExportAct.setCompletePcr(percent + "%");
			dtoExportAct.setStatus(act.getStatus().getDescription());
			dtoExportAct.setIsAcitvity(true);
			actNode = new DtoTreeNode(dtoExportAct);
			personActNode = new DtoTreeNode(dtoExportAct);
			root.addChild(actNode);
			//查询Activity下的Step信息
			stepList = stepDao.listProjectStep(taskId, day, date);
			if(stepList == null || stepList.size() == 0) {
				continue;
			}
			personActMap.clear();
			for(TsStep tsStep : stepList) {
				dtoExport = getDtoExportInstance();
				dtoExport.setItem(tsStep.getProcName());
				dtoExport.setPlanStart(tsStep.getPlanStart());
				dtoExport.setPlanFinish(tsStep.getPlanFinish());
				dtoExport.setActualStart(tsStep.getActualStart());
				dtoExport.setActualFinish(tsStep.getActualFinish());
				dtoExport.setResourceId(tsStep.getResourceId());
				dtoExport.setResourceName(tsStep.getResourceName());
				dtoExport.setWeight(tsStep.getProcWt());
				percent = setScale(nvl(tsStep.getCompletePct()).doubleValue()*100);
				dtoExport.setCompletePcr(percent + "%");
				dtoExport.setStatus(getStepStatus(tsStep.getCompleteFlag()));
				dtoExport.setIsAcitvity(false);
				child = new DtoTreeNode(dtoExport);
				actNode.addChild(child);
				//如果人员Activity资料中没有该人员资料则加入该人员Activity结点和结点下的Step结点
				if(!personActMap.containsKey(tsStep.getResourceId())) {
					personActNode = new DtoTreeNode(dtoExportAct);
					personActNode.addChild(child);
					personActMap.put(tsStep.getResourceId(), personActNode);
				} else {//如果存在则向人员Activity结点中加入Step信息
					personActNode = (ITreeNode) personActMap.get(tsStep.getResourceId());
					personActNode.addChild(child);
				}
				//如果人员资料中没有该人员信息则加入该人的相关信息
				if(!personMap.containsKey(tsStep.getResourceId())) {
					personRoot = new DtoTreeNode(null);
					personRoot.addChild((ITreeNode)personActMap.get(tsStep.getResourceId()));
					personMap.put(tsStep.getResourceId(), personRoot);
					actMap = new HashMap();
					actMap.put(taskId, taskId);
					relationMap.put(tsStep.getResourceId(), actMap);//人员对应已经加入的Activity信息
				} else {
					//如果存在则从关系Map中取人员相关的Activity如果不存在则加入该Activity结点信息
					actMap = (Map) relationMap.get(tsStep.getResourceId());
					if(!actMap.containsKey(taskId)) {
						personRoot = (ITreeNode) personMap.get(tsStep.getResourceId());
						personRoot.addChild((ITreeNode)personActMap.get(tsStep.getResourceId()));
						actMap.put(taskId, taskId);
					}
				}
			}
		}
		resultMap.put("person", personMap);
		resultMap.put("project", root);
		return resultMap;
	}
	private Double nvl(Double d) {
		if(d == null) {
			return new Double(0);
		}
		return d;
	}
	/**
	 * 发送邮件
	 * @param vmFile
	 * @param title
	 * @param hm
	 */
	private void sendMail(final String vmFile, final String title, final HashMap hm) {
		final SendHastenMail shMail = new SendHastenMail();
        shMail.sendHastenMail(vmFile, title, hm);
	}


	public void setStepManagementP3ApiDao(
			IStepManagementP3ApiDao stepManagementP3ApiDao) {
		this.stepManagementP3ApiDao = stepManagementP3ApiDao;
	}

}
