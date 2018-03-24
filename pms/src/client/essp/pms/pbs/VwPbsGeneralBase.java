package client.essp.pms.pbs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwPbsGeneralBase extends VWGeneralWorkArea {

    VWJLabel lblPlannedFinish = new VWJLabel();
    VWJLabel lblProductBrief = new VWJLabel();
    VWJLabel lblProduntName = new VWJLabel();
    VWJLabel lblReference = new VWJLabel();
    VWJLabel lblActualFinish = new VWJLabel();
    VWJLabel lblManager = new VWJLabel();
    VWJLabel lblProductCode = new VWJLabel();
    VWJLabel lblStatus = new VWJLabel();

    VWJText txtProductName = new VWJText();
    VWJHrAllocateButton txtPbsManager = new VWJHrAllocateButton();
    VWJDate dtePlannedFinish = new VWJDate();
    VWJDate dteActualFinish = new VWJDate();
    VWJText txtProductCode = new VWJText();
    VWJText txtPbsReferrence = new VWJText();
    VWJTextArea txaPbsBrief = new VWJTextArea();
    VWJComboBox cmbStatus = new VWJComboBox();

    public VwPbsGeneralBase() {
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
        this.setPreferredSize(new Dimension(720,250) );

        lblPlannedFinish.setText("Planned Finish");
        lblPlannedFinish.setBounds(new Rectangle(21, 85, 107, 20));
        dtePlannedFinish.setBounds(new Rectangle(138, 85, 209, 20));
        dtePlannedFinish.setCanSelect(true);

        lblProductBrief.setText("Product Brief");
        lblProductBrief.setBounds(new Rectangle(21, 160, 107, 20));
        txaPbsBrief.setBounds(new Rectangle(138, 160, 575, 68));

        lblProduntName.setText("Product Name");
        lblProduntName.setBounds(new Rectangle(21, 10, 107, 20));
        txtProductName.setBounds(new Rectangle(138, 10, 209, 20));

        lblReference.setText("Reference");
        lblReference.setBounds(new Rectangle(21, 122, 105, 20));
        txtPbsReferrence.setBounds(new Rectangle(138, 122, 575, 20));

        lblStatus.setText("Status");
        lblStatus.setBounds(new Rectangle(388, 48, 105, 20));
        cmbStatus.setBounds(new Rectangle(505, 48, 209, 20));

        lblActualFinish.setText("Actual Finish");
        lblActualFinish.setBounds(new Rectangle(388, 85, 105, 20));
        dteActualFinish.setBounds(new Rectangle(505, 85, 209, 20));
        dteActualFinish.setCanSelect(true);

        lblManager.setText("Manager");
        lblManager.setBounds(new Rectangle(21, 48, 107, 20));
        txtPbsManager.setBounds(new Rectangle(138, 48, 209, 20));

        lblProductCode.setText("Product Code");
        lblProductCode.setBounds(new Rectangle(388, 10, 105, 20));
        txtProductCode.setBounds(new Rectangle(505, 10, 209, 20));

        this.add(lblProduntName, null);
        this.add(lblManager, null);
        this.add(lblPlannedFinish, null);
        this.add(lblProductBrief, null);
        this.add(lblProductCode, null);
        this.add(lblActualFinish, null);
        this.add(lblReference);
        this.add(lblStatus);

        this.add(txtProductName, null);
        this.add(txtProductCode, null);
        this.add(dteActualFinish, null);
        this.add(dtePlannedFinish, null);
        this.add(txtPbsReferrence, null);
        this.add(txtPbsManager, null);
        this.add(cmbStatus, null);

        this.add(txaPbsBrief, null);
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(txtProductName);
        compList.add(txtProductCode);
        compList.add(txtPbsManager.getTextComp());
        compList.add(cmbStatus);
        compList.add(dtePlannedFinish);
        compList.add(dteActualFinish);
        compList.add(txtPbsReferrence);
        compList.add(txaPbsBrief);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(txtProductName);
        compList.add(txtProductCode);
        compList.add(txtPbsManager.getTextComp());
        compList.add(cmbStatus);
        compList.add(dtePlannedFinish);
        compList.add(dteActualFinish);
        compList.add(txtPbsReferrence);
        compList.add(txaPbsBrief);
        comFORM.setEnterOrder(this, compList);
    }

    private void setUIComponentName() {
        txtProductName.setName("productName");
        txtProductCode.setName("productCode");
        dteActualFinish.setName("actualFinish");
        dtePlannedFinish.setName("plannedFinish");
        txtPbsReferrence.setName("pbsReferrence");
        txtPbsManager.setName("pbsManager");
        txaPbsBrief.setName("pbsBrief");
        cmbStatus.setName("pbsStatus");
    }

    public static void main(String[] args) {
        VWGeneralWorkArea w = new VWGeneralWorkArea ();
        w.addTab("Pbs",new VwPbsGeneralBase(), true);
        TestPanel.show(w);
    }
}
