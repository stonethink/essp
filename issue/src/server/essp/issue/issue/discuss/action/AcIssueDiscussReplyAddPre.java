package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import java.util.Date;
import server.essp.issue.issue.discuss.viewbean.VbIssueDiscussReply;
import com.wits.util.comDate;
import server.essp.issue.common.logic.LgAccount;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;
import db.essp.issue.IssueDiscussMailBak;
import db.essp.issue.Issue;

public class AcIssueDiscussReplyAddPre extends AbstractISSUEAction {
    /**
     * 获得新增IssueDiscussReply所需内容
     * 新建VbIssueDiscussReply，设置其titleId
     * ForwardId:reply
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        System.out.println("in issueDiscussReplyAddPre action!");
        VbIssueDiscussReply vbDiscussReply = new VbIssueDiscussReply();
        String userName=this.getUser().getUserLoginId(); //get current userName
        Date date = new Date();
        String strDate = comDate.dateToString(date, "yyyy-MM-dd"); //get current date
        vbDiscussReply.setFilledBy(userName);
        vbDiscussReply.setFilledDate(strDate);
        String titleId = (String) request.getParameter("titleRid"); //get issueRid from request
        vbDiscussReply.setTitleId(titleId);
        LgIssueDiscuss lgIssueDis=new LgIssueDiscuss();
        String issueRid=lgIssueDis.getTitle(new Long(titleId)).getIssueRid();
        LgAccount lga=new LgAccount();
        String accountrid = lga.getAccountByIssueRid(issueRid).getRid().toString();
        vbDiscussReply.setAccountRid(accountrid);
        try{
                Long rid = new Long(issueRid);
                Issue issue = (Issue)this.getDbAccessor().load(Issue.class, rid);
                LgIssueDiscuss lg = new LgIssueDiscuss();
                IssueDiscussMailBak idmb = lg.getIssueDiscussMailBak(accountrid,issue.getIssueType(),"Reply");
                if(idmb!=null){
                    vbDiscussReply.setTo(idmb.getMailto());
                    vbDiscussReply.setCc(idmb.getMailcc());
                }
            }catch(Exception ex){
                log.error(ex);
                ex.printStackTrace();

            }

        request.setAttribute("webVo", vbDiscussReply);
        data.getReturnInfo().setForwardID("addDiscussReply");

    }

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscuss lnkLgIssueDiscuss; */
}
