package server.essp.timesheet.account.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.account.service.IAccountService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.account.DtoAccount;

public class AcQueryAccounts extends AbstractESSPAction {

	public void executeAct(HttpServletRequest arg0, HttpServletResponse arg1,
			TransactionData data) throws BusinessException {
		DtoAccount dto = (DtoAccount) data.getInputInfo().getInputObj(DtoAccount.DTO_CONDITION);
		IAccountService accountService = (IAccountService)this.getBean("accountService");
		List list = accountService.queryAccounts(dto, this.getUser().getUserLoginId());
		data.getReturnInfo().setReturnObj(DtoAccount.DTO_LIST, list);
	}

}
