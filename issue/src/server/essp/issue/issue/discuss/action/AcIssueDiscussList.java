package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.issue.discuss.form.AfIssueDiscussTitle;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.discuss.logic.LgIssueDiscussList;
import java.util.List;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussTitle;
import java.util.ArrayList;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;

public class AcIssueDiscussList extends AbstractISSUEAction  {
        /**
         * 根据Issue查找对应的Discuss
         * Call:LgIssueDiscussList.list()
         * ForwardId:list
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            System.out.println("in IssueDiscussList Action!") ;
            String pmName="";
            String lgName="";
            AfIssueDiscussTitle issueDiscussForm=(AfIssueDiscussTitle)this.getForm() ;
            String Rid=issueDiscussForm.getIssueRid();
            if("".equals(Rid)||Rid==null||"null".equals(Rid) ){
                VbIssueDiscussTitle vtitle=new VbIssueDiscussTitle();
                List list=new ArrayList();
                list.add(vtitle);
                request.setAttribute("webVo", list);
                request.setAttribute("LoginUser",lgName);
                request.setAttribute("Manager",pmName);
            }else{

                //get Rid of table ISSUE
                Long issueRid = new Long(issueDiscussForm.getIssueRid());
                //get Discuss from table ISSUE_DISCUSS_TITLE
                LgIssueDiscussList discussList = new LgIssueDiscussList();
                List webVo = discussList.list(issueRid);
                request.setAttribute("webVo", webVo);
                //get AccountManager
                LgIssueDiscuss lgIssueDiscuss=new LgIssueDiscuss();
                pmName=lgIssueDiscuss.getPM(issueRid);
                request.setAttribute("Manager",pmName);
                //get login user name
                lgName=this.getUser().getUserLoginId();
                request.setAttribute("LoginUser",lgName);
            }
            data.getReturnInfo().setForwardID("list");
    }

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.LgIssueDiscussList lnkLgIssueDiscussList; */

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscussList lnkLgIssueDiscussList1; */
}
