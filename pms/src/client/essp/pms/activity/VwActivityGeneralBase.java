package client.essp.pms.activity;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJTextArea;
import java.awt.Rectangle;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.model.VMComboBox;
import c2s.essp.pms.activity.DtoMilestone;


public class VwActivityGeneralBase extends VWGeneralWorkArea {
    protected VWJLabel nameLabel = null;
    protected VWJText name = null;
    protected VWJLabel codeLabel = null;
    protected VWJText code = null;
    protected VWJLabel managerLabel = null;
    protected VWJComboBox manager = null;
    protected VWJLabel weightLabel = null;
    protected VWJReal weight = null;
    protected VWJLabel briefLabel = null;
    protected VWJTextArea brief = null;
    //protected JScrollPane briefScrollPanel = null;
    VWJLabel lbIsMilestone = new VWJLabel();
    VWJLabel lbType = new VWJLabel();
    VWJLabel lbReachCon = new VWJLabel();
    VWJLabel lbAnticipatedStart = new VWJLabel();
    VWJLabel lbAnticipatedFinish = new VWJLabel();
    VWJCheckBox inputIsMilestone = new VWJCheckBox();
    VWJComboBox inputType = new VWJComboBox();
    VWJText inputReachCondition = new VWJText();
    VWJDate inputAnticipatedStart = new VWJDate();
    VWJDate inputAnticipatedFinish = new VWJDate();


    public VwActivityGeneralBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        //this.setBackground(new Color(239,238,228));
        this.setLayout(null);
        nameLabel = new VWJLabel();
        name = new VWJText();
        codeLabel = new VWJLabel();
        code = new VWJText();
        managerLabel = new VWJLabel();
        manager = new VWJComboBox();
        weightLabel = new VWJLabel();
        weight = new VWJReal();
        briefLabel = new VWJLabel();
        brief = new VWJTextArea();
        //briefScrollPanel = new JScrollPane();
        //第一行1列
        nameLabel.setBounds(new Rectangle(10, 10, 120, 20));
        nameLabel.setText("Activity Name");
        //第一行2列
        name.setBounds(new Rectangle(141, 10, 183, 20));

        //vWJText1.setBorder(BorderFactory.createLineBorder(color));
        //第一行3列
        codeLabel.setBounds(new Rectangle(357, 10, 120, 20));
        codeLabel.setText("Activity ID");
        //第一行4列
        code.setBounds(new Rectangle(498, 10, 183, 20));

        //vWJText2.setBorder(BorderFactory.createLineBorder(color));
        ////////////////////////////////////////////////////////////////////
        //第二行1列
        managerLabel.setBounds(new Rectangle(10, 40, 120, 20));
        managerLabel.setText("Manager");
        //第二行2列
        manager.setBounds(new Rectangle(141, 40, 183, 20));

        //vWJComboBox1.setBorder(BorderFactory.createLineBorder(color));
        //第二行3列
        weightLabel.setBounds(new Rectangle(357, 40, 120, 20));
        weightLabel.setText("Weight");
        //第二行4列
        weight.setBounds(new Rectangle(498, 40, 183, 20));

        //vWJReal1.setBorder(BorderFactory.createLineBorder(color));
        //第三行1列
        briefLabel.setBounds(new Rectangle(10, 70, 120, 20));
        briefLabel.setText("Activity Brief");
        name.setName("name");
        code.setName("code");
        manager.setName("manager");
        weight.setName("weight");
        weight.setMaxInputDecimalDigit(2);
        weight.setMaxInputIntegerDigit(3);
        brief.setName("brief");

        name.setMaxByteLength(50);
        name.setInput2ByteOk(true);
        code.setMaxByteLength(50);
        code.setInput2ByteOk(false);
        this.add(name);
        this.add(codeLabel);
        this.add(code);
        this.add(managerLabel);
        this.add(manager);
        this.add(weightLabel);
        this.add(weight); //this.add(briefScrollPanel);
        this.add(briefLabel);
        brief.setBounds(new Rectangle(141, 70, 540, 70));
        this.add(brief);

        lbIsMilestone.setText("Is Milestone");
        lbIsMilestone.setBounds(new Rectangle(10, 150, 80, 20));

        lbType.setText("Type");
        lbType.setBounds(new Rectangle(356, 150, 112, 25));

        lbAnticipatedStart.setText("Anticipated Start");
        lbAnticipatedStart.setBounds(new Rectangle(10, 180, 110, 20));

        lbAnticipatedFinish.setText("Anticipated Finish");
        lbAnticipatedFinish.setBounds(new Rectangle(355, 180, 110, 20));

        lbReachCon.setText("Reach Condition");
        lbReachCon.setBounds(new Rectangle(10, 210, 100, 20));

        inputIsMilestone.setBounds(new Rectangle(141, 150, 21, 20));

        inputType.setBounds(new Rectangle(497, 150, 184, 20));
        inputType.setModel(VMComboBox.toVMComboBox(DtoMilestone.
            TYPE_NAMES, DtoMilestone.TYPE_VALUES));

        inputAnticipatedStart.setBounds(new Rectangle(141, 180, 185, 20));
        inputAnticipatedStart.setCanSelect(true);

        inputAnticipatedFinish.setBounds(new Rectangle(497, 180, 185, 20));
        inputAnticipatedFinish.setCanSelect(true);

        inputReachCondition.setBounds(new Rectangle(141, 210, 540, 20));
        inputReachCondition.setMaxByteLength(500);
        this.add(lbIsMilestone);
        this.add(nameLabel);
        this.add(inputType);
        this.add(lbReachCon);
        this.add(inputReachCondition);
        this.add(inputAnticipatedStart);
        this.add(lbAnticipatedFinish);
        this.add(inputAnticipatedFinish);
        this.add(lbAnticipatedStart);
        this.add(lbType);
        this.add(inputIsMilestone);
        inputIsMilestone.setName("milestone");
        inputType.setName("milestoneType");
        inputReachCondition.setName("reachCondition");
        inputAnticipatedStart.setName("anticipatedStart");
        inputAnticipatedFinish.setName("anticipatedFinish");

        setTabOrder();
        setEnterOrder();
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(name);
        compList.add(code);
        compList.add(manager);
        compList.add(weight);
        compList.add(brief);
        compList.add(inputIsMilestone);
        compList.add(inputType);
        compList.add(inputAnticipatedStart);
        compList.add(inputAnticipatedFinish);
        compList.add(inputReachCondition);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(name);
        compList.add(code);
        compList.add(manager);
        compList.add(weight);
        compList.add(brief);
        compList.add(inputIsMilestone);
        compList.add(inputType);
        compList.add(inputAnticipatedStart);
        compList.add(inputAnticipatedFinish);
        compList.add(inputReachCondition);
        comFORM.setEnterOrder(this, compList);
    }

    protected void refreshMilestoneProperty(boolean flag) {
        inputType.setEnabled(flag);
        inputReachCondition.setEnabled(flag);
        inputAnticipatedStart.setEnabled(flag);
        inputAnticipatedFinish.setEnabled(flag);
    }


    public static void main(String[] args) {
        VwActivityGeneralBase vwactivitygeneralbase = new VwActivityGeneralBase();
    }
}
