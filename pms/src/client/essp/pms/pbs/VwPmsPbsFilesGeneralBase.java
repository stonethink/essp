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
import client.essp.common.file.VwJFileButton;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwPmsPbsFilesGeneralBase extends VWGeneralWorkArea {

    VWJLabel lblCreateDate = new VWJLabel();
    VWJLabel lblRemark = new VWJLabel();
    VWJLabel lblName = new VWJLabel();
    VWJLabel lblAttachment = new VWJLabel();
    VWJLabel lblModifyDate = new VWJLabel();
    VWJLabel lblAuthor = new VWJLabel();
    VWJLabel lblVersion = new VWJLabel();
    VWJText txtName = new VWJText();
    VwJFileButton txtAttachment = new VwJFileButton("PBS");
    VWJHrAllocateButton txtAuthor = new VWJHrAllocateButton();
    VWJDate dteCreateDate = new VWJDate();
    VWJDate dteModifyDate = new VWJDate();
    VWJText txtVersion = new VWJText();
    VWJText txtHyperLink = new VWJText();
    VWJTextArea txaRemark = new VWJTextArea();
    public VwPmsPbsFilesGeneralBase() {
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
        this.setPreferredSize(new Dimension(720,220) );

        lblCreateDate.setText("Create Date");
        lblCreateDate.setBounds(new Rectangle(21, 85, 77, 20));
        dteCreateDate.setBounds(new Rectangle(108, 85, 239, 20));
        dteCreateDate.setCanSelect(true);

        lblRemark.setText("Remark");
        lblRemark.setBounds(new Rectangle(21, 122, 77, 20));
        txaRemark.setBounds(new Rectangle(108, 122, 605, 68));

        lblName.setText("Name");
        lblName.setBounds(new Rectangle(21, 10, 77, 20));
        txtName.setBounds(new Rectangle(108, 10, 239, 20));

        lblAttachment.setText("Attachment");
        lblAttachment.setBounds(new Rectangle(388, 10, 77, 20));
        txtAttachment.setBounds(new Rectangle(475, 10, 239, 20));

        lblModifyDate.setText("Modify Date");
        lblModifyDate.setBounds(new Rectangle(388, 85, 105, 20));
        dteModifyDate.setBounds(new Rectangle(475, 85, 239, 20));
        dteModifyDate.setCanSelect(true);

        lblAuthor.setText("Author");
        lblAuthor.setBounds(new Rectangle(21, 48, 77, 20));
        txtAuthor.setBounds(new Rectangle(108, 48, 239, 20));

        lblVersion.setText("Version");
        lblVersion.setBounds(new Rectangle(388, 48, 105, 20));
        txtVersion.setBounds(new Rectangle(475, 48, 239, 20));

        this.add(lblName, null);
        this.add(lblAttachment, null);
        this.add(lblAuthor, null);
        this.add(lblCreateDate, null);
        this.add(lblRemark, null);
        this.add(lblVersion, null);
        this.add(lblModifyDate, null);
        this.add(txtName, null);
        this.add(txtAttachment, null);
        this.add(txtVersion, null);
        this.add(dteModifyDate, null);
        this.add(dteCreateDate, null);
        this.add(txtHyperLink, null);
        this.add(txtAuthor, null);
        this.add(txaRemark, null);
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(txtName);
        //compList.add(txtAttachment);
        compList.add(txtAuthor.getTextComp());
        compList.add(txtVersion);
        compList.add(dteCreateDate);
        compList.add(dteModifyDate);
        compList.add(txtHyperLink);
        compList.add(txaRemark);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(txtName);
        //compList.add(txtAttachment);
        compList.add(txtAuthor.getTextComp());
        compList.add(txtVersion);
        compList.add(dteCreateDate);
        compList.add(dteModifyDate);
        compList.add(txtHyperLink);
        compList.add(txaRemark);
        comFORM.setEnterOrder(this, compList);
    }

    private void setUIComponentName() {
        txtName.setName("fileName");
        txtAttachment.setName("fileLink");
        txtVersion.setName("fileVersion");
        dteModifyDate.setName("fileModDate");
        dteCreateDate.setName("fileCreateDate");
        txtHyperLink.setName("fileLink");
        txtAuthor.setName("fileAuthor");
        txaRemark.setName("fileRemark");
    }

    public static void main(String[] args) {
        VWGeneralWorkArea w = new VWGeneralWorkArea ();
        w.addTab("Files",new VwPmsPbsFilesGeneralBase(), true);
        TestPanel.show(w);
    }
}
