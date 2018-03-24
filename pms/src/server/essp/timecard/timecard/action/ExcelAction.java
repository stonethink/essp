package server.essp.timecard.timecard.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import com.wits.util.Parameter;
import server.essp.timecard.timecard.logic.*;

public class ExcelAction extends AbstractBusinessAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
                           BusinessException {
        ExcelLogic lgPwWp = new ExcelLogic();
        Parameter param = new Parameter();
        param.put("TransactionData", data);
        lgPwWp.setDbAccessor(this.getDbAccessor());
        lgPwWp.executeLogic(param);
        data.getReturnInfo().setReturnObj("SERVER_FILE",param.get("OutPutFileName"));
    }

}
