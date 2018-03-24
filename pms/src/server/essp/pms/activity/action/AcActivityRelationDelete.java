package server.essp.pms.activity.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoActivityRelation;
import c2s.dto.InputInfo;
import server.essp.pms.activity.logic.LgActivityRelation;

public class AcActivityRelationDelete extends AbstractESSPAction {
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

        DtoActivityRelation relation = (DtoActivityRelation) inputInfo.getInputObj("relation");
        LgActivityRelation logic = new LgActivityRelation();
        logic.deleteActivityRelation(relation);
    }
}