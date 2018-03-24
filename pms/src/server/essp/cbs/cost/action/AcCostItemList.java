package server.essp.cbs.cost.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.cbs.cost.logic.LgCost;
import c2s.essp.cbs.cost.DtoCostItem;
import java.util.List;
import java.util.Vector;
import server.essp.common.syscode.LgSysCurrency;
import itf.account.AccountFactory;

public class AcCostItemList extends AbstractESSPAction {
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
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");
        LgCost lg = new LgCost();
        List costItemList = lg.listCostItemDto(acntRid,DtoCostItem.ACCOUNT_SCOPE);
        Vector subjectList = lg.listCostItemSubjectCom(acntRid);

        LgSysCurrency lgCurrency = new LgSysCurrency();
        Vector currencyList = lgCurrency.listComboCurrency();

        String accountCurrency = AccountFactory.create().getAccountByRID(acntRid).getCurrency();
        data.getReturnInfo().setReturnObj("costItemList",costItemList);
        data.getReturnInfo().setReturnObj("subjectList",subjectList);
        data.getReturnInfo().setReturnObj("currencyList",currencyList);
        data.getReturnInfo().setReturnObj("accountCurrency",accountCurrency);
    }
}
