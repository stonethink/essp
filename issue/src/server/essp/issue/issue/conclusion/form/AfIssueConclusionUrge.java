package server.essp.issue.issue.conclusion.form;

import org.apache.struts.action.*;
import org.apache.struts.upload.*;
public class AfIssueConclusionUrge extends ActionForm {
    /** identifier field */
    private String  rid;

    /** nullable persistent field */
    private String urgedBy;

    /** nullable persistent field */
    private String urgeTo;

    /** nullable persistent field */
    private String  urgDate;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private FormFile attachment;

    /** nullable persistent field */
    private String attachmentdesc;

    /** nullable persistent field */

    private String issueRid="";
    private String urgedByScope;
    private String urgeToScope;
    public FormFile getAttachment() {
        return attachment;
    }

    public String getAttachmentdesc() {
        return attachmentdesc;
    }

    public String getDescription() {
        return description;
    }

    public String getIssueRid() {
        return issueRid;
    }

    public String getRid() {
        return rid;
    }



    public String getUrgDate() {
        return urgDate;
    }

    public String getUrgedBy() {
        return urgedBy;
    }

    public String getUrgeTo() {
        return urgeTo;
    }

    public String getUrgedByScope() {
        return urgedByScope;
    }

    public String getUrgeToScope() {
        return urgeToScope;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssueRid(String issueRid) {
        this.issueRid = issueRid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }



    public void setUrgDate(String urgDate) {
        this.urgDate = urgDate;
    }

    public void setUrgedBy(String urgedBy) {
        this.urgedBy = urgedBy;
    }

    public void setUrgeTo(String urgeTo) {
        this.urgeTo = urgeTo;
    }

    public void setUrgedByScope(String urgedByScope) {
        this.urgedByScope = urgedByScope;
    }

    public void setUrgeToScope(String urgeToScope) {
        this.urgeToScope = urgeToScope;
    }


}
