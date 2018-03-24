package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.logic.LgLeaveType;
import db.essp.attendance.TcLeaveType;
import server.essp.attendance.leave.viewbean.VbLeaveType;

public class AcLeaveTypeUpdatePre extends AbstractESSPAction {
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
        String leaveName = request.getParameter("leaveName");
        LgLeaveType lg = new LgLeaveType();
        TcLeaveType leaveType = lg.getLeaveType(leaveName);
        if(leaveType == null)
            throw new BusinessException("TC_LV_10000","The leave type ["+leaveName+"] does not exsit!");
        VbLeaveType webVo = new VbLeaveType();
        DtoUtil.copyBeanToBean(webVo,leaveType);
        webVo.setFunctionId(VbLeaveType.FUN_ID_EDIT);

        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
