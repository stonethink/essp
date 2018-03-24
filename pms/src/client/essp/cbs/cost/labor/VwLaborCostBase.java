package client.essp.cbs.cost.labor;

import c2s.essp.cbs.cost.DtoResCostItem;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import java.util.ArrayList;
import java.util.List;
import c2s.essp.cbs.cost.DtoActivityCostSum;
import c2s.essp.cbs.cost.DtoResCostSum;

public class VwLaborCostBase extends VWTableWorkArea {
    LaborCostTableModel model;
    public VwLaborCostBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        //设置标题栏位
        VWJReal inputReal = new VWJReal();
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);
        Object[][] configs = new Object[][] {
                             {"Job Code", "resName", VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Price", "price", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Budget(PM)", "budgetUnitNum", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Actual(PM)", "costUnitNum", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Remaining(PM)", "remainUnitNum", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Budget(AMT)", "budgetAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Actual(AMT)", "costAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Remaining(AMT)", "remainAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Cost Date", "rut", VMColumnConfig.UNEDITABLE,new VWJDate()},
        };

        try {
            model = new LaborCostTableModel(configs);
            model.setDtoType(DtoResCostItem.class);
            table = new VWJTable(model,new SumNodeViewManager());
            this.add(table.getScrollPane(), null);
//            super.jbInit(configs, );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        VWWorkArea w1 = new VWWorkArea();
        VwLaborCostBase tt = new VwLaborCostBase();
        w1.addTab("Labor Resource", tt);
        List l = new ArrayList();
        DtoResCostSum sum = new DtoResCostSum();
        DtoResCostItem item = new DtoResCostItem();
        item.setCurrency("abcd");
        l.add(sum);
        l.add(item);
        tt.getTable().setRows(l);
        TestPanel.show(w1);
    }
}
