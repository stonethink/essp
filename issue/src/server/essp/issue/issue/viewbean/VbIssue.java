package server.essp.issue.issue.viewbean;

import java.util.*;

public class VbIssue {
    private List detail = new ArrayList();
    private List accountList = new ArrayList();
    private String issueType;
    private String issueTypeReadonly;
    private List issueTypeList = new ArrayList();

    private String abnormal;

    /** identifier field */
    private String rid;

    /** persistent field */
    private String accountId;
    private String accountReadonly;

    /** persistent field */
    private String accountName;

    /** nullable persistent field */
    private String priority;
    private String priorityReadonly;
    private List priorityList = new ArrayList();

    /** nullable persistent field */
    private String filleBy;

    private List filleByList;

    /** nullable persistent field */
    private String filleDate;

    /** nullable persistent field */
    private String phone;

    private String email;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private String scope;
    private String scopeReadonly;
    private List scopeList = new ArrayList();

    /** nullable persistent field */
    private String issueId;

    /** nullable persistent field */
    private String issueName;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String attachment;

    /** nullable persistent field */
    private String attachmentdesc;

    /** nullable persistent field */
    private String principal;
    private String principalFlag;
    private String pmFlag;
    private String filleByFlag;

    /** nullable persistent field */
    private String dueDate;

    /** nullable persistent field */
    private String issueStatus;
    private String issueStatusReadonly;
    private List statusList = new ArrayList();
    private String issueStatusBelongto;

    /** nullable persistent field */
    private String duplationIssue;
    private boolean duplationIssueDisabled;
    private List duplationIssueList = new ArrayList();

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private String rct;

    /** nullable persistent field */
    private String rut;

    //以下三属性为调整后的属性
    //modify by:Robin 20060428
    private String confirmDate;
    private String confirmBy;
    private String instructionOfClosure;
    private String filleByScope;
    private String principalScope;
    private String confirmByScope;

    public VbIssue() {
    }

    public String getAccountId() {
        return accountId;
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

    public String getDueDate() {
        return dueDate;
    }

    public String getDuplationIssue() {
        return duplationIssue;
    }

    public String getFax() {
        return fax;
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

    public String getPhone() {
        return phone;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPriority() {
        return priority;
    }

    public String getRct() {
        return rct;
    }

    public String getRid() {
        return rid;
    }

    public String getRst() {
        return rst;
    }

    public String getRut() {
        return rut;
    }

    public String getScope() {
        return scope;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAbnormal() {
        return abnormal;
    }

    public List getAccountList() {
        return accountList;
    }

    public List getDetail() {
        return detail;
    }

    public List getIssueTypeList() {
        return issueTypeList;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getEmail() {
        return email;
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

    public List getDuplationIssueList() {
        return duplationIssueList;
    }

    public List getFilleByList() {
        return filleByList;
    }

    public boolean isDuplationIssueDisabled() {
        return duplationIssueDisabled;
    }

    public String getIssueStatusBelongto() {
        return issueStatusBelongto;
    }

    public String getPmFlag() {

        return pmFlag;
    }

    public String getPrincipalFlag() {

        return principalFlag;
    }

    public String getFilleByFlag() {
        return filleByFlag;
    }

    public String getAccountReadonly() {
        return accountReadonly;
    }

    public String getIssueTypeReadonly() {
        return issueTypeReadonly;
    }

    public String getPriorityReadonly() {
        return priorityReadonly;
    }

    public String getScopeReadonly() {
        return scopeReadonly;
    }

    public String getIssueStatusReadonly() {
        return issueStatusReadonly;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public String getConfirmBy() {
        return confirmBy;
    }

    public String getInstructionOfClosure() {
        return instructionOfClosure;
    }

    public String getFilleByScope() {
        return filleByScope;
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

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDuplationIssue(String duplationIssue) {
        this.duplationIssue = duplationIssue;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setFilleBy(String filleBy) {
        this.filleBy = filleBy;
    }

    public void setFilleDate(String filleDate) {
        this.filleDate = filleDate;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setRct(String rct) {
        this.rct = rct;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }

    public void setDetail(List detail) {
        this.detail = detail;
    }

    public void setIssueTypeList(List issueTypeList) {
        this.issueTypeList = issueTypeList;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setDuplationIssueList(List duplationIssueList) {
        this.duplationIssueList = duplationIssueList;
    }

    public void setFilleByList(List filleByList) {
        this.filleByList = filleByList;
    }

    public void setDuplationIssueDisabled(boolean duplationIssueDisabled) {
        this.duplationIssueDisabled = duplationIssueDisabled;
    }

    public void setIssueStatusBelongto(String issueStatusBelongto) {
        this.issueStatusBelongto = issueStatusBelongto;
    }

    public void setPmFlag(String pmFlag) {

        this.pmFlag = pmFlag;
    }

    public void setPrincipalFlag(String principalFlag) {

        this.principalFlag = principalFlag;
    }

    public void setFilleByFlag(String filleByFlag) {
        this.filleByFlag = filleByFlag;
    }

    public void setAccountReadonly(String accountReadonly) {
        this.accountReadonly = accountReadonly;
    }

    public void setIssueTypeReadonly(String issueTypeReadonly) {
        this.issueTypeReadonly = issueTypeReadonly;
    }

    public void setPriorityReadonly(String priorityReadonly) {
        this.priorityReadonly = priorityReadonly;
    }

    public void setScopeReadonly(String scopeReadonly) {
        this.scopeReadonly = scopeReadonly;
    }

    public void setIssueStatusReadonly(String issueStatusReadonly) {
        this.issueStatusReadonly = issueStatusReadonly;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public void setConfirmBy(String confirmBy) {
        this.confirmBy = confirmBy;
    }

    public void setInstructionOfClosure(String instructionOfClosure) {
        this.instructionOfClosure = instructionOfClosure;
    }

    public void setFilleByScope(String filleByScope) {
        this.filleByScope = filleByScope;
    }

    public void setPrincipalScope(String principalScope) {
        this.principalScope = principalScope;
    }

    public void setConfirmByScope(String confirmByScope) {
        this.confirmByScope = confirmByScope;
    }
}
