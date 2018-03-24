package server.essp.pwm.wp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pwm.wp.DtoPwWpchk;
import com.wits.util.Parameter;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.wp.logic.FPW01022CheckpointDeleteLogic;
import server.essp.pwm.wp.logic.FPW01022CheckpointSelectLogic;
import server.essp.pwm.wp.logic.FPW01022CheckpointUpdateLogic;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class FPW01022CheckpointAction extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
        AbstractBusinessLogic logic = null;
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String funId = inputInfo.getFunId();

        if (funId.equals("select") == true) {
            Parameter param = new Parameter();
            param.put("inWpId", (Long) inputInfo.getInputObj("inWpId"));
            //param.put("inUserId", (String) inputInfo.getInputObj("inUserId"));

            logic = new FPW01022CheckpointSelectLogic();
            logic.setDbAccessor(this.getDbAccessor());
            logic.executeLogic(param);

            returnInfo.setReturnObj("checkpointList", param.get("checkpointList"));
            returnInfo.setReturnObj("isAssignby", param.get("isAssignby"));
        } else if (funId.equals("update") == true) {
            Parameter param = new Parameter();
            param.put("checkpointList", (List) inputInfo.getInputObj("checkpointList"));
            param.put("inWpId", (Long) inputInfo.getInputObj("inWpId"));

            logic = new FPW01022CheckpointUpdateLogic();
            logic.setDbAccessor(this.getDbAccessor());
            logic.executeLogic(param);

            returnInfo.setReturnObj("checkpointList", param.get("checkpointList"));
        } else if (funId.equals("delete") == true) {
            Parameter param = new Parameter();
            param.put("dtoPwWpchk", (DtoPwWpchk)inputInfo.getInputObj("dtoPwWpchk"));

            logic = new FPW01022CheckpointDeleteLogic();
            logic.executeLogic(param);
         }

    }

}
