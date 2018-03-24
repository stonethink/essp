package client.essp.cbs.budget;

import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Dimension;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;

public class VwAntiBgtPopWin extends VWGeneralWorkArea implements
    IVWPopupEditorEvent{
    VwBudgetMain vwAntiBudget ;
    public VwAntiBgtPopWin() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(900, 600));

        vwAntiBudget = new VwBudgetMain();
        this.addTab("Anticipated Budget", vwAntiBudget);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        vwAntiBudget.setParameter(param);
    }
    public void resetUI(){
        vwAntiBudget.refreshWorkArea();
    }

    public boolean onClickOK(ActionEvent e) {
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

}
