package client.essp.cbs.budget.labor;

import java.util.List;

import c2s.essp.cbs.budget.DtoResBudgetItem;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import com.wits.util.StringUtil;
import c2s.essp.cbs.budget.DtoResBudgtSum;
import c2s.dto.IDto;

public class LaborBgtMonthTableModel extends VMTable2 {
    public  LaborBgtMonthTableModel(List configs){
        super();
        super.setColumnConfigs(configs);
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(rowIndex == 0)//第一行合计不能修改
            return false;
        else
            return super.isCellEditable(rowIndex,columnIndex);
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        List rows = this.getRows();
        if(rows == null)
            return null;
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String month = columnConfig.getDataName();
        DtoResBudgetItem resBudget = (DtoResBudgetItem) getRow(rowIndex);
        Double amount =  resBudget.getMonthUnitNum(month);
        return amount == 0D ? null : amount;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        List rows = this.getRows();
        if(rows == null)
            return ;
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String month = columnConfig.getDataName();
        DtoResBudgetItem resBudget = (DtoResBudgetItem) getRow(rowIndex);
        Double oldNum = resBudget.getMonthUnitNum(month);
        Double inputNum = new Double(StringUtil.nvl(aValue));
        resBudget.setMonthUnitNum(month,inputNum);
        resBudget.getUnitNum();
        resBudget.getAmt();
        resBudget.setOp(IDto.OP_MODIFY);
        //计算当月所有人月数总和:旧的和值 － 当月输入值 + 当月旧值
        DtoResBudgtSum sumBudget = (DtoResBudgtSum) getRow(0);
        Double oldUnitSum = sumBudget.getMonthUnitNum(month);
        double newUnitSum = oldUnitSum.doubleValue() + inputNum.doubleValue() - oldNum.doubleValue();
        sumBudget.setMonthUnitNum(month,new Double(newUnitSum));
        sumBudget.getUnitNum();

        //计算价钱数总和:旧的和值 + (当月输入值 － 当月旧值)*价格
        Double oldAmt = sumBudget.getAmt();
        double newAmt = oldAmt.doubleValue() +( inputNum.doubleValue() - oldNum.doubleValue())*(resBudget.getPrice().doubleValue());
        sumBudget.setAmt(new Double(newAmt));
        fireTableDataChanged();
        /*
         *1.每次修改表格内的值将设置第一行的Dto状态为“MODIFY”，以此来判断整个表格是否修改过
         *2.每次提交到服务端正确保存后再设置其值为"NO_Change"
         */
        sumBudget.setOp(IDto.OP_MODIFY);
    }

    public Class getColumnClass(int columnIndex) {
        return Double.class;
    }
}
