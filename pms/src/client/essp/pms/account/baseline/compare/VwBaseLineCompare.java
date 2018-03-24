package client.essp.pms.account.baseline.compare;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.account.baseline.compare.timing.VwAcntBLTiming;
import client.essp.pms.account.baseline.compare.budget.VwAcntBLBudCom;
import java.awt.BorderLayout;
import com.wits.util.Parameter;

public class VwBaseLineCompare extends VWGeneralWorkArea {
    private VWWorkArea timingPanel = new VWWorkArea();
    private VWWorkArea budgetPanel = new VWWorkArea();

    private VwAcntBLTiming timingCompArea = new VwAcntBLTiming();
    private VwAcntBLBudCom budgetCompArea = new VwAcntBLBudCom();
    public VwBaseLineCompare(){
        this.setLayout(new BorderLayout(0,5));

        timingPanel.addTab("Timing",timingCompArea);
        budgetPanel.addTab("Budget",budgetCompArea);

        this.add(timingPanel,BorderLayout.CENTER);
        this.add(budgetPanel,BorderLayout.SOUTH);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        if(param.get("acntRid")!=null){
            timingCompArea.setParameter(param);
            budgetCompArea.setParameter(param);
        }
    }

    public void resetUI(){
        timingCompArea.resetUI();
        budgetCompArea.resetUI();
    }
}
