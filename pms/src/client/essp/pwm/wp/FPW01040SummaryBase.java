package client.essp.pwm.wp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import client.essp.common.file.VwJFileButton;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJDispNumber;
import client.framework.view.vwcomp.VWJDispReal;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJLine;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTextArea;

public class FPW01040SummaryBase extends VWGeneralWorkArea {
    static final Color LABEL_COLOR = UIManager.getColor("Panel.background");
    VWJLabel lblWpTechtype = new VWJLabel();
    VWJLabel lbl1 = new VWJLabel();
    VWJLabel lblWpSize = new VWJLabel();
    VWJLabel lblWpWkhr = new VWJLabel();

    VWJDisp dspWpWkhrUnit = null;
    VWJDisp dspWpSizeUnit = new VWJDisp();
    VWJDisp dspWpProductivityUnit = new VWJDisp();
    VWJDisp dspWpDensityrateUnit = new VWJDisp();
    VWJDisp dspDefectrateUnit = new VWJDisp();
    VwJFileButton txtWpFilename = new VwJFileButton("PWM");

    VWJLabel lblUnit = new VWJLabel();
    VWJLabel lblPlan = new VWJLabel();
    VWJLabel lblAct = new VWJLabel();
    VWJLabel lblVari = new VWJLabel();
    VWJLabel lblWpDensityrate = new VWJLabel();
    VWJLabel lblWpProductivity = new VWJLabel();
    VWJLabel lblDensity = new VWJLabel();
    VWJLabel lblDefectrate = new VWJLabel();
    VWJLabel lblDensityPlan = new VWJLabel();
    VWJLabel lblWpRemark = new VWJLabel();
    VWJLabel lblDensityNumber = new VWJLabel();
    VWJLabel lblDefect = new VWJLabel();
    VWJLabel lblDefectPlan = new VWJLabel();
    VWJLabel lblDefectNumber = new VWJLabel();
    VWJLabel lblWpAttach = new VWJLabel();
    VWJLabel lblDensityVari = new VWJLabel();
    VWJLabel lblDefectVari = new VWJLabel();
    VWJLabel lblDefectRmv = new VWJLabel();
    VWJLabel lblDefectAct = new VWJLabel();
    VWJLabel lblDensityAct = new VWJLabel();
    VWJComboBox cmbWpTechtype = new VWJComboBox();
    VWJDispReal ralWpSizePlan = new VWJDispReal();
    VWJReal ralWpSizeAct = new VWJReal();
    VWJDispReal ralWpSizeVari = new VWJDispReal();
    VWJDispReal ralWpPlanWkhr = new VWJDispReal();
    VWJDispReal ralWpActWkhr = new VWJDispReal();
    VWJDispReal ralWpVariWkhr = new VWJDispReal();
    VWJDispReal ralWpProductivityPlan = new VWJDispReal();
    VWJDispReal ralWpProductivityAct = new VWJDispReal();
    VWJDispReal ralWpProductivityVari = new VWJDispReal();
    VWJDispReal ralWpDensityratePlan = new VWJDispReal();
    VWJDispReal ralWpDensityrateAct = new VWJDispReal();
    VWJDispReal ralWpDenstityrateVari = new VWJDispReal();
    VWJDispReal ralWpDefectratePlan = new VWJDispReal();
    VWJDispReal ralWpDefectrateAct = new VWJDispReal();
    VWJDispReal ralWpDefectrateVari = new VWJDispReal();
    VWJDispNumber dpnWpDensityPlan = new VWJDispNumber();
    VWJNumber numWpDensityAct = new VWJNumber();
    VWJDispNumber dpnWpDensityVari = new VWJDispNumber();
    VWJDispNumber dpnWpDefectPlan = new VWJDispNumber();
    VWJNumber numWpDefectAct = new VWJNumber();
    VWJNumber numWpDefectRmv = new VWJNumber();
    VWJDispNumber dpnWpDefectVari = new VWJDispNumber();
    VWJTextArea txaWpRemark = new VWJTextArea();
    //VWJDisp dspWpAttachUrl = new VWJDisp();
    //VWJButton btnFile = new VWJButton();
    VWJLine lin1 = new VWJLine();
    VWJLine lin2 = new VWJLine();
    VWJLine lin3 = new VWJLine();
    VWJLine lin4 = new VWJLine();
    VWJLine lin5 = new VWJLine();
    VWJLine lin6 = new VWJLine();
    VWJLine lin7 = new VWJLine();
    VWJLine lin8 = new VWJLine();
    VWJLine lin9 = new VWJLine();
    VWJLine lin10 = new VWJLine();
    VWJLine lin11 = new VWJLine();
    VWJLine lin12 = new VWJLine();
    VWJLine lin13 = new VWJLine();
    VWJLine lin1301 = new VWJLine();
    VWJLine lin14 = new VWJLine();
    VWJLine lin16 = new VWJLine();
    VWJLine lin17 = new VWJLine();
    VWJLine lin18 = new VWJLine();
    VWJLine lin19 = new VWJLine();
    VWJLine lin110 = new VWJLine();

    public FPW01040SummaryBase() {
        try {
            jbInit();
            setTabOrder();
            setEnterOrder();
            setUIComponentName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 490));
//        this.setMaximumSize(new Dimension(630, 440));
//        this.setMinimumSize(new Dimension(630, 440));

        //Tech. Type
        lblWpTechtype.setText(" Tech. Type");
        lblWpTechtype.setBackground(LABEL_COLOR);
        lblWpTechtype.setOpaque(true);
        lblWpTechtype.setBounds(new Rectangle(27, 21, 96, 28));
        cmbWpTechtype.setBounds(new Rectangle(125, 25, 299, 20));

        //title 1
        lbl1.setText("");
        lbl1.setBackground(LABEL_COLOR);
        lbl1.setOpaque(true);
        lbl1.setBounds(new Rectangle(27, 50, 96, 28));
        lblUnit.setBounds(new Rectangle(124, 50, 128, 28));
        lblUnit.setText(" Mea. Units");
        lblUnit.setBackground(LABEL_COLOR);
        lblUnit.setHorizontalAlignment(SwingConstants.CENTER);
        lblUnit.setOpaque(true);
        lblPlan.setBounds(new Rectangle(253, 50, 128, 28));
        lblPlan.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlan.setText("Planned");
        lblPlan.setBackground(LABEL_COLOR);
        lblPlan.setOpaque(true);

        lblAct.setHorizontalAlignment(SwingConstants.CENTER);
        lblAct.setText("Actual");
        lblAct.setBackground(LABEL_COLOR);
        lblAct.setOpaque(true);
        lblAct.setBounds(new Rectangle(382, 50, 128, 28));
        lblVari.setBounds(new Rectangle(511, 50, 144, 28));
        lblVari.setHorizontalAlignment(SwingConstants.CENTER);
        lblVari.setText("Variance");
        lblVari.setBackground(LABEL_COLOR);
        lblVari.setOpaque(true);

        //size
        lblWpSize.setBounds(new Rectangle(27, 79, 96, 28));
        lblWpSize.setText(" Size");
        lblWpSize.setBackground(LABEL_COLOR);
        lblWpSize.setOpaque(true);

        //        dspWpSizeUnit.setText("KLOC");
        dspWpSizeUnit.setText(" abc");
        dspWpSizeUnit.setBounds(new Rectangle(126, 83, 125, 20));

        ralWpSizePlan.setBounds(new Rectangle(255, 83, 124, 20));

        ralWpSizeAct.setBounds(new Rectangle(384, 83, 124, 20));
        ralWpSizeAct.setMaxInputIntegerDigit(8);
        ralWpSizeVari.setBounds(new Rectangle(513, 83, 140, 20));
        ralWpSizeVari.setCanNegative(true);

        //work hours
        lblWpWkhr.setBounds(new Rectangle(27, 108, 96, 28));
        lblWpWkhr.setText(" Work Hours");
        lblWpWkhr.setBackground(LABEL_COLOR);
        lblWpWkhr.setOpaque(true);
        dspWpWkhrUnit = new VWJDisp() {
            //防止VWUtil.clearUI将值清空，这个display控件相当于一个lable
            public void setUICValue(Object value) {
            }
        };
        dspWpWkhrUnit.setText("Hour");
        dspWpWkhrUnit.setBounds(new Rectangle(126, 111, 125, 20));

        ralWpPlanWkhr.setBounds(new Rectangle(255, 111, 124, 20));

        ralWpActWkhr.setBounds(new Rectangle(384, 111, 124, 20));

        ralWpVariWkhr.setBounds(new Rectangle(513, 111, 140, 20));
        ralWpVariWkhr.setCanNegative(true);

        //productivity
        lblWpProductivity.setBounds(new Rectangle(27, 137, 96, 28));
        lblWpProductivity.setText(" Productivity");
        lblWpProductivity.setBackground(LABEL_COLOR);
        lblWpProductivity.setOpaque(true);
        dspWpProductivityUnit.setBounds(new Rectangle(126, 141, 125, 20));

        dspWpProductivityUnit.setText(" acb");
        ralWpProductivityPlan.setBounds(new Rectangle(255, 141, 124, 20));

        ralWpProductivityAct.setBounds(new Rectangle(384, 141, 124, 20));

        ralWpProductivityVari.setBounds(new Rectangle(513, 141, 140, 20));
        ralWpProductivityVari.setCanNegative(true);

        //density rate
        lblWpDensityrate.setText(" Density");
        lblWpDensityrate.setBackground(LABEL_COLOR);
        lblWpDensityrate.setOpaque(true);
        lblWpDensityrate.setBounds(new Rectangle(27, 166, 96, 28));
        dspWpDensityrateUnit.setBounds(new Rectangle(126, 170, 125, 20));
        dspWpDensityrateUnit.setText(" acb");
        ralWpDensityratePlan.setBounds(new Rectangle(255, 170, 124, 20));

        ralWpDensityrateAct.setBounds(new Rectangle(384, 170, 124, 20));

        ralWpDenstityrateVari.setBounds(new Rectangle(513, 170, 140, 20));


        //defect rate
        lblDefectrate.setText(" Defect Rate");
        lblDefectrate.setBackground(LABEL_COLOR);
        lblDefectrate.setOpaque(true);
        lblDefectrate.setBounds(new Rectangle(27, 195, 96, 28));
        dspDefectrateUnit.setBounds(new Rectangle(126, 199, 125, 20));
        dspDefectrateUnit.setText(" acb");
        ralWpDefectratePlan.setBounds(new Rectangle(255, 199, 124, 20));

        ralWpDefectrateAct.setBounds(new Rectangle(384, 199, 124, 20));

        ralWpDefectrateVari.setBounds(new Rectangle(513, 199, 140, 20));
        ralWpDefectrateVari.setCanNegative(true);

        //density title
        lblDensity.setText(" Density");
        lblDensity.setBackground(LABEL_COLOR);
        lblDensity.setOpaque(true);
        lblDensity.setBounds(new Rectangle(27, 224, 96, 28));
        lblDensityPlan.setBounds(new Rectangle(124, 224, 128, 28));
        lblDensityPlan.setHorizontalAlignment(SwingConstants.CENTER);
        lblDensityPlan.setText("Planned");
        lblDensityPlan.setBackground(LABEL_COLOR);
        lblDensityPlan.setOpaque(true);
        lblDensityAct.setHorizontalAlignment(SwingConstants.CENTER);
        lblDensityAct.setText("Actual");
        lblDensityAct.setBackground(LABEL_COLOR);
        lblDensityAct.setOpaque(true);
        lblDensityAct.setBounds(new Rectangle(253, 224, 257, 28));
        lblDensityVari.setHorizontalAlignment(SwingConstants.CENTER);
        lblDensityVari.setText("Variance");
        lblDensityVari.setBackground(LABEL_COLOR);
        lblDensityVari.setOpaque(true);
        lblDensityVari.setBounds(new Rectangle(511, 224, 144, 28));

        //density
        lblDensityNumber.setText(" Numbers");
        lblDensityNumber.setBackground(LABEL_COLOR);
        lblDensityNumber.setOpaque(true);
        lblDensityNumber.setBounds(new Rectangle(27, 253, 96, 28));
        dpnWpDensityPlan.setBounds(new Rectangle(126, 257, 124, 20));
        dpnWpDensityPlan.setMaxInputIntegerDigit(8);
        numWpDensityAct.setBounds(new Rectangle(255, 257, 253, 20));
        numWpDensityAct.setMaxInputIntegerDigit(8);
        dpnWpDensityVari.setBounds(new Rectangle(513, 258, 140, 20));
        dpnWpDensityVari.setCanNegative(true);

        //defect title
        lblDefect.setBounds(new Rectangle(27, 282, 96, 28));
        lblDefect.setText(" Defects");
        lblDefect.setBackground(LABEL_COLOR);
        lblDefect.setOpaque(true);
        lblDefectPlan.setHorizontalAlignment(SwingConstants.CENTER);
        lblDefectPlan.setText("Planned");
        lblDefectPlan.setBackground(LABEL_COLOR);
        lblDefectPlan.setOpaque(true);
        lblDefectPlan.setBounds(new Rectangle(124, 282, 128, 28));
        lblDefectAct.setHorizontalAlignment(SwingConstants.CENTER);
        lblDefectAct.setText("Actual");
        lblDefectAct.setBackground(LABEL_COLOR);
        lblDefectAct.setOpaque(true);
        lblDefectAct.setBounds(new Rectangle(253, 282, 127, 28));
        lblDefectRmv.setBounds(new Rectangle(381, 282, 129, 28));
        lblDefectRmv.setHorizontalAlignment(SwingConstants.CENTER);
        lblDefectRmv.setText(" Removed");
        lblDefectRmv.setBackground(LABEL_COLOR);
        lblDefectRmv.setOpaque(true);
        lblDefectVari.setBounds(new Rectangle(511, 282, 144, 28));
        lblDefectVari.setHorizontalAlignment(SwingConstants.CENTER);
        lblDefectVari.setText("Variance");
        lblDefectVari.setBackground(LABEL_COLOR);
        lblDefectVari.setOpaque(true);

        //defects
        lblDefectNumber.setBounds(new Rectangle(27, 311, 96, 28));
        lblDefectNumber.setText(" Numbers");
        lblDefectNumber.setBackground(LABEL_COLOR);
        lblDefectNumber.setOpaque(true);
        dpnWpDefectPlan.setBounds(new Rectangle(126, 315, 124, 20));
        dpnWpDefectPlan.setMaxInputIntegerDigit(8);
        numWpDefectAct.setBounds(new Rectangle(255, 315, 124, 20));
        numWpDefectAct.setMaxInputIntegerDigit(8);
        numWpDefectRmv.setBounds(new Rectangle(383, 315, 124, 20));
        numWpDefectRmv.setMaxInputIntegerDigit(8);
        dpnWpDefectVari.setBounds(new Rectangle(513, 315, 140, 20));
        dpnWpDefectVari.setCanNegative(true);

        //remark
        lblWpRemark.setBounds(new Rectangle(27, 340, 96, 84));
        lblWpRemark.setText(" Remark");
        lblWpRemark.setBackground(LABEL_COLOR);
        lblWpRemark.setOpaque(true);
        txaWpRemark.setBounds(new Rectangle(126, 344, 528, 78));

        //ATTACH
        lblWpAttach.setText(" Attachment");
        lblWpAttach.setBackground(LABEL_COLOR);
        lblWpAttach.setOpaque(true);
        lblWpAttach.setBounds(new Rectangle(28, 447, 94, 28));

        //txtWpFilename.setBackground(LABEL_COLOR);
        //txtWpFilename.setOpaque(true);
        txtWpFilename.setBounds(new Rectangle(125, 451, 525, 20));
        //dspWpAttachUrl.setHorizontalAlignment(SwingConstants.LEFT);
        //dspWpAttachUrl.setBounds(new Rectangle(255, 451, 298, 20));
        //btnFile.setBounds(new Rectangle(559, 451, 90, 20));
        //btnFile.setText(" Open file...");

        //lines
        lin1.setBounds(new Rectangle(26, 20, 630, 30));
        lin1.setBorder(BorderFactory.createLineBorder(Color.black));
        lin1.setForeground(Color.black);
        lin1.setBackground(Color.gray);
        lin2.setBackground(Color.gray);
        lin2.setForeground(Color.black);
        lin2.setBorder(BorderFactory.createLineBorder(Color.black));
        lin2.setBounds(new Rectangle(26, 49, 630, 30));
        lin3.setBackground(Color.gray);
        lin3.setForeground(Color.black);
        lin3.setBorder(BorderFactory.createLineBorder(Color.black));
        lin3.setBounds(new Rectangle(26, 78, 630, 30));
        lin4.setBackground(Color.gray);
        lin4.setForeground(Color.black);
        lin4.setBorder(BorderFactory.createLineBorder(Color.black));
        lin4.setBounds(new Rectangle(26, 107, 630, 30));
        lin5.setBounds(new Rectangle(26, 136, 630, 30));
        lin5.setBorder(BorderFactory.createLineBorder(Color.black));
        lin5.setForeground(Color.black);
        lin5.setBackground(Color.gray);
        lin6.setBackground(Color.gray);
        lin6.setForeground(Color.black);
        lin6.setBorder(BorderFactory.createLineBorder(Color.black));
        lin6.setBounds(new Rectangle(26, 165, 630, 30));
        lin7.setBounds(new Rectangle(26, 194, 630, 30));
        lin7.setBorder(BorderFactory.createLineBorder(Color.black));
        lin7.setForeground(Color.black);
        lin7.setBackground(Color.gray);
        lin8.setBackground(Color.gray);
        lin8.setForeground(Color.black);
        lin8.setBorder(BorderFactory.createLineBorder(Color.black));
        lin8.setBounds(new Rectangle(26, 223, 630, 30));
        lin9.setBounds(new Rectangle(26, 252, 630, 30));
        lin9.setBorder(BorderFactory.createLineBorder(Color.black));
        lin9.setForeground(Color.black);
        lin9.setBackground(Color.gray);
        lin10.setBackground(Color.gray);
        lin10.setForeground(Color.black);
        lin10.setBorder(BorderFactory.createLineBorder(Color.black));
        lin10.setBounds(new Rectangle(26, 281, 630, 30));
        lin11.setBounds(new Rectangle(26, 310, 630, 30));
        lin11.setBorder(BorderFactory.createLineBorder(Color.black));
        lin11.setForeground(Color.black);
        lin11.setBackground(Color.gray);
        lin12.setBackground(Color.gray);
        lin12.setForeground(Color.black);
        lin12.setBorder(BorderFactory.createLineBorder(Color.black));

        //lin12.setBounds(new Rectangle(26, 339, 496, 30));
        lin12.setBounds(new Rectangle(123, 339, 533, 86));
        lin13.setBounds(new Rectangle(27, 446, 630, 30));
        lin13.setBorder(BorderFactory.createLineBorder(Color.black));
        lin13.setForeground(Color.black);
        lin13.setBackground(Color.gray);

        lin1301.setBounds(new Rectangle(27, 446, 96, 30));
        lin1301.setBorder(BorderFactory.createLineBorder(Color.black));
        lin1301.setForeground(Color.black);
        lin1301.setBackground(Color.gray);

        lin14.setBackground(Color.gray);
        lin14.setForeground(Color.black);
        lin14.setBorder(BorderFactory.createLineBorder(Color.black));
        lin14.setBounds(new Rectangle(26, 20, 98, 405));

        lin16.setBounds(new Rectangle(123, 49, 130, 291));
        lin16.setBorder(BorderFactory.createLineBorder(Color.black));
        lin16.setForeground(Color.black);
        lin16.setBackground(Color.gray);
        lin17.setBackground(Color.gray);
        lin17.setForeground(Color.black);
        lin17.setBorder(BorderFactory.createLineBorder(Color.black));
        lin17.setBounds(new Rectangle(252, 49, 130, 175));
        lin18.setBounds(new Rectangle(381, 49, 130, 175));
        lin18.setBorder(BorderFactory.createLineBorder(Color.black));
        lin18.setForeground(Color.black);
        lin18.setBackground(Color.gray);
        lin19.setBounds(new Rectangle(510, 49, 146, 291));
        lin19.setBorder(BorderFactory.createLineBorder(Color.black));
        lin19.setForeground(Color.black);
        lin19.setBackground(Color.gray);
        lin110.setBounds(new Rectangle(380, 281, 131, 59));
        lin110.setBorder(BorderFactory.createLineBorder(Color.black));
        lin110.setForeground(Color.black);
        lin110.setBackground(Color.gray);

        //dspWpWkhrUnit.setBorder(null);
        //dspWpSizeUnit.setBorder(null);
        //dspWpProductivityUnit.setBorder(null);
        //dspWpDensityrateUnit.setBorder(null);
        //dspDefectrateUnit.setBorder(null);
        //txtWpFilename.setBorder(null);
        //txtWpFilename.setBackground(this.LABEL_COLOR);

        this.add(lblDensityAct, null);
        this.add(dpnWpDensityPlan, null);

        //this.add(txaWpRemark, null);
        this.add(txaWpRemark, null);
        this.add(cmbWpTechtype, null);
        this.add(lblWpTechtype, null);
        this.add(lbl1, null);
        this.add(dspWpSizeUnit, null);
        this.add(lblWpSize, null);
        this.add(lin1, null);
        this.add(dpnWpDensityVari, null);
        this.add(dpnWpDefectVari, null);
        this.add(numWpDensityAct, null);
        this.add(ralWpActWkhr, null);
        this.add(ralWpProductivityAct, null);
        this.add(ralWpDensityrateAct, null);
        this.add(ralWpDefectrateAct, null);
        this.add(ralWpPlanWkhr, null);
        this.add(ralWpDensityratePlan, null);
        this.add(ralWpDefectratePlan, null);
        this.add(lblDensityVari, null);
        this.add(lblDefectVari, null);
        this.add(lblDefectRmv, null);
        this.add(lin110, null);
        this.add(lblDefectAct, null);
        this.add(numWpDefectAct, null);
        this.add(dpnWpDefectPlan, null);
        this.add(lblPlan, null);
        this.add(lin2, null);
        this.add(lblAct, null);
        this.add(lblVari, null);
        this.add(lblUnit, null);
        this.add(lblWpWkhr, null);
        this.add(lblWpProductivity, null);
        this.add(lblWpDensityrate, null);
        this.add(lblDefectrate, null);
        this.add(lin11, null);
        this.add(lblDensity, null);
        this.add(lblDefect, null);
        this.add(lblDefectNumber, null);
        this.add(lblDensityNumber, null);
        this.add(lin9, null);
        this.add(dspWpWkhrUnit, null);
        this.add(dspWpProductivityUnit, null);
        this.add(dspWpDensityrateUnit, null);
        this.add(dspDefectrateUnit, null);
        this.add(lblDensityPlan, null);
        this.add(lblDefectPlan, null);
        this.add(lin10, null);
        this.add(lin16, null);
        this.add(ralWpProductivityPlan, null);
        this.add(lin5, null);
        this.add(ralWpSizePlan, null);
        this.add(lin17, null);
        this.add(ralWpSizeAct, null);
        this.add(lin18, null);
        this.add(numWpDefectRmv, null);
        this.add(ralWpSizeVari, null);
        this.add(lin3, null);
        this.add(ralWpDenstityrateVari, null);
        this.add(ralWpProductivityVari, null);
        this.add(lin4, null);
        this.add(ralWpVariWkhr, null);
        this.add(lin6, null);
        this.add(lin7, null);
        this.add(lin19, null);
        this.add(ralWpDefectrateVari, null);
        this.add(lin8, null);
        this.add(lin12, null);
        this.add(lblWpAttach, null);
        this.add(lblWpRemark, null);
        this.add(lin14, null);
        this.add(lin1301, null);
        //this.add(dspWpAttachUrl, null);
        //this.add(btnFile, null);
        this.add(lin13, null);
        this.add(txtWpFilename, null);
    }

    private void setTabOrder() {
        List tabList = new ArrayList();
        tabList.add(this.cmbWpTechtype);
        tabList.add(this.ralWpSizeAct);
        tabList.add(this.numWpDensityAct);
        tabList.add(this.numWpDefectAct);
        tabList.add(this.numWpDefectRmv);
        tabList.add(this.txaWpRemark);
        comFORM.setTABOrder(this, tabList);
    }

    private void setEnterOrder() {
        //set enter order---------------------------------------
        List enterList = new ArrayList();
        enterList.add(this.cmbWpTechtype);
        enterList.add(this.ralWpSizeAct);
        enterList.add(this.numWpDensityAct);
        enterList.add(this.numWpDefectAct);
        enterList.add(this.numWpDefectRmv);
        enterList.add(this.txaWpRemark);
        comFORM.setEnterOrder(this, enterList);
    }

    private void setUIComponentName() {
        dspWpSizeUnit.setName("wpSizeUnit");
        dspWpProductivityUnit.setName("wpProductivityUnit");
        dspWpDensityrateUnit.setName("wpDensityrateUnit");
        dspDefectrateUnit.setName("wpDefectrateUnit");
        txtWpFilename.setName("wpFilename");

        cmbWpTechtype.setName("wpTechtype");
        ralWpSizePlan.setName("wpSizePlan");
        ralWpSizeAct.setName("wpSizeAct");
        ralWpPlanWkhr.setName("wpPlanWkhr");
        ralWpActWkhr.setName("wpActWkhr");

        ralWpProductivityPlan.setName("wpProductivityPlan");
        ralWpProductivityAct.setName("wpProductivityAct");

        ralWpDensityratePlan.setName("wpDensityratePlan");
        ralWpDensityrateAct.setName("wpDensityrateAct");

        ralWpDefectratePlan.setName("wpDefectratePlan");
        ralWpDefectrateAct.setName("wpDefectrateAct");

        dpnWpDensityPlan.setName("wpDensityPlan");
        numWpDensityAct.setName("wpDensityAct");

        dpnWpDefectPlan.setName("wpDefectPlan");
        numWpDefectAct.setName("wpDefectAct");
        numWpDefectRmv.setName("wpDefectRmv");

        txaWpRemark.setName("wpRemark");
        //dspWpAttachUrl.setName("wpAttachUrl");
    }

    public static void main(String[] args) {
        FPW01040SummaryBase workArea = new FPW01040SummaryBase();

        TestPanelParam.show(workArea);
        workArea.refreshWorkArea();
    }

}
