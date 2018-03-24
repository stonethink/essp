package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.logic.LgLeave;
import java.util.List;

public class AcLeaveReviewLog extends AbstractESSPAction {
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
        List webVo = lg.listLeaveReviewLog(new Long(rid));
        request.setAttribute("webVo",webVo);
    }
}
