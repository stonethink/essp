package db.essp.issue;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 *        @hibernate.class
 *         table="ISSUE_RESOLUTION"
 *
*/
public class IssueResolution implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Double probability;

    /** nullable persistent field */
    private Double riskLevel;

    /** nullable persistent field */
    private Date assignedDate;

    /** nullable persistent field */
    private String resolution;

    /** nullable persistent field */
    private Date planFinishDate;

    /** nullable persistent field */
    private String resolutionBy;

    /** nullable persistent field */
    private String attachment;

    /** nullable persistent field */
    private String attachmentId;

    /** nullable persistent field */
    private String attachmentdesc;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.issue.Issue issue;

    /** persistent field */
    private Set issueResolutionInflues;

    /** persistent field */
    private Set issueResolutionCategories;
    private String resolutionByCustomer;

    /** full constructor */
    public IssueResolution(Long rid, Double probability, Double riskLevel, Date assignedDate, String resolution, Date planFinishDate, String resolutionBy, String attachment, String attachmentdesc, String rst, Date rct, Date rut, db.essp.issue.Issue issue, Set issueResolutionInflues, Set issueResolutionCategories) {
        this.rid = rid;
        this.probability = probability;
        this.riskLevel = riskLevel;
        this.assignedDate = assignedDate;
        this.resolution = resolution;
        this.planFinishDate = planFinishDate;
        this.resolutionBy = resolutionBy;
        this.attachment = attachment;
        this.attachmentdesc = attachmentdesc;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.issue = issue;
        this.issueResolutionInflues = issueResolutionInflues;
        this.issueResolutionCategories = issueResolutionCategories;
    }

    /** default constructor */
    public IssueResolution() {
    }

    /** minimal constructor */
    public IssueResolution(Long rid, Set issueResolutionInflues, Set issueResolutionCategories) {
        this.rid = rid;
        this.issueResolutionInflues = issueResolutionInflues;
        this.issueResolutionCategories = issueResolutionCategories;
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="rid"
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
     *             column="PROBABILITY"
     *             length="8"
     *
     */
    public Double getProbability() {
        return this.probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    /**
     *            @hibernate.property
     *             column="RISK_LEVEL"
     *             length="8"
     *
     */
    public Double getRiskLevel() {
        return this.riskLevel;
    }

    public void setRiskLevel(Double riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     *            @hibernate.property
     *             column="ASSIGNED_DATE"
     *             length="20"
     *
     */
    public Date getAssignedDate() {
        return this.assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    /**
     *            @hibernate.property
     *             column="RESOLUTION"
     *             length="100"
     *
     */
    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    /**
     *            @hibernate.property
     *             column="PLAN_FINISHDATE"
     *             length="20"
     *
     */
    public Date getPlanFinishDate() {
        return this.planFinishDate;
    }

    public void setPlanFinishDate(Date planFinishDate) {
        this.planFinishDate = planFinishDate;
    }

    /**
     *            @hibernate.property
     *             column="RESOLUTION_BY"
     *             length="50"
     *
     */
    public String getResolutionBy() {
        return this.resolutionBy;
    }

    public void setResolutionBy(String resolutionBy) {
        this.resolutionBy = resolutionBy;
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
     *             class="db.essp.issue.Issue"
     *             outer-join="auto"
     *             constrained="true"
     *
     */
    public db.essp.issue.Issue getIssue() {
        return this.issue;
    }

    public void setIssue(db.essp.issue.Issue issue) {
        this.issue = issue;
    }

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="ISSUE_CODE"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssueResolutionInflue"
     *
     */
    public Set getIssueResolutionInflues() {
        return this.issueResolutionInflues;
    }

    public void setIssueResolutionInflues(Set issueResolutionInflues) {
        this.issueResolutionInflues = issueResolutionInflues;
    }

    /**
     *            @hibernate.set
     *             lazy="true"
     *             inverse="true"
     *             cascade="none"
     *            @hibernate.collection-key
     *             column="ISSUE_CODE"
     *            @hibernate.collection-one-to-many
     *             class="db.essp.issue.IssueResolutionCategory"
     *
     */
    public Set getIssueResolutionCategories() {
        return this.issueResolutionCategories;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public String getResolutionByCustomer() {
        return resolutionByCustomer;
    }

    public void setIssueResolutionCategories(Set issueResolutionCategories) {
        this.issueResolutionCategories = issueResolutionCategories;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setResolutionByCustomer(String resolutionByCustomer) {
        this.resolutionByCustomer = resolutionByCustomer;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueResolution) ) return false;
        IssueResolution castOther = (IssueResolution) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
