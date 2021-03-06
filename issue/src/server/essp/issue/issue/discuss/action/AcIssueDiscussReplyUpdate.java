package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.discuss.form.AfIssueDiscussReply;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;
import server.essp.issue.common.logic.LgDownload;
import c2s.essp.common.user.DtoUser;
import server.essp.issue.common.form.MailInputDataBean;
import java.util.HashMap;
import server.essp.issue.common.logic.LgAccount;
import db.essp.issue.Issue;
import c2s.dto.FileInfo;
import db.essp.issue.IssueDiscussReply;

public class AcIssueDiscussReplyUpdate extends AbstractISSUEAction {
    /**
     * 根据AfIssueDiscussReply修改IssueDiscussReply
     * Call:LgIssueDiscuss.updateReply()
     * ForwardId:success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        System.out.println("in IssueDiscussReplyUpdate action!");
        AfIssueDiscussReply afIssueDiscussReply = (AfIssueDiscussReply)this.
                                                  getForm();
        String isMail = (String) request.getParameter("isMail");
        LgIssueDiscuss lgIssueDiscuss = new LgIssueDiscuss();
        lgIssueDiscuss.updateReply(afIssueDiscussReply, "");
        IssueDiscussReply reply = lgIssueDiscuss.loadReply(new Long(
            afIssueDiscussReply.getRid()));
        if (isMail != null && !isMail.equals("")) {
            //调用发邮件的功能，并发送邮件
            MailInputDataBean inputData = new MailInputDataBean();
            LgAccount lga = new LgAccount();
            Long accountRid = lga.getAccountByIssueRid(reply.
                getIssueDiscussTitle().getIssue().getRid().toString()).getRid();
            inputData.setAcntRid(accountRid);

            Issue issue = null;
            issue = reply.getIssueDiscussTitle().getIssue();
            inputData.setIssueType(issue.getIssueType());
            inputData.setCardType("REPLY");
            //默认加入此TOPIC的发起人
            inputData.setMailTo(reply.getIssueDiscussTitle().getFilledBy());
            inputData.setCc(reply.getFilledBy());
            DtoUser user = this.getUser();
            inputData.setFrom(user.getEmail());
            inputData.setTitle(reply.getTitle());
            inputData.setDataBean(reply);
            HashMap atts = new HashMap();
            if (reply.getAttachmentId() != null) {
                LgDownload downloadLogic = new LgDownload();
                FileInfo fileInfo = new FileInfo();
                String accountCode = lga.getAccountByRid(inputData.getAcntRid().
                    toString()).getId();
                fileInfo.setAccountcode(accountCode);
                fileInfo.setId(reply.getAttachmentId());
                fileInfo.setFilename(reply.getAttachment());
                atts.put(reply.getAttachment(),
                         downloadLogic.getDownloadUrl(fileInfo));
            }
            inputData.setAttachments(atts);
            request.setAttribute("inputData", inputData);

            data.getReturnInfo().setForwardID("sendMail");

        }

    }

    /** @link dependency */
    /*# server.essp.issue.issue.discuss.logic.LgIssueDiscuss lnkLgIssueDiscuss; */
}
