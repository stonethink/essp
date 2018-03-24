package client.essp.cbs.budget;

import java.awt.Dimension;

import c2s.essp.cbs.budget.DtoCbsBudget;
import client.essp.cbs.budget.labor.VwLaborBudget;
import client.essp.common.view.VWGeneralWorkArea;

public class VwLaborBgtPopWin extends VWGeneralWorkArea  {
    VwLaborBudget laborBudget ;
    public VwLaborBgtPopWin() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(1200, 600));
        laborBudget = new VwLaborBudget();
        this.addTab("Labor Budget", laborBudget);
    }
    public void setBudget(DtoCbsBudget bugd){
        laborBudget.setBudget(bugd);
    }
    public void resetUI(){
        laborBudget.resetUI();
    }
}
