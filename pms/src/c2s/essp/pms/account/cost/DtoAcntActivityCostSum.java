package c2s.essp.pms.account.cost;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoAcntActivityCostSum extends DtoBase {
    private String sum;
    private String activityId;
    private Double budgetPh;
    private Double actualPh;
    private Double remainPh;
    private Double budgetAmt;
    private Double actualAmt;
    private Double remainAmt;

    public DtoAcntActivityCostSum() {
    }

    public String getSum() {
        return sum;
    }

    public Double getActualAmt() {
        return actualAmt;
    }

    public Double getActualPh() {
        return actualPh;
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public Double getBudgetPh() {
        return budgetPh;
    }

    public Double getRemainAmt() {
        return remainAmt;
    }

    public Double getRemainPh() {
        return remainPh;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public void setActualPh(Double actualPh) {
        this.actualPh = actualPh;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setBudgetPh(Double budgetPh) {
        this.budgetPh = budgetPh;
    }

    public void setRemainAmt(Double remainAmt) {
        this.remainAmt = remainAmt;
    }

    public void setRemainPh(Double remainPh) {
        this.remainPh = remainPh;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
