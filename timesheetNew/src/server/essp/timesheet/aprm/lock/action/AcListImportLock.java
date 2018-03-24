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

public class AcListImportLock extends AbstractESSPAction {

	@Override
	public void executeAct(HttpServletRequest request, 
			HttpServletResponse response,
			TransactionData data) throws BusinessException {
		String strBegin = request.getParameter("beginDate");
		String strEnd = request.getParameter("endDate");
		VbSearchImportLock searchVoSe = null;
		searchVoSe = (VbSearchImportLock) request.getSession().getAttribute("searchVo");
		Date begin = null;
		Date end = null;
		if(strBegin != null && "".equals(strBegin) == false) {
			begin = comDate.toDate(strBegin, "yyyy/MM/dd");
		} else if(searchVoSe != null){
			begin = searchVoSe.getSearchBeginDate();
		} else {
			begin = new Date();
		}
		if(strEnd != null && "".equals(strEnd) == false) {
			end = comDate.toDate(strEnd, "yyyy/MM/dd");
		} else if(searchVoSe != null){
			end = searchVoSe.getSearchEndDate();
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
