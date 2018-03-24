package server.essp.timesheet.weeklyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.weeklyreport.service.ITimeSheetService;
import server.framework.common.BusinessException;

public class AcInitTsByDaily extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		DtoTimeSheet dto = (DtoTimeSheet) data.getInputInfo().getInputObj(DtoTimeSheet.DTO);
		ITimeSheetService service = (ITimeSheetService) this.getBean("timeSheetService");
		DtoTimeSheet dtoTs = service.InitTimesheetByDaily(this.getUser().getUserLoginId(), dto);
		data.getReturnInfo().setReturnObj(DtoTimeSheet.DTO, dtoTs);
	}

}
