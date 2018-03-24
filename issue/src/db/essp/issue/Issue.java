package db.essp.issue;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 *        @hibernate.class
 *         table="ISSUE"
 *         dynamic-update="true"
 *         dynamic-insert="true"
 *
 */
public class Issue implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long accountId;

    /** nullable persistent field */
    private String priority;

    /** nullable persistent field */
    private String filleBy;

    /** nullable persistent field */
    private Date filleDate;

    /** nullable persistent field */
    private String phone;

    /** nullable persistent field */
    private String fax;

    private String email;

    /** nullable persistent field */
    private String scope;

    /** nullable persistent field */
    private String issueId;

    /** nullable persistent field */
    private String issueName;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String attachment;

    /** nullable persistent field */
    private String attachmentId;

    /** nullable persistent field */
    private String attachmentdesc;

    /** nullable persistent field */
    private String principal;

    /** nullable persistent field */
    private Date dueDate;

    /** nullable persistent field */
    private String issueStatus;

    /** nullable persistent field */
    private Long duplationIssue;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut; /** nullable persistent field */
    private db.essp.issue.IssueConclusion issueConclusion;

    /** nullable persistent field */
    private db.essp.issue.IssueReportStatus issuerReportStatus;

    /** persistent field */
    //private db.essp.issue.IssueType issueType;
    private String issueType;

    /** persistent field */
    private Set issueDiscussTitles;
    private IssueResolution resolution;
    private String actualFilledBy;
    private String filleByScope;
    private String principalScope;

    /** full constructor */
    public Issue(Long rid, Long accountId, String priority, String filleBy,
                 Date filleDate, String phone, String fax, String scope,
                 String issueId, String issueName, String description,
                 String attachment, String attachmentdesc, String principal,
                 Date dueDate, String issueStatus, Long duplationIssue,
                 String rst, Date rct, Date rut,
                 db.essp.issue.IssueConclusion issueConclusion,
                 db.essp.issue.IssueReportStatus issuerReportStatus,
                 String issueType, Set issueDiscussTitles,
                 IssueResolution resolution, String actualFilledBy) {
        this.rid = rid;
        this.accountId = accountId;
        this.priority = priority;
        this.filleBy = filleBy;
        this.filleDate = filleDate;
        this.phone = phone;
        this.fax = fax;
        this.scope = scope;
        this.issueId = issueId;
        this.issueName = issueName;
        this.description = description;
        this.attachment = attachment;
        this.attachmentdesc = attachmentdesc;
        this.principal = principal;
        this.dueDate = dueDate;
        this.issueStatus = issueStatus;
        this.duplationIssue = duplationIssue;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.issueConclusion = issueConclusion;
        this.issuerReportStatus = issuerReportStatus;
        this.issueType = issueType;
        this.issueDiscussTitles = issueDiscussTitles;
        this.resolution = resolution;
        this.actualFilledBy = actualFilledBy;
    }

    /** default constructor */
    public Issue() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** minimal constructor */
    public Issue(Long rid, Long accountId, String issueType,
                 Set issueDiscussTitles) {
        this.rid = rid;
        this.accountId = accountId;
        this.issueType = issueType;
        this.issueDiscussTitles = issueDiscussTitles;
    }

    /**
     *            @hibernate.id
     *             generator-class="native"
     *             type="java.lang.Long"
     *             column="RID"
     *
     */
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     *            @hibernate.property
     *             column="ACCOUNT_ID"
     *             length="8"
     *             not-null="true"
     *
     */
    public Long getAccountId() {
        return this.accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     *            @hibernate.property
     *             column="PRIORITY"
     *             length="100"
     *
     */
    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     *            @hibernate.property
     *             column="FILLEBY"
     *             length="50"
     *
     */
    public String getFilleBy() {
        return this.filleBy;
    }

    public void setFilleBy(String filleBy) {
        this.filleBy = filleBy;
    }

    /**
     *            @hibernate.property
     *             column="FILLEDATE"
     *             length="20"
     *
     */
    public Date getFilleDate() {
        return this.filleDate;
    }

    public void setFilleDate(Date filleDate) {
        this.filleDate = filleDate;
    }

    /**
     *            @hibernate.property
     *             column="PHONE"
     *             length="20"
     *
     */
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *            @hibernate.property
     *             column="FAX"
     *             length="20"
     *
     */
    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     *            @hibernate.property
     *             column="SCOPE"
     *             length="100"
     *
     */
    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     *            @hibernate.property
     *             column="ISSUE_ID"
     *             length="20"
     *
     */
    public String getIssueId() {
        return this.issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    /**
     *            @hibernate.property
     *             column="ISSUE_NAME"
     *             length="100"
     *
     */
    public String getIssueName() {
        return this.issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    /**
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="500"
     *
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *            @hibernate.property
     *             column="ATTACHMENT"
     *             length="100"
     *
     */
    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     *            @hibernate.property
     *             column="ATTACHMENTDESC"
     *             length="500"
     *
     */
    public String getAttachmentdesc() {
        return this.attachmentdesc;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    /**
     *            @hibernate.property
     *             column="PRINCIPAL"
     *             length="50"
     *
     */
    public String getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    /**
     *            @hibernate.property
     *             column="DUEDATE"
     *             length="20"
     *
     */
    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     *            @hibernate.property
     *             column="issueStatus"
     *             length="100"
     *
     */
    public String getIssueStatus() {
        return this.issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    /**
     *            @hibernate.property
     *             column="DUPLATION_ISSUE"
     *             length="8"
     *
     */
    public Long getDuplationIssue() {
        return this.duplationIssue;
    }

    public void setDuplationIssue(Long duplationIssue) {
        this.duplationIssue = duplationIssue;
    }

    /**
     *            @hibernate.property
     *             column="RST"
     *             length="1"
     *
     */
    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    /**
     *            @hibernate.property
     *             column="RCT"
     *             length="20"
     *
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    /**
     *            @hibernate.property
     *             column="RUT"
     *             length="20"
     *
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    /**
     *            @hibernate.one-to-one
     *             outer-join="false"
     *
     */
    public db.essp.issue.IssueConclusion getIssueConclusion() {
        return this.issueConclusion;
    }

    public void setIssueConclusion(db.essp.issue.IssueConclusion
                                   issueConclusion) {
        this.issueConclusion = issueConclusion;
    }

    /**
     *            @hibernate.one-to-one
     *             outer-join="false"
     *
     */
    public db.essp.issue.IssueReportStatus getIssuerReportStatus() {
        return this.issuerReportStatus;
    }

    public void setIssuerReportStatus(db.essp.issue.IssueReportStatus
                                      issuerReportStatus) {
        this.issuerReportStatus = issuerReportStatus;
    }

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="TYPE_NAME"
     *
     */
    public String getIssueType() {
        return this.issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="ISSUE_CODE"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssueDiscussTitle"
     *
     */
    public Set getIssueDiscussTitles() {
        return this.issueDiscussTitles;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public IssueResolution getResolution() {
        return resolution;
    }

    public String getActualFilledBy() {
        return actualFilledBy;
    }

    public String getFilleByScope() {
        return filleByScope;
    }

    public String getPrincipalScope() {
        return principalScope;
    }

    public void setIssueDiscussTitles(Set issueDiscussTitles) {
        this.issueDiscussTitles = issueDiscussTitles;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setResolution(IssueResolution resolution) {
        this.resolution = resolution;
    }

    public void setActualFilledBy(String actualFilledBy) {
        this.actualFilledBy = actualFilledBy;
    }

    public void setFilleByScope(String filleByScope) {
        this.filleByScope = filleByScope;
    }

    public void setPrincipalScope(String principalScope) {
        this.principalScope = principalScope;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof Issue)) {
            return false;
        }
        Issue castOther = (Issue) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

    private void jbInit() throws Exception {
    }

}
