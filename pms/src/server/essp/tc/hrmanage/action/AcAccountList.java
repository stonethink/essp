package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import itf.account.AccountFactory;
import java.util.Vector;
import java.util.*;
import c2s.dto.DtoComboItem;

public class AcAccountList extends AbstractESSPAction {
    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        String userId = (String)data.getInputInfo().getInputObj(DtoTcKey.USER_ID);
        Vector accountList = AccountFactory.create().listComboAccountsDefaultStyle(userId);

        //add
        for (Iterator iter = accountList.iterator(); iter.hasNext(); ) {
            DtoComboItem item = (DtoComboItem) iter.next();
            item.setItemRelation(null);
        }

        data.getReturnInfo().setReturnObj("accountList",accountList);
    }
}
