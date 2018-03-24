package server.essp.attendance.leave.action;

import java.util.*;

import javax.servlet.http.*;

import c2s.dto.*;
import c2s.essp.common.calendar.*;
import c2s.essp.common.user.*;
import itf.hr.*;
import server.essp.attendance.leave.logic.*;
import server.essp.attendance.leave.viewbean.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import itf.account.AccountFactory;

public class AcLeaveAppPre extends AbstractESSPAction {
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
        user = HrFactory.create().findResouce(loginId);
        if(user.getOrgId() == null)
            throw new BusinessException("error","The user:" + loginId + " can not leave because he is not in any dept!");
        List accountList = AccountFactory.create().listOptAccountsDefaultStyle(loginId);
        if(accountList == null || accountList.size() ==0)
            throw new BusinessException("error","The user:" + loginId + " can not leave because he is not in any account!");
        VbLeaveApp webVo = new VbLeaveApp();
        webVo.setAccountList(accountList);

        webVo.setLoginId(loginId);
        webVo.setOrgId(user.getOrgId());
        webVo.setOrganization(user.getOrganization());

        LgLeave lg = new LgLeave();
        VbLeavePersonalStatus leaveStatus = lg.getUserLeaveStatus(loginId);
        webVo.setLeaveStatus(leaveStatus);

        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
