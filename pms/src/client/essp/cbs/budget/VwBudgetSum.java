package client.essp.cbs.budget;


import c2s.dto.DtoTreeNode;
import c2s.essp.cbs.budget.DtoCbsBudget;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;


public class VwBudgetSum extends VwBudgetSumBase {
    private DtoCbsBudget budget;
//    private boolean isTreeInited = false;
    public VwBudgetSum() {
        super();
        try {
            addUCIEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUCIEvent() {
    }

    public void resetUI(){
        if(budget != null){
            DtoTreeNode root = budget.getCbsRoot();
            this.getTreeTable().setRoot(root);
            this.getTreeTable().expandRow(0);
//            DtoCbsBudgetItem budgetItem = (DtoCbsBudgetItem) root.getDataBean();
//            int row = 0;
//                if(!budgetItem.isShow())
//                    this.getTreeTable().getTree().fireTreeCollapsed(this.getTreeTable().getTree().getPathForRow(0));
//                List allChildren = root.listAllChildren();
//                for(row = 1; row <= allChildren.size(); row ++){
//                    DtoTreeNode node = (DtoTreeNode) allChildren.get(row - 1);
//                    budgetItem = (DtoCbsBudgetItem) node.getDataBean();
//                    if(!budgetItem.isShow()){
//                        TreePath treePath = this.getTreeTable().getTree().
//                                            getPathForRow(row);
//                        this.getTreeTable().getTree().fireTreeCollapsed(treePath);
//                    }
//                }
//                isTreeInited = true;
//            this.getTreeTable().getTree().fireTreeCollapsed(this.getTreeTable().getTree().getPathForRow());
        }
    }

//    public boolean isTreeInited(){
//        return isTreeInited;
//    }
//    public void setTreeInited(boolean b){
//        this.isTreeInited = b;
//    }
    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwBudgetSum resource = new VwBudgetSum();
        w1.addTab("Labor Cost",resource);
        TestPanel.show(w1);
        resource.refreshWorkArea();
    }

    public DtoCbsBudget getBudget() {
        return budget;
    }

    public void setBudget(DtoCbsBudget budget) {
        this.budget = budget;
    }

    public void setReadOnly(boolean b){
        model.setReadOnly(b);
    }
}
