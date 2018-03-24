package server.essp.hrbase.synchronization.syncexception.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.hrbase.synchronization.syncdata.ISyncService;
import server.essp.hrbase.synchronization.syncexception.service.ISyncExceptionService;
import server.essp.hrbase.synchronization.syncexception.viewbean.VbSyncException;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import db.essp.hrbase.HrbExceptionTemp;

public class AcCarryForward extends AbstractESSPAction {
	
	/**
	 * 用于触发处理异常结转操作的Action
	 */
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
		if(VbSyncException.MODEL_PP.equals(exception.getModel())) {
			ISyncService syncProjectpre = (ISyncService) this.getBean("syncProjectpre");
			syncProjectpre.carryForward(exception);
		} else if(VbSyncException.MODEL_TS.equals(exception.getModel())) {
			ISyncService syncTimesheet = (ISyncService) this.getBean("syncTimesheet");
			syncTimesheet.carryForward(exception);
		} else if(VbSyncException.MODEL_HR.equals(exception.getModel())) {
			ISyncService syncProjectpre = (ISyncService) this.getBean("syncHrbase");
			syncProjectpre.carryForward(exception);
		} else if(VbSyncException.MODEL_FI.equals(exception.getModel())) {
			ISyncService syncFinance = (ISyncService) this.getBean("syncFinance");
			syncFinance.carryForward(exception);
		} else if(VbSyncException.MODEL_104HRMS.equals(exception.getModel())) {
			ISyncService sync104Hrms = (ISyncService) this.getBean("sync104Hrms");
			sync104Hrms.carryForward(exception);
		} else if(VbSyncException.MODEL_P6.equals(exception.getModel())) {
			ISyncService syncPrimavera = (ISyncService)this.getBean("syncPrimavera");
			syncPrimavera.carryForward(exception);
		}
		exception.setStatus(VbSyncException.STATUS_COMPLETED);
		exceptionService.updateException(exception);
	}

}
