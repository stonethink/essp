package client.essp.cbs.budget;

import c2s.essp.cbs.budget.DtoBudgetParam;
import client.essp.common.view.VWWorkArea;
import com.wits.util.Parameter;
import c2s.essp.cbs.CbsConstant;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import client.essp.common.view.VWGeneralWorkArea;

public class VwBudgetMain extends VWGeneralWorkArea  {
    static final String actionIdInit="FCbsBugetParamInitAction";
    protected DtoBudgetParam budgetParam;

    protected VwBudgetLogList budgetLog;
    protected VwBudget budget;
    protected VwAccountPrice price;

    public VwBudgetMain() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        budgetLog = new VwBudgetLogList();
        this.addTab("Log",budgetLog);

        budget = new VwBudget();
        this.addTab("Budget",budget);

        price = new VwAccountPrice();
        this.addTab("Price",price);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        budgetParam = (DtoBudgetParam) param.get("budgetParam");
    }

    public void refreshWorkArea(){
        if(budgetParam != null){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.actionIdInit);
            inputInfo.setInputObj("budgetParam", budgetParam);
            ReturnInfo returnInfo = accessData(inputInfo);
            if(!returnInfo.isError())
                budgetParam = (DtoBudgetParam) returnInfo.getReturnObj("budgetParam");
            Parameter param = new Parameter();
            param.put("budgetParam",budgetParam);
            budgetLog.setParameter(param);
            budget.setParameter(param);

            Parameter param2 = new Parameter();
            param2.put("priceScope",CbsConstant.SCOPE_ACCOUNT);
            param2.put("acntRid",budgetParam.getAcntRid());
            param2.put("currency",budgetParam.getCurrency());
            if(budgetParam.isReadOnly())
                param2.put("isCanModifyPrice",new Boolean(false));
            else
                param2.put("isCanModifyPrice",new Boolean(budgetParam.isCanModifyPrice()));
            price.setParameter(param2);

            budgetLog.resetUI();
            budget.refreshWorkArea();
            price.resetUI();
        }
    }

}
