package server.essp.issue.issue.discuss.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussTitle;
import java.util.Date;
import com.wits.util.*;
import server.essp.issue.common.logic.LgAccount;
import db.essp.issue.Issue;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;
import db.essp.issue.IssueDiscussMailBak;

public class AcIssueDiscussTitleAddPre extends AbstractISSUEAction {
        /**
         * 获得新增IssueDiscussTitle所需内容
         * 新建VbIssueDiscussTitle，设置其issueCode
         * ForwardId:title
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            System.out.println("in issueDiscussTitleAddPre action!");
            VbIssueDiscussTitle vbDiscussTitle=new VbIssueDiscussTitle();
            String userName=this.getUser().getUserLoginId();//get current userName
            Date date=new Date();
            String strDate=comDate.dateToString(date,"yyyy-MM-dd");//get current date
            vbDiscussTitle.setFilledBy(userName);
            vbDiscussTitle.setFilledDate(strDate);
            String issueRid=(String)request.getParameter("issueRid");  //get issueRid from request
            vbDiscussTitle.setIssueRid(issueRid);
            LgAccount lga=new LgAccount();
            String accountrid = lga.getAccountByIssueRid(issueRid).getRid().toString();
            vbDiscussTitle.setAccountRid(accountrid);
            try{
                Long rid = new Long(issueRid);
                Issue issue = (Issue)this.getDbAccessor().load(Issue.class, rid);
                LgIssueDiscuss lg = new LgIssueDiscuss();
                IssueDiscussMailBak idmb = lg.getIssueDiscussMailBak(accountrid,issue.getIssueType(),"Topic");
                if(idmb!=null){
                    vbDiscussTitle.setTo(idmb.getMailto());
                    vbDiscussTitle.setCc(idmb.getMailcc());
                }
            }catch(Exception ex){
                log.error(ex);
                ex.printStackTrace();

            }
            request.setAttribute("webVo",vbDiscussTitle);

            data.getReturnInfo() .setForwardID("addDiscussTitle");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscuss lnkLgIssueDiscuss; */
}
