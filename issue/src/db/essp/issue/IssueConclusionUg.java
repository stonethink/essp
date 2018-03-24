package db.essp.issue;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/**
 *        @hibernate.class
 *         table="issue_conclusion_ug"
 *
*/
public class IssueConclusionUg implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String urgedBy;

    /** nullable persistent field */
    private String urgeTo;

    /** nullable persistent field */
    private Date urgDate;

    /** nullable persistent field */
    private String description;

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

    private IssueConclusion issueConclusion;
    private String urgedByScope;
    private String urgeToScope;
    /** full constructor */
    public IssueConclusionUg(Long rid, String urgedBy, String urgeTo, Date urgDate, String description, String attachment, String attachmentdesc, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.urgedBy = urgedBy;
        this.urgeTo = urgeTo;
        this.urgDate = urgDate;
        this.description = description;
        this.attachment = attachment;
        this.attachmentdesc = attachmentdesc;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public IssueConclusionUg() {
    }

    /** minimal constructor */
    public IssueConclusionUg(Long rid) {
        this.rid = rid;
    }

    /**
     *            @hibernate.id
     *             generator-class="assigned"
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
     *             column="URGEDBY"
     *             length="20"
     *
     */
    public String getUrgedBy() {
        return this.urgedBy;
    }

    public void setUrgedBy(String urgedBy) {
        this.urgedBy = urgedBy;
    }

    /**
     *            @hibernate.property
     *             column="URGETO"
     *             length="20"
     *
     */
    public String getUrgeTo() {
        return this.urgeTo;
    }

    public void setUrgeTo(String urgeTo) {
        this.urgeTo = urgeTo;
    }

    /**
     *            @hibernate.property
     *             column="URGDATE"
     *             length="20"
     *
     */
    public Date getUrgDate() {
        return this.urgDate;
    }

    public void setUrgDate(Date urgDate) {
        this.urgDate = urgDate;
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

    public IssueConclusion getIssueConclusion() {
        return issueConclusion;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public String getUrgedByScope() {
        return urgedByScope;
    }

    public String getUrgeToScope() {
        return urgeToScope;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setIssueConclusion(IssueConclusion issueConclusion) {
        this.issueConclusion = issueConclusion;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public void setUrgedByScope(String urgedByScope) {
        this.urgedByScope = urgedByScope;
    }

    public void setUrgeToScope(String urgeToScope) {
        this.urgeToScope = urgeToScope;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueConclusionUg) ) return false;
        IssueConclusionUg castOther = (IssueConclusionUg) other;
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
