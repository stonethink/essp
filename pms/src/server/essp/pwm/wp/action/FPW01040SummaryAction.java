package server.essp.pwm.wp.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pwm.wp.DtoPwWpsum;
import com.wits.util.Parameter;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.wp.logic.FPW01040SummarySelectLogic;
import server.essp.pwm.wp.logic.FPW01040SummaryUpdateLogic;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.essp.common.account.IDtoAccount;

public class FPW01040SummaryAction extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData transData) throws BusinessException {
        AbstractBusinessLogic logic = null;
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String funId = inputInfo.getFunId();

        if (funId.equals("SELECT") == true) {
            Parameter param = new Parameter();
            param.put("inWpId", (Long)inputInfo.getInputObj("inWpId"));

            logic = new FPW01040SummarySelectLogic();
            logic.setDbAccessor( this.getDbAccessor() );
            logic.executeLogic(param);

            returnInfo.setReturnObj("dtoPwWpsum",param.get("dtoPwWpsum"));
            returnInfo.setReturnObj("sel_Techtype",(Vector)param.get("sel_Techtype"));
        } else if (funId.equals("UPDATE") == true) {
            Parameter param = new Parameter();
            param.put("dtoPwWpsum", (DtoPwWpsum)inputInfo.getInputObj("dtoPwWpsum"));
            param.put("inWpId", (Long)inputInfo.getInputObj("inWpId"));

            logic = new FPW01040SummaryUpdateLogic();
            logic.setDbAccessor( this.getDbAccessor() );
            logic.executeLogic(param);

            returnInfo.setReturnObj("dtoPwWpsum", param.get("dtoPwWpsum"));
        }
    }

}
