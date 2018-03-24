package c2s.essp.pms.activity;

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
public class DtoActivityCost extends DtoBase{
    private String jobCode;
    private Double price;
    private Double budgetPh;
    private Double budgetAmt;
    private Double actualPh;
    private Double actualAmt;
    private Double remain;
    public DtoActivityCost() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setBudgetPh(Double budgetPh) {
        this.budgetPh = budgetPh;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setActualPh(Double actualPh) {
        this.actualPh = actualPh;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }

    public String getJobCode() {
        return jobCode;
    }

    public Double getPrice() {
        return price;
    }

    public Double getBudgetPh() {
        return budgetPh;
    }

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public Double getActualPh() {
        return actualPh;
    }

    public Double getActualAmt() {
        return actualAmt;
    }

    public Double getRemain() {
        return remain;
    }
}
