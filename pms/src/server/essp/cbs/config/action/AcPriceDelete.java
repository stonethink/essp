package server.essp.cbs.config.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.cbs.DtoPrice;
import server.essp.cbs.config.logic.LgPrice;

public class AcPriceDelete extends AbstractESSPAction {
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
        DtoPrice dto = (DtoPrice) data.getInputInfo().getInputObj("dto");
        LgPrice lg = new LgPrice();
        lg.deletePrice(dto);
    }
}
