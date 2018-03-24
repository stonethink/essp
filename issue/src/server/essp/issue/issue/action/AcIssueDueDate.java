package server.essp.issue.issue.action;

import server.essp.issue.common.action.AbstractISSUEAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.form.AfIssue;
import server.essp.issue.issue.logic.LgIssue;

import server.essp.issue.issue.logic.LgDueDate;
import server.essp.issue.issue.viewbean.VbDueDate;
import server.essp.issue.issue.form.AfDueDate;

public class AcIssueDueDate extends AbstractISSUEAction {
    public AcIssueDueDate() {
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
         AfDueDate dueDateForm=(AfDueDate)this.getForm();
         LgDueDate logic=new LgDueDate();
         VbDueDate viewbean=logic.accountDueDate(dueDateForm);
         this.getRequest().setAttribute("webVo",viewbean);
    }
}
