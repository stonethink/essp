package server.essp.attendance.workflow.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import c2s.essp.common.user.DtoUser;
import server.workflow.wfengine.WfEngine;
import java.util.List;
import server.workflow.wfengine.*;
import server.essp.attendance.*;
import c2s.workflow.DtoWorkingProcess;
import c2s.workflow.DtoRequestProcess;

public class AcProcessList extends AbstractESSPAction {
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
        try {
            VbWorkProcess webVo = new VbWorkProcess();
            WfEngine engine = new WfEngine();
            List requestProcessList = engine.
                                      listRequestProcessByUserId(user.getUserLoginId());
            List workingProcessList = engine
                                      .listWorkingProcessByUserId(user.getUserLoginId());
            webVo.setRequestProcessList(requestProcessList);
            webVo.setWorkingProcessList(workingProcessList);
            request.setAttribute("webVo",webVo);
            data.getReturnInfo().setForwardID("success");
        } catch (WfException ex) {
            throw new BusinessException("WK_FLOW_00001",ex.getMessage());
        }
    }
}
