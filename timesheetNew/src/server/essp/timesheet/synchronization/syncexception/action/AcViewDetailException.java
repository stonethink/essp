package server.essp.timesheet.synchronization.syncexception.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.synchronization.syncexception.service.ISyncExceptionService;
import server.essp.timesheet.synchronization.syncexception.viewbean.VbException;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

public class AcViewDetailException extends AbstractESSPAction {
	/**
	 * 获取某条异常结转信息记录的Action
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = null;
		if (request.getParameter("rid") != null) {
			rid = Long.valueOf(request.getParameter("rid"));
		}
		ISyncExceptionService exceptionService = (ISyncExceptionService) this
				.getBean("syncTSExceptionService");
		VbException exception = exceptionService.loadExceptionByRid(rid);
		request.setAttribute(Constant.VIEW_BEAN_KEY, exception);

	}

}
