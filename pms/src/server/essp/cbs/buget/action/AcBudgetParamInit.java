package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.cbs.budget.DtoBudgetParam;
import server.essp.cbs.buget.logic.LgBuget;
import db.essp.cbs.PmsAcntCost;
import itf.account.AccountFactory;
import c2s.essp.cbs.CbsConstant;

public class AcBudgetParamInit extends AbstractESSPAction {
    /**
     * executeAct
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
        DtoBudgetParam budgetParam = (DtoBudgetParam) data.getInputInfo().getInputObj("budgetParam");
        if(budgetParam == null || budgetParam.getAcntRid() == null || budgetParam.getBudgetType() == null)
            throw new BusinessException("CBS_BGT_0000","illegal budget parameter!");
        Long acntRid = budgetParam.getAcntRid();
        String budgetType = budgetParam.getBudgetType();
        String currency = AccountFactory.create().getAccountByRID(acntRid).getCurrency();
        budgetParam.setCurrency(currency);
        LgBuget lg = new LgBuget();
        PmsAcntCost acntCost = lg.getAcntCost(acntRid);
        if(acntCost != null){
            if(CbsConstant.ANTICIPATED_BUDGET.equals(budgetType)){
                budgetParam.setBudgetRid(acntCost.getAntiRid());
            }else if(CbsConstant.PROPOSED_BUDGET.equals(budgetType)){
                budgetParam.setBudgetRid(acntCost.getPropRid());
            }
        }
        data.getReturnInfo().setReturnObj("budgetParam",budgetParam);
    }
}
