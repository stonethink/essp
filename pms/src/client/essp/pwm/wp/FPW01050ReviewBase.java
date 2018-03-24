package client.essp.pwm.wp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.common.Constant;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJDispDate;
import client.framework.view.vwcomp.VWJDispNumber;
import client.framework.view.vwcomp.VWJDispReal;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJLine;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTextArea;

public class FPW01050ReviewBase extends VWGeneralWorkArea {
    static final Color LABEL_COLOR = UIManager.getColor("Panel.background");
    VWJLabel lblWpDefectRmv = new VWJLabel();
    VWJLabel lblWpDefectRmn = new VWJLabel();
    VWJLabel lblWprevWkyield = new VWJLabel();
    VWJLabel lblWprevQuality = new VWJLabel();
    VWJLabel lblWpPlanFinish = new VWJLabel();
    VWJLabel lblWpActFinish = new VWJLabel();
    VWJLabel lblWpVariFinish = new VWJLabel();
    VWJLabel lblWprevTime = new VWJLabel();
    VWJLabel lblWpPlanWkhr = new VWJLabel();
    VWJLabel lblWpActWkhr = new VWJLabel();
    VWJLabel lblWpVariWkhr = new VWJLabel();
    VWJLabel lblWprevCost = new VWJLabel();
    VWJLabel lblWprevPerformance = new VWJLabel();
    VWJLabel lblWprevComment = new VWJLabel();
    VWJDispNumber dpnWpDefectRmv = new VWJDispNumber();
    VWJNumber numWpDefectRmn = new VWJNumber();
    VWJReal ralWprevWkyield = new VWJReal();
    VWJNumber numWprevQuality = new VWJNumber();
    VWJDispDate dpdWpPlanFinish = new VWJDispDate();
    VWJDispDate dpdWpActFinish = new VWJDispDate();
    VWJDispNumber dpnWpVariFinish = new VWJDispNumber();
    VWJNumber numWprevTime = new VWJNumber();
    VWJDispReal dpnWpPlanWkhr = new VWJDispReal();
    VWJDispReal dpnWpActWkhr = new VWJDispReal();
    VWJDispReal dpnWpVariWkhr = new VWJDispReal();
    VWJNumber numWprevCost = new VWJNumber();
    VWJDispNumber dpnWprevPerformance = new VWJDispNumber();
    VWJTextArea txtWprevComment = new VWJTextArea();
    VWJLine lin19 = new VWJLine();
    VWJLine lin111 = new VWJLine();
    VWJLine lin112 = new VWJLine();
    VWJLine lin6 = new VWJLine();
    VWJLine lin7 = new VWJLine();
    VWJLine lin8 = new VWJLine();
    VWJLine lin3 = new VWJLine();
    VWJLine lin4 = new VWJLine();
    VWJLine lin5 = new VWJLine();
    VWJLine lin16 = new VWJLine();
    VWJLine lin17 = new VWJLine();

    public FPW01050ReviewBase() {
        try {
            jbInit();
            addComponent();
            setTabOrder();
            setEnterOrder();
            setUIComponentName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 400));

        lblWpDefectRmv.setBounds(new Rectangle(28, 22, 128, 39));
        lblWpDefectRmv.setText(" Removed Defect");
        lblWpDefectRmv.setBackground(LABEL_COLOR);
        lblWpDefectRmv.setOpaque(true);

        dpnWpDefectRmv.setBounds(new Rectangle(158, 31, 74, 20));
        dpnWpDefectRmv.setMaxInputIntegerDigit(10);

        lblWpDefectRmn.setText(" Remained Defect");
        lblWpDefectRmn.setBackground(LABEL_COLOR);
        lblWpDefectRmn.setOpaque(true);

        lblWpDefectRmn.setBounds(new Rectangle(235, 22, 123, 39));
        numWpDefectRmn.setMaxInputIntegerDigit(5);
        numWpDefectRmn.setBounds(new Rectangle(360, 31, 67, 20));

        lblWprevWkyield.setText(" Work Yield");
        lblWprevWkyield.setBackground(LABEL_COLOR);
        lblWprevWkyield.setOpaque(true);

        lblWprevWkyield.setBounds(new Rectangle(429, 22, 73, 39));
        ralWprevWkyield.setBounds(new Rectangle(505, 31, 43, 20));
        ralWprevWkyield.setMaxInputIntegerDigit(2);
        ralWprevWkyield.setMaxInputDecimalDigit(2);

        lblWprevQuality.setBounds(new Rectangle(550, 22, 84, 39));
        lblWprevQuality.setText(" Quality");
        lblWprevQuality.setBackground(LABEL_COLOR);
        lblWprevQuality.setOpaque(true);

        numWprevQuality.setBounds(new Rectangle(636, 31, 43, 20));
        numWprevQuality.setMaxInputIntegerDigit(3);

        lblWpPlanFinish.setBounds(new Rectangle(28, 62, 128, 39));
        lblWpPlanFinish.setText(" Planned Finish");
        lblWpPlanFinish.setBackground(LABEL_COLOR);
        lblWpPlanFinish.setOpaque(true);

        dpdWpPlanFinish.setBounds(new Rectangle(158, 71, 74, 20));
        dpdWpPlanFinish.setDataType(Constant.DATE_YYYYMMDD);

        lblWpActFinish.setText(" Actual Finish");
        lblWpActFinish.setBackground(LABEL_COLOR);
        lblWpActFinish.setOpaque(true);

        lblWpActFinish.setBounds(new Rectangle(235, 62, 123, 39));
        dpdWpActFinish.setBounds(new Rectangle(360, 71, 67, 20));
        dpdWpActFinish.setDataType(Constant.DATE_YYYYMMDD);

        lblWpVariFinish.setText(" Variance");
        lblWpVariFinish.setBackground(LABEL_COLOR);
        lblWpVariFinish.setOpaque(true);

        lblWpVariFinish.setBounds(new Rectangle(429, 62, 73, 39));
        dpnWpVariFinish.setBounds(new Rectangle(505, 71, 43, 20));
        dpnWpVariFinish.setMaxInputIntegerDigit(10);
        dpnWpVariFinish.setCanNegative(true);

        lblWprevTime.setBounds(new Rectangle(550, 62, 84, 39));
        lblWprevTime.setText(" Time");
        lblWprevTime.setBackground(LABEL_COLOR);
        lblWprevTime.setOpaque(true);

        numWprevTime.setBounds(new Rectangle(636, 71, 43, 20));
        numWprevTime.setMaxInputIntegerDigit(3);

        lblWpPlanWkhr.setBounds(new Rectangle(28, 102, 128, 39));
        lblWpPlanWkhr.setText(" Planned Work Hours");
        lblWpPlanWkhr.setBackground(LABEL_COLOR);
        lblWpPlanWkhr.setOpaque(true);

        dpnWpPlanWkhr.setBounds(new Rectangle(158, 112, 74, 20));
        dpnWpPlanWkhr.setMaxInputIntegerDigit(10);

        lblWpActWkhr.setText(" Actual Work Hours");
        lblWpActWkhr.setBackground(LABEL_COLOR);
        lblWpActWkhr.setOpaque(true);

        lblWpActWkhr.setBounds(new Rectangle(235, 102, 123, 39));
        dpnWpActWkhr.setBounds(new Rectangle(360, 111, 67, 20));
        dpnWpActWkhr.setMaxInputIntegerDigit(10);

        lblWpVariWkhr.setText(" Variance");
        lblWpVariWkhr.setBackground(LABEL_COLOR);
        lblWpVariWkhr.setOpaque(true);

        lblWpVariWkhr.setBounds(new Rectangle(429, 102, 73, 39));
        dpnWpVariWkhr.setBounds(new Rectangle(505, 111, 43, 20));
        dpnWpVariWkhr.setMaxInputIntegerDigit(10);
        dpnWpVariWkhr.setCanNegative(true);

        lblWprevCost.setBounds(new Rectangle(550, 102, 84, 39));
        lblWprevCost.setText(" Cost");
        lblWprevCost.setBackground(LABEL_COLOR);
        lblWprevCost.setOpaque(true);

        numWprevCost.setBounds(new Rectangle(636, 111, 43, 20));
        numWprevCost.setMaxInputIntegerDigit(3);

        lblWprevPerformance.setText(" Performance");
        lblWprevPerformance.setBackground(LABEL_COLOR);
        lblWprevPerformance.setOpaque(true);

        lblWprevPerformance.setBounds(new Rectangle(550, 142, 84, 39));
        dpnWprevPerformance.setBounds(new Rectangle(636, 151, 43, 20));
        dpnWprevPerformance.setMaxInputIntegerDigit(10);

        lblWprevComment.setBounds(new Rectangle(28, 212, 120, 132));
        lblWprevComment.setText(" Comment");
        lblWprevComment.setBackground(LABEL_COLOR);
        lblWprevComment.setOpaque(true);

        txtWprevComment.setBounds(new Rectangle(151, 222, 528, 111));

        lin3.setBackground(Color.gray);
        lin3.setForeground(Color.black);
        lin3.setBorder(BorderFactory.createLineBorder(Color.black));
        lin3.setBounds(new Rectangle(27, 101, 655, 41));
        lin4.setBackground(Color.gray);
        lin4.setForeground(Color.black);
        lin4.setBorder(BorderFactory.createLineBorder(Color.black));
        lin4.setBounds(new Rectangle(27, 61, 655, 41));
        lin5.setBounds(new Rectangle(27, 21, 655, 41));
        lin5.setBorder(BorderFactory.createLineBorder(Color.black));
        lin5.setForeground(Color.black);
        lin5.setBackground(Color.gray);
        lin16.setBounds(new Rectangle(156, 21, 79, 121));
        lin16.setBorder(BorderFactory.createLineBorder(Color.black));
        lin16.setForeground(Color.black);
        lin16.setBackground(Color.gray);
        lin17.setBackground(Color.gray);
        lin17.setForeground(Color.black);
        lin17.setBorder(BorderFactory.createLineBorder(Color.black));
        lin17.setBounds(new Rectangle(234, 21, 125, 121));
        lin19.setBackground(Color.gray);
        lin19.setForeground(Color.black);
        lin19.setBorder(BorderFactory.createLineBorder(Color.black));
        lin19.setBounds(new Rectangle(428, 21, 75, 121));
        lin111.setBounds(new Rectangle(634, 21, 48, 161));
        lin111.setBorder(BorderFactory.createLineBorder(Color.black));
        lin111.setForeground(Color.black);
        lin111.setBackground(Color.gray);
        lin112.setBounds(new Rectangle(549, 21, 86, 161));
        lin112.setBorder(BorderFactory.createLineBorder(Color.black));
        lin112.setForeground(Color.black);
        lin112.setBackground(Color.gray);
        lin6.setBounds(new Rectangle(27, 141, 655, 41));
        lin6.setBorder(BorderFactory.createLineBorder(Color.black));
        lin6.setForeground(Color.black);
        lin6.setBackground(Color.gray);
        lin7.setBackground(Color.gray);
        lin7.setForeground(Color.black);
        lin7.setBorder(BorderFactory.createLineBorder(Color.black));
        lin7.setBounds(new Rectangle(148, 211, 534, 134));
        lin8.setBackground(Color.gray);
        lin8.setForeground(Color.black);
        lin8.setBorder(BorderFactory.createLineBorder(Color.black));
        lin8.setBounds(new Rectangle(27, 211, 122, 134));

        this.add(numWprevQuality, null);
        this.add(dpnWprevPerformance, null);
        this.add(lin6, null);
        this.add(numWpDefectRmn, null);
        this.add(dpnWpActWkhr, null);
        this.add(dpdWpActFinish, null);
        this.add(ralWprevWkyield, null);
        this.add(lblWprevWkyield, null);
        this.add(lblWpVariFinish, null);
        this.add(lblWpVariWkhr, null);
        this.add(dpnWpVariFinish, null);
        this.add(dpnWpVariWkhr, null);
        this.add(numWprevTime, null);
        this.add(numWprevCost, null);
        this.add(lblWprevQuality, null);
        this.add(lblWprevTime, null);
        this.add(lblWprevCost, null);
        this.add(lin7, null);
        this.add(lblWprevComment, null);
        this.add(txtWprevComment, null);
        this.add(lin8, null);
        this.add(dpnWpPlanWkhr, null);
        this.add(lblWpPlanFinish, null);
        this.add(lblWpActFinish, null);
        this.add(lblWpActWkhr, null);
        this.add(lblWpDefectRmn, null);
        this.add(dpdWpPlanFinish, null);
        this.add(dpnWpDefectRmv, null);
        this.add(lin4, null);
        this.add(lin16, null);
        this.add(lblWpDefectRmv, null);
        this.add(lin17, null);
        this.add(lin19, null);
        this.add(lin112, null);
        this.add(lin111, null);
        this.add(lblWpPlanWkhr, null);
        this.add(lin3, null);
        this.add(lin5, null);
        this.add(lblWprevPerformance, null);

    }

    private void addComponent() {

    }

    private void setTabOrder() {
        List tabList = new ArrayList();
        tabList.add(this.numWpDefectRmn);
        tabList.add(this.ralWprevWkyield);
        tabList.add(this.numWprevQuality);
        tabList.add(this.numWprevTime);
        tabList.add(this.numWprevCost);
        tabList.add(this.txtWprevComment);
        comFORM.setTABOrder(this, tabList);

    }

    private void setEnterOrder() {
        List enterList = new ArrayList();
        enterList.add(this.numWpDefectRmn);
        enterList.add(this.ralWprevWkyield);
        enterList.add(this.numWprevQuality);
        enterList.add(this.numWprevTime);
        enterList.add(this.numWprevCost);
        enterList.add(this.txtWprevComment);
        comFORM.setEnterOrder(this, enterList);
    }

    private void setUIComponentName() {
        dpnWpDefectRmv.setName("wpDefectRmv");
        numWpDefectRmn.setName("wpDefectRmn");
        ralWprevWkyield.setName("wprevWkyield");
        numWprevQuality.setName("wprevQuality");

        dpdWpPlanFinish.setName("wpPlanFihish");
        dpdWpActFinish.setName("wpActFinish");

        //        dpnWpVariFinish.setName("");
        numWprevTime.setName("wprevTime");

        dpnWpPlanWkhr.setName("wpPlanWkhr");
        dpnWpActWkhr.setName("wpActWkhr");

        //        dpnWpVariWkhr.setName("");
        numWprevCost.setName("wprevCost");

        dpnWprevPerformance.setName("wprevPerformance");
        txtWprevComment.setName("wprevComment");
    }


    public static void main(String[] args) {
        FPW01050ReviewBase workArea = new FPW01050ReviewBase();

        TestPanelParam.show(workArea);
        workArea.refreshWorkArea();
    }

}
