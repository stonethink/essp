package server.essp.cbs.config.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.cbs.DtoCbs;
import server.essp.cbs.config.logic.LgCbs;

public class AcCbsSave extends AbstractESSPAction {
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
        DtoCbs cbs = (DtoCbs) data.getInputInfo().getInputObj("cbs");
        LgCbs lg = new LgCbs();
        lg.updateCbsDefine(cbs);
    }
}
