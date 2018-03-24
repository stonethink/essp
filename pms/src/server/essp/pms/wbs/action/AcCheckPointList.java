package server.essp.pms.wbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.wbs.logic.LgMileStone;
import java.util.List;
import server.essp.pms.wbs.logic.LgCheckPoint;
import server.essp.framework.action.AbstractESSPAction;

public class AcCheckPointList extends AbstractESSPAction  {
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

        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        Long wbsRid = (Long) inputInfo.getInputObj("wbsRid");

        LgCheckPoint logic = new LgCheckPoint();
        List checkPointList = logic.listCheckPointDto(acntRid,wbsRid);

        returnInfo.setReturnObj("checkPointList",checkPointList);
    }
}
