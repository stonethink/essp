package client.essp.pms.account;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import client.essp.common.humanAllocate.HrAllocate;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTextArea;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwAccountGeneralBase extends VWGeneralWorkArea {

    VWJLabel lblAcntOrganization = new VWJLabel();
    VWJComboBox cmbAcntOrganization = new VWJComboBox();
    VWJLabel lblBrief = new VWJLabel();
    VWJComboBox cmbAcntType = new VWJComboBox();
    VWJLabel lblAcntCurrency = new VWJLabel();
    VWJComboBox cmbAcntCurrency = new VWJComboBox();
    VWJLabel lblAcntManager = new VWJLabel();
    VWJHrAllocateButton txtAcntManager = new VWJHrAllocateButton( HrAllocate.ALLOC_SINGLE);

    VWJLabel lblAcntType = new VWJLabel();
    VWJTextArea txaAcntBrief = new VWJTextArea();
    VWJDate txtAcntActualFinish = new VWJDate();
    VWJLabel lblAcntStatus = new VWJLabel();
    VWJDate txtAcntPlannedFinish = new VWJDate();
    VWJLabel lblPlannedFinish = new VWJLabel();
    VWJComboBox cmbAcntStatus = new VWJComboBox();
    VWJLabel lblActualFinish = new VWJLabel();
    VWJDate txtAcntPlannedStart = new VWJDate();
    VWJLabel lblPlannedStart = new VWJLabel();
    VWJDate txtAcntAnticipatedFinish = new VWJDate();
    VWJLabel lblAcntAnticipatedStart = new VWJLabel();
    VWJDisp txtAcntName = new VWJDisp();
    VWJLabel lblAcntName = new VWJLabel();
    VWJDate txtAcntAnticipatedStart = new VWJDate();
    VWJLabel lblAcntAnticipatedFinish = new VWJLabel();
    VWJDisp txtAcntId = new VWJDisp();
    VWJLabel lblAcntId = new VWJLabel();
    VWJLabel lblActualStart = new VWJLabel();
    VWJDate txtAcntActualStart = new VWJDate();

    public VwAccountGeneralBase() {
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
        this.setPreferredSize(new Dimension(700,280));

        lblAcntOrganization.setText("Organization");
        lblAcntOrganization.setBounds(new Rectangle(402, 74, 133, 20));
        cmbAcntOrganization.setBounds(new Rectangle(544, 74, 212, 20));

        lblAcntType.setText("Account Type");
        lblAcntType.setBounds(new Rectangle(35, 74, 128, 20));
        cmbAcntType.setBounds(new Rectangle(169, 74, 212, 20));

        lblAcntCurrency.setText("Account Currency");
        lblAcntCurrency.setBounds(new Rectangle(402, 49, 133, 20));
        cmbAcntCurrency.setBounds(new Rectangle(544, 49, 212, 20));

        lblAcntManager.setText("Account Manager");
        lblAcntManager.setBounds(new Rectangle(35, 49, 128, 20));
        txtAcntManager.setBounds(new Rectangle(169, 49, 212, 20));

        lblBrief.setText("Brief");
        lblBrief.setBounds(new Rectangle(35, 205, 128, 20));
        txaAcntBrief.setBounds(new Rectangle(169, 199, 585, 70));

        lblAcntStatus.setText("Status");
        lblAcntStatus.setBounds(new Rectangle(35, 179, 128, 20));
        cmbAcntStatus.setBounds(new Rectangle(169, 174, 212, 20));

        lblPlannedFinish.setText("Planned Finish ");
        lblPlannedFinish.setBounds(new Rectangle(402, 124, 133, 20));
        txtAcntPlannedFinish.setBounds(new Rectangle(544, 124, 212, 20));
        txtAcntPlannedFinish.setCanSelect( true );

        lblActualFinish.setText("Actual Finish");
        lblActualFinish.setBounds(new Rectangle(402, 149, 133, 20));
        txtAcntActualFinish.setBounds(new Rectangle(544, 149, 212, 20));
        txtAcntActualFinish.setCanSelect( true );

        lblPlannedStart.setText("Planned Start");
        lblPlannedStart.setBounds(new Rectangle(35, 124, 128, 20));
        txtAcntPlannedStart.setBounds(new Rectangle(169, 124, 212, 20));
        txtAcntPlannedStart.setCanSelect( true );

        lblAcntAnticipatedStart.setText("Anticipated Start");
        lblAcntAnticipatedStart.setBounds(new Rectangle(35, 99, 128, 20));
        txtAcntAnticipatedStart.setBounds(new Rectangle(169, 99, 212, 20));
        txtAcntAnticipatedStart.setCanSelect( true );

        lblAcntName.setText("Account Name");
        lblAcntName.setBounds(new Rectangle(402, 24, 133, 20));
        txtAcntName.setBounds(new Rectangle(544, 24, 212, 20));

        lblAcntAnticipatedFinish.setText("Anticipated Finish ");
        lblAcntAnticipatedFinish.setBounds(new Rectangle(402, 99, 133, 20));
        txtAcntAnticipatedFinish.setBounds(new Rectangle(544, 99, 212, 20));
        txtAcntAnticipatedFinish.setCanSelect( true );

        txtAcntId.setBounds(new Rectangle(169, 24, 212, 20));
        lblAcntId.setText("Account Code");
        lblAcntId.setBounds(new Rectangle(35, 24, 128, 20));

        lblActualStart.setText("Actual Start");
        lblActualStart.setBounds(new Rectangle(35, 149, 128, 20));
        txtAcntActualStart.setBounds(new Rectangle(169, 149, 212, 20));
        txtAcntActualStart.setCanSelect( true );

        this.add(txaAcntBrief);
        this.add(lblAcntType);
        this.add(lblAcntId);
        this.add(lblPlannedStart);
        this.add(lblActualStart);
        this.add(lblAcntAnticipatedStart);
        this.add(lblAcntManager);
        this.add(txtAcntId);
        this.add(lblAcntStatus);
        this.add(lblBrief);
        this.add(txtAcntManager);
        this.add(cmbAcntType);
        this.add(txtAcntAnticipatedStart);
        this.add(txtAcntPlannedStart);
        this.add(txtAcntActualStart);
        this.add(cmbAcntStatus);
        this.add(txtAcntName);
        this.add(cmbAcntOrganization);
        this.add(txtAcntPlannedFinish);
        this.add(txtAcntActualFinish);
        this.add(cmbAcntCurrency);
        this.add(lblAcntName);
        this.add(lblActualFinish);
        this.add(lblPlannedFinish);
        this.add(lblAcntAnticipatedFinish);
        this.add(lblAcntCurrency);
        this.add(lblAcntOrganization);
        this.add(txtAcntAnticipatedFinish);
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(txtAcntId);
        compList.add(txtAcntName);
        compList.add(txtAcntManager);
        compList.add(cmbAcntCurrency);
        compList.add(cmbAcntType);
        compList.add(cmbAcntOrganization);
        compList.add(txtAcntAnticipatedStart);
        compList.add(txtAcntAnticipatedFinish);
        compList.add(txtAcntPlannedStart);
        compList.add(txtAcntPlannedFinish);
        compList.add(txtAcntActualStart);
        compList.add(txtAcntActualFinish);
        compList.add(cmbAcntStatus);
        compList.add(txaAcntBrief);

        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(txtAcntId);
        compList.add(txtAcntName);
        compList.add(txtAcntManager);
        compList.add(cmbAcntCurrency);
        compList.add(cmbAcntType);
        compList.add(cmbAcntOrganization);
        compList.add(txtAcntAnticipatedStart);

        compList.add(txtAcntAnticipatedFinish);
        compList.add(txtAcntPlannedStart);
        compList.add(txtAcntPlannedFinish);
        compList.add(txtAcntActualStart);
        compList.add(txtAcntActualFinish);
        compList.add(cmbAcntStatus);
        compList.add(txaAcntBrief);

        comFORM.setEnterOrder(this, compList);
    }

    private void setUIComponentName() {
        txtAcntId.setName("id");
        txtAcntName.setName("name");
        txtAcntManager.setName("manager");
        cmbAcntCurrency.setName("currency");
        cmbAcntType.setName("type");
        cmbAcntOrganization.setName("organization");
        txtAcntAnticipatedStart.setName("anticipatedStart");
        txtAcntAnticipatedFinish.setName("anticipatedFinish");
        txtAcntPlannedStart.setName("plannedStart");
        txtAcntPlannedFinish.setName("plannedFinish");
        txtAcntActualStart.setName("actualStart");
        txtAcntActualFinish.setName("actualFinish");
        cmbAcntStatus.setName("status");
        txaAcntBrief.setName("brief");
    }

    public static void main(String args[]) {
        VwAccountGeneralBase vwAccountGeneralBase = new VwAccountGeneralBase();
        vwAccountGeneralBase.txtAcntId.setEnabled(false);
        TestPanelParam.show(new VwAccountGeneralBase());

    }

}
