package server.essp.timesheet.aprm.lock.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.aprm.lock.form.AfImportLock;
import server.essp.timesheet.aprm.lock.service.IImportLockService;
import server.framework.common.BusinessException;

public class AcSaveImportLock extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request, 
			HttpServletResponse response,
			TransactionData data) throws BusinessException {
		AfImportLock form = (AfImportLock) this.getForm();
		IImportLockService service = (IImportLockService) 
										this.getBean("importLockService");
		service.saveImportLock(form);
	}

}
