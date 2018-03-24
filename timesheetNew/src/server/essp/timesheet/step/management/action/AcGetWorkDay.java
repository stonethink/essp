package server.essp.timesheet.step.management.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.step.management.service.IStepManagementService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcGetWorkDay extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String id = data.getInputInfo().getInputObj("ActivityObjectId")
				.toString();
		Date start = (Date) data.getInputInfo().getInputObj("StartDate");
		Date finish = (Date) data.getInputInfo().getInputObj("FinishDate");
		IStepManagementService service = (IStepManagementService) this
				.getBean("stepManagementService");
		data.getReturnInfo().setReturnObj("WorkDay",
				service.getAllWorkDayByProjectCalendar(id, start, finish));

	}

}
