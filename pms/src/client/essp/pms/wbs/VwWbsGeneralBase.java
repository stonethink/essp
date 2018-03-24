package client.essp.pms.wbs;

import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTextArea;
import javax.swing.*;
import client.framework.view.vwcomp.VWJDate;
import java.awt.Rectangle;
import java.awt.*;
import client.essp.common.view.VWGeneralWorkArea;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import com.wits.util.TestPanel;
import client.framework.model.VMComboBox;
import c2s.essp.pms.wbs.DtoWbsActivity;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import client.essp.pms.activity.VwWbsDisp;

public class VwWbsGeneralBase extends VWGeneralWorkArea {
    public final static String[] completeRateCompMethod = {DtoWbsActivity.
        WBS_COMPLETE_BY_ACTIVITY, DtoWbsActivity.WBS_COMPLETE_BY_CHECKPOINT,
        DtoWbsActivity.WBS_COMPLETE_BY_MANUAL};
    public final static String[] completeRateCompMethodTitle = {
        "Activity Percent", "WBS CheckPoint Percent", "Custom Percent"};

    protected VWJLabel nameLabel = null;
    protected VWJText name = null;
    protected VWJLabel codeLabel = null;
    protected VWJText code = null;
    protected VWJLabel managerLabel = null;
    protected VWJComboBox manager = null;
    protected VWJLabel weightLabel = null;
    protected VWJReal weight = null;
    protected VWJLabel briefLabel = null;
    protected VWJLabel plannedStartLabel = null;
    protected VWJLabel plannedFinishLabel = null;
//    protected VWJLabel anticipatedStartLabel = null;
    protected VWJDate plannedStart = null;
    protected VWJDate plannedFinish = null;
//    protected VWJDate anticipatedStart = null;
//    protected VWJLabel anticipatedFinsihLabel = null;
    //protected VWJLabel actualStartLabel = null;
    //protected VWJLabel actualFinishLabel = null;
//    protected VWJDate anticipatedFinish = null;
    //protected VWJDate actualStart = null;
    //protected VWJDate actualFinish = null;
    protected VWJTextArea brief = null;
    //protected JScrollPane briefScrollPanel = null;

    //加入完工比例计算方法
    protected VWJLabel completeLabel = new VWJLabel();
    protected VWJComboBox completeMethod = new VWJComboBox();
    protected VWJReal completeManualInput = new VWJReal();
    protected VWJLabel completeLabel_percentSingle = new VWJLabel();

    private String wbsDisp = null;

    public VwWbsGeneralBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public VwWbsGeneralBase(String wbsDisp) {
        try {
            this.wbsDisp = wbsDisp;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VwWbsGeneralBase vwwbsgeneralbase = new VwWbsGeneralBase();
        TestPanel.show(vwwbsgeneralbase);
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
        plannedStartLabel = new VWJLabel();
        plannedFinishLabel = new VWJLabel();
//        anticipatedStartLabel = new VWJLabel();
        plannedStart = new VWJDate();
        plannedFinish = new VWJDate();
//        anticipatedStart = new VWJDate();
//        anticipatedFinsihLabel = new VWJLabel();
        //actualStartLabel = new VWJLabel();
        //actualFinishLabel = new VWJLabel();
//        anticipatedFinish = new VWJDate();
        //actualStart = new VWJDate();
        //actualFinish = new VWJDate();
        brief = new VWJTextArea();
        //briefScrollPanel = new JScrollPane();
        //第一行1列
        nameLabel.setBounds(new Rectangle(10, 22, 120, 19));
        nameLabel.setText("WBS Name");
        //第一行2列
        name.setBounds(new Rectangle(141, 22, 183, 20));

        //vWJText1.setBorder(BorderFactory.createLineBorder(color));
        //第一行3列
        codeLabel.setBounds(new Rectangle(357, 22, 120, 19));
        codeLabel.setText("WBS Code");
        //第一行4列
        code.setBounds(new Rectangle(498, 22, 183, 20));

        //vWJText2.setBorder(BorderFactory.createLineBorder(color));
        ////////////////////////////////////////////////////////////////////
        //第二行1列
        managerLabel.setBounds(new Rectangle(10, 52, 120, 19));
        managerLabel.setText("Manager");
        //第二行2列
        manager.setBounds(new Rectangle(141, 52, 183, 20));

        //vWJComboBox1.setBorder(BorderFactory.createLineBorder(color));
        //第二行3列
        weightLabel.setBounds(new Rectangle(357, 52, 120, 19));
        weightLabel.setText("Weight");
        //第二行4列
        weight.setBounds(new Rectangle(498, 52, 183, 20));

        //vWJReal1.setBorder(BorderFactory.createLineBorder(color));
        //第三行1列
        briefLabel.setBounds(new Rectangle(10, 140, 120, 19));
        briefLabel.setText("WBS Brief");
        //briefScrollPanel.setBounds(new Rectangle(141, 82, 540, 70));
        //jScrollPane1.setBorder(BorderFactory.createLineBorder(color));
        plannedStartLabel.setBounds(new Rectangle(10, 81, 120, 19));
        plannedStart.setBounds(new Rectangle(141, 81, 183, 20));

        plannedFinishLabel.setBounds(new Rectangle(357, 81, 130, 19));
        plannedFinish.setBounds(new Rectangle(498, 81, 183, 20));

//        anticipatedStartLabel.setBounds(new Rectangle(10, 81, 120, 19));
        //actualStartLabel.setBounds(new Rectangle(9, 140, 120, 19));
//        anticipatedFinsihLabel.setBounds(new Rectangle(357, 81, 130, 19));
        //actualStart.setBounds(new Rectangle(141, 140, 183, 20));

//        anticipatedStart.setBounds(new Rectangle(141, 81, 183, 20));

        //actualFinish.setBounds(new Rectangle(498, 140, 183, 20));
//        anticipatedFinish.setBounds(new Rectangle(498, 81, 183, 20));
        //actualFinishLabel.setBounds(new Rectangle(357, 140, 91, 19));

        name.setName("name");
        code.setName("code");
        manager.setName("manager");
        weight.setName("weight");
//        anticipatedStart.setName("anticipatedStart");
//        anticipatedFinish.setName("anticipatedFinish");
        plannedStart.setName("plannedStart");
        plannedFinish.setName("plannedFinish");
        //actualStart.setName("actualStart");
        //actualFinish.setName("actualFinish");

        name.setInput2ByteOk(true);
        name.setMaxByteLength(50);
        code.setInput2ByteOk(false);
        code.setMaxByteLength(50);

        weight.setMaxInputDecimalDigit(2);
        weight.setMaxInputIntegerDigit(3);

//        anticipatedStart.setCanSelect(true);
//        anticipatedFinish.setCanSelect(true);
        plannedStart.setCanSelect(true);
        plannedFinish.setCanSelect(true);
        //actualStart.setCanSelect(true);
        //actualFinish.setCanSelect(true);

        brief.setName("brief");

        this.add(nameLabel);
        this.add(name);
        this.add(codeLabel);
        this.add(code);
        this.add(managerLabel);
        this.add(manager);
        this.add(weightLabel);
        this.add(weight); //this.add(briefScrollPanel);
        this.add(plannedStartLabel);
        this.add(plannedFinishLabel);
        this.add(plannedFinish);
//        this.add(anticipatedStartLabel);
//        this.add(anticipatedFinsihLabel);
//        this.add(anticipatedFinish);
//        this.add(anticipatedStart);
        this.add(plannedStart);
        //this.add(actualStart);
        //this.add(actualStartLabel);
        //this.add(actualFinishLabel);
        //this.add(actualFinish);
        this.add(briefLabel);
        brief.setBounds(new Rectangle(141, 140, 541, 70));
        this.add(brief);
        //actualStartLabel.setText("Actual Start");
//        anticipatedStartLabel.setText("Anticipated Start");
        plannedStartLabel.setText("Planned Start");
        plannedFinishLabel.setText("Planned Finish");
        //actualFinishLabel.setText("Actual Finish");
//        anticipatedFinsihLabel.setText("Anticipated Finsih");
        completeLabel.setText("%Calculate Method");
        completeLabel.setToolTipText("Complete Rate Calculate Method");
        completeLabel.setBounds(new Rectangle(10, 111, 120, 20));
        this.add(completeLabel);

        completeManualInput.setBounds(325, 111, 100, 20);
        completeManualInput.setName("completeRate");
        this.add(completeManualInput);
        completeLabel_percentSingle.setText("%");
        completeLabel_percentSingle.setBounds(425, 111, 10, 20);
        this.add(completeLabel_percentSingle);

        completeMethod.setBounds(141, 111, 183, 20);
        VMComboBox completeMethodModel = VMComboBox.toVMComboBox(this.
            completeRateCompMethodTitle, this.
            completeRateCompMethod);
        completeMethod.setModel(completeMethodModel);
        completeMethod.setName("completeMethod");
        completeMethod.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                Object item = completeMethod.getUICValue();
                if (completeRateCompMethod[2].equals(item) && (wbsDisp == null ||
                    !wbsDisp.equals(VwWbsDisp.IS_WBS_DISP))) {
                    completeManualInput.setVisible(true);
                    completeLabel_percentSingle.setVisible(true);
                } else {
                    completeManualInput.setVisible(false);
                    completeLabel_percentSingle.setVisible(false);
                }
            } //Method end
        });
        this.add(completeMethod);

        setTabOrder();

        setEnterOrder();
    }


    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(name);
        compList.add(code);
        compList.add(manager);
        compList.add(weight);
//        compList.add(anticipatedStart);
//        compList.add(anticipatedFinish);
        compList.add(plannedStart);
        compList.add(plannedFinish);
        compList.add(completeMethod);
        compList.add(brief);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(name);
        compList.add(code);
        compList.add(manager);
        compList.add(weight);
//        compList.add(anticipatedStart);
//        compList.add(anticipatedFinish);
        compList.add(plannedStart);
        compList.add(plannedFinish);
        compList.add(completeMethod);
        compList.add(brief);
        comFORM.setEnterOrder(this, compList);
    }
}
