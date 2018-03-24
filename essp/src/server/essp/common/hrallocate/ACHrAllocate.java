package server.essp.common.hrallocate;

import server.framework.action.IBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;

public class ACHrAllocate implements IBusinessAction {

    public void execute(HttpServletRequest request,
                        HttpServletResponse response, TransactionData transData) throws
        Exception {
        String funId = transData.getInputInfo().getFunId();

        LGHrAllocate lg = new LGHrAllocate();

        if (funId != null && 
        		funId.equals("getUserName")) {
            lg.getUserName(transData);
        }
   }
}
