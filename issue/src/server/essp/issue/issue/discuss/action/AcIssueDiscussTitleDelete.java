package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;

public class AcIssueDiscussTitleDelete extends AbstractISSUEAction {
        /**
         * 根据主键删除IssueDiscussTitle，并级联删除对应IssueDiscussReply
         * Call:LgIssueDiscuss.deleteTitle()
         * ForwardId:list
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            System.out.println("in IssueDiscussTitleDelete action!");
            Long titleRid=new Long(request.getParameter("titleRid"));//get titleRid from request
            String issueRid=(request.getParameter("issueRid")).toString();
            LgIssueDiscuss lgIssueDiscuss=new LgIssueDiscuss();
            lgIssueDiscuss.deleteTitle(titleRid);
            request.setAttribute("issueRid",issueRid) ;
            data.getReturnInfo().setForwardID("list") ;
    }

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscuss lnkLgIssueDiscuss; */
}
