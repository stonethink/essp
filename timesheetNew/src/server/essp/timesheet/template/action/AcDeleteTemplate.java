package server.essp.timesheet.template.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.service.ITemplateService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcDeleteTemplate extends AbstractESSPAction {
	
	/**
	 * 根据rid删除当前的模版
	 */	
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = Long.valueOf(request.getParameter("rid"));
		ITemplateService service = (ITemplateService) this.getBean("templateService");
		service.deleteTemplate(rid);

	}

}
