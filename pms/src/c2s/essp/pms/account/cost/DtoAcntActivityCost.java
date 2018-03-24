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
public class DtoAcntActivityCost extends DtoBase{
    private Long activityId;
    private String activityName;
    private Double budgetPh;
    private Double actualPh;
    private Double remainPh;
    private Double budgetAmt;
    private Double actualAmt;
    private Double remainAmt;
    private String isActivity;
    private String code;
    public DtoAcntActivityCost() {
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setBudgetPh(Double budgetPh) {
        this.budgetPh = budgetPh;
    }

    public void setActualPh(Double actualPh) {
        this.actualPh = actualPh;
    }

    public void setRemainPh(Double remainPh) {
        this.remainPh = remainPh;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public void setRemainAmt(Double remainAmt) {
        this.remainAmt = remainAmt;
    }

    public void setIsActivity(String isActivity) {
        this.isActivity = isActivity;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public Double getBudgetPh() {
        return budgetPh;
    }

    public Double getActualPh() {
        return actualPh;
    }

    public Double getRemainPh() {
        return remainPh;
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public Double getActualAmt() {
        return actualAmt;
    }

    public Double getRemainAmt() {
        return remainAmt;
    }

    public String getIsActivity() {
        return isActivity;
    }

    public String getCode() {
        return code;
    }
}
