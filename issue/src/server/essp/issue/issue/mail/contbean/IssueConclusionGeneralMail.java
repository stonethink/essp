package server.essp.issue.issue.mail.contbean;

import org.apache.struts.upload.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IssueConclusionGeneralMail {
    private String  uid;
    private String  account;
    private String  issueid;
    private String  issuetitle;
    private String  priority;
    private String  fillby;
    private String  filldate;
    private String  status;
    private String  duedate;
    private String  issuedescription;
    private String  attachmentdesc;
    private String  principal;
    private FormFile attachment;
    private String issue;
    private String principalScope;
    public IssueConclusionGeneralMail() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getUid() {
        return uid;
    }

    public String getDuedate() {
        return duedate;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getIssuetitle() {
        return issuetitle;
    }

    public String getIssueid() {
        return issueid;
    }

    public String getIssuedescription() {
        return issuedescription;
    }

    public String getFilldate() {
        return filldate;
    }

    public String getFillby() {
        return fillby;
    }

    public String getAttachmentdesc() {
        return attachmentdesc;
    }

    public String getPrincipal() {
        return principal;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public String getAccount() {
        return account;
    }

    public String getIssue() {
        return issue;
    }

    public String getPrincipalScope() {
        return principalScope;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public void setFillby(String fillby) {
        this.fillby = fillby;
    }

    public void setFilldate(String filldate) {
        this.filldate = filldate;
    }

    public void setIssuedescription(String issuedescription) {
        this.issuedescription = issuedescription;
    }

    public void setIssueid(String issueid) {
        this.issueid = issueid;
    }

    public void setIssuetitle(String issuetitle) {
        this.issuetitle = issuetitle;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setPrincipalScope(String principalScope) {
        this.principalScope = principalScope;
    }

    private void jbInit() throws Exception {
    }
}
