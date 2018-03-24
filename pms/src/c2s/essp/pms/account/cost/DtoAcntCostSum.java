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
public class DtoAcntCostSum extends DtoBase{
    private String jobCode;
    private Double budgetPm;
    private Double actualPm;
    private Double remainPm;
    private Double budgetAmt;
    private Double actualAmt;
    private Double remainAmt;
    private Long acntRid;
    public Long getAcntRid() {
        return acntRid;
    }

    public Double getActualAmt() {
        return actualAmt;
    }

    public Double getActualPm() {
        return actualPm;
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public Double getBudgetPm() {
        return budgetPm;
    }

    public String getJobCode() {
        return jobCode;
    }

    public Double getRemainAmt() {
        return remainAmt;
    }

    public Double getRemainPm() {
        return remainPm;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public void setActualPm(Double actualPm) {
        this.actualPm = actualPm;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setBudgetPm(Double budgetPm) {
        this.budgetPm = budgetPm;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setRemainAmt(Double remainAmt) {
        this.remainAmt = remainAmt;
    }

    public void setRemainPm(Double remainPm) {
        this.remainPm = remainPm;
    }

}
