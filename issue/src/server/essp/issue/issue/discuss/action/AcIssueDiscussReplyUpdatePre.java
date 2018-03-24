package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.discuss.form.AfIssueDiscussReply;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussReply;
import java.util.Date;
import com.wits.util.*;
import db.essp.issue.IssueDiscussReply;
import server.essp.issue.common.logic.LgDownload;
import c2s.dto.FileInfo;
import server.essp.issue.common.logic.LgAccount;

public class AcIssueDiscussReplyUpdatePre extends AbstractISSUEAction {
        /**
         * 查找要修改的IssueDiscussReply
         * Call: LgIssueDiscuss.loadReply()
         * ForwardId: reply
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
           // AfIssueDiscussReply afIssueDiscussReply= (AfIssueDiscussReply)this.getForm();
            VbIssueDiscussReply vbDiscussReply=new VbIssueDiscussReply();
            /*String userName=this.getUser().getUseLoginName();//get current userName
            Date date=new Date();
            String strDate=comDate.dateToString(date,"yyyy-MM-dd");//get current date
            vbDiscussReply.setFilledBy(userName);
            vbDiscussReply.setFilledDate(strDate);*/
            String replyRid=(String)request.getParameter("replyRid");  //get issueRid from request
            vbDiscussReply.setRid(replyRid);

            String right_flag="true";
            String hasRight="false";
          if(request.getParameter("right_flag")!=null){
               hasRight=request.getParameter("right_flag");
              if("true".equalsIgnoreCase(hasRight))
                  right_flag = "false";
               else right_flag="true";
          }

            //get record by calling getReply()with vbDiscussReply and rid
             Long Rid=new Long(replyRid);
             LgIssueDiscuss lgIssueDiscuss=new LgIssueDiscuss();
             VbIssueDiscussReply vbReply=new VbIssueDiscussReply();
             //call LgIssueDiscuss logic
             vbReply=lgIssueDiscuss.getReply(Rid);
             /*if("false".equalsIgnoreCase(right_flag)){
                 vbReply.setFilledBy(vbDiscussReply.getFilledBy());
                 vbReply.setFilledDate(vbDiscussReply.getFilledDate());
             }*/
             String titleId=lgIssueDiscuss.getReply(new Long(replyRid)).getTitleId();


        String issueRid=lgIssueDiscuss.getTitle(new Long(titleId)).getIssueRid();
        LgAccount lga=new LgAccount();
        vbReply.setAccountRid(lga.getAccountByIssueRid(issueRid).getRid().toString());


            request.setAttribute("webVo",vbReply) ;
            request.setAttribute("right_flag",right_flag);
            request.setAttribute("hasRight",hasRight);

            data.getReturnInfo() .setForwardID("modifyDiscussReply");

    }


    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscuss lnkLgIssueDiscuss; */
}
