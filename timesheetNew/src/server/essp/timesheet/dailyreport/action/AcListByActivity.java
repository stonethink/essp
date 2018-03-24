package server.essp.timesheet.dailyreport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.timesheet.dailyreport.DtoDrActivity;
import c2s.essp.timesheet.dailyreport.DtoDrStep;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.dailyreport.service.IDailyReportService;
import server.framework.common.BusinessException;

public class AcListByActivity extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		Long activityId = (Long) data.getInputInfo().getInputObj(DtoDrActivity.DTO_ACTIVITYID);
		Long accountRid = (Long) data.getInputInfo().getInputObj(DtoDrActivity.DTO_ACCOUNTRID);
		IDailyReportService service = (IDailyReportService) this.getBean("dailyReportService");
		data.getReturnInfo().setReturnObj(DtoDrStep.DTO_RESULT, service.listByActivityId(this.getUser().getUserLoginId(), activityId, accountRid));
	}

}
