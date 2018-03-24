package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.form.AfLeaveType;
import server.essp.attendance.leave.logic.LgLeaveType;

public class AcLeaveTypeAdd extends AbstractESSPAction {
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
        AfLeaveType form = (AfLeaveType) this.getForm();
        LgLeaveType lg = new LgLeaveType();
        lg.addLeaveType(form);
        data.getReturnInfo().setForwardID("success");
    }
}
