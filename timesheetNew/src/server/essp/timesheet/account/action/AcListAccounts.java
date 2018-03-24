package server.essp.timesheet.account.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.account.service.IAccountService;
import server.framework.common.BusinessException;

import java.util.List;
import c2s.essp.timesheet.account.DtoAccount;

/**
 * <p>Title: list Accounts action</p>
 *
 * <p>Description: 列出当前用户为PM的所有状态正常的项目</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcListAccounts extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
    	boolean isPmo = (Boolean) data.getInputInfo().getInputObj(DtoAccount.DTO_PMO);
        IAccountService accountService = (IAccountService)this.getBean("accountService");
        List list =  accountService.listAccounts(this.getUser().getUserLoginId(), isPmo);
        data.getReturnInfo().setReturnObj(DtoAccount.DTO_LIST, list);
    }
}
