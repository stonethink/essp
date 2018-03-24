package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.buget.logic.LgBuget;
import java.util.List;

public class AcBugetLogList extends AbstractESSPAction {
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
        Long budgetRid = (Long) data.getInputInfo().getInputObj("budgetRid");
        LgBuget lg = new LgBuget();
        List budgetLogList = lg.listBudgetLog(budgetRid);
        data.getReturnInfo().setReturnObj("budgetLogList",budgetLogList);
    }
}
