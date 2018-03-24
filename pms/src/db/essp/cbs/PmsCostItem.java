package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class PmsCostItem implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long acntRid;

    /** nullable persistent field */
    private Long activityId;

    /** nullable persistent field */
    private String scope;

    /** nullable persistent field */
    private String attribute;

    /** nullable persistent field */
    private String subjectCode;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private Double amt;

    private String currency;
    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private Date costDate;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public PmsCostItem(Long rid, Long acntRid, Long activityId, String scope, String attribute, String subjectCode, String name, Double amt, String remark, Date costDate, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.acntRid = acntRid;
        this.activityId = activityId;
        this.scope = scope;
        this.attribute = attribute;
        this.subjectCode = subjectCode;
        this.name = name;
        this.amt = amt;
        this.remark = remark;
        this.costDate = costDate;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public PmsCostItem() {
    }

    /** minimal constructor */
    public PmsCostItem(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCostDate() {
        return this.costDate;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public String getCurrency() {
        return currency;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PmsCostItem) ) return false;
        PmsCostItem castOther = (PmsCostItem) other;
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
