package c2s.essp.common.issue;

public interface IDtoIssue {

    public String getAccountId();

    public String getDescription();

    public String getFilleBy();

    public String getFilleDate();

    public String getIssueId();

    public String getIssueName();

    public String getPriority();

    public String getRid();

    public String getScope();

    public void setAccountId(String accountId);

    public void setDescription(String description);

    public void setFilleBy(String filleBy);

    public void setFilleDate(String filleDate);

    public void setIssueId(String issueId);

    public void setIssueName(String issueName);

    public void setPriority(String priority);

    public void setRid(String rid);

    public void setScope(String scope);

    public String getIssueStatus();

    public String getDueDate();

    public void setIssueStatus(String issueStatus);

    public void setDueDate(String dueDate);

    public String getIssueType();

    public void setIssueType(String issueType);

    public void setCloseDate(String closeDate);

    public String getCloseDate();

    public void setPrincipal(String principal);

    public String getPrincipal();

    public void setConfirmBy(String confirmBy);

    public String getConfirmBy();

    public void setPrincipalScope(String principalScope);

    public String getPrincipalScope();

    public void setConfirmByScope(String confirmByScope);

    public String getConfirmByScope();

}
