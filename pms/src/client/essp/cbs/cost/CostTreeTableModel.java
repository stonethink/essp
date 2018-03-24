package client.essp.cbs.cost;

import java.util.List;

import c2s.dto.ITreeNode;
import c2s.essp.cbs.cost.DtoCbsCost;
import c2s.essp.cbs.cost.DtoCbsCostItem;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTreeTableModel;
import c2s.dto.IDto;

public class CostTreeTableModel extends VMTreeTableModel {
    private DtoCbsCost costDto;
    public CostTreeTableModel(ITreeNode treeNode, Object[][] configs,String treeColumnName) {
        super(treeNode, configs,treeColumnName);
    }
//    public boolean isCellEditable(Object node, int columnIndex) {
//        ITreeNode treeNode = (ITreeNode) node;
//        DtoCbsCostItem costItem = (DtoCbsCostItem) treeNode.getDataBean();
//        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
//                                      get(columnIndex);
//        String dataName = columnConfig.getDataName();
//        if("costAmt".equals(dataName))
//            return DtoCbsCostItem.TYPE_ENTRY.equals(costItem.getCostCalType());
//        else if("description".equals(dataName))
//            return true;
//        else
//            return super.isCellEditable(node,columnIndex);
//    }
    public Object getValueAt(Object node, int columnIndex) {
//        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
//                                      get(columnIndex);
//        String dataName = columnConfig.getDataName();
//        if("costDate".equals(dataName) && costDto != null){
//            return costDto.getCostDate();
//        }else{
            return super.getValueAt(node,columnIndex);
//        }
    }
    public void setValueAt(Object aValue, Object node, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String dataName = columnConfig.getDataName();
        if("costAmt".equals(dataName)){
            ITreeNode treeNode = (ITreeNode) node;
//            DtoCbsCostItem costItem = (DtoCbsCostItem) treeNode.getDataBean();
//            BigDecimal inputCostAmt = (BigDecimal) aValue;
//            double budget = costItem.getBudgetAmt() == null ? 0d : costItem.getBudgetAmt().doubleValue();
//            double costAmt = inputCostAmt == null ? 0d : inputCostAmt.doubleValue();
//            if(budget - costAmt < 0d){
//                comMSG.dispErrorDialog("The cost can be larger than budget!");
//            }else{
                super.setValueAt(aValue, node, columnIndex);
                updateParent(treeNode);
//            }
        }else{
            super.setValueAt(aValue,node,columnIndex);
        }
        if(costDto != null)
            costDto.setOp(IDto.OP_MODIFY);
    }

    private void updateParent(ITreeNode node){
        if(node == null)
            return;
        ITreeNode parent = node.getParent();
        if(parent == null || parent.getDataBean() == null)
            return;
        DtoCbsCostItem parentCostItem = (DtoCbsCostItem) parent.getDataBean();
        if( !DtoCbsCostItem.TYPE_AUTO_CALCULATE.equals(parentCostItem.getCostCalType())
            || parent.children() == null || parent.children().size() <= 0)
            return;
        List children = parent.children();
        double costAmt = 0d;
        for(int i =0;i < children.size();i ++){
            ITreeNode child = (ITreeNode) children.get(i);
            DtoCbsCostItem costItem = (DtoCbsCostItem) child.getDataBean();
            if(costItem.getCostAmt() != null)
                costAmt = costAmt + costItem.getCostAmt().doubleValue();
        }
        parentCostItem.setCostAmt(new Double(costAmt));
        updateParent(parent);
    }

    public void setCostDto(DtoCbsCost costDto) {
        this.costDto = costDto;
    }

    public DtoCbsCost getCostDto() {
        return costDto;
    }
}
