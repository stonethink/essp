package client.essp.cbs.budget;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.common.Global;

public class VwBudgetLogBase extends VWGeneralWorkArea {
    public VwBudgetLogBase() {
        try {
            jbInit();
            setComponentName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setComponentName() {
        inputBaseID.setName("baseId");
        inputBasePM.setName("basePm");
        inputBaseAmt.setName("baseAmt");
        inputCurrentPm.setName("totalBugetPm");
        inputCurrentAmt.setName("totalBugetAmt");
        inputChangedPm.setName("changeBugetPm");
        inputChangedAmt.setName("changeBugetAmt");
        inputChangedBy.setName("changedBy");
        inputChangedDate.setName("logDate");
        inputChangedReason.setName("reson");
    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(430,220));
        lbBaseId.setText("Base Line ID");
        lbBaseId.setBounds(new Rectangle(20, 20, 80, 20));

        lbBasePm.setText("Base Line PM");
        lbBasePm.setBounds(new Rectangle(20, 50, 80, 20));
        lbBaseAmt.setText("Base Line Amount");
        lbBaseAmt.setBounds(new Rectangle(210, 50, 120, 20));

        lbCurrentPm.setText("Current PM");
        lbCurrentPm.setBounds(new Rectangle(20, 80, 88, 20));
        lbCurrentAmt.setText("Current Amount");
        lbCurrentAmt.setBounds(new Rectangle(210, 80, 99, 20));

        lbChangedPm.setText("Changed PM");
        lbChangedPm.setBounds(new Rectangle(20, 110, 79, 20));
        lbChangedAmt.setToolTipText("");
        lbChangedAmt.setText("Changed Amount");
        lbChangedAmt.setBounds(new Rectangle(210, 110, 91, 20));

        lbChangedBy.setText("Changed By");
        lbChangedBy.setBounds(new Rectangle(20, 140, 77, 20));
        lgChangedDate.setText("Changed Date");
        lgChangedDate.setBounds(new Rectangle(210, 140, 85, 20));

        lbChangeReason.setText("Change Reason");
        lbChangeReason.setBounds(new Rectangle(20, 170, 82, 20));

        inputBaseID.setBounds(new Rectangle(105, 20, 90, 20));
        inputBaseID.setEnabled(false);

        inputBasePM.setBounds(new Rectangle(105, 50, 90, 20));
        inputBaseAmt.setBounds(new Rectangle(315, 50, 90, 20));
        inputBasePM.setEnabled(false);
        inputBaseAmt.setEnabled(false);

        inputCurrentPm.setBounds(new Rectangle(105, 80, 90, 20));
        inputCurrentAmt.setBounds(new Rectangle(315, 80, 90, 20));
        inputCurrentPm.setEnabled(false);
        inputCurrentAmt.setEnabled(false);

        inputChangedPm.setBounds(new Rectangle(105, 110, 90, 20));
        inputChangedAmt.setBounds(new Rectangle(315, 110, 90, 20));
        inputChangedPm.setCanNegative(true);
        inputChangedPm.setEnabled(false);
        inputChangedAmt.setEnabled(false);
        inputChangedAmt.setCanNegative(true);

        inputChangedBy.setBounds(new Rectangle(105, 140, 90, 20));
        inputChangedDate.setBounds(new Rectangle(315, 140, 90, 20));
        inputChangedDate.setUICValue(Global.todayDate);
        inputChangedDate.setCanSelect(true);
        inputChangedBy.setEnabled(false);

        inputChangedReason.setBounds(new Rectangle(105, 170, 300, 40));
        this.add(lbBaseId);
        this.add(lbBasePm);
        this.add(lbBaseAmt);
        this.add(lbCurrentAmt);
        this.add(lbChangedAmt);
        this.add(lbChangedPm);
        this.add(lbCurrentPm);
        this.add(lgChangedDate);
        this.add(lbChangeReason);
        this.add(inputBaseID);
        this.add(inputBasePM);
        this.add(inputBaseAmt);
        this.add(lbChangedBy);
        this.add(inputCurrentPm);
        this.add(inputCurrentAmt);
        this.add(inputChangedPm);
        this.add(inputChangedAmt);
        this.add(inputChangedDate);
        this.add(inputChangedBy);
        this.add(inputChangedReason);
    }

    VWJLabel lbBaseId = new VWJLabel();
    VWJLabel lbBasePm = new VWJLabel();
    VWJLabel lbBaseAmt = new VWJLabel();
    VWJLabel lbCurrentPm = new VWJLabel();
    VWJLabel lbCurrentAmt = new VWJLabel();
    VWJLabel lbChangedPm = new VWJLabel();
    VWJLabel lbChangedAmt = new VWJLabel();
    VWJLabel lbChangedBy = new VWJLabel();
    VWJLabel lgChangedDate = new VWJLabel();
    VWJLabel lbChangeReason = new VWJLabel();

    VWJText inputBaseID = new VWJText();
    VWJReal inputBasePM = new VWJReal();
    VWJReal inputBaseAmt = new VWJReal();
    VWJReal inputCurrentPm = new VWJReal();
    VWJReal inputCurrentAmt = new VWJReal();
    VWJReal inputChangedPm = new VWJReal();
    VWJReal inputChangedAmt = new VWJReal();
    VWJText inputChangedBy = new VWJText();
    VWJDate inputChangedDate = new VWJDate();
    VWJTextArea inputChangedReason = new VWJTextArea();
}
