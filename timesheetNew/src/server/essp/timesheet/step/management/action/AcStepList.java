package server.essp.timesheet.step.management.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.step.management.service.IStepManagementService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.step.management.DtoActivityForStep;
import c2s.essp.timesheet.step.management.DtoStep;

public class AcStepList extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		String id=data.getInputInfo().getInputObj("ActvityObjectId").toString();
		IStepManagementService service = (IStepManagementService) this
				.getBean("stepManagementService");
		data.getReturnInfo().setReturnObj(
				DtoStep.KEY_STEP_LIST,
				service.listStepByActivityId(new Long(id)));
		data.getReturnInfo().setReturnObj(
				DtoActivityForStep.KEY_DTO,
				service.getActivity(new Long(id)));
	}

}