package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CbsCostLabor implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String jobCodeId;

    /** nullable persistent field */
    private Double price;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private String costUnit;

    /** nullable persistent field */
    private Double costUnitNum;

    /** nullable persistent field */
    private Double costAmt;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;
    private Long acntRid; /** full constructor */
    public CbsCostLabor(Long rid, String jobCodeId, Double price, String currency, String costUnit, Double costUnitNum, Double costAmt, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.jobCodeId = jobCodeId;
        this.price = price;
        this.currency = currency;
        this.costUnit = costUnit;
        this.costUnitNum = costUnitNum;
        this.costAmt = costAmt;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public CbsCostLabor() {
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getJobCodeId() {
        return this.jobCodeId;
    }

    public void setJobCodeId(String jobCodeId) {
        this.jobCodeId = jobCodeId;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCostUnit() {
        return this.costUnit;
    }

    public void setCostUnit(String costUnit) {
        this.costUnit = costUnit;
    }

    public Double getCostUnitNum() {
        return this.costUnitNum;
    }

    public void setCostUnitNum(Double costUnitNum) {
        this.costUnitNum = costUnitNum;
    }

    public Double getCostAmt() {
        return this.costAmt;
    }

    public void setCostAmt(Double costAmt) {
        this.costAmt = costAmt;
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

    public Long getAcntRid() {
        return acntRid;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CbsCostLabor) ) return false;
        CbsCostLabor castOther = (CbsCostLabor) other;
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
