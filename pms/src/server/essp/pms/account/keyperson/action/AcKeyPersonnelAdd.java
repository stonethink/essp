package server.essp.pms.account.keyperson.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import c2s.dto.InputInfo;
import server.essp.pms.account.keyperson.logic.LgAccountKeyPersonnel;

public class AcKeyPersonnelAdd extends AbstractESSPAction {
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
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();

        DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) inputInfo.getInputObj("dto");
        LgAccountKeyPersonnel logic = new LgAccountKeyPersonnel();
        logic.add(dto);
    }
}
