package server.essp.timesheet.template.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.template.service.ITemplateService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcListTemplate extends AbstractESSPAction {
	
	/**
	 * 列出所有的模版
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		ITemplateService service = (ITemplateService) this.getBean("templateService");
		List list = service.getTemplateList();
		request.setAttribute(Constant.VIEW_BEAN_KEY,list);
	}

}
