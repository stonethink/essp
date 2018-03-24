package server.essp.tc.weeklyreport.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.tc.weeklyreport.logic.LgWeeklyReportSumByWorker;
import itf.account.IAccountUtil;
import itf.account.AccountFactory;
import java.util.*;
import c2s.essp.common.account.IDtoAccount;

public class AcGetAcntComboList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String userId = (String) inputInfo.getInputObj(DtoTcKey.USER_ID);

        IAccountUtil acntUtil = AccountFactory.create();
        List acntList = acntUtil.listAccountsByLoginID(userId);
        List acntNames = new ArrayList();
        List acntIds = new ArrayList();
        List acntRids = new ArrayList();
        for (Iterator iter = acntList.iterator(); iter.hasNext(); ) {
          IDtoAccount account = (IDtoAccount) iter.next();
          acntNames.add(account.getName());
          acntIds.add(account.getId());
          acntRids.add(account.getRid());
        }

        returnInfo.setReturnObj(DtoTcKey.ACCOUNT_NAME_LIST, acntNames);
        returnInfo.setReturnObj(DtoTcKey.ACCOUNT_Code_LIST, acntIds);
        returnInfo.setReturnObj(DtoTcKey.ACCOUNT_RID_LIST, acntRids);
    }
}
