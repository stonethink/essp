package server.essp.timesheet.synchronization.syncexception.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.synchronization.syncexception.service.ISyncExceptionService;
import server.essp.timesheet.synchronization.syncexception.viewbean.VbException;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

public class AcCancelException extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = null;
		if (request.getParameter("rid") != null) {
			rid = Long.valueOf(request.getParameter("rid"));
		} else {
			return;
		}
		ISyncExceptionService exceptionService = (ISyncExceptionService) this
				.getBean("syncTSExceptionService");

		exceptionService.update(rid, VbException.STATUS_CANCELED);

	}

}
