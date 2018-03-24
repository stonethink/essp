package client.essp.cbs.cost.activity;

import c2s.essp.cbs.cost.DtoActivityCost;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJTable;
import client.essp.cbs.cost.labor.SumNodeViewManager;

public class VwActivityCostListBase extends VWTableWorkArea {
    public VwActivityCostListBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);
        Object[][] configs = new Object[][] {
                             {"Activity ID", "activityCode",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Activity Name", "activityName", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Cost Date", "costDate", VMColumnConfig.UNEDITABLE,new VWJDate(),Boolean.TRUE},
                             {"Budget PH", "budgetPh", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Budget Labor", "budgetLaborAmt", VMColumnConfig.UNEDITABLE,inputReal,Boolean.TRUE},
                             {"Budget NonLabor", "budgetNonlaborAmt", VMColumnConfig.UNEDITABLE,inputReal,Boolean.TRUE},
                             {"Budget Expense", "budgetExpAmt", VMColumnConfig.UNEDITABLE,inputReal,Boolean.TRUE},
                             {"Budget(AMT)", "budgetAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Actual PH", "actualPh", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Actual Labor", "actualLaborAmt", VMColumnConfig.UNEDITABLE,inputReal,Boolean.TRUE},
                             {"Actual NonLabor", "actualNonlaborAmt", VMColumnConfig.UNEDITABLE,inputReal,Boolean.TRUE},
                             {"Actual Expense", "actualExpAmt", VMColumnConfig.UNEDITABLE,inputReal,Boolean.TRUE},
                             {"Cost(AMT)", "actualAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Remaining(AMT)", "remain", VMColumnConfig.UNEDITABLE,inputReal},

        };
        try {
        model = new VMTable2(configs);
        model.setDtoType(DtoActivityCost.class);
        table = new VWJTable(model,new SumNodeViewManager());
        this.add(table.getScrollPane(), null);
//            super.jbInit(configs, DtoActivityCost.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
