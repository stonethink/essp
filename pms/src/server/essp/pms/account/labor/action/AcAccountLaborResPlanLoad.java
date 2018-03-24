package server.essp.pms.account.labor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.dto.InputInfo;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import java.util.List;

public class AcAccountLaborResPlanLoad extends AbstractESSPAction {
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

        DtoAcntLoaborRes dto = (DtoAcntLoaborRes) inputInfo.getInputObj("laborResource");
        LgAccountLaborRes logic = new LgAccountLaborRes();
        List list = logic.getLaborResPlan(dto.getRid());
        dto.setPlanDetail(list);
    }
}
