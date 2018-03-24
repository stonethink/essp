package server.essp.tc.pmmanage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.tc.pmmanage.logic.LgTcInitAccountList;
import server.framework.common.BusinessException;

public class AcTcInitAccountList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = null;
        IDtoAccount account = (IDtoAccount) request.getSession().getAttribute(IDtoAccount.SESSION_KEY);
        if (account != null) {
            acntRid = account.getRid();
        } else {
            //acntRid = new Long(1); //for test
            //throw new BusinessException("E0000","Please select account first.");
        }

        LgTcInitAccountList logic = new LgTcInitAccountList();
        List acntRidList = logic.listAcntRid();
        List acntNameList = logic.listAcntName();

        if(acntRid == null && acntRidList.size() > 0 ){
            acntRid = (Long)acntRidList.get(0);
        }

        returnInfo.setReturnObj(DtoTcKey.ACNT_RID, acntRid);
        returnInfo.setReturnObj(DtoTcKey.ACCOUNT_RID_LIST, acntRidList);
        returnInfo.setReturnObj(DtoTcKey.ACCOUNT_NAME_LIST, acntNameList);
    }
}
