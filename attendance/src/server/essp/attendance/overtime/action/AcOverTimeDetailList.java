package server.essp.attendance.overtime.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.overtime.logic.LgOverTime;
import server.essp.attendance.overtime.viewbean.VbOverTimeDetail;

public class AcOverTimeDetailList extends AbstractESSPAction {
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
        String readOnly = request.getParameter("readOnly");
        String rid = request.getParameter("rid");
        LgOverTime lg = new LgOverTime();
        VbOverTimeDetail webVo = lg.getOverTimeDetail(new Long(rid));
        webVo.setReadOnly(readOnly);
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
