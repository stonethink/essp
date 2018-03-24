package client.essp.pwm.wp;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;

import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJDispDate;
import client.framework.view.vwcomp.VWJDispReal;
import client.framework.view.vwcomp.VWJEditComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJRadioButton;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import client.essp.common.humanAllocate.HrAllocate;
import client.essp.common.loginId.VWJLoginId;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class FPW01010GeneralBase extends VWGeneralWorkArea {
    protected VWJDisp cmbAccountName = new VWJDisp();
    protected VWJDisp cmbProjectId = new VWJDisp();
    protected VWJDisp dspAccountType = new VWJDisp();
    protected VWJLoginId dspManager = new VWJLoginId();
    protected VWJComboBox cmbClnitem = new VWJComboBox();
    protected VWJEditComboBox cmbActivityId = new VWJEditComboBox();
    protected VWJHrAllocateButton dspWpAssignBy = new VWJHrAllocateButton();
    protected VWJDispDate ddtWpAssignDate = new VWJDispDate();
    protected VWJText txtWpCode = new VWJText();
    protected VWJText txtWpName = new VWJText();
    protected VWJComboBox cmbWpType = new VWJComboBox();
    protected VWJHrAllocateButton dspWpWorker = new VWJHrAllocateButton(HrAllocate.ALLOC_MULTIPLE);
    protected VWJReal numWpReqWkhr = new VWJReal();
    protected VWJReal numWpPlanWkhr = new VWJReal();
    protected VWJReal dpnWpActWkhr = new VWJReal();
    protected VWJDate dteWpPlanStart = new VWJDate();
    protected VWJDate dteWpPlanFinish = new VWJDate();
    protected VWJDate dteWpActStart = new VWJDate();
    protected VWJDate dteWpActFinish = new VWJDate();
    protected VWJReal ralWpSizePlan = new VWJReal();
    protected VWJComboBox cmbWpSizeUnit = new VWJComboBox();
    protected VWJReal ralWpDensityratePlan = new VWJReal();
    protected VWJComboBox cmbWpDensityrateUnit = new VWJComboBox();
    protected VWJReal ralWpDefectratePlan = new VWJReal();
    protected VWJComboBox cmbWpDefectrateUnit = new VWJComboBox();
    protected VWJComboBox cmbWpStatus = new VWJComboBox();
    private ButtonGroup buttonGroup = new ButtonGroup();
    protected VWJRadioButton rdoWpCmpltrateType1 = new VWJRadioButton();
    protected VWJRadioButton rdoWpCmpltrateType2 = new VWJRadioButton();

    //  protecte VWJText TXT_WP_CMPLTRATE_TYPE = new VWJText();
    protected VWJNumber numWpCmpltrate = new VWJNumber();
    protected VWJTextArea txaWpRequirement = new VWJTextArea();
    protected VWJLabel lblAccountName = new VWJLabel();
    protected VWJLabel lblProjectId = new VWJLabel();
    protected VWJLabel lblAccountType = new VWJLabel();
    protected VWJLabel lblManager = new VWJLabel();
    protected VWJLabel lblClnitem = new VWJLabel();
    protected VWJLabel lblActivityId = new VWJLabel();
    protected VWJLabel lblWpAssignBy = new VWJLabel();
    protected VWJLabel lblWpAssignDate = new VWJLabel();
    protected VWJLabel lblWpCode = new VWJLabel();
    protected VWJLabel lblWpName = new VWJLabel();
    protected VWJLabel lblWpType = new VWJLabel();
    protected VWJLabel lblWpWorker = new VWJLabel();
    protected VWJLabel lblWpReqWkhr = new VWJLabel();
    protected VWJLabel lblWpPlanWkhr = new VWJLabel();
    protected VWJLabel lblWpActWkhr = new VWJLabel();
    protected VWJLabel lblWpPlanStart = new VWJLabel();
    protected VWJLabel lblWpPlanFinish = new VWJLabel();
    protected VWJLabel lblWpActStart = new VWJLabel();
    protected VWJLabel lblWpActFinish = new VWJLabel();
    protected VWJLabel lblWpSizePlan = new VWJLabel();
    protected VWJLabel lblWpSizeUnit = new VWJLabel();
    protected VWJLabel lblWpDensityratePlan = new VWJLabel();
    protected VWJLabel lblWpDensityrateUnit = new VWJLabel();
    protected VWJLabel lblWpDefectratePlan = new VWJLabel();
    protected VWJLabel lblWpDefectrateUnit = new VWJLabel();
    protected VWJLabel lblWpStatus = new VWJLabel();
    protected VWJLabel lblWpCmpltrateType = new VWJLabel();
    protected VWJLabel lblWpCmpltrate = new VWJLabel();
    protected VWJLabel lblWpCmpltrate1 = new VWJLabel();
    protected VWJLabel lblWpRequirement = new VWJLabel();

    public FPW01010GeneralBase() {
        try {
            jbInit();
            setTabOrder();
            setEnterOrder();
            setUIComponentName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //建议在画好页面后再另添代码
    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 470));

        lblAccountName.setText("Account Name");
        lblAccountName.setBounds(new Rectangle(362, 20, 120, 20));

        //comboBox
        cmbAccountName.setBounds(new Rectangle(502, 20, 160, 20));

        lblProjectId.setText("Account Code");
        lblProjectId.setBounds(new Rectangle(26, 20, 130, 20));

        //comboBox--
        cmbProjectId.setBounds(new Rectangle(165, 20, 160, 20));

        lblAccountType.setText("Account Type");
        lblAccountType.setBounds(new Rectangle(26, 45, 130, 20));
        dspAccountType.setBounds(new Rectangle(165, 45, 160, 20));

        lblManager.setText("Manager");
        lblManager.setBounds(new Rectangle(362, 45, 120, 20));
        dspManager.setBounds(new Rectangle(502, 45, 160, 20));
        dspManager.setEnabled(false);

        lblClnitem.setText("Activity Name");
        lblClnitem.setBounds(new Rectangle(362, 70, 120, 20));
        cmbClnitem.setBounds(new Rectangle(502, 70, 160, 20));

        //        CMB_CLNITEM.setEditable(true);
        lblActivityId.setText("Activity ID");
        lblActivityId.setBounds(new Rectangle(26, 70, 130, 20));
        cmbActivityId.setBounds(new Rectangle(165, 70, 160, 20));
//    cmbActivityId.setEditable(true);

        lblWpAssignBy.setText("Assign by");
        lblWpAssignBy.setBounds(new Rectangle(26, 95, 130, 20));
        dspWpAssignBy.setBounds(new Rectangle(165, 95, 160, 20));

        lblWpAssignDate.setText("Assign date");
        lblWpAssignDate.setBounds(new Rectangle(362, 95, 120, 20));
        ddtWpAssignDate.setBounds(new Rectangle(502, 95, 160, 20));

        lblWpCode.setText("WP Code");
        lblWpCode.setBounds(new Rectangle(26, 130, 130, 20));
        txtWpCode.setBounds(new Rectangle(165, 130, 160, 20));

        lblWpName.setText("WP Name");
        lblWpName.setBounds(new Rectangle(362, 130, 120, 20));
        txtWpName.setBounds(new Rectangle(503, 130, 160, 20));

        lblWpType.setText("WP Type");
        lblWpType.setBounds(new Rectangle(26, 155, 130, 20));

        //comboBox--
        cmbWpType.setBounds(new Rectangle(165, 155, 160, 20));

        lblWpWorker.setText("Worker");
        lblWpWorker.setBounds(new Rectangle(26, 180, 130, 20));
        dspWpWorker.setBounds(new Rectangle(167, 180, 494, 20));
        dspWpWorker.getTextComp().setEnabled(false);

        lblWpReqWkhr.setText("Required Work Hours");
        lblWpReqWkhr.setBounds(new Rectangle(359, 155, 120, 20));
        numWpReqWkhr.setMaxInputIntegerDigit(5);
        numWpReqWkhr.setMaxInputDecimalDigit(2);
        numWpReqWkhr.setBounds(new Rectangle(503, 155, 160, 20));

        lblWpPlanWkhr.setText("Planned Work Hours");
        lblWpPlanWkhr.setBounds(new Rectangle(26, 205, 130, 20));
        numWpPlanWkhr.setMaxInputIntegerDigit(5);
        numWpPlanWkhr.setMaxInputDecimalDigit(2);
        numWpPlanWkhr.setBounds(new Rectangle(165, 205, 160, 20));

        lblWpActWkhr.setText("Actual Work Hours");
        lblWpActWkhr.setBounds(new Rectangle(362, 205, 120, 20));
        dpnWpActWkhr.setMaxInputIntegerDigit(5);
        dpnWpActWkhr.setMaxInputDecimalDigit(2);
        dpnWpActWkhr.setBounds(new Rectangle(502, 205, 160, 20));

        lblWpPlanStart.setText("Planned Start");
        lblWpPlanStart.setBounds(new Rectangle(26, 230, 130, 20));
        dteWpPlanStart.setBounds(new Rectangle(165, 230, 160, 20));
        dteWpPlanStart.setCanSelect(true);

        //
        lblWpPlanFinish.setText("Planned Finish");
        lblWpPlanFinish.setBounds(new Rectangle(362, 230, 120, 20));
        dteWpPlanFinish.setBounds(new Rectangle(502, 230, 160, 20));
        dteWpPlanFinish.setCanSelect(true);

        //
        lblWpActStart.setText("Actual Start");
        lblWpActStart.setBounds(new Rectangle(26, 255, 130, 20));
        dteWpActStart.setBounds(new Rectangle(165, 255, 160, 20));
        dteWpActStart.setCanSelect(true);

        //
        lblWpActFinish.setText("Actual Finish");
        lblWpActFinish.setBounds(new Rectangle(362, 255, 120, 20));
        dteWpActFinish.setBounds(new Rectangle(502, 255, 160, 20));
        dteWpActFinish.setCanSelect(true);

        lblWpSizePlan.setText("Size");
        lblWpSizePlan.setBounds(new Rectangle(26, 280, 130, 20));
        ralWpSizePlan.setMaxInputIntegerDigit(8);
        ralWpSizePlan.setBounds(new Rectangle(165, 280, 63, 20));
        cmbWpSizeUnit.setBounds(new Rectangle(231, 280, 94, 20));

        lblWpDensityratePlan.setText("Density");
        lblWpDensityratePlan.setBounds(new Rectangle(362, 280, 120, 20));
        ralWpDensityratePlan.setMaxInputIntegerDigit(5);
        ralWpDensityratePlan.setBounds(new Rectangle(502, 280, 63, 20));
        cmbWpDensityrateUnit.setBounds(new Rectangle(568, 280, 94, 20));

        lblWpDefectratePlan.setText("Defect Rate");
        lblWpDefectratePlan.setBounds(new Rectangle(26, 305, 130, 20));
        ralWpDefectratePlan.setMaxInputIntegerDigit(5);
        ralWpDefectratePlan.setBounds(new Rectangle(165, 305, 63, 20));
        cmbWpDefectrateUnit.setBounds(new Rectangle(231, 305, 94, 20));

        lblWpStatus.setText("Status");
        lblWpStatus.setBounds(new Rectangle(362, 305, 120, 20));
        cmbWpStatus.setBounds(new Rectangle(502, 305, 160, 20));

        lblWpCmpltrate.setText("Complete Rate");
        lblWpCmpltrate.setBounds(new Rectangle(26, 330, 130, 20));
        rdoWpCmpltrateType1.setBounds(new Rectangle(165, 330, 112, 20));
        rdoWpCmpltrateType1.setText("By Work Hours");

        rdoWpCmpltrateType2.setBounds(new Rectangle(285, 330, 122, 20));
        rdoWpCmpltrateType2.setText("By Manual Work");

        buttonGroup.add(rdoWpCmpltrateType1);
        buttonGroup.add(rdoWpCmpltrateType2);

        numWpCmpltrate.setBounds(new Rectangle(410, 330, 29, 20));
        numWpCmpltrate.setMaxInputIntegerDigit(3);
        lblWpCmpltrate1.setText("%");
        lblWpCmpltrate1.setBounds(new Rectangle(442, 330, 29, 20));

        lblWpRequirement.setText("WP Requirement");
        lblWpRequirement.setBounds(new Rectangle(26, 355, 130, 20));
        txaWpRequirement.setBounds(new Rectangle(165, 355, 500, 100));

        this.add(lblWpSizeUnit, null);
        this.add(lblWpDensityrateUnit, null);
        this.add(lblWpDefectrateUnit, null);
        this.add(lblWpCmpltrateType, null);
        this.add(lblProjectId, null);
        this.add(lblAccountType, null);
        this.add(lblActivityId, null);
        this.add(lblWpAssignBy, null);
        this.add(lblWpCode, null);
        this.add(lblWpWorker, null);
        this.add(lblWpPlanWkhr, null);
        this.add(lblWpPlanStart, null);
        this.add(lblWpActStart, null);
        this.add(lblWpSizePlan, null);
        this.add(lblWpDefectratePlan, null);
        this.add(lblWpCmpltrate, null);
        this.add(lblWpRequirement, null);
        this.add(txaWpRequirement);
        this.add(lblWpType, null);
        this.add(rdoWpCmpltrateType1, null);
        this.add(cmbWpSizeUnit, null);
        this.add(cmbWpDefectrateUnit, null);
        this.add(cmbWpType, null);
        this.add(numWpPlanWkhr, null);
        this.add(cmbProjectId, null);
        this.add(dteWpPlanStart, null);
        this.add(dteWpActStart, null);
        this.add(dspAccountType, null);
        this.add(cmbActivityId, null);
        this.add(dspWpAssignBy, null);
        this.add(txtWpCode, null);
        this.add(ralWpSizePlan, null);
        this.add(ralWpDefectratePlan, null);
        this.add(lblAccountName, null);
        this.add(lblManager, null);
        this.add(lblClnitem, null);
        this.add(lblWpAssignDate, null);
        this.add(lblWpActWkhr, null);
        this.add(lblWpPlanFinish, null);
        this.add(lblWpActFinish, null);
        this.add(lblWpDensityratePlan, null);
        this.add(lblWpStatus, null);
        this.add(dspWpWorker, null);
        this.add(ralWpDensityratePlan, null);
        this.add(cmbWpDensityrateUnit, null);
        this.add(dteWpActFinish, null);
        this.add(cmbAccountName, null);
        this.add(dspManager, null);
        this.add(cmbClnitem, null);
        this.add(ddtWpAssignDate, null);
        this.add(dpnWpActWkhr, null);
        this.add(dteWpPlanFinish, null);
        this.add(cmbWpStatus, null);
        this.add(rdoWpCmpltrateType2, null);
        this.add(numWpCmpltrate, null);
        this.add(lblWpCmpltrate1, null);
        this.add(lblWpName, null);
        this.add(txtWpName, null);
        this.add(numWpReqWkhr, null);
        this.add(lblWpReqWkhr, null);
    }

    private void setTabOrder() {
        //set tab order---------------------------------------
        List tabList = new ArrayList();
        tabList.add(cmbProjectId);
        tabList.add(cmbAccountName);

        tabList.add(cmbActivityId);
        tabList.add(cmbClnitem);

        tabList.add(txtWpCode);
        tabList.add(txtWpName);
        tabList.add(cmbWpType);
        tabList.add(numWpReqWkhr);
        tabList.add(dspWpWorker.getButtonComp());
        tabList.add(numWpPlanWkhr);
        tabList.add(dpnWpActWkhr);
        tabList.add(dteWpPlanStart);
        tabList.add(dteWpPlanFinish);
        tabList.add(dteWpActStart);
        tabList.add(dteWpActFinish);
        tabList.add(ralWpSizePlan);
        tabList.add(cmbWpSizeUnit);
        tabList.add(ralWpDensityratePlan);
        tabList.add(cmbWpDensityrateUnit);
        tabList.add(ralWpDefectratePlan);
        tabList.add(cmbWpDefectrateUnit);
        tabList.add(cmbWpStatus);
        tabList.add(rdoWpCmpltrateType1);
        tabList.add(rdoWpCmpltrateType2);
        tabList.add(numWpCmpltrate);
        tabList.add(txaWpRequirement);
        tabList.add(cmbProjectId);
        comFORM.setTABOrder(this, tabList);

    }

    private void setEnterOrder() {
        //set enter order---------------------------------------
        List enterList = new ArrayList();
        enterList.add(cmbProjectId);
        enterList.add(cmbAccountName);

        enterList.add(cmbActivityId);
        enterList.add(cmbActivityId.getEditor().getEditorComponent());
        enterList.add(cmbClnitem);

        enterList.add(txtWpCode);
        enterList.add(txtWpName);
        enterList.add(cmbWpType);
        enterList.add(numWpReqWkhr);
//        enterList.add(btnWorker);
        enterList.add(numWpPlanWkhr);
        enterList.add(dpnWpActWkhr);
        enterList.add(dteWpPlanStart);
        enterList.add(dteWpPlanFinish);
        enterList.add(ralWpSizePlan);
        enterList.add(cmbWpSizeUnit);
        enterList.add(ralWpDensityratePlan);
        enterList.add(cmbWpDensityrateUnit);
        enterList.add(ralWpDefectratePlan);
        enterList.add(cmbWpDefectrateUnit);
        enterList.add(cmbWpStatus);
//        enterList.add(rdoWpCmpltrateType1);
//        enterList.add(rdoWpCmpltrateType2);
        enterList.add(numWpCmpltrate);
        enterList.add(txaWpRequirement);
        enterList.add(cmbProjectId);

        comFORM.setEnterOrder(this, enterList);

    }

    private void setUIComponentName() {
        cmbAccountName.setName("accountName");
        cmbProjectId.setName("accountId");
        dspAccountType.setName("accountTypeName");
        dspManager.setName("accountManagerName");
        cmbClnitem.setName("activityId");
        cmbActivityId.setName("activityId");
        dspWpAssignBy.setName("wpAssignby");
        ddtWpAssignDate.setName("wpAssigndate");
        txtWpCode.setName("wpCode");
        txtWpName.setName("wpName");
        cmbWpType.setName("wpTypeCode");
        dspWpWorker.setName("wpWorker");

        //btnWorker.setName("");
        numWpReqWkhr.setName("wpReqWkhr");
        numWpPlanWkhr.setName("wpPlanWkhr");
        dpnWpActWkhr.setName("wpActWkhr");
        dteWpPlanStart.setName("wpPlanStart");
        dteWpPlanFinish.setName("wpPlanFihish");
        dteWpActStart.setName("wpActStart");
        dteWpActFinish.setName("wpActFinish");
        ralWpSizePlan.setName("wpSizePlan");
        cmbWpSizeUnit.setName("wpSizeUnit");
        ralWpDensityratePlan.setName("wpDensityratePlan");
        cmbWpDensityrateUnit.setName("wpDensityrateUnit");
        ralWpDefectratePlan.setName("wpDefectratePlan");
        cmbWpDefectrateUnit.setName("wpDefectrateUnit");
        cmbWpStatus.setName("wpStatus");

        //rdoWpCmpltrateType1.setName("");
        //rdoWpCmpltrateType2.setName("");
        //TXT_WP_CMPLTRATE_TYPE.setName("wpCmpltrateType");
        numWpCmpltrate.setName("wpCmpltrate");
        txaWpRequirement.setName("wpRequirement");
    }

    //处理事件---------
    public static void main(String[] args) {
        FPW01010GeneralBase vWPwWpGeneralBas = new FPW01010GeneralBase();

        client.essp.common.util.TestPanelParam.show(vWPwWpGeneralBas);
    }

}
