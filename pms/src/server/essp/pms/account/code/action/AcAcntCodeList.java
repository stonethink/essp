package server.essp.pms.account.code.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.code.DtoKey;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import server.essp.pms.account.code.logic.LgAcntCode;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcAcntCodeList extends AbstractBusinessAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long) inputInfo.getInputObj(DtoKey.ACNT_RID);

        LgAcntCode logic = new LgAcntCode(acntRid);
        List codeList = logic.list();

        IAccountUtil util = AccountFactory.create();
        IDtoAccount account = util.getAccountByRID(acntRid);
        String accountType = account.getType();

        returnInfo.setReturnObj(DtoKey.DTO_LIST, codeList);
        returnInfo.setReturnObj(DtoKey.CATALOG, accountType);
    }
}
