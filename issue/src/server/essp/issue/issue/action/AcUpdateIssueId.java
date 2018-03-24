package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.issue.form.AfIssueId;
import server.essp.issue.issue.logic.LgIssueId;
import server.essp.issue.issue.viewbean.VbIssueId;

public class AcUpdateIssueId extends AbstractISSUEAction {
    public AcUpdateIssueId() {
    }

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
          AfIssueId issueId=(AfIssueId)this.getForm();
          LgIssueId logic=new LgIssueId();
          VbIssueId viewBean=logic.getCheckIssueId(issueId);
          this.getRequest().setAttribute("webVo",viewBean);

    }
}
