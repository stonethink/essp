package server.essp.timesheet.dailyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.dailyreport.DtoDrStep;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.dailyreport.service.IDailyReportService;
import server.framework.common.BusinessException;

public class AcDelete extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		DtoDrStep dto = (DtoDrStep) data.getInputInfo().getInputObj(DtoDrStep.DTO);
		IDailyReportService service = (IDailyReportService) this.getBean("dailyReportService");
		service.deleteDailyReport(dto);
	}

}
