package server.essp.cbs.cost.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.cost.logic.LgActivityCost;
import c2s.essp.cbs.cost.DtoActivityCost;

public class AcAccountActivityCostGet extends AbstractESSPAction {
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
        Long activityId = (Long) data.getInputInfo().getInputObj("activityId");

        LgActivityCost lg = new LgActivityCost();
        DtoActivityCost activityCost = lg.getActivityCost(acntRid,activityId);
        data.getReturnInfo().setReturnObj("activityCost",activityCost);

    }
}
