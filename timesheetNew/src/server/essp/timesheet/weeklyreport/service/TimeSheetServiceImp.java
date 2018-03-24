package server.essp.timesheet.weeklyreport.service;

import java.math.BigDecimal;
import java.util.*;

import com.wits.util.ThreadVariant;
import com.wits.util.comDate;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.weeklyreport.*;
import db.essp.timesheet.*;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.approval.service.IApprovalAssistService;
import server.essp.timesheet.code.codevalue.dao.ICodeValueDao;
import server.essp.timesheet.dailyreport.dao.IDailyReportDao;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.weeklyreport.dao.IAttLeaveDao;
import server.essp.timesheet.weeklyreport.dao.IAttOvertimeDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3ApiDao;
import server.framework.common.BusinessException;

/**
 *
 * <p>Title: TimeSheetServiceImp</p>
 *
 * <p>Description: 与工时单填写相关的功能实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class TimeSheetServiceImp implements ITimeSheetService {
    private ITimeSheetDao timeSheetDao;
    private ITimeSheetP3ApiDao timeSheetApiDao;
    private IPreferenceDao preferenceDao;
    private IRmMaintDao rmMaintDao;
    private IAttLeaveDao attLeaveDao;
    private IAttOvertimeDao attOvertimeDao;
    private IAccountDao accountDao;
    private IRmMaintService rmMaintService;
    private ICodeValueDao codeValueDao;
    private IApprovalAssistService approvalAssistService;
    private IDailyReportDao dailyReportDao;

    public void setDailyReportDao(IDailyReportDao dailyReportDao) {
		this.dailyReportDao = dailyReportDao;
	}

	public void setCodeValueDao(ICodeValueDao codeValueDao) {
		this.codeValueDao = codeValueDao;
	}

	public void setRmMaintService(IRmMaintService rmMaintService) {
		this.rmMaintService = rmMaintService;
	}

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public void setAttLeaveDao(IAttLeaveDao attLeaveDao) {
		this.attLeaveDao = attLeaveDao;
	}

	public void setAttOvertimeDao(IAttOvertimeDao attOvertimeDao) {
		this.attOvertimeDao = attOvertimeDao;
	}

	public void setRmMaintDao(IRmMaintDao rmMaintDao) {
		this.rmMaintDao = rmMaintDao;
	}

	public void setTimeSheetApiDao(ITimeSheetP3ApiDao timeSheetApiDao) {
        this.timeSheetApiDao = timeSheetApiDao;
    }

    public void setPreferenceDao(IPreferenceDao preferenceDao) {

        this.preferenceDao = preferenceDao;
    }

    public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
        this.timeSheetDao = timeSheetDao;
    }

    /**
     * active timesheet 将状态为Rejected的TimeSheet的状态设为Active
     *
     * @param tsId Long
     * @param rsrcId Long
     */
    public void activeTimeSheet(DtoTimeSheet dtoTimeSheet) {
        dtoTimeSheet.setStatus(DtoTimeSheet.STATUS_ACTIVE);
        dtoTimeSheet.setStatusDate(new Date());
        dtoTimeSheet.setOp(IDto.OP_MODIFY);
        List<DtoTimeSheetDetail> details = dtoTimeSheet.getTsDetails();
        if (details != null) {
            for (DtoTimeSheetDetail detail : details) {
                detail.setStatus(DtoTimeSheet.STATUS_ACTIVE);
                detail.setOp(IDto.OP_MODIFY);
            }
        }

        this.saveTimeSheet(dtoTimeSheet);
    }
    
    public DtoTimeSheet copyFromLastTs(DtoTimeSheet dtoTimeSheet) {
    	if(dtoTimeSheet == null) {
    		return null;
    	}
    	DtoTimeSheet dto = TsTimesheetMaster2DtoTimeSheet(
                timeSheetDao.getLastTimeSheetMaster(dtoTimeSheet.getLoginId(),
                		dtoTimeSheet.getBeginDate()));
    	copyTimeSheetDetails(dtoTimeSheet, dto);
    	return dto;
    }
    
    private static void copyTimeSheetDetails(DtoTimeSheet dtoTimeSheet, DtoTimeSheet dto) {
    	List<DtoTimeSheetDetail> detailList = dto.getTsDetails();
    	for(DtoTimeSheetDetail dtoDetail : detailList) {
    		dtoDetail.setRid(null);
    		dtoDetail.setOp(IDto.OP_INSERT);
    		dtoDetail.setStatus(DtoTimeSheet.STATUS_ACTIVE);
    		dtoDetail.getTsDays().clear();
    	}
    	dtoTimeSheet.setTsDetails(detailList);
    }

    /**
     * 获取TimeSheet及其ResourceHours
     * @param tsId Long
     * @param loginId String
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(Long tsId, String loginId) {
        return TsTimesheetMaster2DtoTimeSheet(
            timeSheetDao.getTimeSheetMaster(loginId, tsId));
    }
    
    /**
     * 判断工时单是否已经存在
     * @param tsId
     * @param loginId
     * @return
     */
    public boolean isTimeSheetExist(Long tsId, String loginId) {
    	return timeSheetDao.isFillTS(loginId, tsId);
    }

    /**
     * 根据Rid获取DtoTimeSheet
     * @param rid Long
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(Long rid) {
        return TsTimesheetMaster2DtoTimeSheet(
            timeSheetDao.getTimeSheetMaster(rid));
    }

    /**
     * 将TsTimesheetMaster转换成DtoTimeSheet，
     *             并加载TsTimesheetDetail和DtoTimeSheetDay
     * @param bean TsTimesheetMaster
     * @return DtoTimeSheet
     */
    private DtoTimeSheet TsTimesheetMaster2DtoTimeSheet(TsTimesheetMaster bean) {
        if(bean == null) return null;

        DtoTimeSheet dto = new DtoTimeSheet();
        DtoUtil.copyBeanToBean(dto, bean);
        String site = getSite(bean.getLoginId());
        if(site == null) {
        	throw new BusinessException("error.logic.TimeSheetServiceImp.noSite", 
        			bean.getLoginId() + " has no site info! ");
        }
        TsParameter param = preferenceDao.loadPreferenceBySite(site);
        if(param != null) {
            dto.setDecimalDigit(param.getHrDecimalCnt().intValue());
            dto.setCanBeforeActivityStart(param.getPrestartTaskHrsFlag());
            dto.setCanAfterActivityFinish(param.getPostendTaskHrsFlag());
            DtoPreference dtoPrefer = new DtoPreference();
            DtoUtil.copyBeanToBean(dtoPrefer, param);
            dto.setPreference(dtoPrefer);
        }
        List dateList = DtoTimeSheetPeriod.period2DateList(dto.getBeginDate(),
                dto.getEndDate());
        dto.setStandarHours(listStandarHours(bean.getLoginId(), dateList));
        dto.setLeaveHours(listLeaveHours(bean.getLoginId(), dateList));
        dto.setOvertimeHours(listOvertimeHours(bean.getLoginId(), dateList));


        //get timesheet detail
        List<TsTimesheetDetail> details =
            timeSheetDao.listTimeSheetDetail(bean.getRid());
        List dtoDetails = new ArrayList();
        for(TsTimesheetDetail detail : details) {
            DtoTimeSheetDetail dtoDetail = new DtoTimeSheetDetail();
            DtoUtil.copyBeanToBean(dtoDetail, detail);
            dtoDetails.add(dtoDetail);
            try {
                timeSheetApiDao.getActivityPlanDate(dtoDetail);
            } catch(Exception e) {
                //get activity error
            }
            //get timesheet day
            List<TsTimesheetDay> days = timeSheetDao.getTimeSheetDay(detail.getRid());
            if(days == null) continue;
            HashMap dayMap = new HashMap();
            for(TsTimesheetDay day : days) {
                DtoTimeSheetDay dtoDay = new DtoTimeSheetDay();
                DtoUtil.copyBeanToBean(dtoDay, day);
                dayMap.put(dtoDay.getWorkDate(), dtoDay);
            }
            dtoDetail.setTsDays(dayMap);

        }
        dto.setTsDetails(dtoDetails);
        return dto;
    }

    /**
     * 获取T当前用户workDay所在的DtoTimeSheet
     * @param workDay Date
     * @return DtoTimeSheet
     */
    public DtoTimeSheet getTimeSheet(String loginId, Date workDay) {
        DtoTimeSheetPeriod period;
        String site = getSite(loginId);
        if(site == null) {
        	throw new BusinessException("error.logic.TimeSheetServiceImp.noSite", 
        			loginId + " has no site info! ");
        }
        TsParameter param = preferenceDao.loadPreferenceBySite(site);
        //检查出入的日期是否在允许的范围内
        if(param == null || isWorkDateEnable(workDay, param.getFutureTsCnt(),
                            param.getPastTsCnt(), param.getFutureTsHrsFlag()) == false) {
            return null;
        }
        try {
            period = timeSheetApiDao.getTimeSheetPeriod(workDay);
        } catch (Exception ex) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.getTimesheetErr",
                                        "get timesheet period error", ex);
        }
        if(period == null)  return null;
        DtoTimeSheet timeSheet = getTimeSheet(period.getTsId(), loginId);
        if(timeSheet == null) {
            timeSheet = new DtoTimeSheet();
            timeSheet.setOp(IDto.OP_INSERT);
            timeSheet.setLoginId(loginId);
            timeSheet.setTsDetails(new ArrayList());
            timeSheet.setTsId(period.getTsId());
            timeSheet.setBeginDate(period.getBeginDate());
            timeSheet.setEndDate(period.getEndDate());
            timeSheet.setStandarHours(listStandarHours(loginId, period.getDateList()));
            timeSheet.setLeaveHours(listLeaveHours(loginId, period.getDateList()));
            timeSheet.setOvertimeHours(listOvertimeHours(loginId, period.getDateList()));
            timeSheet.setDecimalDigit(param.getHrDecimalCnt().intValue());
            timeSheet.setCanBeforeActivityStart(param.getPrestartTaskHrsFlag());
            timeSheet.setCanAfterActivityFinish(param.getPostendTaskHrsFlag());
            DtoPreference dtoPrefer = new DtoPreference();
            DtoUtil.copyBeanToBean(dtoPrefer, param);
            timeSheet.setPreference(dtoPrefer);
        }
        return timeSheet;
    }

    /**
     * 检查出入的日期是否在允许的范围内
     *   workDate 为null无效
     * @param workDate Date
     * @param fCnt Long 若为0为不限制
     * @param pCnt Long 若为0为不限制
     * @return boolean
     */
    private boolean isWorkDateEnable(Date workDate, Long fCnt, Long pCnt, boolean future) {
        if(workDate == null) return false;
        Date today = new Date();
        int offset = 0;
        if(workDate.after(today)) {
            //若为0为不限制
            if(future && fCnt == 0) return true;
            try {
                offset = timeSheetApiDao.getPeriodNum(today, workDate) - 1;
            } catch (Exception ex) {
                return false;
            }
            if(!future && offset > 0) {
            	return false;
            }
            return offset <= fCnt;
        } else if(workDate.before(today)) {
            //若为0为不限制
            if(pCnt == 0) return true;
            try {
                offset = timeSheetApiDao.getPeriodNum(workDate, today) - 1;
            } catch (Exception ex) {
                return false;
            }
            return offset <= pCnt;
        }
        return true;
    }


    /**
     * 保存TimeSheet
     *  及其下属的DtoTimeSheetDetail， DtoTimeSheetDay
     * @param dto DtoTimeSheet
     */
    public void saveTimeSheet(DtoTimeSheet dto) {
        if(dto == null) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.dtoTimesheetNull",
                                        "DtoTimeSheet is null");
        }

        //保存Master
        TsTimesheetMaster master = null;
        //确认其它Session没有提交该工时单
        if(dto.getRid() != null && DtoTimeSheet.MODEL_MODIFY.equals(dto.getUiModel()) == false) {
        	master = timeSheetDao.getTimeSheetMaster(dto.getRid());
        	DtoTimeSheet d = new DtoTimeSheet();
        	d.setStatus(master.getStatus());
        	if(d.editable() == false) {
        		throw new BusinessException("error.logic.TimeSheetServiceImp.tsUneditable",
                "The timesheet is unmodifable!");
        	}
        }
        
        //先清除
        timeSheetDao.deleteTimeSheetData(dto.getLoginId(), dto.getTsId(), dto.getRid());
        
        //再保存，新增
        if(master == null) {
        	master = new TsTimesheetMaster();
        }
        DtoUtil.copyBeanToBean(master, dto);
        
        if(dto.isInsert()) {
            timeSheetDao.addTimeSheetMaster(master);
        } else if(dto.isModify()) {
            timeSheetDao.updateTimeSheetMaster(master);
        }
        DtoUtil.copyBeanToBean(dto, master);
        dto.setOp(IDto.OP_NOCHANGE);

        //遍历保存Detail
        List<DtoTimeSheetDetail> details = dto.getTsDetails();
        if(details != null) {
            for (DtoTimeSheetDetail detail : details) {
                detail.setTsRid(dto.getRid());
                saveDtoTimeSheetDetail(detail);
            }
        }
        
        //修改模式执行特殊动作
        if(DtoTimeSheet.MODEL_MODIFY.equals(dto.getUiModel()) && dto.getRid() != null) {
        	//在note中增加修改记录
        	DtoUser user = getSessionUser();
        	String notes = "This Timesheet had been modified by "
 	           + user.getUserName() + "(" + user.getUserLoginId() 
 	           + ") at " + comDate.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss")
 	           + "\n\n";
        	this.saveTimeSheetNotes(notes, dto.getRid());
        	
        	//如果是approved状态想Primavera回写
        	if(DtoTimeSheet.STATUS_APPROVED.equals(dto.getStatus())) {
        		approvalAssistService.writeBackToP3(dto.getRid());
        	}
        }
    }

    /**
     * 保存DtoTimeSheetDetail 及其 下属的 DtoTimeSheetDay
     * @param dto DtoTimeSheetDetail
     */
    private void saveDtoTimeSheetDetail(DtoTimeSheetDetail dto) {
        if(dto == null) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.dtoTimesheetDetailNull",
                                        "DtoTimeSheetDetail is null");
        }

        //保存detail
        TsTimesheetDetail detail = new TsTimesheetDetail();
        DtoUtil.copyBeanToBean(detail, dto);
        if(!dto.isDelete()) {
            timeSheetDao.addTimeSheetDetail(detail);
        }
//        else if(dto.isModify()) {
//            timeSheetDao.updateTimeSheetDetail(detail);
//        }
        DtoUtil.copyBeanToBean(dto, detail);
        dto.setOp(IDto.OP_NOCHANGE);

        //遍历保存day
        Collection<DtoTimeSheetDay> days = dto.getTsDays().values();
        for(DtoTimeSheetDay day : days) {
            day.setTsDetailRid(dto.getRid());
          if(day.getWorkHours() == null ||
	          Double.valueOf(0).equals(day.getWorkHours())) {
//        	  deleteDtoTimeSheetDay(day);
          } else {
        	  saveDtoTimeSheetDay(day);
          }
        }
    }

    /**
     * 保存DtoTimeSheetDay
     * @param dto DtoTimeSheetDay
     */
    private void saveDtoTimeSheetDay(DtoTimeSheetDay dto) {
        if(dto == null) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.dtoTimesheetDayNull",
                                        "DtoTimeSheetDay is null");
        }

        TsTimesheetDay day = new TsTimesheetDay();
        DtoUtil.copyBeanToBean(day, dto);
        if(!dto.isDelete()) {
            timeSheetDao.addTimeSheetDay(day);
        } 
//            else if(dto.isModify()) {
//            timeSheetDao.updateTimeSheetDay(day);
//        }
        DtoUtil.copyBeanToBean(dto, day);
        dto.setOp(IDto.OP_NOCHANGE);
    }
    
    /**
     * 保存DtoTimeSheetDay
     * @param dto DtoTimeSheetDay
     */
    private void deleteDtoTimeSheetDay(DtoTimeSheetDay dto) {
    	if(dto.getRid() == null) return;
    	TsTimesheetDay day = new TsTimesheetDay();
        DtoUtil.copyBeanToBean(day, dto);
    	timeSheetDao.deleteTimeSheetDay(day);
    }

    /**
     * 保存TimeSheet notes
     * @param note String
     * @param tsRid Long
     * @return String
     */
    public String saveTimeSheetNotes(String note, Long tsRid) {
        if(tsRid == null) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.dtoTimesheetRidNull",
                                        "DtoTimeSheet rid is null");
        }
        TsTimesheetMaster ts = timeSheetDao.getTimeSheetMaster(tsRid);
        if(ts == null) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.dtoTimesheetNoexist",
                                        "DtoTimeSheet is not exist");
        }
        if(ts.getNotes() != null) {
            ts.setNotes(note + ts.getNotes());
        } else {
            ts.setNotes(note);
        }

        timeSheetDao.updateTimeSheetMaster(ts);
        return ts.getNotes();
    }


    /**
     * submit timesheet 将状态为Active的TimeSheet的状态设为Submited
     *
     * @param dtoTimeSheet DtoTimeSheet
     */
    public void submitTimeSheet(DtoTimeSheet dtoTimeSheet) {
        if(dtoTimeSheet == null) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.dtoTimesheetNull",
                                        "DtoTimeSheet is null");
        }
        
        String loginId = dtoTimeSheet.getLoginId();
        dtoTimeSheet.setStatus(DtoTimeSheet.STATUS_SUBMITTED);
        dtoTimeSheet.setStatusDate(new Date());
        dtoTimeSheet.setOp(IDto.OP_MODIFY);
        
        TsParameter param = preferenceDao.loadPreferenceByLoginId(loginId);
        
        //根据HR请假数据自动生成假别代码工时
        if(DtoPreference.LEAVE_EFFECTIVE_GENERATE.equals(param.getLeaveEffective())) {
        	this.setLeaveHours(dtoTimeSheet);
        }
        
        boolean isAllPMApproval = true;
        List<DtoTimeSheetDetail> details = dtoTimeSheet.getTsDetails();
        
        if(details != null) {
            for (DtoTimeSheetDetail detail : details) {
                detail.setStatus(getTsStatusByCondition(detail, param, loginId));
                detail.setOp(IDto.OP_MODIFY);
                if(detail.getStatus().equals(DtoTimeSheet.STATUS_SUBMITTED)) {
                	isAllPMApproval = false;
                }
            }
        }
        if(isAllPMApproval) {
        	dtoTimeSheet.setStatus(DtoTimeSheet.STATUS_PM_APPROVED);
        }
        
        this.saveTimeSheet(dtoTimeSheet);
    }
    /**
     * 
     * @param detail
     * @param loginId
     * @return
     */
    private String getTsStatusByCondition(DtoTimeSheetDetail detail, TsParameter param, String loginId) {
    	String status = DtoTimeSheet.STATUS_SUBMITTED;
    	String tsApprovalLevel = "";
        if(param != null) {
        	tsApprovalLevel = param.getTsApprovalLevel();
        }
        //如果不是单一审批流程则检查
        if(!DtoPreference.APPROVAL_LEVEL_PM.equals(tsApprovalLevel)
         &&!DtoPreference.APPROVAL_LEVEL_RM.equals(tsApprovalLevel)){
        	TsAccount account = accountDao.loadAccount(detail.getAccountRid());
        	//PM不用审批自己的工时单
        	if(loginId.equals(account.getManager())){
        		status = DtoTimeSheet.STATUS_PM_APPROVED;
        	}
        	String rmId = rmMaintService.getRmByLoginId(loginId);
        	//工时填写本部门代码, 且部门主管与RM相同时,则不需要PM审批
        	if(account.getDeptFlag().equals(DtoAccount.DEPT_FLAG_DEPT)
        	  && account.getManager().equals(rmId)) {
        		status = DtoTimeSheet.STATUS_PM_APPROVED;
        	}
        }
    	return status;
    }
    /**
     * 删除TimeSheet先的一条记录
     * @param dtoActivity DtoTimeSheetActivity
     */
    public void deleteTimeSheetDetail(DtoTimeSheetDetail dtoDetail) {
        if(dtoDetail == null) {
            throw new BusinessException("error.logic.TimeSheetServiceImp.dtoTimesheetDetailNull",
                                        "DtoTimeSheetDetail is null");
        }

        TsTimesheetDetail detail = new TsTimesheetDetail();
        DtoUtil.copyBeanToBean(detail, dtoDetail);
        timeSheetDao.deleteTimeSheetDetail(detail);
    }
    /**
     * 获取timesheet的Notes
     * @param tsRid Long
     * @return String
     */
    public String getTimeSheetNotes(Long tsRid) {
        TsTimesheetMaster tsMaster = timeSheetDao.getTimeSheetMaster(tsRid);
        if(tsMaster != null && tsMaster.getNotes() != null){
            return tsMaster.getNotes();
        }
        return "";
    }
    
    /**
     * 根据loginId获取该用户所在的Site
     * @param loginId
     * @return
     */
    public String getSite(String loginId) {
    	if(loginId == null) {
    		return null;
    	}
    	
    	TsHumanBase human = rmMaintDao.loadHumanById(loginId);
    	if(human != null) {
    		return human.getSite();
    	} else {
    		return null;
    	}
    }
    
    /**
     * 获取指定用户指定指定时间的标准工时
     * @param loginId
     * @param dateList
     * @return
     */
    public List listStandarHours(String loginId, List<Date> dateList) {
        List resultList = null;
    	try {
            resultList = timeSheetApiDao.listStandarHours(loginId, dateList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }
    
    /**
     * 获取指定用户指定指定时间的请假工时
     * @param loginId
     * @param dateList
     * @return
     */
    public List listLeaveHours(String loginId, List<Date> dateList) {
    	List resultList = new ArrayList();
    	DtoAttLeave dto = new DtoAttLeave();
    	for(int i = 0; i < dateList.size(); i ++) {
    		DtoAttLeave d = getLeaveHours(loginId, dateList.get(i));
    		dto.setHours(dto.getHours() + d.getHours());
    		dto.setActualHours(dto.getActualHours() + d.getActualHours());
    		resultList.add(d);
    	}
    	resultList.add(dto);
    	return resultList;
    }
    
    private DtoAttLeave getLeaveHours(String loginId, Date date) {
    	DtoAttLeave dto = new DtoAttLeave();
    	dto.setLeaveDate(date);
    	dto.setEmployeeId(loginId);
    	List list = this.attLeaveDao.loadByCondition(loginId, date);
    	if(list == null) {
    		return dto;
    	}
    	for(int i = 0; i < list.size(); i ++) {
    		AttLeave leave = (AttLeave)list.get(i);
    		dto.setHours(dto.getHours() + nvl(leave.getHours()));
    		dto.setActualHours(dto.getActualHours() + nvl(leave.getActualHours()));
    		String tip = dto.getTipText();
    		if(tip == null || "".equals(tip)) {
    			tip = leave.getUnitCode() + " | " + leave.getPhaseCode();
    		} else {
    			tip += ",  " + leave.getUnitCode() + " | " + leave.getPhaseCode();
    		}
    		dto.setTipText(tip + "");
    	}
    	return dto;
    }
    
    /**
     * 获取指定用户指定指定时间的加班工时
     * @param loginId
     * @param dateList
     * @return
     */
    public List listOvertimeHours(String loginId, List<Date> dateList) {
    	List resultList = new ArrayList();
    	DtoAttOvertime dto = new DtoAttOvertime();
    	for(int i = 0; i < dateList.size(); i ++) {
    		DtoAttOvertime d = getOvertimeHours(loginId, dateList.get(i));
    		dto.setHours(dto.getHours() + d.getHours());
    		dto.setActualHours(dto.getActualHours() + d.getActualHours());
    		resultList.add(d);
    	}
    	resultList.add(dto);
    	return resultList;
    }
    
    /**
     * 根据HR请假数据自动生成假别代码工时
     * @param dtoTimeSheet DtoTimeSheet
     */
    private void setLeaveHours(DtoTimeSheet dtoTimeSheet) {
    	List list = this.attLeaveDao.loadByCondition(dtoTimeSheet.getLoginId()
    			, dtoTimeSheet.getBeginDate(), dtoTimeSheet.getEndDate());
    	HashMap deptCodeMap = new HashMap();
    	for(int i = 0; i < list.size(); i ++) {
    		AttLeave leave = (AttLeave)list.get(i);
    		String key = leave.getUnitCode() + "&$&" + leave.getPhaseCode();
    		DtoTimeSheetDetail detail = (DtoTimeSheetDetail)deptCodeMap.get(key);
    		
    		//生成DtoTimeSheetDetail
    		if(detail == null) {
    			detail = new DtoTimeSheetDetail();
    			
    			//匹配本系统部门代码信息
    			TsAccount acnt = accountDao.loadByAccountId(leave.getUnitCode());
    			if(acnt == null || acnt.getRid() == null) {
    				throw new BusinessException("error.logic.TimeSheetServiceImp.setLeaveHoursDeptInexist",
                    "Unit code [" + leave.getUnitCode() + "] is inexist in system!");
    			}
    			detail.setAccountRid(acnt.getRid());
    			detail.setAccountName(acnt.getAccountId() + " -- " + acnt.getAccountName());
    			
    			//匹配本系统假别代码信息
    			TsCodeValue code = codeValueDao.findByTypeRidName(acnt.getLeaveCodeTypeRid(), leave.getPhaseCode());
    			if(code == null) {
    				throw new BusinessException("error.logic.TimeSheetServiceImp.setLeaveHoursCodeInexist",
    	                    "Unit code [" + leave.getPhaseCode() + "] is inexist in system!");
    			}
    			detail.setCodeValueRid(code.getRid());
    			detail.setCodeValueName(code.getName() + " -- " + code.getDescription());
    			detail.setIsLeaveType(true);
    			detail.setJobDescription("Generate by ESSP");
    			detail.setOp(IDto.OP_INSERT);
    			detail.setStatus(DtoTimeSheet.STATUS_ACTIVE);
    			deptCodeMap.put(key, detail);
    			dtoTimeSheet.getTsDetails().add(detail);
    		}
    		
    		DtoTimeSheetDay day = detail.getHour(leave.getLeaveDate());
    		if(day == null) {
    			detail.setHour(leave.getLeaveDate(), leave.getActualHours(), 0D);
    		} else {
    			day.setWorkHours(nvl(day.getWorkHours()) + leave.getActualHours());
    		}
    		
    	}
    }
    
    private DtoAttOvertime getOvertimeHours(String loginId, Date date) {
    	DtoAttOvertime dto = new DtoAttOvertime();
    	dto.setOvertimeDate(date);
    	dto.setEmployeeId(loginId);
    	List list = this.attOvertimeDao.loadByCondition(loginId, date);
    	if(list == null) {
    		return dto;
    	}
    	for(int i = 0; i < list.size(); i ++) {
    		AttOvertime overtime = (AttOvertime)list.get(i);
    		dto.setHours(dto.getHours() + nvl(overtime.getHours()));
    		dto.setActualHours(dto.getActualHours() + nvl(overtime.getActualHours()));
    		String tip = dto.getTipText();
    		if(tip == null || "".equals(tip)) {
    			tip =  overtime.getUnitCode() + " | " + overtime.getProjectCode();
    		} else {
    			tip += ",  " + overtime.getUnitCode() + " | " + overtime.getProjectCode();
    		}
    		dto.setTipText(tip);
    	}
    	return dto;
    }
    
    private final static Double nvl(Double d) {
    	if(d == null) {
    		return Double.valueOf(0);
    	} else {
    		return d;
    	}
    }

	public void setApprovalAssistService(
			IApprovalAssistService approvalAssistService) {
		this.approvalAssistService = approvalAssistService;
	}
	
	private DtoUser getSessionUser() {
		ThreadVariant thread = ThreadVariant.getInstance();
		return (DtoUser)thread.get(DtoUser.SESSION_USER);
	}

	public DtoTimeSheet InitTimesheetByDaily(String loginId, DtoTimeSheet dto) {
		//查询日报相关资料
		List<Object[]> dailyList = dailyReportDao.listDailyReportByDate(loginId, dto.getBeginDate(), dto.getEndDate());
		if(dailyList == null || dailyList.size() == 0){
			return dto;
		}
		//查询每天填写的总时间
		List<Object[]> timeList = dailyReportDao.listHoursByDate(loginId, dto.getBeginDate(), dto.getEndDate());
		if(timeList == null || timeList.size() == 0) {
			return dto;
		}
		Map dateTimeMap = new HashMap();
		for(Object[] obj : timeList) {
			dateTimeMap.put(comDate.dateToString((Date)obj[0]), obj[1]);
		}
		int scale = dto.getDecimalDigit();
		List detailList = new ArrayList();
		DtoTimeSheetDetail dtoTimeSheetDetail = null;
		DtoTimeSheetDay dtoTimeSheetDay = null;
		Double dayHours = null;
		List standarHoursList = dto.getStandarHours();
		//获取每天标准工时
		List<Date> dateList = dto.getDateList();
		Map standarMap = new HashMap();
		Map overTimeMap = new HashMap();
		Map leaveMap = new HashMap();
		Map subOverTimeMap = new HashMap();
		for(int i = 0;i<dateList.size();i++) {
			Date workDate = dateList.get(i);
			String dataStr = comDate.dateToString(workDate);
			standarMap.put(dataStr, standarHoursList.get(i));
			Double otHour = this.getOverTimeHour(loginId, workDate);
			overTimeMap.put(dataStr, otHour);
			subOverTimeMap.put(dataStr, otHour);
			leaveMap.put(dataStr, this.getLeaveHour(loginId, workDate));
		}
		Double standarHours = null;
		Double actualHours = null;
		Double overTimeHours = null;
		Double leaveHours = null;
		Object[] lastRecord = null;
		Double totalHours = new Double(0);
		Map detailDayMap = new HashMap();
		for(int i = 0;i<dailyList.size();i++) {
			Object[] obj = dailyList.get(i);
			//如果有两条以上记录，并且不是最后一条获取当前记录的下一笔记录
			if(dailyList.size() > 2 && i != dailyList.size() - 1){
				lastRecord = dailyList.get(i + 1);
			} else {
				lastRecord = obj;
			}
			
			//
			if(detailDayMap.containsKey(String.valueOf(obj[0]) + String.valueOf(obj[4]))) {
				dtoTimeSheetDetail = (DtoTimeSheetDetail) detailDayMap.get(String.valueOf(obj[0]) + String.valueOf(obj[4]));
				dtoTimeSheetDay = new DtoTimeSheetDay();
				dtoTimeSheetDay.setWorkDate((Date)obj[6]);
				String dateStr = comDate.dateToString(dtoTimeSheetDay.getWorkDate());
				dayHours = nvl((Double)dateTimeMap.get(dateStr));
				standarHours = (Double)standarMap.get(dateStr);
				overTimeHours = (Double)overTimeMap.get(dateStr);
				leaveHours = (Double)leaveMap.get(dateStr);
				actualHours = standarHours + overTimeHours - leaveHours;
				
				//如果当天填写总工时超过标准工时则按比例分摊否则直接放入数值
				if(dayHours <= actualHours) {
					dtoTimeSheetDay.setWorkHours(resetScale((Double)obj[7], scale));
				} else {
					if(dtoTimeSheetDay.getWorkDate().compareTo((Date)lastRecord[6]) == 0) {
						dtoTimeSheetDay.setWorkHours(resetScale((Double)obj[7] / dayHours * actualHours, scale));
						totalHours += dtoTimeSheetDay.getWorkHours();
					} else {
						dtoTimeSheetDay.setWorkHours(resetScale(actualHours - totalHours, scale));
						totalHours = new Double(0);
					}
				}
				if(dtoTimeSheetDay.getWorkHours() == 0) {
					continue;
				}
				//分摊加班时间
				setOverTimeHour(subOverTimeMap, dtoTimeSheetDay, scale);
				
				HashMap dayMap = dtoTimeSheetDetail.getTsDays();
				dayMap.put(dtoTimeSheetDay.getWorkDate(), dtoTimeSheetDay);
			} else {
				dtoTimeSheetDetail = new DtoTimeSheetDetail();
				dtoTimeSheetDetail.setAccountName((String)obj[3]);
				dtoTimeSheetDetail.setAccountRid((Long)obj[2]);
				dtoTimeSheetDetail.setActivityId((Long)obj[0]);
				dtoTimeSheetDetail.setActivityName((String)obj[1]);
				dtoTimeSheetDetail.setCodeValueRid((Long)obj[4]);
				dtoTimeSheetDetail.setCodeValueName((String)obj[5]);
				dtoTimeSheetDetail.setStatus(DtoTimeSheet.STATUS_ACTIVE);
				dtoTimeSheetDetail.setOp(IDto.OP_INSERT);
				dtoTimeSheetDetail.setIsLeaveType(false);
				
				dtoTimeSheetDay = new DtoTimeSheetDay();
				dtoTimeSheetDay.setWorkDate((Date)obj[6]);
				String dateStr = comDate.dateToString(dtoTimeSheetDay.getWorkDate());
				dayHours = nvl((Double)dateTimeMap.get(dateStr));
				standarHours = (Double)standarMap.get(dateStr);
				overTimeHours = (Double)overTimeMap.get(dateStr);
				leaveHours = (Double)leaveMap.get(dateStr);
				actualHours = standarHours + overTimeHours - leaveHours;
				//如果当天填写总工时超过标准工时则按比例分摊否则直接放入数值
				if(dayHours <= actualHours) {
					dtoTimeSheetDay.setWorkHours(resetScale((Double)obj[7], scale));
				} else {
					if(dtoTimeSheetDay.getWorkDate().compareTo((Date)lastRecord[6]) == 0) {
						dtoTimeSheetDay.setWorkHours(resetScale((Double)obj[7] / dayHours * actualHours, scale));
						totalHours += dtoTimeSheetDay.getWorkHours();
					} else {
						dtoTimeSheetDay.setWorkHours(resetScale(actualHours - totalHours, scale));
						totalHours = new Double(0);
					}
				}
				if(dtoTimeSheetDay.getWorkHours() == 0) {
					continue;
				}
				//分摊加班时间
				setOverTimeHour(subOverTimeMap, dtoTimeSheetDay, scale);

				HashMap dayMap = new HashMap();
				dayMap.put(dtoTimeSheetDay.getWorkDate(), dtoTimeSheetDay);
				dtoTimeSheetDetail.setTsDays(dayMap);
				detailList.add(dtoTimeSheetDetail);
				detailDayMap.put(String.valueOf(dtoTimeSheetDetail.getActivityId()) 
						       + String.valueOf(dtoTimeSheetDetail.getCodeValueRid()), dtoTimeSheetDetail);
			}
			
		}
		dto.setTsDetails(detailList);
		return dto;
	}
	
	/**
	 * 分摊加班时间
	 * @param subOverTimeMap Map
	 * @param dtoTimeSheetDay DtoTimeSheetDay
	 * @param scale 精确位数
	 */
	private static void setOverTimeHour(final Map subOverTimeMap, final DtoTimeSheetDay dtoTimeSheetDay, final int scale) {
		if(dtoTimeSheetDay == null) {
			return;
		}
		Double workHours = dtoTimeSheetDay.getWorkHours();
		if(workHours == null || workHours <= 0D) {
			return;
		}
		String dateStr = comDate.dateToString(dtoTimeSheetDay.getWorkDate());
		Double subOverTimeHours =  (Double) subOverTimeMap.get(dateStr);
		if(subOverTimeHours == null || workHours <= 0D) {
			return;
		}
		//实际工时足够，就把加班时间全放进来
		if(workHours >= subOverTimeHours) {
			dtoTimeSheetDay.setOvertimeHours(resetScale(subOverTimeHours, scale));
			subOverTimeMap.put(dateStr, 0D);
		} else { //如果不够就先放实际工时
			dtoTimeSheetDay.setOvertimeHours(workHours);
			subOverTimeMap.put(dateStr, resetScale(subOverTimeHours - workHours, scale));
		}
	}
	
	/**
	 * 获取请假时间
	 * @param loginId String
	 * @param leaveDate Date
	 * @return Double
	 */
	private Double getLeaveHour(final String loginId, final Date leaveDate) {
		final DtoAttLeave dtoLeave = this.getLeaveHours(loginId, leaveDate);
		return dtoLeave.getActualHours();
	}
	
	/**
	 * 获取加班时间
	 * @param loginId String
	 * @param overTimeDate Date
	 * @return Double
	 */
	private Double getOverTimeHour(final String loginId, final Date overTimeDate) {
		final DtoAttOvertime dtoOverTime = this.getOvertimeHours(loginId, overTimeDate);
		return dtoOverTime.getActualHours();
	}
	
	
	private static Double resetScale(Double value, int scale) {
		if(value == null) {
			return null;
		}
		BigDecimal bigd = new BigDecimal(value);
		return bigd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
