package server.essp.pwm.wp.action;

import server.framework.action.IBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.pwm.wp.logic.LGGetAccountList;

public class ACGetAccountList implements IBusinessAction {

    public void execute(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data) throws
        Exception {
        LGGetAccountList lgGetAccountList = new LGGetAccountList();
        lgGetAccountList.getAccountList(data);
    }
}
