package server.essp.cbs.buget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import server.essp.cbs.buget.logic.LgLaborBgt;

public class AcLaborBudgetSave extends AbstractESSPAction {
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
        List laborBgtList = (List) data.getInputInfo().getInputObj("laborBgtList");

        LgLaborBgt lg = new LgLaborBgt();
        lg.updateLaborBgtList(laborBgtList);
    }
}
