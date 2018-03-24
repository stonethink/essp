package client.essp.ebs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.TestPanel;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwEbsGroupBase extends VWGeneralWorkArea {

    VWJLabel lblEbsName = new VWJLabel();
    VWJText txtEbsName = new VWJText();
    VWJLabel lblEbsDescription = new VWJLabel();
    VWJText txtEbsId = new VWJText();
    VWJLabel lblEbsStatus = new VWJLabel();
    VWJComboBox txtEbsStatus = new VWJComboBox();
    VWJLabel lblEbsManager = new VWJLabel();
    VWJHrAllocateButton txtEbsManager = new VWJHrAllocateButton();
    VWJLabel lblEbsId = new VWJLabel();
    VWJTextArea txaEbsDescription = new VWJTextArea();

    public VwEbsGroupBase() {
        try {
            jbInit();

            setUIComponentName();
            setEnterOrder();
            setTabOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //建议在画好页面后再另添代码
    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700,200));

        lblEbsName.setText("EBS Name");
        lblEbsName.setBounds(new Rectangle(350, 26, 72, 20));
        txtEbsName.setBounds(new Rectangle(428, 24, 204, 20));

        lblEbsId.setText("EBS Code");
        lblEbsId.setBounds(new Rectangle(35, 24, 84, 20));
        txtEbsId.setBounds(new Rectangle(126, 24, 193, 20));

        lblEbsStatus.setText("Status");
        lblEbsStatus.setBounds(new Rectangle(350, 55, 72, 20));
        txtEbsStatus.setBounds(new Rectangle(428, 55, 204, 20));

        lblEbsManager.setText("EBS Manager");
        lblEbsManager.setBounds(new Rectangle(35, 55, 84, 20));
        txtEbsManager.setBounds(new Rectangle(126, 55, 193, 20));

        lblEbsDescription.setText("Brief");
        lblEbsDescription.setBounds(new Rectangle(35, 88, 84, 20));
        txaEbsDescription.setBounds(new Rectangle(126, 88, 505, 70));

        this.add(lblEbsId);
        this.add(txtEbsName);
        this.add(txaEbsDescription);
        this.add(txtEbsId);
        this.add(lblEbsManager);
        this.add(lblEbsStatus);
        this.add(txtEbsStatus);
        this.add(txtEbsManager);
        this.add(lblEbsName);
        this.add(lblEbsDescription);
        this.add(txtEbsManager, null);
    }

    private void setUIComponentName() {
        txtEbsName.setName("ebsName");
        txtEbsId.setName("ebsId");
        txaEbsDescription.setName("ebsDescription");
        txtEbsManager.setName("ebsManager");
        txtEbsStatus.setName("ebsStatus");
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(txtEbsId);
        compList.add(txtEbsName);
        compList.add(txtEbsManager);
        compList.add(txtEbsStatus);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(txtEbsId);
        compList.add(txtEbsName);
        compList.add(txtEbsManager);
        compList.add(txtEbsStatus);

        comFORM.setEnterOrder(this, compList);
    }

    public static void main(String args[]) {
        TestPanel.show(new VwEbsGroupBase());
    }


}
