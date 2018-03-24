package server.essp.pms.pbs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsFilesList;
import server.framework.common.BusinessException;
import c2s.essp.common.account.IDtoAccount;

public class AcPmsPbsFilesList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        IDtoAccount account = (IDtoAccount) request.getSession().getAttribute(
            IDtoAccount.SESSION_KEY);
        String acntCode = "";
        if (account != null) {
            acntCode = account.getId();
        } else {
            throw new BusinessException("E000", "Can't get the account information from session.Please select one account.");
        }

        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        Long pbsRid = (Long) inputInfo.getInputObj("pbsRid");

        LgPmsPbsFilesList logic = new LgPmsPbsFilesList();
        logic.setDbAccessor(this.getDbAccessor());
        List fileList = logic.list(acntRid, pbsRid);

        returnInfo.setReturnObj("fileList", fileList);
        returnInfo.setReturnObj("acntCode", acntCode);
    }
}
