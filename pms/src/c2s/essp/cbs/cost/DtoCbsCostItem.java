package c2s.essp.cbs.cost;

import c2s.dto.DtoUtil;
import c2s.essp.cbs.DtoSubject;
import c2s.essp.cbs.budget.DtoCbsBudgetItem;

public class DtoCbsCostItem extends DtoSubject {
    private Double budgetAmt;
    private Double costAmt;
    private Double remainAmt;
    private String description;
    private static final long serialVersionUID = -5883212061808593344L;

    public DtoCbsCostItem(DtoCbsBudgetItem budgetItem,boolean copyBudget) {
        if(budgetItem == null)
            throw new IllegalArgumentException();
        DtoUtil.copyBeanToBean(this,budgetItem);
        setDescription(null);
        if(copyBudget)
            setBudgetAmt(budgetItem.getAmt());
    }
    public DtoCbsCostItem(DtoCbsBudgetItem budgetItem) {
        this(budgetItem,true);
    }
    public DtoCbsCostItem(){}

    public Double getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public Double getCostAmt() {
        return costAmt;
    }

    public void setCostAmt(Double costAmt) {
        this.costAmt = costAmt;
    }

    public Double getRemainAmt() {
        if(budgetAmt == null)
            return new Double(0);
        else if(costAmt == null)
            return budgetAmt;
        double remain = budgetAmt.doubleValue() - costAmt.doubleValue();
        this.remainAmt = new Double(remain);
        return remainAmt;
    }

    public void setRemainAmt(Double remainAmt) {
        this.remainAmt = remainAmt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addCostAmt(Double increAmt){
        if(increAmt == null)
            return ;
        else if(costAmt == null){
            costAmt = increAmt;
        }else{
            double temp = costAmt.doubleValue() + increAmt.doubleValue();
            costAmt = new Double(temp);
        }
    }

}
