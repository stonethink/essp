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
public class IssueConclusionDiscussMail {
    private String rid;
    private String mailto;
    private String cc;
    private String title;
    private String filldate;
    private String fillby;
    private FormFile attachment;
    private String  attachmentdesc;
    private String description;
    private String issue;
    private String account;
    private String remark;
    private String filledByScope;

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    public void setFillby(String fillby) {
        this.fillby = fillby;
    }

    public void setFilldate(String filldate) {
        this.filldate = filldate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setFilledByScope(String filledByScope) {
        this.filledByScope = filledByScope;
    }

    public String getRid() {
        return rid;
    }

    public String getMailto() {
        return mailto;
    }

    public String getCc() {
        return cc;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public String getAttachmentdesc() {
        return attachmentdesc;
    }

    public String getFillby() {
        return fillby;
    }

    public String getFilldate() {
        return filldate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIssue() {
        return issue;
    }

    public String getAccount() {
        return account;
    }

    public String getRemark() {
        return remark;
    }

    public String getFilledByScope() {
        return filledByScope;
    }
}
