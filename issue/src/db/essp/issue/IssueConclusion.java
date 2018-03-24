package db.essp.issue;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 *        @hibernate.class
 *         table="ISSUE_CONCLUSION"
 *
*/
public class IssueConclusion implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String actualInfluence;

    /** nullable persistent field */
    private String solvedDescription;

    /** nullable persistent field */
    private Date finishedDate;

    /** nullable persistent field */
    private Date deliveredDate;

    /** nullable persistent field */
    private String attachment;

    /** nullable persistent field */
    private String attachmentId;

    /** nullable persistent field */
    private String attachmentDesc;

    /** nullable persistent field */
    private String closureStatus;

    /** nullable persistent field */
    private Date confirmDate;

    /** nullable persistent field */
    private String confirmBy;

    /** nullable persistent field */
    private String instructionClosure;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.issue.Issue issue;

    /** persistent field */
    private Set issueConclusionUgs;
    private String confirmByScope;

    /** full constructor */
    public IssueConclusion(Long rid,  String actualInfluence, String solvedDescription, Date finishedDate, Date deliveredDate, String attachment, String attachmentDesc, String closureStatus, Date conformDate, String conformBy, String instructionClosure, String rst, Date rct, Date rut, db.essp.issue.Issue issue, Set issueConclusionUgs) {
        this.rid = rid;
        this.actualInfluence = actualInfluence;
        this.solvedDescription = solvedDescription;
        this.finishedDate = finishedDate;
        this.deliveredDate = deliveredDate;
        this.attachment = attachment;
        this.attachmentDesc = attachmentDesc;
        this.closureStatus = closureStatus;
        this.confirmDate = conformDate;
        this.confirmBy = conformBy;
        this.instructionClosure = instructionClosure;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.issue = issue;
        this.issueConclusionUgs = issueConclusionUgs;
    }

    /** default constructor */
    public IssueConclusion() {
    }

    /** minimal constructor */
    public IssueConclusion(Long rid, Set issueConclusionUgs) {
        this.rid = rid;
        this.issueConclusionUgs = issueConclusionUgs;
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
     *             column="ACTUAL_INFLUENCE"
     *             length="500"
     *
     */
    public String getActualInfluence() {
        return this.actualInfluence;
    }

    public void setActualInfluence(String actualInfluence) {
        this.actualInfluence = actualInfluence;
    }

    /**
     *            @hibernate.property
     *             column="SOLVED_DESCRIPTION"
     *             length="500"
     *
     */
    public String getSolvedDescription() {
        return this.solvedDescription;
    }

    public void setSolvedDescription(String solvedDescription) {
        this.solvedDescription = solvedDescription;
    }

    /**
     *            @hibernate.property
     *             column="FINISHED_DATE"
     *             length="20"
     *
     */
    public Date getFinishedDate() {
        return this.finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    /**
     *            @hibernate.property
     *             column="DELIVERED_DATE"
     *             length="20"
     *
     */
    public Date getDeliveredDate() {
        return this.deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
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
     *             column="ATTACHMENT_DESC"
     *             length="500"
     *
     */
    public String getAttachmentDesc() {
        return this.attachmentDesc;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    /**
     *            @hibernate.property
     *             column="CLOSURE_STATUS"
     *             length="50"
     *
     */
    public String getClosureStatus() {
        return this.closureStatus;
    }

    public void setClosureStatus(String closureStatus) {
        this.closureStatus = closureStatus;
    }

    /**
     *            @hibernate.property
     *             column="CONFIRM_DATE"
     *             length="20"
     *
     */
    public Date getConfirmDate() {
        return this.confirmDate;
    }

    public void setConfirmDate(Date conformDate) {
        this.confirmDate = conformDate;
    }

    /**
     *            @hibernate.property
     *             column="CONFIRM_BY"
     *             length="50"
     *
     */
    public String getConfirmBy() {
        return this.confirmBy;
    }

    public void setConfirmBy(String conformBy) {
        this.confirmBy = conformBy;
    }

    /**
     *            @hibernate.property
     *             column="INSTRUCTION_CLOSURE"
     *             length="500"
     *
     */
    public String getInstructionClosure() {
        return this.instructionClosure;
    }

    public void setInstructionClosure(String instructionClosure) {
        this.instructionClosure = instructionClosure;
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
     *             class="db.essp.issue.IssueConclusionUg"
     *
     */
    public Set getIssueConclusionUgs() {
        return this.issueConclusionUgs;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public String getConfirmByScope() {
        return confirmByScope;
    }

    public void setIssueConclusionUgs(Set issueConclusionUgs) {
        this.issueConclusionUgs = issueConclusionUgs;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setConfirmByScope(String confirmByScope) {
        this.confirmByScope = confirmByScope;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueConclusion) ) return false;
        IssueConclusion castOther = (IssueConclusion) other;
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
