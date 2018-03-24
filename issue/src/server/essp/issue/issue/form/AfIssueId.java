package server.essp.issue.issue.form;

import org.apache.struts.action.*;

public class AfIssueId extends ActionForm {
   private String accountId;
   private String accountRid;

   private String rid;

    private String issueId;

    private String issueType;
    private String typeName;

    public String getAccountId() {
        return accountId;
    }

    public String getRid() {
        return rid;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getAccountRid() {
        return accountRid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setAccountRid(String accountRid) {
        this.accountRid = accountRid;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
