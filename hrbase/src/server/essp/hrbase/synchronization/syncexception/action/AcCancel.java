package server.essp.hrbase.synchronization.syncexception.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import db.essp.hrbase.HrbExceptionTemp;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.synchronization.syncexception.service.ISyncExceptionService;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;

public class AcCancel extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = null;
		if(request.getParameter("rid") != null) {
			rid = Long.valueOf(request.getParameter("rid"));
		} else {
			return;
		}
		ISyncExceptionService exceptionService = (ISyncExceptionService) this.getBean("syncExceptionService");
		HrbExceptionTemp exception = exceptionService.getExceptionByRid(rid);
		exception.setStatus(VbSyncException.STATUS_CANCELED);
		exceptionService.updateException(exception);
	}

}
