package server.essp.cbs.buget.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.cbs.buget.logic.LgLaborBgt;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcLaborBudgetList extends AbstractESSPAction {
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
        Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");

        LgLaborBgt lg = new LgLaborBgt();
        List laborBgtList = lg.listLaborBudget(budgetRid,acntRid);
//        List laborBgtList = lg.listLaborBudgetWithSum(laborBgt);

        data.getReturnInfo().setReturnObj("laborBgtList",laborBgtList);
    }

}
