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
public class DtoAcntCost extends DtoBase {
    private String jobCodeId;
    private String jobCode;
    private String currency;
    private Double price;
    private Double budgetPm;
    private Double actualPm;
    private Double budgetAmt;
    private Double actualAmt;
    private Long acntRid;
    public DtoAcntCost() {
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setBudgetPm(Double budgetPm) {
        this.budgetPm = budgetPm;
    }

    public void setActualPm(Double actualPm) {
        this.actualPm = actualPm;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setJobCodeId(String jobCodeId) {
        this.jobCodeId = jobCodeId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getPrice() {
        return price;
    }

    public Double getBudgetPm() {
        return budgetPm;
    }

    public Double getActualPm() {
        return actualPm;
    }

    public Double getRemainPm() {
        if(budgetPm == null )
            budgetPm = new Double(0);
        if(actualPm ==  null)
            actualPm = new Double(0);
        return new Double(budgetPm.doubleValue() - actualPm.doubleValue());
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public Double getActualAmt() {
        return actualAmt;
    }

    public Double getRemainAmt() {
        if(budgetAmt == null )
            budgetAmt = new Double(0);
        if(actualAmt ==  null)
            actualAmt = new Double(0);
        return new Double(budgetAmt.doubleValue() - actualAmt.doubleValue());
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getJobCodeId() {
        return jobCodeId;
    }
}
