package server.essp.issue.issue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.framework.common.BusinessException;
import server.essp.issue.issue.form.AfIssueId;
import server.essp.issue.issue.viewbean.VbIssueId;
import server.essp.issue.issue.logic.LgIssueId;

public class AcIssueId extends AbstractISSUEAction {
    public AcIssueId() {
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
    /**
     * 取出form；
     * 创建并调用logic；
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
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
