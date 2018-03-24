package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.buget.logic.LgLaborBgt;
import java.util.List;
import java.util.Date;

public class AcLaborBudgetCaculate extends AbstractESSPAction {
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
        Date beginDate =  (Date) data.getInputInfo().getInputObj("beginDate");
        Date endDate =  (Date) data.getInputInfo().getInputObj("endDate");
        LgLaborBgt lg = new LgLaborBgt();
        List laborBgtList = lg.caculateLaborBudget(acntRid,budgetRid,beginDate,endDate);

        data.getReturnInfo().setReturnObj("laborBgtList",laborBgtList);
    }
}
