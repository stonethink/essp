package db.essp.cbs;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CbsBgtLabor implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private String jobCodeId;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private Double price;

    /** nullable persistent field */
    private String unit;

    /** nullable persistent field */
    private Double unitNum;

    /** nullable persistent field */
    private Double amt;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.cbs.CbsBudget cbsBudget;

    /** persistent field */
    private Set cbsBgtLaborMons;

    /** full constructor */
    public CbsBgtLabor(Long rid, String jobCodeId, String currency, Double price, String unit, Double unitNum, Double amt, String description, String rst, Date rct, Date rut, db.essp.cbs.CbsBudget cbsBudget, Set cbsBgtLaborMons) {
        this.rid = rid;
        this.jobCodeId = jobCodeId;
        this.currency = currency;
        this.price = price;
        this.unit = unit;
        this.unitNum = unitNum;
        this.amt = amt;
        this.description = description;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.cbsBudget = cbsBudget;
        this.cbsBgtLaborMons = cbsBgtLaborMons;
    }

    /** default constructor */
    public CbsBgtLabor() {
    }

    /** minimal constructor */
    public CbsBgtLabor(Long rid, String jobCodeId, db.essp.cbs.CbsBudget cbsBudget, Set cbsBgtLaborMons) {
        this.rid = rid;
        this.jobCodeId = jobCodeId;
        this.cbsBudget = cbsBudget;
        this.cbsBgtLaborMons = cbsBgtLaborMons;
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

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getUnitNum() {
        return this.unitNum;
    }

    public void setUnitNum(Double unitNum) {
        this.unitNum = unitNum;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    /** 
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="BGT_RID"
     *         
     */
    public db.essp.cbs.CbsBudget getCbsBudget() {
        return this.cbsBudget;
    }

    public void setCbsBudget(db.essp.cbs.CbsBudget cbsBudget) {
        this.cbsBudget = cbsBudget;
    }

    public Set getCbsBgtLaborMons() {
        return this.cbsBgtLaborMons;
    }

    public void setCbsBgtLaborMons(Set cbsBgtLaborMons) {
        this.cbsBgtLaborMons = cbsBgtLaborMons;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CbsBgtLabor) ) return false;
        CbsBgtLabor castOther = (CbsBgtLabor) other;
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
