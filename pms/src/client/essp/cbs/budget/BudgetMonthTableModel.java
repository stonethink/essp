package client.essp.cbs.budget;

import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.essp.cbs.DtoSubject;
import c2s.essp.cbs.budget.DtoCbsBudget;
import c2s.essp.cbs.budget.DtoCbsBudgetItem;
import client.framework.common.treeTable.TreeTableModelAdapter;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import java.util.List;

public class BudgetMonthTableModel extends VMTable2 {
    TreeTableModelAdapter treeModel;
    private boolean readOnly;
    private DtoCbsBudget budget;
    public  BudgetMonthTableModel(TreeTableModelAdapter treeModel){
        this.treeModel = treeModel;
    }

//    public String getColumnName(int columnIndex) {
//        if(budget != null){
//            List monthList = budget.getMonthList();
//            if (monthList != null && columnIndex < monthList.size() ){
//                return (String) monthList.get(columnIndex);
//            }
//        }
//        return "";
//    }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String month = columnConfig.getDataName();
        ITreeNode node = (ITreeNode) treeModel.nodeForRow(rowIndex);
        DtoCbsBudgetItem budgetItem = (DtoCbsBudgetItem) node.getDataBean();
        Double monthAmt = new Double(aValue.toString());
        Double oldMonthAmt = budgetItem.getMonthAmt(month);
        budgetItem.setMonthAmt(month,monthAmt);
        budgetItem.setOp(IDto.OP_MODIFY);
        double increAmt = monthAmt.doubleValue() - oldMonthAmt.doubleValue();

        updateParent(node,month,increAmt);
        fireTableDataChanged();
        treeModel.fireTableDataChanged();
        if(budget != null)
            budget.setOp(IDto.OP_MODIFY);
    }
    /**
     * 递归更新父节点当月的数据
     * 1.判断该父节点是否需要更新,从子节点合计的才需更新
     * @param node ITreeNode
     * @param month String
     * @param oldMonthAmt Double
     */
    private void updateParent(ITreeNode node,String month,double increAmt) {
        if(node == null || node.getParent() == null)
            return;
        ITreeNode parentNode = node.getParent();
        DtoCbsBudgetItem parentSubject = (DtoCbsBudgetItem) parentNode.getDataBean();
        if(parentSubject != null && parentSubject.getBudgetCalType().equals(DtoSubject.TYPE_AUTO_CALCULATE)){
            Double oldParentMonAmt = parentSubject.getMonthAmt(month);
            //父科目当月更新后的值 ＝ 更新前的值 ＋ 累加值
            double newParentMontAmt = oldParentMonAmt.doubleValue() + increAmt;
            parentSubject.setMonthAmt(month,new Double(newParentMontAmt));
            updateParent(parentNode,month,increAmt);
        }else{
            return ;
        }
    }
    public Object getValueAt(int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String month = columnConfig.getDataName();
        ITreeNode node = (ITreeNode) treeModel.nodeForRow(rowIndex);
        DtoCbsBudgetItem budgetItem = (DtoCbsBudgetItem) node.getDataBean();
        return budgetItem.getMonthAmt(month);
    }
    public int getRowCount(){
        return treeModel.getRowCount();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(readOnly)
            return false;
        ITreeNode node = (ITreeNode) treeModel.nodeForRow(rowIndex);
        DtoCbsBudgetItem budgetItem = (DtoCbsBudgetItem) node.getDataBean();
        //自动计算的科目不能修改
        if(DtoCbsBudgetItem.TYPE_AUTO_CALCULATE.equals(budgetItem.getBudgetCalType())){
            return false;
        }
        return true;
    }

    public DtoCbsBudget getBudget() {
        return budget;
    }

    public void setBudget(DtoCbsBudget budget) {
        this.budget = budget;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Class getColumnClass(int columnIndex) {
        return Double.class;
    }

    public Object getRow(int rowIndex){
        ITreeNode node = (ITreeNode) treeModel.nodeForRow(rowIndex);
        DtoCbsBudgetItem budgetItem = (DtoCbsBudgetItem) node.getDataBean();
        return budgetItem;
    }
}
