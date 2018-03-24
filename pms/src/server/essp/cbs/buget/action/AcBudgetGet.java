package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.buget.logic.LgBuget;
import c2s.essp.cbs.budget.DtoCbsBudget;
import c2s.essp.cbs.CbsConstant;
import db.essp.cbs.PmsAcntCost;

public class AcBudgetGet extends AbstractESSPAction {
    /**
     * executeAct
     *1.查找BudgetRid对应的Budget,若找不到
     *2.查找Account对应的类型的Budget，若该类型Budget不存在则初始化该类型的Budget
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        Long budgetRid = (Long) data.getInputInfo().getInputObj("budgetRid");
        Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");
        String budgetType = (String) data.getInputInfo().getInputObj("budgetType");
        LgBuget lg = new LgBuget();
        DtoCbsBudget budget = null;
        if(budgetRid != null)
            budget = lg.getBudget(budgetRid);
        if(budget == null){
            PmsAcntCost acntCost = lg.getAcntCost(acntRid);
            if(CbsConstant.ANTICIPATED_BUDGET.equals(budgetType)){
                if( acntCost != null && acntCost.getAntiRid() != null)
                    budget = lg.getBudget(acntCost.getAntiRid());
                else
                    budget = lg.initAntiBudget(acntRid);
            }else if(CbsConstant.PROPOSED_BUDGET.equals(budgetType)){
                if( acntCost != null && acntCost.getPropRid() != null)
                    budget = lg.getBudget(acntCost.getPropRid());
                else
                    budget = lg.initPropBudget(acntRid);
            }else{
                throw new BusinessException("CBS_BGT","can not init budget of type["+budgetType+"]");
            }
        }
        budget.setAcntRid(acntRid);
        data.getReturnInfo().setReturnObj("budget",budget);
    }
}
