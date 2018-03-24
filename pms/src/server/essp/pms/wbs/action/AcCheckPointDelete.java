package server.essp.pms.wbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import server.essp.pms.wbs.logic.LgCheckPoint;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.essp.pms.wbs.DtoCheckPoint;

public class AcCheckPointDelete extends AbstractESSPAction {
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

        DtoCheckPoint dto  = (DtoCheckPoint) inputInfo.getInputObj("dto");
        LgCheckPoint logic = new LgCheckPoint();
        logic.delete(dto);
    }
}
