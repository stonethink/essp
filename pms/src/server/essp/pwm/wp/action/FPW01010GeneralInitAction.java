package server.essp.pwm.wp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import itf.account.AccountFactory;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.wp.logic.FPW01000CommonLogic;
import server.framework.common.BusinessException;
import server.essp.pwm.wp.logic.FPW01000CommonLogic;
import com.wits.util.StringUtil;
import itf.account.AccountFactory;

public class FPW01010GeneralInitAction extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Boolean isCreateWp = (Boolean)inputInfo.getInputObj("isCreateWp");
        if( isCreateWp == null ){
            isCreateWp = Boolean.FALSE;
        }

        Long acntRid = null;
        String accountId = "";
        String accountName = "";
        String accountTypeName = "";
        String accountManagerName = "";
        Long generatedWpCodeNum = null;

        IDtoAccount account = (IDtoAccount)request.getSession().getAttribute(IDtoAccount.SESSION_KEY);
        if( account == null ){
            acntRid = (Long)inputInfo.getInputObj("acntRid");
            if( acntRid != null ){
                account = AccountFactory.create().getAccountByRID(acntRid);
            }
        }

        if( account != null ){
            accountId = account.getId();
            accountName = account.getName();
            accountTypeName = account.getType();
            accountManagerName = account.getManager();
            acntRid = account.getRid();

            if( isCreateWp.booleanValue() == true ){
                generatedWpCodeNum = FPW01000CommonLogic.generatorWpCode(getDbAccessor(), acntRid);
            }
        }else{
            //throw new BusinessException("E000","Can't get the account information from session.Please choose one account first.");
        }

        returnInfo.setReturnObj("acntRid", acntRid);
        returnInfo.setReturnObj("accountId", accountId);
        returnInfo.setReturnObj("accountName", accountName);
        returnInfo.setReturnObj("accountTypeName", accountTypeName);
        returnInfo.setReturnObj("accountManagerName", accountManagerName);
        returnInfo.setReturnObj("generatedWpCodeNum", generatedWpCodeNum);
    }

}


