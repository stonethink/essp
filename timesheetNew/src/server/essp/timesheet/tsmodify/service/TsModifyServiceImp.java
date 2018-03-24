package server.essp.timesheet.tsmodify.service;

import java.util.ArrayList;
import java.util.List;

import server.essp.timesheet.approval.service.IApprovalAssistService;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import c2s.essp.timesheet.approval.DtoTsApproval;
import c2s.essp.timesheet.preference.DtoPreference;
import c2s.essp.timesheet.tsmodify.DtoTsModify;
import db.essp.timesheet.TsTimesheetMaster;

public class TsModifyServiceImp implements ITsModifyService {
	
	private ITimeSheetDao timeSheetDao;
	private IApprovalAssistService approvalAssistService;

	public List queryByCondition(DtoTsModify dto) {
		List<TsTimesheetMaster> list = timeSheetDao.listTimeSheetMasterByDateStatus(
				dto.getLoginId(), dto.getStartDate(), dto.getFinishDate(), 
					"('"+DtoTsApproval.STATUS_APPROVED+"')");
		if (list == null || list.size() == 0) {
			return new ArrayList();
        }
		DtoTsApproval dtoTsApproval = null;
		List resultList = new ArrayList();
		for (TsTimesheetMaster tsTimesheetMaster : list) {
        	dtoTsApproval = new DtoTsApproval();
        	dtoTsApproval.setLoginId(dto.getLoginId());
        	dtoTsApproval.setStatus(tsTimesheetMaster.getStatus());
        	dtoTsApproval.setTsRid(tsTimesheetMaster.getRid());
        	dtoTsApproval.setStartDate(tsTimesheetMaster.getBeginDate());
        	dtoTsApproval.setFinishDate(tsTimesheetMaster.getEndDate());
        	dtoTsApproval.setStandarHours(approvalAssistService
				.getStandarHours(tsTimesheetMaster.getBeginDate(),
						tsTimesheetMaster.getEndDate(),dtoTsApproval.getLoginId()));
        	if (!approvalAssistService.setHoursOk(dtoTsApproval,
				tsTimesheetMaster.getRid(),
				DtoPreference.APPROVAL_LEVEL_PM_AND_RM)) {
        		continue;
        	}
        	resultList.add(dtoTsApproval);
        }
		return resultList;
	}
	public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
		this.timeSheetDao = timeSheetDao;
	}

	public void setApprovalAssistService(
			IApprovalAssistService approvalAssistService) {
		this.approvalAssistService = approvalAssistService;
	}

}
