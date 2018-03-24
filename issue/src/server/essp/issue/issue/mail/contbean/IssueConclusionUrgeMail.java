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
 * @author wenjun.yang
 * @version 1.0
 */
public class IssueConclusionUrgeMail {

    /** identifier field */
    private String  rid;

    /** nullable persistent field */
    private String urgedBy;

    /** nullable persistent field */
    private String urgeTo;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String attachmentdesc;

    /** nullable persistent field */
    private String issueRid="";

    /** nullable persistent field */
    private FormFile attachment;

    /** nullable persistent field */
    private String issue;

    /** nullable persistent field */
    private String account;
    private String urgeToScope;
    private String urgedByScope;
    public IssueConclusionUrgeMail() {
    }

    public String getAttachmentdesc() {
        return attachmentdesc;
    }

    public String getDescription() {
        return description;
    }

    public String getRid() {
        return rid;
    }

    public String getUrgedBy() {
        return urgedBy;
    }

    public String getIssueRid() {
        return issueRid;
    }

    public String getUrgeTo() {
        return urgeTo;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public String getIssue() {
        return issue;
    }

    public String getAccount() {
        return account;
    }

    public String getUrgeToScope() {
        return urgeToScope;
    }

    public String getUrgedByScope() {
        return urgedByScope;
    }

    public void setUrgedBy(String urgedBy) {
        this.urgedBy = urgedBy;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    public void setIssueRid(String issueRid) {
        this.issueRid = issueRid;
    }

    public void setUrgeTo(String urgeTo) {
        this.urgeTo = urgeTo;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUrgeToScope(String urgeToScope) {
        this.urgeToScope = urgeToScope;
    }

    public void setUrgedByScope(String urgedByScope) {
        this.urgedByScope = urgedByScope;
    }
}
