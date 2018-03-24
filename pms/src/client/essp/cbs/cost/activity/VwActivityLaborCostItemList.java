package client.essp.cbs.cost.activity;

import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.cost.DtoAcitvityLaborCost;
import c2s.essp.cbs.cost.DtoActivityCost;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

public class VwActivityLaborCostItemList extends VWTableWorkArea {
    static final String actionIdCostItemList = "FCbsActivytCostItemListAction";

    DtoActivityCost activityCost;
    List costItemList;


    VWJReal inputReal = new VWJReal();
    private Object attribute;
    public VwActivityLaborCostItemList() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        inputReal.setMaxInputDecimalDigit(2);
        inputReal.setMaxInputIntegerDigit(8);
        inputReal.setCanNegative(true);
        Object[][] configs = new Object[][] {
                             {"JobCode", "jobCode", VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Price", "price", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Budget PH", "budgetPh", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Budget Amt", "budgetAmt", VMColumnConfig.UNEDITABLE, inputReal},
                             {"Actual PH", "actualPh", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Actual Amt", "actualAmt", VMColumnConfig.UNEDITABLE,inputReal},
                             {"Remain", "remain", VMColumnConfig.UNEDITABLE,inputReal},
        };
        super.jbInit(configs, DtoAcitvityLaborCost.class);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        activityCost = (DtoActivityCost) param.get("activityCost");
        attribute = (String)param.get("attribute");
    }

    public void resetUI(){
        if (activityCost == null || activityCost.getAcntRid() == null
            || activityCost.getActivityId() == null) {
            diableUI();
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdCostItemList);
        inputInfo.setInputObj("attribute", attribute);
        inputInfo.setInputObj("acntRid", activityCost.getAcntRid());
        inputInfo.setInputObj("activityId", activityCost.getActivityId());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            costItemList = (List) returnInfo.getReturnObj("costItemList");
            table.setRows(costItemList);
        }
    }
    private void diableUI() {
        this.getButtonPanel().setEnabled(false);
        this.getTable().setEnabled(false);
    }
}
