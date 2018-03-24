package client.essp.cbs.budget.labor;

import c2s.essp.cbs.budget.DtoResBudgetItem;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;


public class VwLaborBudgetSum extends VWTableWorkArea {
    LaborBgtSumTableModel model;
    public VwLaborBudgetSum() {
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
        //设置标题栏位
        Object[][] configs = new Object[][] {
                             {"Job Code", "resName",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Budget(PM)", "unitNum", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Currency", "currency", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Price", "price", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Budget(AMT)", "amt", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Remark", "description", VMColumnConfig.EDITABLE,new VWJText()},

        };
        try {
            model = new LaborBgtSumTableModel(configs);
            table = new VWJTable(model,new LaborBgtNodeViewManager());
            model.setDtoType(DtoResBudgetItem.class);
            this.add(table.getScrollPane(), null);
            //super.jbInit(configs,DtoResBudgetItem.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public VMTable2 getModel(){
        return model;
    }
}
