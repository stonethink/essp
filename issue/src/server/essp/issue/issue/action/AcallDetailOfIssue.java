package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.issue.logic.LgallDetailOfIssue;
import java.util.HashMap;
import server.essp.issue.issue.viewbean.VbAllDetailOfIssue;

public class AcallDetailOfIssue extends AbstractISSUEAction {

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
        VbAllDetailOfIssue webVo=new VbAllDetailOfIssue();
        Long rid=new Long(request.getParameter("issueRid"));
        LgallDetailOfIssue lgallDetailOfIssue=new LgallDetailOfIssue();
        webVo=lgallDetailOfIssue.getAllDetailOfIssue(rid);
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("listAll");

    }
}
