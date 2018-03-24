package server.essp.hrbase.synchronization.syncexception.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.synchronization.syncexception.service.ISyncExceptionService;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.TransactionData;

public class AcPreviewException extends AbstractESSPAction {

	/**
	 * 获取某条异常结转信息记录的Action
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		Long rid = null;
		if(request.getParameter("rid") != null) {
			rid = Long.valueOf(request.getParameter("rid"));
		}
		ISyncExceptionService exceptionService = (ISyncExceptionService) this.getBean("syncExceptionService");
		VbSyncException exception = exceptionService.loadExceptionByRid(rid);
		request.setAttribute(Constant.VIEW_BEAN_KEY, exception);
	}

}
