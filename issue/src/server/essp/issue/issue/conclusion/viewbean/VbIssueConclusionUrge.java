package server.essp.issue.issue.conclusion.viewbean;




/**
 * Issue Conclusion Uege界面显示所需内容
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
public class VbIssueConclusionUrge {
    /** identifier field */
    private String  rid="";

    /** nullable persistent field */
    private String urgedBy="";

    /** nullable persistent field */
    private String urgeTo="";

    /** nullable persistent field */
    private String  urgDate="";

    /** nullable persistent field */
    private String description="";

    /** nullable persistent field */
    private String attachment="";

    /** nullable persistent field */
    private String attachmentdesc="";

    /** nullable persistent field */
    private String rst="";
    private String issueRid="";
      private String accountId="";
      private String isPrincipal="";
    private String urgeToScope;
    private String urgedByScope;
    public VbIssueConclusionUrge() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public String getAttachment() {
        return attachment;
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

    public String getRst() {
        return rst;
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

    public String getIssueRid() {
        return issueRid;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getIsPrincipal() {
        return isPrincipal;
    }

    public String getUrgeToScope() {
        return urgeToScope;
    }

    public String getUrgedByScope() {
        return urgedByScope;
    }


    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setRst(String rst) {
        this.rst = rst;
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

    public void setIssueRid(String issueRid) {
        this.issueRid = issueRid;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setIsPrincipal(String isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

    public void setUrgeToScope(String urgeToScope) {
        this.urgeToScope = urgeToScope;
    }

    public void setUrgedByScope(String urgedByScope) {
        this.urgedByScope = urgedByScope;
    }


}
