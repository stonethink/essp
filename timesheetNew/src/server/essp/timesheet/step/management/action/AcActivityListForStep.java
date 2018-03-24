package server.essp.timesheet.step.management.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.step.management.service.IStepManagementService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.step.management.DtoActivityForStep;

public class AcActivityListForStep extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String id = data.getInputInfo().getInputObj("ProjectId")
				.toString();
		IStepManagementService service = (IStepManagementService) this
				.getBean("stepManagementService");
		String loginId = this.getUser().getUserLoginId();
		List result = service.listActivity(loginId, id);
		data.getReturnInfo().setReturnObj(
				DtoActivityForStep.KEY_ACTIVITY_LIST_FOR_STEP, result);

	}
}
