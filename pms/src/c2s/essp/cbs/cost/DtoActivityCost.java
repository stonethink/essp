package c2s.essp.cbs.cost;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoActivityCost extends DtoBase {
    private Long acntRid;
    private Long activityId;
    private String activityCode;
    private String activityName;
    private Date costDate;
    private Long budgetPh;
    private Double budgetLaborAmt;
    private Double budgetNonlaborAmt;
    private Double budgetExpAmt;
    private Double budgetAmt;
    private Long actualPh;
    private Double actualLaborAmt;
    private Double actualNonlaborAmt;
    private Double actualExpAmt;
    private Double actualAmt;
    private Double remain;
    public Long getAcntRid() {
        return acntRid;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public Double getActualExpAmt() {
        return actualExpAmt;
    }

    public Double getActualLaborAmt() {
        return actualLaborAmt;
    }

    public Double getActualNonlaborAmt() {
        return actualNonlaborAmt;
    }

    public void addActualNonLaborAmt(double increAmt){
        if(actualNonlaborAmt == null)
            actualNonlaborAmt = new Double(increAmt);
        else{
            double temp = actualNonlaborAmt.doubleValue() + increAmt;
            actualNonlaborAmt = new Double(temp);
        }
    }
    public void addActualExpenseAmt(double increAmt){
        if(actualExpAmt == null)
            actualExpAmt = new Double(increAmt);
        else{
            double temp = actualExpAmt.doubleValue() + increAmt;
            actualExpAmt = new Double(temp);
        }
    }
    public Long getActualPh() {
        return actualPh;
    }

    public Double getBudgetExpAmt() {
        return budgetExpAmt;
    }

    public Double getBudgetLaborAmt() {
        return budgetLaborAmt;
    }

    public Double getBudgetNonlaborAmt() {
        return budgetNonlaborAmt;
    }

    public Long getBudgetPh() {
        return budgetPh;
    }

    public Date getCostDate() {
        return costDate;
    }

    public Double getActualAmt() {
        double amt = 0.0d;
        amt += (actualLaborAmt == null ? 0.0d : actualLaborAmt.doubleValue());
        amt += (actualExpAmt == null ? 0.0d : actualExpAmt.doubleValue());
        amt += (actualNonlaborAmt == null ? 0.0d : actualNonlaborAmt.doubleValue());
        actualAmt = new Double(amt);
        return actualAmt;
    }

    public Double getBudgetAmt() {
        double amt = 0.0d;
        amt += (budgetLaborAmt == null ? 0.0d : budgetLaborAmt.doubleValue());
        amt += (budgetNonlaborAmt == null ? 0.0d : budgetNonlaborAmt.doubleValue());
        amt += (budgetExpAmt == null ? 0.0d : budgetExpAmt.doubleValue());
        budgetAmt = new Double(amt);
        return budgetAmt;
    }

    public Double getRemain() {
        double amt = getBudgetAmt().doubleValue() - getActualAmt().doubleValue();
        remain = new Double(amt);
        return remain;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

    public void setBudgetPh(Long budgetPh) {
        this.budgetPh = budgetPh;
    }

    public void setBudgetNonlaborAmt(Double budgetNonlaborAmt) {
        this.budgetNonlaborAmt = budgetNonlaborAmt;
    }

    public void setBudgetLaborAmt(Double budgetLaborAmt) {
        this.budgetLaborAmt = budgetLaborAmt;
    }

    public void setBudgetExpAmt(Double budgetExpAmt) {
        this.budgetExpAmt = budgetExpAmt;
    }

    public void setActualPh(Long actualPh) {
        this.actualPh = actualPh;
    }

    public void setActualNonlaborAmt(Double actualNonlaborAmt) {
        this.actualNonlaborAmt = actualNonlaborAmt;
    }

    public void setActualLaborAmt(Double actualLaborAmt) {
        this.actualLaborAmt = actualLaborAmt;
    }

    public void setActualExpAmt(Double actualExpAmt) {
        this.actualExpAmt = actualExpAmt;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }
}
