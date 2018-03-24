package server.essp.issue.issue.discuss.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.common.action.AbstractISSUEAction;
import server.essp.issue.issue.discuss.form.AfIssueDiscussTitle;
import server.essp.issue.issue.discuss.logic.LgIssueDiscuss;
import server.essp.issue.issue.logic.LgIssue;
import server.essp.issue.common.logic.LgDownload;
import c2s.essp.common.user.DtoUser;
import server.essp.issue.common.form.MailInputDataBean;
import java.util.HashMap;
import server.essp.issue.common.logic.LgAccount;
import db.essp.issue.Issue;
import c2s.dto.FileInfo;
import db.essp.issue.IssueDiscussTitle;

public class AcIssueDiscussTitleUpdate extends AbstractISSUEAction {
    /**
     * 根据AfIssueDiscussTitle修改IssueDiscussTitle
     * Call:LgIssueDiscuss.updateTitle()
     * ForwardId:success

     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        // System.out.println("in issueDiscussTitleUpdate action!");
        AfIssueDiscussTitle afIssueDiscussTitle = (AfIssueDiscussTitle)this.
                                                  getForm();
        String isMail = (String) request.getParameter("isMail");
        LgIssueDiscuss lgIssueDiscuss = new LgIssueDiscuss();
        lgIssueDiscuss.updateTitle(afIssueDiscussTitle, "");
        IssueDiscussTitle topic = lgIssueDiscuss.loadTitle(new Long(
            afIssueDiscussTitle.getRid()));
        if (isMail != null && !isMail.equals("")) {
            //在request里面放入要传进去的参数,以一个JavaBean的形式
            MailInputDataBean inputData = new MailInputDataBean();
            LgAccount lga = new LgAccount();
            Long accountRid = lga.getAccountByIssueRid(afIssueDiscussTitle.
                getIssueRid()).getRid();
            inputData.setAcntRid(accountRid);
            Issue issue = null;
            LgIssue lgIssue = new LgIssue();
            issue = lgIssue.load(new Long(afIssueDiscussTitle.getIssueRid()));
//            System.out.println(issue.getRid());
            inputData.setIssueType(issue.getIssueType());
            inputData.setCardType("TOPIC");
            inputData.setMailTo("");
            inputData.setCc(topic.getFilledBy());
            DtoUser user = this.getUser();
            inputData.setFrom(user.getEmail());
            inputData.setTitle(topic.getTitle());
            inputData.setDataBean(topic);
            HashMap atts = new HashMap();
            if (topic.getAttachmentId() != null) {
                LgDownload downloadLogic = new LgDownload();
                FileInfo fileInfo = new FileInfo();
                String accountCode = lga.getAccountByRid(accountRid.toString()).
                                     getId();
                fileInfo.setAccountcode(accountCode);
                fileInfo.setId(topic.getAttachmentId());
                fileInfo.setFilename(topic.getAttachment());
                atts.put(topic.getAttachment(),
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
