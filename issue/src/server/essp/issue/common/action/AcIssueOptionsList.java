package server.essp.issue.common.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import c2s.essp.common.user.DtoUser;
import server.essp.issue.common.logic.LgIssueType;
import server.essp.issue.common.form.AfIssueOptions;

public class AcIssueOptionsList extends AbstractBusinessAction {
    public AcIssueOptionsList() {
    }

    /**
     * 根据IssueType 找到所有的Priority
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
        AfIssueOptions form=(AfIssueOptions)this.getForm();
        String issueType = form.getIssueType();
        String userType = form.getUserType();
        String prioritySelectbox=form.getPrioritySelectbox();
        String scopeSelectbox=form.getScopeSelectbox();
        String statusSelectbox=form.getStatusSelectbox();

        DtoUser user = (DtoUser)this.getSession().getAttribute(DtoUser.SESSION_USER);
        if (user != null) {
            userType = user.getUserType();
        }
        LgIssueType logic = new LgIssueType();
        if(prioritySelectbox!=null) {
            request.setAttribute("priorityOptions",
                                 logic.getPriorityOptions(issueType));
            request.setAttribute("prioritySelectbox",prioritySelectbox);
        }
        if(scopeSelectbox!=null) {
            request.setAttribute("scopeOptions",
                                 logic.getScopeOptions(issueType,
                userType));
            request.setAttribute("scopeSelectbox",scopeSelectbox);
        }
        if(statusSelectbox!=null) {
            request.setAttribute("statusOptions",
                                 logic.getStatusOptions(issueType));
            request.setAttribute("statusSelectbox",statusSelectbox);
        }
    }
}
