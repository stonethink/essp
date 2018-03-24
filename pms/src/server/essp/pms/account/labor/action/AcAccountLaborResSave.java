package server.essp.pms.account.labor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import java.util.List;
import server.essp.framework.action.AbstractESSPAction;

public class AcAccountLaborResSave extends AbstractESSPAction {
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
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        List laborResourceList = (List) inputInfo.getInputObj("laborResourceList");
        LgAccountLaborRes logic = new LgAccountLaborRes();
        logic.updateDtoList(laborResourceList);
    }
}
