package client.essp.cbs.config;

import java.awt.Rectangle;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import java.util.List;
import java.util.ArrayList;
import client.framework.view.common.comFORM;
import client.framework.model.VMComboBox;
import c2s.essp.cbs.DtoSubject;

public class VwCbsGeneralBase extends VWGeneralWorkArea {
    public VwCbsGeneralBase() {
        try {
            jbInit();
            setTabOrder();
            setEnterOrder();
            setUIComponentName();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * setUIComponentName
     */
    private void setUIComponentName() {
        inputCode.setName("subjectCode");
        inputName.setName("subjectName");
        inputAttribute.setName("subjectAttribute");
        inputBugetCalType.setName("budgetCalType");
        inputCostCalType.setName("costCalType");
    }

    /**
     * setEnterOrder
     */
    private void setEnterOrder() {
        setTabOrder();
    }

    /**
     * setTabOrder
     */
    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(inputCode);
        compList.add(inputName);
        compList.add(inputBugetCalType);
        compList.add(inputCostCalType);
        compList.add(inputAttribute);
        comFORM.setTABOrder(this, compList);
    }

    public void jbInit() throws Exception {
        this.setLayout(null);

        lbCode.setText("Subject Code");
        lbCode.setBounds(new Rectangle(35, 40, 90, 20));
        lbName.setText("Subject Name");
        lbName.setBounds(new Rectangle(300, 40, 90, 20));
        lbBugetCalType.setText("Buget Caculation Type");
        lbBugetCalType.setBounds(new Rectangle(35, 90, 131, 20));
        lbCostCalType.setText("Cost Calculation Type");
        lbCostCalType.setBounds(new Rectangle(300, 90, 131, 20));
        lbAttribute.setText("Attribute");
        lbAttribute.setBounds(new Rectangle(35, 140, 90, 20));

        inputCode.setBounds(new Rectangle(165, 40, 120, 20));
        inputName.setBounds(new Rectangle(426, 40, 120, 20));
        inputBugetCalType.setBounds(new Rectangle(165, 90, 120, 20));
        inputCostCalType.setBounds(new Rectangle(426, 90, 120, 20));
        inputAttribute.setBounds(new Rectangle(165, 140, 120, 20));
        this.add(lbCode);
        this.add(inputName);
        this.add(inputCode);
        this.add(lbName);
        this.add(lbBugetCalType);
        this.add(lbCostCalType);
        this.add(inputBugetCalType);
        this.add(inputCostCalType);
        this.add(inputAttribute);
        this.add(lbAttribute);

        //科目计算类型选项
        String[] calTypeName = new String[]{DtoSubject.TYPE_AUTO_CALCULATE_NAME,
                               DtoSubject.TYPE_ENTRY_NAME};
        String[] calType = new String[]{DtoSubject.TYPE_AUTO_CALCULATE,
                           DtoSubject.TYPE_ENTRY};
        inputBugetCalType.setVMComboBox(VMComboBox.toVMComboBox(calTypeName,calType));
        inputCostCalType.setVMComboBox(VMComboBox.toVMComboBox(calTypeName,calType));
        //科目属性
        String[] attributeName = new String[]{"No Attribute",
                                 DtoSubject.ATTRI_LABOR_SUM,
                                 DtoSubject.ATTRI_NONLABOR_SUM,
                                 DtoSubject.ATTRI_EXPENSE_SUM};
        String[] attributeValue = new String[]{"",
                                 DtoSubject.ATTRI_LABOR_SUM,
                                 DtoSubject.ATTRI_NONLABOR_SUM,
                                 DtoSubject.ATTRI_EXPENSE_SUM};
        inputAttribute.setVMComboBox(VMComboBox.toVMComboBox(attributeName,attributeValue));
    }


    VWJLabel lbCode = new VWJLabel();
    VWJLabel lbName = new VWJLabel();
    VWJLabel lbBugetCalType = new VWJLabel();
    VWJLabel lbCostCalType = new VWJLabel();
    VWJLabel lbAttribute = new VWJLabel();
    VWJText inputCode = new VWJText();
    VWJText inputName = new VWJText();
    VWJComboBox inputBugetCalType = new VWJComboBox();
    VWJComboBox inputCostCalType = new VWJComboBox();
    VWJComboBox inputAttribute = new VWJComboBox();
}
