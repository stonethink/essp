package server.essp.common.calendar.action;

import c2s.dto.*;

import server.essp.common.calendar.logic.*;
import server.framework.action.*;

import java.util.*;

import javax.servlet.http.*;

public class AcListWorkDayConfig extends AbstractBusinessAction {
    public AcListWorkDayConfig() {
    }

    /**
     * execute
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data VDView_Data*/
    public void executeAct(HttpServletRequest  request,
                        HttpServletResponse response,
                        TransactionData     data) {
//        String sAction = data.getInputInfo().getFunId();
        String calendarId = (String)data.getInputInfo().getInputObj("calendarId");
        WrokCalendarUtilImplS logic = new WrokCalendarUtilImplS();
        List list = logic.listYearWorkDayConfig(calendarId);
        data.getReturnInfo().setReturnObj("listYearWorkDayConfig",list);
    }

}
