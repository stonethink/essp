package server.essp.cbs.config.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import server.essp.cbs.config.logic.LgPrice;

public class AcPriceSave extends AbstractESSPAction {
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
        List priceList = (List) data.getInputInfo().getInputObj("priceList");
        LgPrice lg = new LgPrice();
        lg.updateDtoList(priceList);
    }
}
