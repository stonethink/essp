package server.essp.timesheet.template.step.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.step.service.IDetailStepService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcListStep extends AbstractESSPAction {

	/**
	 * 浏览当前选中模版下的所有的steps
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = null;
		if(request.getParameter("tempId") != null) {
			rid = Long.valueOf(request.getParameter("tempId"));
		}
		if(rid == null && request.getAttribute("tempId") != null) {
			rid = Long.valueOf(request.getAttribute("tempId").toString());
		}
		IDetailStepService service = (IDetailStepService) this.getBean("stepService");
		List list=service.listStep(rid);
		request.setAttribute(Constant.VIEW_BEAN_KEY,list);
		request.setAttribute("tempId", rid);
		}
		
}
