package client.essp.pms.account.keyperson;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.TestPanel;

public class VwAcntKeyPersonnelGeneralBase extends VWGeneralWorkArea {
    //定义标签
    VWJLabel lbType = new VWJLabel();
    VWJLabel lbLoginId = new VWJLabel();
    VWJLabel lbPassword = new VWJLabel();
    VWJLabel lbUserName = new VWJLabel();
    VWJLabel lbOrganization = new VWJLabel();
    VWJLabel lbTitle = new VWJLabel();
    VWJLabel lbPhone = new VWJLabel();
    VWJLabel lbFax = new VWJLabel();
    VWJLabel lbEmail = new VWJLabel();
    VWJLabel lbEnable = new VWJLabel();
    //定义输入控件
    VWJComboBox inputType = new VWJComboBox();
    VWJHrAllocateButton inputLoginId  = new VWJHrAllocateButton();
    VWJText inputPassword = new VWJText();
    VWJText inputUserName = new VWJText();
    VWJText inputOrganization = new VWJText();
    VWJText inputTitle = new VWJText();
    VWJText inputPhone = new VWJText();
    VWJText inputFax = new VWJText();
    VWJText inputEmail = new VWJText();
    VWJCheckBox inputEnable = new VWJCheckBox();
    public VwAcntKeyPersonnelGeneralBase() {
        try {
            jbInit();
            initUI();
            setTabOrder();
            setEnterOrder();
            setUIComponentName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setLayout(null);

        lbType.setText("Type");
        lbType.setBounds(new Rectangle(40, 30, 80, 30));

        lbLoginId.setText("Login Id");
        lbLoginId.setBounds(new Rectangle(40, 85, 80, 30));

        lbPassword.setText("Password");
        lbPassword.setBounds(new Rectangle(280, 85, 80, 30));

        lbUserName.setText("User Name");
        lbUserName.setBounds(new Rectangle(40, 140, 80, 30));

        lbOrganization.setText("Organization");
        lbOrganization.setBounds(new Rectangle(280, 140, 80, 30));

        lbTitle.setText("Title");
        lbTitle.setBounds(new Rectangle(40, 195, 80, 30));

        lbPhone.setText("Phone");
        lbPhone.setBounds(new Rectangle(280, 195, 80, 30));

        lbFax.setText("Fax");
        lbFax.setBounds(new Rectangle(40, 260, 80, 30));

        lbEmail.setText("Email");
        lbEmail.setBounds(new Rectangle(280, 260, 80, 30));

        lbEnable.setText("Enable");
        lbEnable.setBounds(new Rectangle(40, 305, 80, 30));

        inputType.setBounds(new Rectangle(110, 30, 150, 25));

        inputLoginId.setBounds(new Rectangle(110, 85, 150, 25));
        inputLoginId.getButtonComp().setEnabled(false);
        inputLoginId.getTextComp().setMaxByteLength(50);
        inputLoginId.setShowName(false);

        inputPassword.setBounds(new Rectangle(360, 85, 150, 25));
        inputPassword.setMaxByteLength(20);

        inputUserName.setBounds(new Rectangle(110, 140, 150, 25));
        inputUserName.setMaxByteLength(50);

        inputOrganization.setBounds(new Rectangle(360, 140, 150, 25));
        inputOrganization.setMaxByteLength(100);

        inputTitle.setBounds(new Rectangle(110, 195, 150, 25));
        inputTitle.setMaxByteLength(100);

        inputPhone.setBounds(new Rectangle(360, 195, 150, 25));
        inputPhone.setMaxByteLength(50);

        inputFax.setBounds(new Rectangle(110, 260, 150, 25));
        inputFax.setMaxByteLength(50);

        inputEmail.setBounds(new Rectangle(360, 260, 150, 25));
        inputEmail.setMaxByteLength(100);

        inputEnable.setBounds(new Rectangle(110, 315, 15, 15));
        this.add(lbUserName);
        this.add(inputOrganization);
        this.add(inputLoginId);
        this.add(lbType);
        this.add(inputType);
        this.add(inputUserName);
        this.add(inputTitle);
        this.add(lbTitle);
        this.add(lbFax);
        this.add(inputFax);
        this.add(inputEnable);
        this.add(lbLoginId);
        this.add(lbOrganization);
        this.add(inputPhone);
        this.add(inputEmail);
        this.add(lbEmail);
        this.add(lbPhone);
        this.add(lbEnable);
        this.add(lbPassword);
        this.add(inputPassword);
    }

    private void initUI() {
        inputType.setVMComboBox(DtoAcntKeyPersonnel.KEY_PERSON_TYPES);
        inputType.setUICValue(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0]);
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(inputType);
        compList.add(inputLoginId.getTextComp());
        compList.add(inputPassword);
        compList.add(inputUserName);
        compList.add(inputOrganization);
        compList.add(inputTitle);
        compList.add(inputPhone);
        compList.add(inputFax);
        compList.add(inputEmail);
        compList.add(inputEnable);
        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(inputType);
        compList.add(inputLoginId.getTextComp());
        compList.add(inputUserName);
        compList.add(inputPassword);
        compList.add(inputOrganization);
        compList.add(inputTitle);
        compList.add(inputPhone);
        compList.add(inputFax);
        compList.add(inputEmail);
        compList.add(inputEnable);
        comFORM.setEnterOrder(this, compList);
    }

    private void setUIComponentName() {
        inputType.setName("typeName");
        inputPassword.setName("password");
        inputLoginId.setName("loginId");
        inputUserName.setName("userName");
        inputOrganization.setName("organization");
        inputTitle.setName("title");
        inputPhone.setName("phone");
        inputFax.setName("fax");
        inputEmail.setName("email");
        //inputEnable.setName("enable");
    }
    public static void main(String[] args) {
        VWWorkArea resource = new VWWorkArea();
        VwAcntKeyPersonnelGeneralBase general = new VwAcntKeyPersonnelGeneralBase();
        resource.addTab("add", general);
        TestPanel.show(resource);
    }

}
