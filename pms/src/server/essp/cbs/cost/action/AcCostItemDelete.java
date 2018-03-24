package server.essp.cbs.cost.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.cost.logic.LgCost;
import c2s.essp.cbs.cost.DtoCostItem;
import c2s.essp.cbs.cost.DtoActivityCost;
import server.essp.cbs.cost.logic.LgActivityCost;

public class AcCostItemDelete extends AbstractESSPAction {
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
        DtoCostItem dto =  (DtoCostItem) data.getInputInfo().getInputObj("dto");
        LgCost lg = new LgCost();
        lg.deleteCostItem(dto);

        DtoActivityCost activityCost = (DtoActivityCost) data.getInputInfo().getInputObj("activityCost");
        if(activityCost != null){
            LgActivityCost lgActCost = new LgActivityCost();
            lgActCost.updateActivityCost(activityCost);
        }

    }
}
