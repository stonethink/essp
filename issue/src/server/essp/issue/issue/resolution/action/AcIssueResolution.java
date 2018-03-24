package server.essp.issue.issue.resolution.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.resolution.logic.LgIssueResolution;
import server.essp.issue.issue.resolution.form.AfIssueResolution;
import server.essp.issue.common.action.AbstractISSUEAction;


public class AcIssueResolution extends AbstractISSUEAction {
    /**
     * 根据传入AfIssueResolution分析Issue
     * Call：LgIssueResolution.resolution()
     * ForwardId:success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfIssueResolution form = (AfIssueResolution)this.getForm();
        LgIssueResolution logic=new LgIssueResolution();
        logic.resolution(form);
        request.setAttribute("rid",form.getRid());
        request.setAttribute("isMail",request.getParameter("isMail"));
    }

    /** @link dependency */
    /*# server.essp.issue.issue.resolution.logic.LgIssueResolution lnkLgIssueResolution; */
}
