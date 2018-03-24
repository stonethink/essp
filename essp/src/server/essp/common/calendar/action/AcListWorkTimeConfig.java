package server.essp.common.calendar.action;

import c2s.dto.*;

import server.essp.common.calendar.logic.*;
import server.framework.action.*;

import java.util.*;

import javax.servlet.http.*;

public class AcListWorkTimeConfig extends AbstractBusinessAction {
    public AcListWorkTimeConfig() {
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
        String workTimeId = (String)data.getInputInfo().getInputObj("workTimeId");
        WrokTimeUtilImplS logic = new WrokTimeUtilImplS();
        List list = logic.listWorkTimeConfig(workTimeId);
        data.getReturnInfo().setReturnObj("listWorkTimeConfig",list);
    }

}
