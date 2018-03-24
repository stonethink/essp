package client.essp.cbs.config;

import javax.swing.JButton;

import c2s.essp.cbs.DtoSubject;
import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJText;

public class VwCbsListBase extends VWTreeTableWorkArea {
    JButton btnDown;
    JButton btnUp;
    JButton btnLeft;
    JButton btnRight;
    JButton btnExpand;
    JButton btnDisplay;
    private VWJComboBox inputCalType = new VWJComboBox();
    private VWJComboBox inputAttri = new VWJComboBox();
    public VwCbsListBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        //科目计算类型选项
        String[] calTypeName = new String[]{DtoSubject.TYPE_AUTO_CALCULATE_NAME,
                               DtoSubject.TYPE_ENTRY_NAME};
        String[] calType = new String[]{DtoSubject.TYPE_AUTO_CALCULATE,
                           DtoSubject.TYPE_ENTRY};
        inputCalType.setVMComboBox(VMComboBox.toVMComboBox(calTypeName,calType));
        //科目属性
        String[] attributeName = new String[]{"No Attribute",
                                 DtoSubject.ATTRI_LABOR_SUM,
                                 DtoSubject.ATTRI_NONLABOR_SUM,
                                 DtoSubject.ATTRI_EXPENSE_SUM};
        String[] attributeValue = new String[]{"",
                                 DtoSubject.ATTRI_LABOR_SUM,
                                 DtoSubject.ATTRI_NONLABOR_SUM,
                                 DtoSubject.ATTRI_EXPENSE_SUM};
        inputAttri.setVMComboBox(VMComboBox.toVMComboBox(attributeName,attributeValue));
        Object[][] configs = new Object[][] {
                             {"Subject Code", "subjectCode",VMColumnConfig.EDITABLE, null},
                             {"Subject Name", "subjectName", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Attribute", "subjectAttribute", VMColumnConfig.UNEDITABLE, inputAttri},
                             {"Budget Calculation Type", "budgetCalType", VMColumnConfig.UNEDITABLE,inputCalType},
                             {"Cost Calculation Type", "costCalType", VMColumnConfig.UNEDITABLE,inputCalType},
        };
        try {
            super.jbInit(configs, "subjectCode",
                         DtoSubject.class, new NodeViewManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
