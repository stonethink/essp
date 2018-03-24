package client.essp.cbs.cost;

import c2s.essp.cbs.cost.DtoCbsCostItem;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.view.VWTreeTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.NodeViewManager;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTreeTable;

public class VwCostBase extends VWTreeTableWorkArea {
    CostTreeTableModel model;
    public VwCostBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    protected void jbInit() throws Exception {
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setCanNegative(true);
        Object[][] configs = new Object[][] {
                             {"Subject Code", "subjectCode",VMColumnConfig.EDITABLE, null},
                             {"Subject Name", "subjectName", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Budget(AMT)", "budgetAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Cost(AMT)", "costAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Remaining(AMT)", "remainAmt", VMColumnConfig.UNEDITABLE,inputReal},
//                             {"Cost Date", "costDate", VMColumnConfig.UNEDITABLE,new VWJDate()},
//                             {"Remark", "description", VMColumnConfig.EDITABLE,new VWJText()},
        };
        try {
            model = new CostTreeTableModel(null,configs,"subjectCode");
            model.setDtoType(DtoCbsCostItem.class);
            treeTable = new VWJTreeTable(model, new NodeViewManager());
            TableColumnWidthSetter.set(treeTable);
            this.add(treeTable.getScrollPane(), null);
//            super.jbInit(configs, "subjectCode",
//                         DtoCbsCostItem.class, new NodeViewManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

