package server.essp.issue.issue.viewbean;

import java.util.*;

/**
 * IssueReload页面显示所需的内容
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
public class VbIssueReload {
    private String accountId="";
    private String issueType="";
    private String priority="";
    private String scope="";
    private String status="";

    private List issueTypeList =new ArrayList();
    private List accountList = new ArrayList();
    private List priorityList = new ArrayList();
    private List scopeList = new ArrayList();
    private List statusList = new ArrayList();
    private String fillDateBegin;
    private String fillDateEnd;
    private String dueDateBegin;
    private String dueDateEnd;
    private String fillBy;
    private String principal;
    private String issueId;
    private String issueName;
    private String resolveBy;
    private String principalScope;
    public VbIssueReload() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public String getIssueType() {
        return issueType;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public List getAccountList() {
        return accountList;
    }

    public String getDueDateBegin() {
        return dueDateBegin;
    }

    public String getDueDateEnd() {
        return dueDateEnd;
    }

    public String getFillDateBegin() {
        return fillDateBegin;
    }

    public String getFillDateEnd() {
        return fillDateEnd;
    }

    public String getFillBy() {
        return fillBy;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getScope() {
        return scope;
    }

    public List getIssueTypeList() {
        return issueTypeList;
    }

    public List getPriorityList() {
        return priorityList;
    }

    public List getScopeList() {
        return scopeList;
    }

    public List getStatusList() {
        return statusList;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getResolveBy() {
        return resolveBy;
    }

    public String getPrincipalScope() {
        return principalScope;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }

    public void setDueDateBegin(String dueDateBegin) {
        this.dueDateBegin = dueDateBegin;
    }

    public void setDueDateEnd(String dueDateEnd) {
        this.dueDateEnd = dueDateEnd;
    }

    public void setFillDateBegin(String fillDateBegin) {
        this.fillDateBegin = fillDateBegin;
    }

    public void setFillDateEnd(String fillDateEnd) {
        this.fillDateEnd = fillDateEnd;
    }

    public void setFillBy(String fillBy) {
        this.fillBy = fillBy;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setIssueTypeList(List issueTypeList) {
        this.issueTypeList = issueTypeList;
    }

    public void setPriorityList(List priorityList) {
        this.priorityList = priorityList;
    }

    public void setScopeList(List scopeList) {
        this.scopeList = scopeList;
    }

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setResolveBy(String resolveBy) {
        this.resolveBy = resolveBy;
    }

    public void setPrincipalScope(String principalScope) {
        this.principalScope = principalScope;
    }
}
