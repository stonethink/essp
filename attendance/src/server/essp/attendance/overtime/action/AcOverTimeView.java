package server.essp.attendance.overtime.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.attendance.overtime.logic.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.action.*;
import server.framework.common.*;

public class AcOverTimeView extends AbstractESSPAction {
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
        VbOverTime webVo = lg.getOverTimeByRid(new Long(rid));

        webVo.setReadOnly(Boolean.TRUE);

        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
