package client.essp.pms.account.budget;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.budget.DtoBudgetParam;
import c2s.essp.cbs.budget.DtoCbsBudget;
import client.essp.cbs.budget.VwAccountPrice;
import client.essp.cbs.budget.VwBudgetLogList;
import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.Parameter;

public class VwAccountBudgetMain extends VWGeneralWorkArea {
    static final String actionIdInit="FCbsBugetParamInitAction";
    static final String actionIdGetBudget = "FCbsBudgetGetAction";

    protected DtoBudgetParam budgetParam;
    VwAccountLaborBudget budget;
    VwBudgetLogList budgetLog;
    VwAccountPrice price;

    public VwAccountBudgetMain() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.budgetLog = new VwBudgetLogList();
        this.addTab("Log",budgetLog);

        budget = new VwAccountLaborBudget();
        this.addTab("Labor Budget",budget);

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
            price.resetUI();


            InputInfo inputInfo2 = new InputInfo();
            inputInfo2.setActionId(this.actionIdGetBudget);
            inputInfo2.setInputObj("acntRid",budgetParam.getAcntRid());
            inputInfo2.setInputObj("budgetType",budgetParam.getBudgetType());
            inputInfo2.setInputObj("budgetRid",budgetParam.getBudgetRid());
            ReturnInfo returnInfo2 = accessData(inputInfo2);
            if(!returnInfo2.isError()){
                DtoCbsBudget budgetDto = (DtoCbsBudget) returnInfo2.getReturnObj("budget");
                if(budgetDto != null){
                    Parameter param3 = new Parameter();
                    param3.put("budget",budgetDto);
                    budget.setParameter(param3);
                    budget.resetUI();
                }
            }
        }
    }
}
