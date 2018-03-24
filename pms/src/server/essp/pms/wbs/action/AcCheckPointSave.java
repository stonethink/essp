package server.essp.pms.wbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.wbs.logic.LgCheckPoint;
import java.util.List;
import server.essp.framework.action.AbstractESSPAction;

public class AcCheckPointSave extends AbstractESSPAction  {
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

        List checkPointList = (List) inputInfo.getInputObj("checkPointList");
        LgCheckPoint logic = new LgCheckPoint();
        logic.updateDtoList(checkPointList);

        returnInfo.setReturnObj("checkPointList",checkPointList);
    }
}
