package client.essp.cbs.budget.labor;

import client.framework.model.VMTable2;
import c2s.essp.cbs.budget.DtoResBudgtSum;
import c2s.dto.IDto;

public class LaborBgtSumTableModel extends VMTable2 {
    public  LaborBgtSumTableModel(Object[][] configs){
        super(configs);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(rowIndex == 0)//第一行合计不能修改
            return false;
        else
            return super.isCellEditable(rowIndex,columnIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue,rowIndex,columnIndex);
        DtoResBudgtSum sumBudget = (DtoResBudgtSum) getRow(0);
        sumBudget.setOp(IDto.OP_MODIFY);
    }
}
