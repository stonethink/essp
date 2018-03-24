package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;

public class AcIssueDiscussReplyDelete extends AbstractISSUEAction {
        /**
         * ¸ù¾ÝÖ÷¼üÉ¾³ýIssueDiscussReply
         * Call:LgIssueDiscuss.deleteReply()
         * ForwardId:list
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            System.out.println("in IssueDiscussReplyDelete action!");
           Long replyRid=new Long(request.getParameter("replyRid"));//get titleRid from request
           String issueRid=(request.getParameter("issueRid")).toString();
           LgIssueDiscuss lgIssueDiscuss=new LgIssueDiscuss();
           lgIssueDiscuss.deleteReply(replyRid);
           request.setAttribute("issueRid",issueRid) ;
           data.getReturnInfo().setForwardID("list") ;

    }

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscuss lnkLgIssueDiscuss; */
}
