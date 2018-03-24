package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.config.logic.LgPrice;
import java.util.List;

public class AcAcountPriceInit extends AbstractESSPAction {
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
        LgPrice lg = new LgPrice();
        List priceList = lg.intAcntPrice(acntRid);
        data.getReturnInfo().setReturnObj("priceList",priceList);
    }
}
