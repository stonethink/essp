package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.attendance.DtoAttendance;
import c2s.essp.common.code.DtoKey;
import server.essp.tc.hrmanage.logic.LgAttendance;

public class AcAttendanceDelete extends AbstractESSPAction {
    public AcAttendanceDelete() {
    }

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
         DtoAttendance dto = (DtoAttendance) data.getInputInfo().getInputObj(DtoKey.DTO);
         LgAttendance lgAttendance=new LgAttendance();
         lgAttendance.delete(dto);
    }
}
