package server.essp.timesheet.dailyreport.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.dailyreport.service.IDailyReportService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.dailyreport.DtoDrActivity;

public class AcDelDailyReport extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		Date day = (Date) data.getInputInfo().getInputObj(DtoDrActivity.DTO_DAY);
		DtoDrActivity dto = (DtoDrActivity) data.getInputInfo().getInputObj(DtoDrActivity.DTO);
		IDailyReportService service = (IDailyReportService) this.getBean("dailyReportService");
		service.delDailyReportInAct(day, dto, this.getUser().getUserLoginId());
	}

}
