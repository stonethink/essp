package server.essp.timesheet.dailyreport.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.dailyreport.service.IDailyReportService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.dailyreport.DtoDrActivity;

public class AcListActivityInDB extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		IDailyReportService service = (IDailyReportService) this.getBean("dailyReportService");
		Date day = (Date) data.getInputInfo().getInputObj(DtoDrActivity.DTO_DAY);
		data.getReturnInfo().setReturnObj(DtoDrActivity.DTO_ACTIVITY_LIST, 
		service.listActivityInDB(this.getUser().getUserLoginId(), day));
	}

}
