package client.essp.common.code.value;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJCheckBox;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwCodeValueGeneralBase extends VWGeneralWorkArea {

    VWJLabel lblValue = new VWJLabel();
    VWJLabel lblStatus = new VWJLabel();
    VWJLabel lblDescription = new VWJLabel();

    VWJText txtValue = new VWJText();
    VWJCheckBox chkStatus = new VWJCheckBox();
    VWJTextArea txaDescription = new VWJTextArea();

    public VwCodeValueGeneralBase() {
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

        lblValue.setText("Code Value");
        lblValue.setBounds(new Rectangle(35, 24, 84, 20));
        txtValue.setBounds(new Rectangle(126, 24, 193, 20));

        lblStatus.setText("Disabled");
        lblStatus.setBounds(new Rectangle(375, 24, 49, 20));
        chkStatus.setBounds(new Rectangle(430, 24, 20, 20));

        lblDescription.setText("Description");
        lblDescription.setBounds(new Rectangle(34, 67, 84, 20));
        txaDescription.setBounds(new Rectangle(126, 67, 505, 70));

        this.add(lblValue);
        this.add(txaDescription);
        this.add(txtValue);
        this.add(lblDescription);
        this.add(lblStatus);
        this.add(chkStatus);
    }

    private void setUIComponentName() {
        txtValue.setName("value");
        chkStatus.setName("status");
        txaDescription.setName("description");
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(txtValue);
        compList.add(chkStatus);
        compList.add(txaDescription);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(txtValue);
        compList.add(chkStatus);
        compList.add(txaDescription);
        comFORM.setEnterOrder(this, compList);
    }

    public static void main(String args[]) {
        TestPanel.show(new VwCodeValueGeneralBase());
    }
}
