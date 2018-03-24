package server.essp.attendance.overtime.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.overtime.form.AfOverTimeAppCaculate;
import server.essp.attendance.overtime.logic.LgOverTime;
import server.essp.attendance.overtime.viewbean.VbOverTimeDetail;
import java.util.Enumeration;

public class AcOverTimeAppCaculate extends AbstractESSPAction {
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
        AfOverTimeAppCaculate form = (AfOverTimeAppCaculate) this.getForm();
        LgOverTime lg = new LgOverTime();
        VbOverTimeDetail webVo = lg.cacualteOverTime(form);
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
