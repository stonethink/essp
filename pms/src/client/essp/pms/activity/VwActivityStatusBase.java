package client.essp.pms.activity;

import client.essp.common.view.VWGeneralWorkArea;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJComboBox;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import client.framework.model.VMComboBox;
import c2s.essp.pms.wbs.DtoWbsActivity;

public class VwActivityStatusBase extends VWGeneralWorkArea {
    public VwActivityStatusBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VwActivityStatusBase vwactivitystatusbase = new VwActivityStatusBase();
    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        //jPanel1.setBackground(Color.gray);
        statusPanel.setBorder(border2);
        statusPanel.setBounds(new Rectangle(13, 21, 335, 200));
        statusPanel.setLayout(null);
        plannedStartLabel.setText("Planned Start");
        plannedStartLabel.setBounds(new Rectangle(7, 22, 91, 20));
        plannedStart.setBounds(new Rectangle(138, 22, 183, 20));
        plannedFinishLabel.setBounds(new Rectangle(7, 51, 91, 20));
        plannedFinishLabel.setText("Planned Finish");
        plannedFinish.setBounds(new Rectangle(138, 51, 183, 20));
        actualStart.setBounds(new Rectangle(138, 79, 183, 20));
        actualStartLabel.setBounds(new Rectangle(30, 79, 85, 20));
        actualStartLabel.setText("Actual Start");
        isActualStart.setBounds(new Rectangle(7, 79, 16, 20));
        isActualFinish.setBounds(new Rectangle(7, 107, 16, 20));
        actualFinishLabel.setBounds(new Rectangle(30, 107, 85, 20));
        actualFinishLabel.setText("Actual Finish");
        actualFinish.setBounds(new Rectangle(138, 107, 183, 20));
        completeMethodLabel.setBounds(new Rectangle(7, 136, 130, 20));
        completeMethodLabel.setText("% Complete Computing");
        completeMethod.setBounds(new Rectangle(138, 136, 183, 20));
        completeRateLabel.setBounds(new Rectangle(7, 166, 119, 20));
        completeRateLabel.setText("% Complete");
        completeRate.setBounds(new Rectangle(138, 166, 183, 20));
        durationPanel.setBorder(border4);
        durationPanel.setBounds(new Rectangle(358, 21, 335, 200));
        durationPanel.setLayout(null);
        plannedDurationLabel.setText("Duration");
        plannedDurationLabel.setBounds(new Rectangle(12, 21, 91, 20));
        VWJLabel timeLimitTypeLabel=new VWJLabel();
        timeLimitTypeLabel.setText("Duration Type");
        timeLimitTypeLabel.setBounds(12, 50, 91, 20);
        actualDurationLabel.setBounds(new Rectangle(12, 77, 91, 20));
        actualDurationLabel.setText("Actual");
        remainingLabel.setBounds(new Rectangle(12, 106, 91, 20));
        remainingLabel.setText("Remaining");
        completeDurationRateLabel.setBounds(new Rectangle(12, 130, 91, 20));
        completeDurationRateLabel.setText("% Complete");
        durationPlan.setBounds(new Rectangle(138, 22, 183, 20));
        timeLimitType.setModel(VMComboBox.toVMComboBox(DtoWbsActivity.ACTIVITY_TIME_LIMIT_TYPE));
        timeLimitType.setBounds(138, 51, 183, 20);
        durationActual.setBounds(new Rectangle(138, 79, 183, 20));
        durationRemain.setBounds(new Rectangle(138, 107, 183, 20));
        durationCompleteRate.setBounds(new Rectangle(138, 130, 183, 20));
        this.add(statusPanel);
        this.add(durationPanel);
        durationPanel.add(plannedDurationLabel);
        durationPanel.add(timeLimitTypeLabel);
        durationPanel.add(timeLimitType);
        durationPanel.add(actualDurationLabel);
        durationPanel.add(remainingLabel);
        durationPanel.add(completeDurationRateLabel);
        durationPanel.add(durationPlan);
        durationPanel.add(durationActual);
        durationPanel.add(durationRemain);
        durationPanel.add(durationCompleteRate);
        statusPanel.add(plannedStartLabel);
        statusPanel.add(plannedFinish);
        statusPanel.add(plannedFinishLabel);
        statusPanel.add(isActualFinish);
        statusPanel.add(actualFinishLabel);
        statusPanel.add(completeMethodLabel);
        statusPanel.add(completeRateLabel);
        statusPanel.add(isActualStart);
        statusPanel.add(actualStart);
        statusPanel.add(actualStartLabel);
        statusPanel.add(actualFinish);
        statusPanel.add(plannedStart);
        statusPanel.add(completeMethod);
        statusPanel.add(completeRate);

        plannedStart.setName("plannedStart");
        plannedFinish.setName("plannedFinish");
        isActualStart.setName("start");
        isActualFinish.setName("finish");
        actualStart.setName("actualStart");
        actualFinish.setName("actualFinish");
        completeMethod.setName("completeMethod");
        completeRate.setName("completeRate");
        durationPlan.setName("timeLimit");
        timeLimitType.setName("timeLimitType");
        durationActual.setName("durationActual");
        durationRemain.setName("durationRemain");
        durationCompleteRate.setName("durationComplete");

        plannedStart.setCanSelect(true);
        plannedFinish.setCanSelect(true);
        actualStart.setCanSelect(true);
        actualFinish.setCanSelect(true);

        completeRate.setMaxInputDecimalDigit(2);
        completeRate.setMaxInputIntegerDigit(3);

        setTabOrder();
        setEnterOrder();
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(plannedStart);
        compList.add(plannedFinish);
        compList.add(isActualStart);
        compList.add(actualStart);
        compList.add(isActualFinish);
        compList.add(actualFinish);
        compList.add(completeMethod);
        compList.add(completeRate);
        compList.add(durationPlan);
        compList.add(timeLimitType);
        compList.add(durationActual);
        compList.add(durationRemain);
        compList.add(durationCompleteRate);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(plannedStart);
        compList.add(plannedFinish);
        compList.add(isActualStart);
        compList.add(actualStart);
        compList.add(isActualFinish);
        compList.add(actualFinish);
        compList.add(completeMethod);
        compList.add(completeRate);
        compList.add(durationPlan);
        compList.add(timeLimitType);
        compList.add(durationActual);
        compList.add(durationRemain);
        compList.add(durationCompleteRate);
        comFORM.setEnterOrder(this, compList);
    }

    JPanel statusPanel = new JPanel();
    TitledBorder titledBorder1 = new TitledBorder("");
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
        new Color(178, 178, 178));
    Border border2 = new TitledBorder(border1, "Status");
    VWJLabel plannedStartLabel = new VWJLabel();
    VWJDate plannedStart = new VWJDate();
    VWJLabel plannedFinishLabel = new VWJLabel();
    VWJDate plannedFinish = new VWJDate();
    VWJDate actualStart = new VWJDate();
    VWJLabel actualStartLabel = new VWJLabel();
    VWJCheckBox isActualStart = new VWJCheckBox();
    VWJCheckBox isActualFinish = new VWJCheckBox();
    VWJLabel actualFinishLabel = new VWJLabel();
    VWJDate actualFinish = new VWJDate();
    VWJLabel completeMethodLabel = new VWJLabel();
    VWJComboBox completeMethod = new VWJComboBox();
    VWJLabel completeRateLabel = new VWJLabel();
    VWJReal completeRate = new VWJReal();
    JPanel durationPanel = new JPanel();
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
        new Color(178, 178, 178));
    Border border4 = new TitledBorder(border3, "Duration(Day)");
    VWJLabel plannedDurationLabel = new VWJLabel();
    VWJLabel actualDurationLabel = new VWJLabel();
    VWJLabel remainingLabel = new VWJLabel();
    VWJLabel completeDurationRateLabel = new VWJLabel();
    VWJReal durationPlan = new VWJReal();
    VWJReal durationActual = new VWJReal();
    VWJReal durationRemain = new VWJReal();
    VWJReal durationCompleteRate = new VWJReal();
    VWJComboBox timeLimitType=new VWJComboBox();

}

