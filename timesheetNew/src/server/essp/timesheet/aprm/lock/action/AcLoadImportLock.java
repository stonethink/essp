package server.essp.timesheet.aprm.lock.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import db.essp.timesheet.TsImportLock;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.aprm.lock.service.IImportLockService;
import server.framework.common.BusinessException;

public class AcLoadImportLock extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request, 
			HttpServletResponse response,
			TransactionData data) throws BusinessException {
		String strRid = request.getParameter("importLockRid");
		TsImportLock lock;
		if(strRid == null || "".equals(strRid)) {
			lock = new TsImportLock();
		} else {
			Long rid = new Long(strRid);
			IImportLockService service = (IImportLockService) 
											this.getBean("importLockService");
			lock = service.getImportLock(rid);
		}
		request.setAttribute("webVo", lock);
	}

}
