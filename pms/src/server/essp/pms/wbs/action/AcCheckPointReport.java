package server.essp.pms.wbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.pms.wbs.logic.LgCheckPoint;
import java.util.List;
import c2s.dto.ReturnInfo;
import server.framework.common.Constant;

public class AcCheckPointReport extends AbstractBusinessAction {
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
        String acntRid = request.getParameter("acntRid");
        String wbsRid = request.getParameter("wbsRid");

        LgCheckPoint logic = new LgCheckPoint();
        List checkPointList = logic.listCheckPointReport(new Long(acntRid),
            new Long(wbsRid));
        request.setAttribute(Constant.VIEW_BEAN_KEY, checkPointList);
        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setForwardID("list");
    }
}
