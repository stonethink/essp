package db.essp.issue;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="ISSUE_RESOLUTION_CATEGORY"
 *
*/
public class IssueResolutionCategory implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String categoryName;

    /** nullable persistent field */
    private String categoryValue;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    private IssueResolution issueResolution;

    /** full constructor */
    public IssueResolutionCategory(Long rid, String categoryName, String categoryValue, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.categoryName = categoryName;
        this.categoryValue = categoryValue;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public IssueResolutionCategory() {
    }

    /** minimal constructor */
    public IssueResolutionCategory(Long rid) {
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
     *             column="CATEGORYNAME"
     *             length="100"
     *
     */
    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     *            @hibernate.property
     *             column="CATEGORYVALUE"
     *             length="100"
     *
     */
    public String getCategoryValue() {
        return this.categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
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

    public IssueResolution getIssueResolution() {
        return issueResolution;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setIssueResolution(IssueResolution issueResolution) {
        this.issueResolution = issueResolution;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof IssueResolutionCategory) ) return false;
        IssueResolutionCategory castOther = (IssueResolutionCategory) other;
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
