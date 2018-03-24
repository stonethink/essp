package server.essp.attendance.modify.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.logic.LgLeave;
import server.essp.attendance.modify.logic.LgLeaveModify;
import server.essp.attendance.modify.viewbean.VbLeaveModify;

public class AcLeaveModifyPre extends AbstractESSPAction {
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
        String rid = request.getParameter("rid");
        LgLeaveModify lg = new LgLeaveModify();
        VbLeaveModify webVo = lg.getLeaveModifyByRid(new Long(rid));
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
