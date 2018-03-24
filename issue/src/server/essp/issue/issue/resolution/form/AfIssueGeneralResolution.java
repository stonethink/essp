package server.essp.issue.issue.resolution.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

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
public class AfIssueGeneralResolution extends ActionForm {
    private String rid;
    private String issuefid;
    private String mailto;
    private String cc;
    private String issueid;
    private String issuename;
    private String account;
    private String priority;
    private String fillby;
    private String filldate;
    private String status;
    private String duedate;
    private String resolution;
    private String assigneddate;
    private String finishdate;
    private String issuedesc;
    private FormFile attachment1;
    private FormFile attachment2;
    private String attachmentname1;
    private String attachmentname2;
    private String attachmentdesc1;
    private String attachmentdesc2;
    private String issue;
    private String accountId;
    public String getAccount() {
        return account;
    }

    public String getAssigneddate() {
        return assigneddate;
    }

    public FormFile getAttachment1() {
        return attachment1;
    }

    public FormFile getAttachment2() {
        return attachment2;
    }

    public String getAttachmentdesc1() {
        return attachmentdesc1;
    }

    public String getAttachmentdesc2() {
        return attachmentdesc2;
    }

    public String getCc() {
        return cc;
    }

    public String getDuedate() {
        return duedate;
    }

    public String getFillby() {
        return fillby;
    }

    public String getFilldate() {
        return filldate;
    }

    public String getFinishdate() {
        return finishdate;
    }

    public String getIssuedesc() {
        return issuedesc;
    }

    public String getIssueid() {
        return issueid;
    }

    public String getIssuename() {
        return issuename;
    }

    public String getMailto() {
        return mailto;
    }

    public String getPriority() {
        return priority;
    }

    public String getResolution() {
        return resolution;
    }

    public String getRid() {
        return rid;
    }

    public String getStatus() {
        return status;
    }

    public String getIssuefid() {
        return issuefid;
    }

    public String getAttachmentname1() {
        return attachmentname1;
    }

    public String getAttachmentname2() {
        return attachmentname2;
    }

    public String getIssue() {
        return issue;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public void setIssuename(String issuename) {
        this.issuename = issuename;
    }

    public void setIssueid(String issueid) {
        this.issueid = issueid;
    }

    public void setIssuedesc(String issuedesc) {
        this.issuedesc = issuedesc;
    }

    public void setFinishdate(String finishdate) {
        this.finishdate = finishdate;
    }

    public void setFilldate(String filldate) {
        this.filldate = filldate;
    }

    public void setFillby(String fillby) {
        this.fillby = fillby;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setAttachmentdesc2(String attachmentdesc2) {
        this.attachmentdesc2 = attachmentdesc2;
    }

    public void setAttachmentdesc1(String attachmentdesc1) {
        this.attachmentdesc1 = attachmentdesc1;
    }

    public void setAttachment2(FormFile attachment2) {
        this.attachment2 = attachment2;
    }

    public void setAttachment1(FormFile attachment1) {
        this.attachment1 = attachment1;
    }

    public void setAssigneddate(String assigneddate) {
        this.assigneddate = assigneddate;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setIssuefid(String issuefid) {
        this.issuefid = issuefid;
    }

    public void setAttachmentname2(String attachmentname2) {
        this.attachmentname2 = attachmentname2;
    }

    public void setAttachmentname1(String attachmentname1) {
        this.attachmentname1 = attachmentname1;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
