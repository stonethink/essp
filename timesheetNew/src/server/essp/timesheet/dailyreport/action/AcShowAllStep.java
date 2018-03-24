package server.essp.timesheet.dailyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.dailyreport.service.IDailyReportService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import c2s.essp.timesheet.dailyreport.DtoDrStep;

public class AcShowAllStep extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		DtoDrActivity dto = (DtoDrActivity) data.getInputInfo().getInputObj(DtoDrActivity.DTO);
		IDailyReportService service = (IDailyReportService) this.getBean("dailyReportService");
		data.getReturnInfo().setReturnObj(DtoDrStep.DTO_RESULT, service.showAllSteps(dto, this.getUser().getUserLoginId()));
	}

}
