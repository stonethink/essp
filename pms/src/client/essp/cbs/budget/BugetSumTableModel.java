package client.essp.cbs.budget;

import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.budget.DtoCbsBudget;
import client.framework.model.VMTreeTableModel;

public class BugetSumTableModel extends VMTreeTableModel {
    private DtoCbsBudget budget;
    private boolean readOnly;
    public BugetSumTableModel(ITreeNode root, Object[][] configs,
                              String treeColumnName) {
        super(root, configs, treeColumnName);
    }

//    public boolean isCellEditable(Object node, int columnIndex) {
//        //Remark栏位才能修改
//        if(columnIndex == -1 && columnIndex == 2)
//            return true;
//        else
//            return false;
//    }

    public void setBudget(DtoCbsBudget budget) {
        this.budget = budget;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setValueAt(Object aValue, Object node, int columnIndex) {
        if(budget != null)
            budget.setOp(IDto.OP_MODIFY);
        super.setValueAt( aValue,  node,  columnIndex) ;
    }
    public boolean isCellEditable(Object node, int columnIndex) {
        if(readOnly)
            return false;
        return super.isCellEditable(node,columnIndex);
    }
}
