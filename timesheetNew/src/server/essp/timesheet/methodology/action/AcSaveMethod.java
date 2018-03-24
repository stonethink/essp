package server.essp.timesheet.methodology.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.methodology.service.IMethodService;
import server.framework.common.BusinessException;
import server.essp.timesheet.methodology.form.AfMethod;

public class AcSaveMethod extends AbstractESSPAction {

	/**
	 * 新增或者修改methodology
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid=null;
		IMethodService service = (IMethodService) this.getBean("methodService");
		AfMethod methodForm=(AfMethod)this.getForm();
		rid = service.saveMethod(methodForm);
		request.setAttribute("RefreshUpPage","true");
		request.setAttribute("rid",rid);

		
	}

}
