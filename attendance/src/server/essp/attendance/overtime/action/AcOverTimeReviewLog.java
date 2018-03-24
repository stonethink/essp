package server.essp.attendance.overtime.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.overtime.logic.LgOverTime;
import java.util.List;

public class AcOverTimeReviewLog extends AbstractESSPAction {
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
        LgOverTime lg = new LgOverTime();
        List webVo = lg.listOverTimeReviewLog(new Long(rid));
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
