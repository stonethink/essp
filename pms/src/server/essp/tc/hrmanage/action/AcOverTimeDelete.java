package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.overtime.DtoOverTime;
import server.essp.attendance.overtime.logic.LgOverTime;
import db.essp.attendance.TcOvertime;
import c2s.essp.common.code.DtoKey;

public class AcOverTimeDelete extends AbstractESSPAction {
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
        DtoOverTime dto = (DtoOverTime) data.getInputInfo().getInputObj(DtoKey.DTO);
        LgOverTime lg = new LgOverTime();
        TcOvertime overTime = lg.getOverTime(dto.getRid());
        try {
            lg.getDbAccessor().delete(overTime.getTcOvertimeLogs());
            lg.getDbAccessor().delete(overTime.getTcOvertimeDetails());
            lg.getDbAccessor().delete(overTime);
        } catch (Exception ex) {
            throw new BusinessException("TC_OVERTIME_99999","error delete overtime!",ex);
        }
    }
}
