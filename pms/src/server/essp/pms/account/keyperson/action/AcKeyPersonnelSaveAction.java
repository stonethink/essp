package server.essp.pms.account.keyperson.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.pms.account.keyperson.logic.LgAccountKeyPersonnel;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.framework.action.AbstractESSPAction;

public class AcKeyPersonnelSaveAction extends AbstractESSPAction {
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

        List keyPersonnelList = (List) inputInfo.getInputObj("keyPersonnelList");
        LgAccountKeyPersonnel logic = new LgAccountKeyPersonnel();
        logic.updateDtoList(keyPersonnelList);

        returnInfo.setReturnObj("keyPersonnelList",keyPersonnelList);

    }
}
