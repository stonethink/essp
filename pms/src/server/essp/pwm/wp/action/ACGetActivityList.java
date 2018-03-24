package server.essp.pwm.wp.action;

import server.framework.action.IBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.pwm.wp.logic.LGGetActivityList;

public class ACGetActivityList implements IBusinessAction {

    public void execute(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data) throws
        Exception {
        LGGetActivityList lg = new LGGetActivityList();
        lg.getActivityList(data);
    }
}
