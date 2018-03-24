package server.essp.timesheet.template.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.form.AfTemplate;
import server.essp.timesheet.template.service.ITemplateService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcSaveTemplate extends AbstractESSPAction {

	/**
	 * 新增或者修改template
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		ITemplateService service = (ITemplateService) this.getBean("templateService");
		Long templateId=null;
		AfTemplate form=(AfTemplate)this.getForm();
		String [] methods=request.getParameterValues("type");		
		templateId=service.saveTemplate(form,methods);	
		request.setAttribute("RefreshUpPage","true");
		request.setAttribute("tempId",templateId);
		
	}

}
