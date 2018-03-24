package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcActivityRightMove extends AbstractESSPAction {
    public AcActivityRightMove() {
    }

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
    }

    public static void main(String[] args) {
        AcActivityRightMove acactivityrightmove = new AcActivityRightMove();
    }
}
