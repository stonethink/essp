package server.essp.timesheet.template.step.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.step.service.IDetailStepService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteStep extends AbstractESSPAction {

	/**
	 * 删除当前模板下的step
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = Long.valueOf(request.getParameter("rid"));
		IDetailStepService service = (IDetailStepService) this.getBean("stepService");
		Long tempId = service.deleteStep(rid);
		request.setAttribute("tempId", tempId);
	}

}
