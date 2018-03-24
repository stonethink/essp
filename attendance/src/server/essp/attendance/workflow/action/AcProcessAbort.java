package server.essp.attendance.workflow.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.workflow.wfengine.WfEngine;
import server.workflow.wfengine.*;

public class AcProcessAbort extends AbstractESSPAction {
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
        String wkRid = request.getParameter("wkRid");
        WfEngine engine = new WfEngine();
        try {
            WfProcess process = engine.getProcessInstance(new Long(wkRid));
            process.abort();
        } catch (WfException ex) {
            throw new BusinessException("WF","error abort process",ex);
        }
    }
}
