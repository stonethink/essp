package client.essp.cbs.cost.activity;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Dimension;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;

public class VwActivityCostPopWin extends VWGeneralWorkArea  implements IVWPopupEditorEvent{
    VwActivityCostList activityCost;
    public VwActivityCostPopWin(){
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAcntRid(Long acntRid){
        activityCost.setAcntRid(acntRid);
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800,600));
        activityCost = new VwActivityCostList();
        this.addTab("Activity Cost",activityCost);
    }

    public void resetUI(){
        activityCost.resetUI();
    }

    public boolean onClickOK(ActionEvent e) {
        return false;
    }

    public boolean onClickCancel(ActionEvent e) {
        return false;
    }

}
