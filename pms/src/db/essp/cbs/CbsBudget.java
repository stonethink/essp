package db.essp.cbs;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CbsBudget implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String budgetType;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private Double lastPm;

    /** nullable persistent field */
    private Double lastAmt;

    /** nullable persistent field */
    private Double currentPm;

    /** nullable persistent field */
    private Double currentAmt;

    /** nullable persistent field */
    private Blob budget;

    /** nullable persistent field */
    private Date fromMonth;

    /** nullable persistent field */
    private Date toMonth;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private Set cbsBgtLabors;

    /** full constructor */
    public CbsBudget(Long rid, String budgetType, String currency, Double lastPm, Double lastAmt, Double currentPm, Double currentAmt, Blob budget, Date fromMonth, Date toMonth, String rst, Date rct, Date rut, Set cbsBgtLabors) {
        this.rid = rid;
        this.budgetType = budgetType;
        this.currency = currency;
        this.lastPm = lastPm;
        this.lastAmt = lastAmt;
        this.currentPm = currentPm;
        this.currentAmt = currentAmt;
        this.budget = budget;
        this.fromMonth = fromMonth;
        this.toMonth = toMonth;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.cbsBgtLabors = cbsBgtLabors;
    }

    /** default constructor */
    public CbsBudget() {
    }

    /** minimal constructor */
    public CbsBudget(Long rid, Set cbsBgtLabors) {
        this.rid = rid;
        this.cbsBgtLabors = cbsBgtLabors;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getBudgetType() {
        return this.budgetType;
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getLastPm() {
        return this.lastPm;
    }

    public void setLastPm(Double lastPm) {
        this.lastPm = lastPm;
    }

    public Double getLastAmt() {
        return this.lastAmt;
    }

    public void setLastAmt(Double lastAmt) {
        this.lastAmt = lastAmt;
    }

    public Double getCurrentPm() {
        return this.currentPm;
    }

    public void setCurrentPm(Double currentPm) {
        this.currentPm = currentPm;
    }

    public Double getCurrentAmt() {
        return this.currentAmt;
    }

    public void setCurrentAmt(Double currentAmt) {
        this.currentAmt = currentAmt;
    }

    public Blob getBudget() {
        return this.budget;
    }

    public void setBudget(Blob budget) {
        this.budget = budget;
    }

    public Date getFromMonth() {
        return this.fromMonth;
    }

    public void setFromMonth(Date fromMonth) {
        this.fromMonth = fromMonth;
    }

    public Date getToMonth() {
        return this.toMonth;
    }

    public void setToMonth(Date toMonth) {
        this.toMonth = toMonth;
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

    public Set getCbsBgtLabors() {
        return this.cbsBgtLabors;
    }

    public void setCbsBgtLabors(Set cbsBgtLabors) {
        this.cbsBgtLabors = cbsBgtLabors;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof CbsBudget) ) return false;
        CbsBudget castOther = (CbsBudget) other;
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
