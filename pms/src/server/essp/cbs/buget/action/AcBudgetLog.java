package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.cbs.budget.DtoBudgetLog;
import server.essp.cbs.buget.logic.LgBuget;

public class AcBudgetLog extends AbstractESSPAction {
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
        DtoBudgetLog budgetLog = (DtoBudgetLog) data.getInputInfo().getInputObj("budgetLog");

        LgBuget lg = new LgBuget();
        lg.addBudgetLog(budgetLog);
    }
}
