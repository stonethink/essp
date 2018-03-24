package c2s.essp.cbs.cost;

public class DtoResCostSum extends DtoResCostItem {
    private Double budgetAmt;
    private Double costAmt;
    public static final String SUM = "SUM";

    public DtoResCostSum(){
        super();
        setResName(SUM);
        setRid(new Long(0));
    }
    public void setBudgetAmt(Double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }
    public void addBudgetAmt(Double increAmt){
        if(increAmt == null)
            return;
        else if(budgetAmt == null)
            this.budgetAmt = increAmt;
        else{
            double temp = budgetAmt.doubleValue() + increAmt.doubleValue();
            this.budgetAmt = new Double(temp);
        }
    }
    public Double getBudgetAmt() {
        if(budgetAmt == null)
            return new Double(0);
        return budgetAmt;
    }
    public void setCostAmt(Double costAmt) {
        this.costAmt = costAmt;
    }
    public void addCostAmt(Double increAmt){
        if(increAmt == null)
            return;
        else if(costAmt == null)
            this.costAmt = increAmt;
        else{
            double temp = costAmt.doubleValue() + increAmt.doubleValue();
            this.costAmt = new Double(temp);
        }
    }
     public Double getCostAmt() {
         if(costAmt == null)
             return new Double(0);
        return costAmt;
     }

     public void addBudgetUnitNum(Double increNum){
         if(increNum == null)
             return;
         else if(getBudgetUnitNum() == null)
             setBudgetUnitNum(increNum);
         else{
             double temp = getBudgetUnitNum().doubleValue() + increNum.doubleValue();
             setBudgetUnitNum(new Double(temp));
         }
     }
     public void addCostUnitNum(Double increNum){
         if(increNum == null)
            return;
        else if(getCostUnitNum() == null)
            setCostUnitNum(increNum);
        else{
            double temp = getCostUnitNum().doubleValue() + increNum.doubleValue();
            setCostUnitNum(new Double(temp));
        }
     }
}
