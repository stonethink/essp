package db.essp.issue;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 *        @hibernate.class
 *         table="ISSUE_REPORT_STATUS"
 *
*/
public class IssueReportStatus implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private Long accountId;

    /** nullable persistent field */
    private String issueType;

    /** nullable persistent field */
    private String issueStatus;

    /** nullable persistent field */
    private Date processingDate;

    /** nullable persistent field */
    private Date deliverdDate;

    /** nullable persistent field */
    private Date closedDate;

    /** nullable persistent field */
    private Date rejectDate;

    /** nullable persistent field */
    private Date duplationDate;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;


    /** full constructor */
    public IssueReportStatus(Long rid, Long accountId, String issueType, String issueStatus, Date processingDate, Date deliverdDate, Date closedDate, Date rejectDate, Date duplationDate) {
        this.rid = rid;
        this.accountId = accountId;
        this.issueType = issueType;
        this.issueStatus = issueStatus;
        this.processingDate = processingDate;
        this.deliverdDate = deliverdDate;
        this.closedDate = closedDate;
        this.rejectDate = rejectDate;
        this.duplationDate = duplationDate;
    }

    /** default constructor */
    public IssueReportStatus() {
    }

    /** minimal constructor */
    public IssueReportStatus(Long rid, Long accountId) {
        this.rid = rid;
        this.accountId = accountId;
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
     *             column="ISSUE_TYPE"
     *             length="100"
     *
     */
    public String getIssueType() {
        return this.issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    /**
     *            @hibernate.property
     *             column="ISSUE_STATUS"
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
     *             column="PROCESSINGDATE"
     *             length="20"
     *
     */
    public Date getProcessingDate() {
        return this.processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    /**
     *            @hibernate.property
     *             column="DELIVERDDATE"
     *             length="20"
     *
     */
    public Date getDeliverdDate() {
        return this.deliverdDate;
    }

    public void setDeliverdDate(Date deliverdDate) {
        this.deliverdDate = deliverdDate;
    }

    /**
     *            @hibernate.property
     *             column="CLOSEDDATE"
     *             length="20"
     *
     */
    public Date getClosedDate() {
        return this.closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    /**
     *            @hibernate.property
     *             column="REJECTDATE"
     *             length="20"
     *
     */
    public Date getRejectDate() {
        return this.rejectDate;
    }

    public void setRejectDate(Date rejectDate) {
        this.rejectDate = rejectDate;
    }

    /**
     *            @hibernate.property
     *             column="DUPLATIONDATE"
     *             length="20"
     *
     */
    public Date getDuplationDate() {
        return this.duplationDate;
    }

    public String getRst() {
        return rst;
    }

    public Date getRct() {
        return rct;
    }

    public Date getRut() {
        return rut;
    }

    public void setDuplationDate(Date duplationDate) {
        this.duplationDate = duplationDate;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueReportStatus) ) return false;
        IssueReportStatus castOther = (IssueReportStatus) other;
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
