package server.essp.tc.hrmanage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;

public class AcTcInitAccountList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = null;
        IDtoAccount account = (IDtoAccount) request.getSession().getAttribute(IDtoAccount.SESSION_KEY);
        if (account != null) {
            log.info("Get from session: account ="+ account.getRid());
//            acntRid = account.getRid(); //hr的项目不从session中取
        } else {
            log.info("not get from session: account");
            //acntRid = new Long(1);
            //throw new BusinessException("E0000","Please select account first.");
        }

        IAccountUtil acntUtil = AccountFactory.create();
        List acntRidList = acntUtil.getHrAcntRidList();
        List acntNameList = acntUtil.getHrAcntNameList();

        returnInfo.setReturnObj(DtoTcKey.ACCOUNT_RID_LIST, acntRidList);
        returnInfo.setReturnObj(DtoTcKey.ACCOUNT_NAME_LIST, acntNameList);
        returnInfo.setReturnObj(DtoTcKey.ACNT_RID, acntRid);
    }

    public static void main(String args[]){
        AcTcInitAccountList action = new AcTcInitAccountList();
        TransactionData transData = new TransactionData();
        action.executeAct(null,null,transData);
        List acntRidList = (List)transData.getReturnInfo().getReturnObj(DtoTcKey.ACCOUNT_RID_LIST);
        List acntNameList = (List)transData.getReturnInfo().getReturnObj(DtoTcKey.ACCOUNT_NAME_LIST);
        System.out.println(acntNameList.size());
        System.out.println(acntRidList.size());
    }
}
