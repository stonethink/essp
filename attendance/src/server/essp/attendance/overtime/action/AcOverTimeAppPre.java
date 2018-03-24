package server.essp.attendance.overtime.action;

import java.util.*;

import javax.servlet.http.*;

import c2s.dto.*;
import c2s.essp.common.user.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;

public class AcOverTimeAppPre extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        DtoUser user = this.getUser();
        if(user == null)
            throw new BusinessException("unknown","The user does not login in!");
        String loginId = user.getUserLoginId();
        VbOverTimeApp webVo = new VbOverTimeApp();
        webVo.setLoginId(loginId);
        IAccountUtil lg = AccountFactory.create();
        List accountList = lg.listOptAccountsDefaultStyle(loginId);
        if(accountList == null || accountList.size() == 0)
            throw new BusinessException("TC_OVERTIME_00000",loginId + " is not in any account!");
        webVo.setAccountList(accountList);
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
