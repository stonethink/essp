package server.essp.timesheet.aprm.lock.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wits.util.comDate;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.aprm.lock.service.IImportLockService;
import server.essp.timesheet.aprm.lock.viewbean.VbSearchImportLock;
import server.framework.common.BusinessException;

public class AcInitImportLock extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request, HttpServletResponse arg1,
			TransactionData arg2) throws BusinessException {
		String strBegin = request.getParameter("beginDate");
		String strEnd = request.getParameter("endDate");
		Date begin ;
		Date end;
		if(strBegin != null && "".equals(strBegin) == false) {
			begin = comDate.toDate(strBegin, "yyyy/MM/dd");
		} else {
			begin = new Date();
		}
		if(strEnd != null && "".equals(strEnd) == false) {
			end = comDate.toDate(strEnd, "yyyy/MM/dd");
		} else {
			end = new Date();
		}
		IImportLockService service = (IImportLockService) 
										this.getBean("importLockService");
		List list = service.listImportLock(begin, end);
		VbSearchImportLock searchVo = new VbSearchImportLock(); 
		searchVo.setSearchBeginDate(begin);
		searchVo.setSearchEndDate(end);
		request.getSession().setAttribute("searchVo", searchVo);
		request.setAttribute("webVo", list);

	}

}
