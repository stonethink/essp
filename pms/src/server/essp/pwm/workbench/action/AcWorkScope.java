package server.essp.pwm.workbench.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.workbench.logic.LgWorkScope;
import server.framework.common.BusinessException;

public class AcWorkScope extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        LgWorkScope logic = new LgWorkScope();
        List scopeList = logic.getScope();

        returnInfo.setReturnObj("scopeList", scopeList);
    }
}
