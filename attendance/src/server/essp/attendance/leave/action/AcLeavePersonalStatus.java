package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import c2s.essp.common.user.DtoUser;
import server.essp.attendance.leave.viewbean.VbLeavePersonalStatus;

import server.essp.attendance.leave.logic.LgLeave;

public class AcLeavePersonalStatus extends AbstractESSPAction {
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
        String yearStr = request.getParameter("year");
        LgLeave lgLeave = new LgLeave();

        VbLeavePersonalStatus webVo = lgLeave.getUserLeaveStatus(loginId,yearStr);


        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
