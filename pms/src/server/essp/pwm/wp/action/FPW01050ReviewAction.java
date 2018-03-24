package server.essp.pwm.wp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pwm.wp.DtoPwWprev;
import com.wits.util.Parameter;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.wp.logic.FPW01050ReviewSelectLogic;
import server.essp.pwm.wp.logic.FPW01050ReviewUpdateLogic;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class FPW01050ReviewAction extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData transData) throws BusinessException {
        AbstractBusinessLogic logic = null;
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String funId = inputInfo.getFunId();

        if (funId.equals("SELECT") == true) {
            Parameter param = new Parameter();
            param.put("inWpId", (Long)inputInfo.getInputObj("inWpId"));

            logic = new FPW01050ReviewSelectLogic();
            logic.setDbAccessor( this.getDbAccessor() );
            logic.executeLogic(param);

            returnInfo.setReturnObj("dtoPwWprev",param.get("dtoPwWprev"));
        } else if (funId.equals("UPDATE") == true) {
            Parameter param = new Parameter();
            param.put("dtoPwWprev", (DtoPwWprev)inputInfo.getInputObj("dtoPwWprev"));

            logic = new FPW01050ReviewUpdateLogic();
            logic.setDbAccessor( this.getDbAccessor() );
            logic.executeLogic(param);

            returnInfo.setReturnObj("dtoPwWprev", param.get("dtoPwWprev"));
        }
    }

}
