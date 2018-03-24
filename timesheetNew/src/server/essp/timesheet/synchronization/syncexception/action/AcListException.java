package server.essp.timesheet.synchronization.syncexception.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import db.essp.timesheet.TsException;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.synchronization.syncexception.service.ISyncExceptionService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

public class AcListException extends AbstractESSPAction {
	/**
	 * 列出所有未处理的异常结转信息的Action
	 */
	public void executeAct(HttpServletRequest request,
			HttpServletResponse response, TransactionData data)
			throws BusinessException {
		ISyncExceptionService exceptionService = (ISyncExceptionService) this
				.getBean("syncTSExceptionService");
		List<TsException> list = exceptionService.listException();
		request.setAttribute(Constant.VIEW_BEAN_KEY, list);
	}
}
