package c2s.essp.common.issue;

import c2s.dto.*;

public class DtoIssue extends DtoBase implements IDtoIssue{
  private String rid;
  private String accountId;
  private String priority;
  private String filleBy;
  private String filleDate;
  private String scope;
  private String issueId;
  private String issueName;
  private String description;
  private String principal;
  private String dueDate;
  private String issueStatus;
  private String issueType;
  private String confirmBy;
  private String closeDate;
    private String principalScope;
    private String confirmByScope;
    public String getAccountId() {
        return accountId;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public String getConfirmBy() {
        return confirmBy;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getFilleBy() {
        return filleBy;
    }

    public String getFilleDate() {
        return filleDate;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPriority() {
        return priority;
    }

    public String getRid() {
        return rid;
    }

    public String getScope() {
        return scope;
    }

    public String getDescription() {
        return description;
    }

    public String getPrincipalScope() {
        return principalScope;
    }

    public String getConfirmByScope() {
        return confirmByScope;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setFilleBy(String filleBy) {
        this.filleBy = filleBy;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConfirmBy(String confirmBy) {
        this.confirmBy = confirmBy;
    }

    public void setFilleDate(String filleDate) {
        this.filleDate = filleDate;
    }

    public void setPrincipalScope(String principalScope) {
        this.principalScope = principalScope;
    }

    public void setConfirmByScope(String confirmByScope) {
        this.confirmByScope = confirmByScope;
    }

}
