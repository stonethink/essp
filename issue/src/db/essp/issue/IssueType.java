package db.essp.issue;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="ISSUE_TYPE"
 *
*/
public class IssueType implements Serializable {

    /** identifier field */
    private String typeName;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private Long sequence;

    /** nullable persistent field */
    private String description; /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private Set issueCategoryTypes;

    /** persistent field */
    private Set issueStatuses;

    /** persistent field */
    private Set issuePriorities;

    /** persistent field */
    private Set issueScopes;

    /** persistent field */
    private Set issueRisks;
    private String saveStatusHistory;
    private String saveInfluenceHistory;

    /** full constructor */
    public IssueType(String typeName, Long rid, Long sequence, String description, String rst, Date rct, Date rut, Set issueCategoryTypes, Set issueStatuses, Set issuePriorities, Set issueScopes, Set issueRisks) {
        this.typeName = typeName;
        this.rid = rid;
        this.sequence = sequence;
        this.description = description;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.issueCategoryTypes = issueCategoryTypes;
        this.issueStatuses = issueStatuses;
        this.issuePriorities = issuePriorities;
        this.issueScopes = issueScopes;
        this.issueRisks = issueRisks;
    }

    /** default constructor */
    public IssueType() {
    }

    /** minimal constructor */
    public IssueType(String typeName, Set issueCategoryTypes, Set issueStatuses, Set issuePriorities, Set issueScopes, Set issueRisks) {
        this.typeName = typeName;
        this.issueCategoryTypes = issueCategoryTypes;
        this.issueStatuses = issueStatuses;
        this.issuePriorities = issuePriorities;
        this.issueScopes = issueScopes;
        this.issueRisks = issueRisks;
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="Type_Name"
     *
     */
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *            @hibernate.property
     *             column="RID"
     *             length="8"
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
     *             column="Sequence"
     *             length="8"
     *
     */
    public Long getSequence() {
        return this.sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    /**
     *            @hibernate.property
     *             column="Description"
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
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="Type_Name"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssueCategoryType"
     *
     */
    public Set getIssueCategoryTypes() {
        return this.issueCategoryTypes;
    }

    public void setIssueCategoryTypes(Set issueCategoryTypes) {
        this.issueCategoryTypes = issueCategoryTypes;
    }

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="Type_Name"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssueStatus"
     *
     */
    public Set getIssueStatuses() {
        return this.issueStatuses;
    }

    public void setIssueStatuses(Set issueStatuses) {
        this.issueStatuses = issueStatuses;
    }

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="Type_Name"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssuePriority"
     *
     */
    public Set getIssuePriorities() {
        return this.issuePriorities;
    }

    public void setIssuePriorities(Set issuePriorities) {
        this.issuePriorities = issuePriorities;
    }

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="Type_Name"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssueScope"
     *
     */
    public Set getIssueScopes() {
        return this.issueScopes;
    }

    public void setIssueScopes(Set issueScopes) {
        this.issueScopes = issueScopes;
    }

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="Type_Name"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssueRisk"
     *
     */
    public Set getIssueRisks() {
        return this.issueRisks;
    }

    public String getSaveStatusHistory() {
        return saveStatusHistory;
    }

    public String getSaveInfluenceHistory() {
        return saveInfluenceHistory;
    }

    public void setIssueRisks(Set issueRisks) {
        this.issueRisks = issueRisks;
    }

    public void setSaveStatusHistory(String saveStatusHistory) {
        this.saveStatusHistory = saveStatusHistory;
    }

    public void setSaveInfluenceHistory(String saveInfluenceHistory) {
        this.saveInfluenceHistory = saveInfluenceHistory;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("typeName", getTypeName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueType) ) return false;
        IssueType castOther = (IssueType) other;
        return new EqualsBuilder()
            .append(this.getTypeName(), castOther.getTypeName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getTypeName())
            .toHashCode();
    }

}
