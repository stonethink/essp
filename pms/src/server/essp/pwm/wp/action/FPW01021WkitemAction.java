package server.essp.pwm.wp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import com.wits.util.Parameter;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.wp.logic.FPW01021WkitemDeleteLogic;
import server.essp.pwm.wp.logic.FPW01021WkitemSelectLogic;
import server.essp.pwm.wp.logic.FPW01021WkitemUpdateLogic;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.pwm.wp.DtoPwWkitem;

public class FPW01021WkitemAction extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData transData) throws BusinessException {
        AbstractBusinessLogic logic = null;
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String funId = inputInfo.getFunId();

        if (funId.equals("getWorkItems") == true) {
            Parameter param = new Parameter();
            param.put("inWpId", (Long)inputInfo.getInputObj("inWpId"));

            logic = new FPW01021WkitemSelectLogic();
            logic.executeLogic(param);

            returnInfo.setReturnObj("workItems",param.get("workItems"));
        } else if (funId.equals("saveWorkItemsByWpId") == true) {
            Parameter param = new Parameter();
            param.put("inWpId", (Long)inputInfo.getInputObj("inWpId"));
            param.put("workItems", (List)inputInfo.getInputObj("workItems"));
            param.put("inUserId", (String)inputInfo.getInputObj("inUserId"));

            logic = new FPW01021WkitemUpdateLogic();
            logic.executeLogic(param);

            returnInfo.setReturnObj("workItems", param.get("workItems"));
        } else if (funId.equals("delete") == true) {
            Parameter param = new Parameter();
            param.put("dtoPwWkitem", (DtoPwWkitem)inputInfo.getInputObj("dtoPwWkitem"));

            logic = new FPW01021WkitemDeleteLogic();
            logic.executeLogic(param);
         }
    }

}


