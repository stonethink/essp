package server.essp.timesheet.account.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.account.DtoAccount;
import server.essp.timesheet.account.service.IAccountService;

/**
 * <p>Title: save Account action</p>
 *
 * <p>Description: 保存项目general信息</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcSaveAccount extends AbstractESSPAction {

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
        DtoAccount dtoAccount = (DtoAccount) data.getInputInfo()
                                .getInputObj(DtoAccount.DTO);
        IAccountService accountService = (IAccountService)
                                         this.getBean("accountService");
        accountService.saveAccount(dtoAccount);
    }
}
