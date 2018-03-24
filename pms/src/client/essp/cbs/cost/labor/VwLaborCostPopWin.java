package client.essp.cbs.cost.labor;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Dimension;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;

public class VwLaborCostPopWin extends VWGeneralWorkArea  implements IVWPopupEditorEvent{

    VwLaborCost laborCost;
    public VwLaborCostPopWin(){
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAcntRid(Long acntRid){
        laborCost.setAcntRid(acntRid);
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800,600));
        laborCost = new VwLaborCost();
        this.addTab("Labor Cost",laborCost);
    }

    public void resetUI(){
        laborCost.resetUI();
    }

    public boolean onClickOK(ActionEvent e) {
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }
    public boolean isRefreshParent(){
        return laborCost.isRefreshParent();
    }

}
