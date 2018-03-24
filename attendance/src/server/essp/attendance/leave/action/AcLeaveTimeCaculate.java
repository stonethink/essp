package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.form.AfLeaveTimeCaculate;
import server.essp.attendance.leave.logic.LgLeave;
import java.util.Map;
import java.util.Iterator;

public class AcLeaveTimeCaculate extends AbstractESSPAction {
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
        AfLeaveTimeCaculate form = (AfLeaveTimeCaculate) this.getForm();
        LgLeave lg = new LgLeave();
        Map dayHoursMap = lg.caculateWorkHours(form.getDateFrom(),form.getDateTo(),
                          form.getTimeFrom(),form.getTimeTo());
        float leaveTime = 0f;
        Iterator it = dayHoursMap.values().iterator();
        while(it.hasNext()){
           Float f =  (Float) it.next();
           leaveTime += f.floatValue();
        }
        request.setAttribute("LeaveTime",new Float(leaveTime));
        data.getReturnInfo().setForwardID("success");
    }
}
