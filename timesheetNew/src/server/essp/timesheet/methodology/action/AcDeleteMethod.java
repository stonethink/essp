package server.essp.timesheet.methodology.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.methodology.service.IMethodService;
import server.framework.common.BusinessException;

public class AcDeleteMethod extends AbstractESSPAction {
	
	/**
	 * 删除methodology,当前的methodology下面有模版的时候中止操作
	 */	
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = Long.valueOf(request.getParameter("rid"));
		IMethodService service = (IMethodService) this.getBean("methodService");
		service.deleteMethod(rid);

	}

}
