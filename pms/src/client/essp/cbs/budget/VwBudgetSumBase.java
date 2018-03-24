package client.essp.cbs.budget;

import java.awt.Dimension;

import c2s.essp.cbs.budget.DtoCbsBudgetItem;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTreeTable;

public class VwBudgetSumBase  extends VWTreeTableWorkArea {
    BugetSumTableModel model;
    public VwBudgetSumBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(680, 240));
        //设置标题栏位
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputIntegerDigit(10);
        inputReal.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] {
                             {"Subject", "subjectCode",VMColumnConfig.EDITABLE, null},
                             {"Budget(AMT)", "amt", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Remark", "description", VMColumnConfig.EDITABLE, new VWJText()},
        };
        try {
            model = new BugetSumTableModel(null,configs,"subjectCode");
            model.setDtoType(DtoCbsBudgetItem.class);
            treeTable = new VWJTreeTable(model, new NodeViewManager());
            TableColumnWidthSetter.set(treeTable);
            this.add(treeTable.getScrollPane(), null);
//            super.jbInit(configs, "subjectCode",DtoCbsBudgetItem.class, new NodeViewManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
