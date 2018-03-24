package db.essp.cbs;

import db.essp.pms.Activity;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import db.essp.pms.ActivityPK;

/** @author Hibernate CodeGenerator */
public class PmsActivityCost implements Serializable {

    /** identifier field */
    private ActivityPK pk;

    /** nullable persistent field */
    private Date costDate;

    /** nullable persistent field */
    private Long budgetPh;

    /** nullable persistent field */
    private Double budgetLaborAmt;

    /** nullable persistent field */
    private Double budgetNonlaborAmt;

    /** nullable persistent field */
    private Double budgetExpAmt;

    /** nullable persistent field */
    private Long actualPh;

    /** nullable persistent field */
    private Double actualLaborAmt;

    /** nullable persistent field */
    private Double actualNonlaborAmt;

    /** nullable persistent field */
    private Double actualExpAmt;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private Activity activity;

    /** full constructor */
    public PmsActivityCost(ActivityPK pk, Date costDate, Long budgetPh, Double budgetLaborAmt, Double budgetNonlaborAmt, Double budgetExpAmt, Long actualPh, Double actualLaborAmt, Double actualNonlaborAmt, Double actualExpAmt, String rst, Date rct, Date rut, Activity activity) {
        this.pk = pk;
        this.costDate = costDate;
        this.budgetPh = budgetPh;
        this.budgetLaborAmt = budgetLaborAmt;
        this.budgetNonlaborAmt = budgetNonlaborAmt;
        this.budgetExpAmt = budgetExpAmt;
        this.actualPh = actualPh;
        this.actualLaborAmt = actualLaborAmt;
        this.actualNonlaborAmt = actualNonlaborAmt;
        this.actualExpAmt = actualExpAmt;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.activity = activity;
    }

    /** default constructor */
    public PmsActivityCost() {
    }

    /** minimal constructor */
    public PmsActivityCost(ActivityPK pk) {
        this.pk = pk;
    }

    public ActivityPK getPk() {
        return this.pk;
    }

    public void setPk(ActivityPK pk) {
        this.pk = pk;
    }

    public Date getCostDate() {
        return this.costDate;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

    public Long getBudgetPh() {
        return this.budgetPh;
    }

    public void setBudgetPh(Long budgetPh) {
        this.budgetPh = budgetPh;
    }

    public Double getBudgetLaborAmt() {
        return this.budgetLaborAmt;
    }

    public void setBudgetLaborAmt(Double budgetLaborAmt) {
        this.budgetLaborAmt = budgetLaborAmt;
    }

    public Double getBudgetNonlaborAmt() {
        return this.budgetNonlaborAmt;
    }

    public void setBudgetNonlaborAmt(Double budgetNonlaborAmt) {
        this.budgetNonlaborAmt = budgetNonlaborAmt;
    }

    public Double getBudgetExpAmt() {
        return this.budgetExpAmt;
    }

    public void setBudgetExpAmt(Double budgetExpAmt) {
        this.budgetExpAmt = budgetExpAmt;
    }

    public Long getActualPh() {
        return this.actualPh;
    }

    public void setActualPh(Long actualPh) {
        this.actualPh = actualPh;
    }

    public Double getActualLaborAmt() {
        return this.actualLaborAmt;
    }

    public void setActualLaborAmt(Double actualLaborAmt) {
        this.actualLaborAmt = actualLaborAmt;
    }

    public Double getActualNonlaborAmt() {
        return this.actualNonlaborAmt;
    }

    public void setActualNonlaborAmt(Double actualNonlaborAmt) {
        this.actualNonlaborAmt = actualNonlaborAmt;
    }

    public Double getActualExpAmt() {
        return this.actualExpAmt;
    }

    public void setActualExpAmt(Double actualExpAmt) {
        this.actualExpAmt = actualExpAmt;
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

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof PmsActivityCost) ) return false;
        PmsActivityCost castOther = (PmsActivityCost) other;
        return new EqualsBuilder()
            .append(this.getPk(), castOther.getPk())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPk())
            .toHashCode();
    }

}
