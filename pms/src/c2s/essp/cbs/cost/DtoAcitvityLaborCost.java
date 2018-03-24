package c2s.essp.cbs.cost;

import c2s.dto.DtoBase;
import c2s.essp.common.calendar.WorkCalendarConstant;

public class DtoAcitvityLaborCost extends DtoBase {
    private String jobCode;
    private Double price;
    private Long budgetPh;
    private Double budgetAmt;
    private Long actualPh;
    private Double actualAmt;
    private Double remain;
    public String getJobCode() {
        return jobCode;
    }

    public Double getPrice() {
        return price;
    }

    public Long getBudgetPh() {
        return budgetPh;
    }

    public void addBudgetPh(Long incre){
        if(incre == null)
            return;
        else if(budgetPh == null){
            budgetPh = incre;
        }else{
            budgetPh = new Long( budgetPh.longValue() + incre.longValue() );
        }
    }
    public Double getBudgetAmt() {
        if(price == null || budgetPh == null)
            return new Double(0);
        double amt = price.doubleValue() * ( budgetPh.doubleValue() / WorkCalendarConstant.MONTHLY_WORK_HOUR);
        budgetAmt = new Double(amt);
        return budgetAmt;
    }

    public Long getActualPh() {
        return actualPh;
    }

    public void addActualPh(Long incre){
        if(incre == null)
            return;
        else if(actualPh == null){
            actualPh = incre;
        }else{
            actualPh = new Long( actualPh.longValue() + incre.longValue() );
        }
    }
    public Double getActualAmt() {
        if(price == null || actualPh == null)
            return new Double(0);
        double amt = price.doubleValue() * ( actualPh.doubleValue() / WorkCalendarConstant.MONTHLY_WORK_HOUR);
        actualAmt = new Double(amt);
        return actualAmt;
    }

    public Double getRemain() {
        double temp = getBudgetAmt().doubleValue() - getActualAmt().doubleValue();
        remain = new Double(temp);
        return remain;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setBudgetPh(Long budgetPh) {
        this.budgetPh = budgetPh;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public void setActualPh(Long actualPh) {
        this.actualPh = actualPh;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public void setRemain(Double remain) {
        this.remain = remain;
    }
}
