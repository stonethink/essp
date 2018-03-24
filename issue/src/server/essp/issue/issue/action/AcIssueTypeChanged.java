package server.essp.issue.issue.action;

import server.framework.action.AbstractBusinessAction;
import c2s.essp.common.user.DtoUser;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.issue.common.form.AfIssueOptions;
import server.essp.issue.common.logic.LgIssueType;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import server.framework.taglib.util.TagUtils;

public class AcIssueTypeChanged extends AbstractBusinessAction {
    public AcIssueTypeChanged() {
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

        DtoUser user = (DtoUser)this.getSession().getAttribute(DtoUser.
            SESSION_USER);
        if (user != null) {
            userType = user.getUserType();
        }
        LgIssueType logic = new LgIssueType();
        if(prioritySelectbox!=null) {
            List priorityList=logic.getPriorityOptions(issueType);
            TagUtils.addSelectedOption(0,priorityList,"  ----  Please Select  ----  ","");
            request.setAttribute("priorityOptions",
                                 priorityList);
            request.setAttribute("prioritySelectbox",prioritySelectbox);
        }
        if(scopeSelectbox!=null) {
            List scopeList=logic.getScopeOptions(issueType,
                userType);
            TagUtils.addSelectedOption(0,scopeList,"  ----  Please Select  ----  ","");
            request.setAttribute("scopeOptions",
                                 scopeList);
            request.setAttribute("scopeSelectbox",scopeSelectbox);
        }
        if(statusSelectbox!=null) {
            List statusList=logic.getStatusOptions(issueType);
            TagUtils.addSelectedOption(0,statusList,"  ----  Please Select  ----  ","");
            request.setAttribute("statusOptions",
                                 statusList);
            request.setAttribute("statusSelectbox",statusSelectbox);
        }
    }
}
