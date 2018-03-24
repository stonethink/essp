package server.essp.issue.issue.viewbean;

import java.util.ArrayList;
import java.util.List;

import server.essp.issue.common.viewbean.VbPageBean;

/**
 * Issue List页面显示所需内容，查询结果列表和当前IssueType
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
public class VbIssueList extends VbPageBean {
    private List detail = new ArrayList();
    private String sortInfo;

    private String abnormal;

    /** identifier field */
    private String typeName;

    /** identifier field */
    private String rid;

    /** persistent field */
    private String accountId;

    /** persistent field */
    private String accountName;

    /** nullable persistent field */
    private String priority;

    /** nullable persistent field */
    private String filleBy;

    /** nullable persistent field */
    private String filleDate;

    /** nullable persistent field */
    private String phone;

    /** nullable persistent field */
    private String fax;

    /** nullable persistent field */
    private String scope;

    /** nullable persistent field */
    private String issueId;

    /** nullable persistent field */
    private String issueName;

    /** nullable persistent field */
    private String issueType;
    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String attachment;

    /** nullable persistent field */
    private String attachmentdesc;

    /** nullable persistent field */
    private String principal;

    /** nullable persistent field */
    private String dueDate;

    /** nullable persistent field */
    private String issueStatus;

    /** nullable persistent field */
    private String duplationIssue;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private String rct;

    /** nullable persistent field */
    private String rut;

    private String status;

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

    public List getDetail() {
        return detail;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getStatus() {
        return status;
    }

    public String getAbnormal() {
        return abnormal;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getSortInfo() {
        return sortInfo;
    }

    public String getIssueType() {
        return issueType;
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

    public void setDetail(List detail) {
        this.detail = detail;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setSortInfo(String sortInfo) {
        this.sortInfo = sortInfo;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
}
