package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.viewbean.VbLeave;
import server.essp.attendance.leave.logic.LgLeave;

public class AcLeaveView extends AbstractESSPAction {
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
        LgLeave lg = new LgLeave();
        VbLeave webVo = lg.getLeaveByRid(new Long(rid));
        webVo.setReadOnly(Boolean.TRUE);

        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
