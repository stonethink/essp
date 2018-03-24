package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import itf.hr.HrFactory;
import c2s.essp.tc.leave.DtoLeave;
import c2s.essp.common.user.DtoUser;
import java.util.Map;
import java.util.HashMap;
import c2s.dto.DtoComboItem;
import c2s.essp.common.calendar.WorkCalendarBase;
import server.essp.attendance.leave.viewbean.VbLeaveType;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.attendance.leave.logic.LgLeave;
import server.essp.attendance.leave.viewbean.VbLeavePersonalStatus;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import server.essp.tc.hrmanage.logic.LgAttendance;
import c2s.essp.tc.attendance.DtoAttendance;
import server.essp.common.syscode.LgSysParameter;

public class AcAttendanceInit extends AbstractESSPAction {
    public AcAttendanceInit() {
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
         DtoAttendance dto = (DtoAttendance)data.getInputInfo().getInputObj(DtoTcKey.USER_ID);
         String loginId = dto.getLoginId();

         //≤È’“attendance¿‡–Õ
         LgSysParameter lg=new LgSysParameter();
         Vector attendanceTypeComList =lg.listComboSysParas("ATTENDANCE_TYPE");
         data.getReturnInfo().setReturnObj("attendanceTypeComList",attendanceTypeComList);
     }

}
