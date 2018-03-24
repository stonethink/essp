package server.essp.timesheet.methodology.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.methodology.service.IMethodService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcListMethod extends AbstractESSPAction {
	
	/**
	 * 列出所有的methodlogy
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		IMethodService service = (IMethodService) this.getBean("methodService");
		List list = service.getMethodList();
		request.setAttribute(Constant.VIEW_BEAN_KEY,list);
	}

}
