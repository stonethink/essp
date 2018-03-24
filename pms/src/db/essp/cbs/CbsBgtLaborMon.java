package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CbsBgtLaborMon implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Date month;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private Double bgtUnitNum;

    /** nullable persistent field */
    private Double bgtAmt;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.cbs.CbsBgtLabor cbsBgtLabor;

    /** full constructor */
    public CbsBgtLaborMon(Long rid, Date month, String currency, Double bgtUnitNum, Double bgtAmt, String rst, Date rct, Date rut, db.essp.cbs.CbsBgtLabor cbsBgtLabor) {
        this.rid = rid;
        this.month = month;
        this.currency = currency;
        this.bgtUnitNum = bgtUnitNum;
        this.bgtAmt = bgtAmt;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.cbsBgtLabor = cbsBgtLabor;
    }

    /** default constructor */
    public CbsBgtLaborMon() {
    }

    /** minimal constructor */
    public CbsBgtLaborMon(Long rid, db.essp.cbs.CbsBgtLabor cbsBgtLabor) {
        this.rid = rid;
        this.cbsBgtLabor = cbsBgtLabor;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Date getMonth() {
        return this.month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getBgtUnitNum() {
        return this.bgtUnitNum;
    }

    public void setBgtUnitNum(Double bgtUnitNum) {
        this.bgtUnitNum = bgtUnitNum;
    }

    public Double getBgtAmt() {
        return this.bgtAmt;
    }

    public void setBgtAmt(Double bgtAmt) {
        this.bgtAmt = bgtAmt;
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

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public db.essp.cbs.CbsBgtLabor getCbsBgtLabor() {
        return this.cbsBgtLabor;
    }

    public void setCbsBgtLabor(db.essp.cbs.CbsBgtLabor cbsBgtLabor) {
        this.cbsBgtLabor = cbsBgtLabor;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CbsBgtLaborMon) ) return false;
        CbsBgtLaborMon castOther = (CbsBgtLaborMon) other;
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
