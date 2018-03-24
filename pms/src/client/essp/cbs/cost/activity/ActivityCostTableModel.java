package client.essp.cbs.cost.activity;

import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.cost.DtoActivityCost;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import java.math.BigDecimal;
import c2s.essp.cbs.DtoSubject;
import c2s.dto.IDto;

public class ActivityCostTableModel extends VMTable2 {
    private DtoActivityCost activityCost;
    public static final int LABOR_ROW = 0;
    public static final int NONLABOR_ROW = 1;
    public static final int EXPENSE_ROW = 2;
    public static final String[] ROW_NAME = {DtoSubject.ATTRI_LABOR_SUM,
                                            DtoSubject.ATTRI_NONLABOR_SUM,
                                            DtoSubject.ATTRI_EXPENSE_SUM};
    public ActivityCostTableModel(Object[][] configs){
        super(configs);
    }
    /**
     * 表格固定只有三行：
     * 第1行：Labor Cost;第2行：NonLabor Cost;第3行：Expense Cost;
     * @return int
     */
    public int getRowCount() {
        return 3;
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String dataName = columnConfig.getDataName();
        //NonLabor和Expense的Budget可以修改，其他栏位都不能修改
        if( (rowIndex == NONLABOR_ROW || rowIndex == EXPENSE_ROW) &&
            ("budgetAmt".equals(dataName)))
            return true;
        return false;
    }
    public Object getRow(int row) {
        if(row < 3 || row >= 0)
            return ROW_NAME[row];
        return null;
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(activityCost != null){
            VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                          get(columnIndex);
            String dataName = columnConfig.getDataName();
            return getCostValue(rowIndex,dataName);
        }
        return null;
    }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(activityCost != null){
            VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                          get(columnIndex);
            String dataName = columnConfig.getDataName();
            if(NONLABOR_ROW == rowIndex && "budgetAmt".equals(dataName)){
                BigDecimal input = (BigDecimal) aValue;
                activityCost.setBudgetNonlaborAmt(new Double(input.doubleValue()));
                activityCost.setOp(IDto.OP_MODIFY);
            }else if(EXPENSE_ROW == rowIndex && "budgetAmt".equals(dataName)){
                BigDecimal input = (BigDecimal) aValue;
                activityCost.setBudgetExpAmt(new Double(input.doubleValue()));
                activityCost.setOp(IDto.OP_MODIFY);
            }
            this.fireTableDataChanged();
        }
    }
    private Object getCostValue(int costRow,String dataName){
        switch (costRow) {
        case LABOR_ROW :
            if("subject".equals(dataName))
                return CbsConstant.LABOR_RESOURCE;
            else if("budgetAmt".equals(dataName))
                return activityCost.getBudgetLaborAmt();
            else if("actualAmt".equals(dataName))
                return activityCost.getActualLaborAmt();
            else
                return caculateRemain(activityCost.getBudgetLaborAmt(),activityCost.getActualLaborAmt());
        case NONLABOR_ROW :
            if("subject".equals(dataName))
                return CbsConstant.NONLABOR_RESOURCE;
            else if("budgetAmt".equals(dataName))
                return activityCost.getBudgetNonlaborAmt();
            else if("actualAmt".equals(dataName))
                return activityCost.getActualNonlaborAmt();
            else
                return caculateRemain(activityCost.getBudgetNonlaborAmt(),activityCost.getActualNonlaborAmt());
        case EXPENSE_ROW :
            if("subject".equals(dataName))
                return CbsConstant.EXPENSE;
            else if("budgetAmt".equals(dataName))
                return activityCost.getBudgetExpAmt();
            else if("actualAmt".equals(dataName))
                return activityCost.getActualExpAmt();
            else
                return caculateRemain(activityCost.getBudgetExpAmt(),activityCost.getActualExpAmt());
        }
        return null;
    }

    private Object caculateRemain(Double budget,Double cost) {
        if(cost == null)
            return budget;
        double temp = cost.doubleValue();
        if(budget == null){
            return new Double(-temp);
        }else{
            temp = budget.doubleValue() - temp;
            return new Double(temp);
        }
    }

    public DtoActivityCost getActivityCost() {
        return activityCost;
    }

    public void setActivityCost(DtoActivityCost activityCost) {
        this.activityCost = activityCost;
    }

}
