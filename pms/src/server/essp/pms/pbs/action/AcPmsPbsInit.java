package server.essp.pms.pbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsInit;
import server.framework.common.BusinessException;

public class AcPmsPbsInit extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = null;
        IDtoAccount account = (IDtoAccount)request.getSession().getAttribute(IDtoAccount.SESSION_KEY);
        if( account != null ){
            acntRid = account.getRid();
        }

        LgPmsPbsInit logic = new LgPmsPbsInit();
        Long maxCode = logic.generatorCode(acntRid);

        returnInfo.setReturnObj("maxCode", maxCode);
    }
}
