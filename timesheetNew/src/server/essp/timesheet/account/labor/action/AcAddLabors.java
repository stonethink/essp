package server.essp.timesheet.account.labor.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.account.labor.service.ILaborService;
import server.essp.timesheet.synchronization.service.ISyncMainService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.account.DtoAccount;

public class AcAddLabors extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		Long rid = (Long) data.getInputInfo()
        								.getInputObj(DtoAccount.DTO_RID);
		String loginIds = (String) data.getInputInfo()
        								.getInputObj(DtoAccount.DTO_LOGINIDS);
		ILaborService service = (ILaborService) this.getBean("laborService");
		List loginIdList = 
			service.addLabors(loginIds, rid);
		
		ISyncMainService syncService = (ISyncMainService) this.getBean("syncTSMainService");
		List dtoList = syncService.dataToDtoList(loginIdList, rid);
		syncService.insert(dtoList);
	}

}
