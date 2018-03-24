package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.common.code.DtoKey;
import c2s.essp.tc.leave.DtoLeave;
import server.essp.attendance.leave.logic.LgLeave;
import db.essp.attendance.TcLeaveMain;

public class AcLeaveDelete extends AbstractESSPAction {
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
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        DtoLeave dto = (DtoLeave) data.getInputInfo().getInputObj(DtoKey.DTO);
        try {
            TcLeaveMain leave = (TcLeaveMain)this.getDbAccessor().load(TcLeaveMain.class, dto.getRid());
            LgLeave lg = new LgLeave();
            double hours = leave.getActualTotalHours().doubleValue();
            lg.returnUseLeaveHours(leave.getLoginId(),leave.getLeaveName(),hours);
            this.getDbAccessor().delete(leave.getTcLeaveLogs());
            this.getDbAccessor().delete(leave.getTcLeaveDetails());
            this.getDbAccessor().delete(leave);
        } catch (Exception ex) {
            throw new BusinessException("HR_LEAVE_0005","error deleting leave",ex);
        }
    }
}
