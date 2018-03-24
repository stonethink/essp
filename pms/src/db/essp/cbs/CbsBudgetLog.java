package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *        @hibernate.class
 *         table="cbs_budget_log"
 *
*/
public class CbsBudgetLog implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Date logDate;

    /** nullable persistent field */
    private String baseId;

    /** nullable persistent field */
    private Double basePm;

    /** nullable persistent field */
    private Double baseAmt;

    /** nullable persistent field */
    private Double totalBugetAmt;

    /** nullable persistent field */
    private Double totalBugetPm;

    /** nullable persistent field */
    private Double changeBugetAmt;

    /** nullable persistent field */
    private Double changeBugetPm;

    /** nullable persistent field */
    private String changedBy;

    /** nullable persistent field */
    private String reson;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;
    private Long budgetRid;

    /** full constructor */
    public CbsBudgetLog(Long rid, Date logDate, String baseId, Double basePm, Double baseAmt, Double totalBugetAmt, Double totalBugetPm, Double changeBugetAmt, Double changeBugetPm, String changedBy, String reson, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.logDate = logDate;
        this.baseId = baseId;
        this.basePm = basePm;
        this.baseAmt = baseAmt;
        this.totalBugetAmt = totalBugetAmt;
        this.totalBugetPm = totalBugetPm;
        this.changeBugetAmt = changeBugetAmt;
        this.changeBugetPm = changeBugetPm;
        this.changedBy = changedBy;
        this.reson = reson;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public CbsBudgetLog() {
    }

    /** minimal constructor */
    public CbsBudgetLog(Long rid) {
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
     *             column="LOG_DATE"
     *             length="19"
     *
     */
    public Date getLogDate() {
        return this.logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    /**
     *            @hibernate.property
     *             column="BASE_ID"
     *             length="50"
     *
     */
    public String getBaseId() {
        return this.baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    /**
     *            @hibernate.property
     *             column="BASE_PM"
     *             length="10"
     *
     */
    public Double getBasePm() {
        return this.basePm;
    }

    public void setBasePm(Double basePm) {
        this.basePm = basePm;
    }

    /**
     *            @hibernate.property
     *             column="BASE_AMT"
     *             length="14"
     *
     */
    public Double getBaseAmt() {
        return this.baseAmt;
    }

    public void setBaseAmt(Double baseAmt) {
        this.baseAmt = baseAmt;
    }

    /**
     *            @hibernate.property
     *             column="TOTAL_BUGET_AMT"
     *             length="14"
     *
     */
    public Double getTotalBugetAmt() {
        return this.totalBugetAmt;
    }

    public void setTotalBugetAmt(Double totalBugetAmt) {
        this.totalBugetAmt = totalBugetAmt;
    }

    /**
     *            @hibernate.property
     *             column="TOTAL_BUGET_PM"
     *             length="8"
     *
     */
    public Double getTotalBugetPm() {
        return this.totalBugetPm;
    }

    public void setTotalBugetPm(Double totalBugetPm) {
        this.totalBugetPm = totalBugetPm;
    }

    /**
     *            @hibernate.property
     *             column="CHANGE_BUGET_AMT"
     *             length="14"
     *
     */
    public Double getChangeBugetAmt() {
        return this.changeBugetAmt;
    }

    public void setChangeBugetAmt(Double changeBugetAmt) {
        this.changeBugetAmt = changeBugetAmt;
    }

    /**
     *            @hibernate.property
     *             column="CHANGE_BUGET_PM"
     *             length="8"
     *
     */
    public Double getChangeBugetPm() {
        return this.changeBugetPm;
    }

    public void setChangeBugetPm(Double changeBugetPm) {
        this.changeBugetPm = changeBugetPm;
    }

    /**
     *            @hibernate.property
     *             column="CHANGED_BY"
     *             length="50"
     *
     */
    public String getChangedBy() {
        return this.changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    /**
     *            @hibernate.property
     *             column="RESON"
     *             length="65535"
     *
     */
    public String getReson() {
        return this.reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
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
     *             length="19"
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
     *             length="19"
     *
     */
    public Date getRut() {
        return this.rut;
    }

    public Long getBudgetRid() {
        return budgetRid;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setBudgetRid(Long budgetRid) {
        this.budgetRid = budgetRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CbsBudgetLog) ) return false;
        CbsBudgetLog castOther = (CbsBudgetLog) other;
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
