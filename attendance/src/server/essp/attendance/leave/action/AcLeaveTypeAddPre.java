package server.essp.attendance.leave.action;

import server.essp.attendance.leave.viewbean.VbLeaveType;
import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

public class AcLeaveTypeAddPre extends AbstractESSPAction {
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
        VbLeaveType webVo = new VbLeaveType();
        webVo.setRelation(c2s.essp.attendance.Constant.LEAVE_RELATION_NO);
        webVo.setStatus(Constant.RST_NORMAL);
        webVo.setFunctionId(VbLeaveType.FUN_ID_ADD);
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
