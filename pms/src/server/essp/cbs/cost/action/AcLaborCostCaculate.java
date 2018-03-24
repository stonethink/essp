package server.essp.cbs.cost.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.cost.logic.LgLaborCost;
import java.util.List;

public class AcLaborCostCaculate extends AbstractESSPAction {
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
        LgLaborCost lg = new LgLaborCost();
        lg.caculateLaborCost(acntRid);
        List laborCostList = lg.listLaborCostDto(acntRid);
        data.getReturnInfo().setReturnObj("laborCostList",laborCostList);
    }
}
