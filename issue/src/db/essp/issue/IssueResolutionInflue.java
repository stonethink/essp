package db.essp.issue;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="ISSUE_RESOLUTION_INFLUE"
 *
*/
public class IssueResolutionInflue implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String influenceName;

    /** nullable persistent field */
    private Long impactLevel;

    /** nullable persistent field */
    private Double weight;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    private IssueResolution issueResolution;

    /** full constructor */
    public IssueResolutionInflue(Long rid, String influenceName, Long impactLevel, Double weight, String remark, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.influenceName = influenceName;
        this.impactLevel = impactLevel;
        this.weight = weight;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public IssueResolutionInflue() {
    }

    /** minimal constructor */
    public IssueResolutionInflue(Long rid) {
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
     *             column="INFLUENCE_NAME"
     *             length="100"
     *
     */
    public String getInfluenceName() {
        return this.influenceName;
    }

    public void setInfluenceName(String influenceName) {
        this.influenceName = influenceName;
    }

    /**
     *            @hibernate.property
     *             column="IMPACT_LEVEL"
     *             length="8"
     *
     */
    public Long getImpactLevel() {
        return this.impactLevel;
    }

    public void setImpactLevel(Long impactLevel) {
        this.impactLevel = impactLevel;
    }

    /**
     *            @hibernate.property
     *             column="WEIGHT"
     *             length="8"
     *
     */
    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     *            @hibernate.property
     *             column="REMARK"
     *             length="8"
     *
     */
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        if ( !(other instanceof IssueResolutionInflue) ) return false;
        IssueResolutionInflue castOther = (IssueResolutionInflue) other;
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
