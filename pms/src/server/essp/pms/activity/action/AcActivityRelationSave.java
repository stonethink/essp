package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.List;
import server.essp.pms.activity.logic.LgActivityRelation;

public class AcActivityRelationSave extends AbstractESSPAction {
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

        List relationList = (List) inputInfo.getInputObj("relationList");
        LgActivityRelation logic = new LgActivityRelation();
        logic.updateDtoList(relationList);
    }
}
