package server.essp.timesheet.account.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.account.DtoAccount;
import server.essp.timesheet.account.service.IAccountService;
import server.essp.timesheet.code.codetype.service.ICodeTypeService;
import server.essp.timesheet.methodology.service.IMethodService;

import java.util.Vector;
import c2s.essp.timesheet.code.DtoCodeType;

/**
 * <p>Title: load Account action</p>
 *
 * <p>Description: 获取项目general信息</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcLoadAccount extends AbstractESSPAction {
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
        Long rid = (Long) data.getInputInfo()
                              .getInputObj(DtoAccount.DTO_RID);
        IAccountService accountService = (IAccountService)
                                         this.getBean("accountService");
        ICodeTypeService codeTypeService = (ICodeTypeService)
                                           this.getBean("codeTypeService");
        IMethodService methodService = (IMethodService) this.getBean("methodService");
        Vector vCode = codeTypeService.ListCodeTypeDtoComboItem(false);
        Vector vLeave = codeTypeService.ListCodeTypeDtoComboItem(true);
        Vector vMethod = methodService.getMethodCmb();
        DtoAccount dtoAccount =  accountService.loadAccount(rid);
        data.getReturnInfo().setReturnObj(DtoAccount.DTO, dtoAccount);
        data.getReturnInfo().setReturnObj(DtoCodeType.DTO, vCode);
        data.getReturnInfo().setReturnObj(DtoCodeType.DTO_IS_LEAVE_TYPE, vLeave);
        data.getReturnInfo().setReturnObj(DtoAccount.DTO_METHOD, vMethod);
    }
}
