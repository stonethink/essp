package server.essp.timesheet.step.management.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.step.management.service.IStepManagementService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcProjectList extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {

		IStepManagementService service = (IStepManagementService) this
				.getBean("stepManagementService");
		data.getReturnInfo().setReturnObj("ProjectList", service.listAcnt(this.getUser().getUserLoginId()));
	}

}
