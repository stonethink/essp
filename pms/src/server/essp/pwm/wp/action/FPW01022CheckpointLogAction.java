package server.essp.pwm.wp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import com.wits.util.Parameter;
import server.essp.pwm.wp.logic.FPW01022CheckpointLogInsertLogic;
import server.essp.pwm.wp.logic.FPW01022CheckpointLogSelectLogic;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.pwm.wp.DtoPwWpchklog;

public class FPW01022CheckpointLogAction extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
        AbstractBusinessLogic logic = null;
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String funId = inputInfo.getFunId();

        if (funId.equals("getCheckpointLog") == true) {
            Parameter param = new Parameter();
            param.put("inCheckpointId", (String) inputInfo.getInputObj("inCheckpointId"));

            logic = new FPW01022CheckpointLogSelectLogic();
            logic.setDbAccessor(this.getDbAccessor());
            logic.executeLogic(param);

            returnInfo.setReturnObj("chkLogList", param.get("chkLogList"));
        } else if (funId.equals("saveCheckpointLog") == true) {
            Parameter param = new Parameter();
            param.put("dtoPwWpchklog", (DtoPwWpchklog) inputInfo.getInputObj("dtoPwWpchklog"));

            logic = new FPW01022CheckpointLogInsertLogic();
            logic.setDbAccessor(this.getDbAccessor());
            logic.executeLogic(param);

            returnInfo.setReturnObj("dtoPwWpchklog", param.get("dtoPwWpchklog"));
        }
    }

}
