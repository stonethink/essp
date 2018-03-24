package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.buget.logic.LgBuget;
import db.essp.cbs.PmsAcntCost;
import c2s.essp.cbs.budget.DtoBudgetLog;
import c2s.essp.common.user.DtoUser;
import c2s.essp.cbs.budget.DtoCbsBudget;
import java.util.Date;

public class AcBudgetLogInit extends AbstractESSPAction {
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
        Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");
        Long budgetRid = (Long) data.getInputInfo().getInputObj("budgetRid");
        LgBuget lg = new LgBuget();
        PmsAcntCost acntCost = lg.getAcntCost(acntRid);
        if(acntCost == null)
            throw new BusinessException("","Before logging a budget ,you must create the budget!");
        DtoBudgetLog log = new DtoBudgetLog();
        log.setBaseAmt(acntCost.getBaseAmt());
        log.setBasePm(acntCost.getBasePm());
        log.setBaseId(acntCost.getBaseId());
        DtoUser user = this.getUser();
        log.setChangedBy(user.getUserLoginId());

        DtoCbsBudget budget = lg.getBudget(budgetRid);

        log.setBudgetRid(budget.getRid());
        Double currentAmt = budget.getCurrentAmt();
        Double currentPm = budget.getCurrentPm();
        log.setTotalBugetAmt(currentAmt);
        log.setTotalBugetPm(currentPm);
        Double lastAmt = budget.getLastAmt();
        Double lastPm = budget.getLastPm();
        if(lastAmt == null)
            log.setChangeBugetAmt(currentAmt);
        else if(currentAmt != null){
            double changeAmt = currentAmt.doubleValue() - lastAmt.doubleValue();
            log.setChangeBugetAmt(new Double(changeAmt));
        }
        if(lastPm == null)
            log.setChangeBugetPm(currentPm);
        else if(currentPm != null){
            double changePm = currentPm.doubleValue() - lastPm.doubleValue();
            log.setChangeBugetPm(new Double(changePm));
        }
        log.setLogDate(new Date());

        data.getReturnInfo().setReturnObj("log",log);
    }
}
