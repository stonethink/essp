package server.essp.timesheet.aprm.lock.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.aprm.lock.service.IImportLockService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import db.essp.timesheet.TsImportLock;

public class AcDeleteImportLock extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request, 
			HttpServletResponse response,
			TransactionData data) throws BusinessException {
		String strRid = request.getParameter("importLockRid");
		if(strRid == null || "".equals(strRid)) {
			return;
		} else {
			Long rid = new Long(strRid);
			IImportLockService service = (IImportLockService) 
											this.getBean("importLockService");
			service.deleteImportLock(rid);
		}
	}
}
