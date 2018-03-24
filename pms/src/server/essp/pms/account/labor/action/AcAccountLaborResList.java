package server.essp.pms.account.labor.action;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import server.framework.common.BusinessException;
import server.essp.framework.action.AbstractESSPAction;

public class AcAccountLaborResList extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        LgAccountLaborRes logic = new LgAccountLaborRes();
        List result = logic.listLaborResWithPlan(acntRid);

        returnInfo.setReturnObj("laborResourceList",result);
    }

}
