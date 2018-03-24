package server.essp.cbs.cost.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.cbs.DtoSubject;
import server.essp.cbs.cost.logic.LgActivityCost;
import java.util.List;
import c2s.essp.cbs.cost.DtoCostItem;
import server.essp.cbs.cost.logic.LgCost;
import java.util.Vector;
import itf.account.AccountFactory;
import server.essp.common.syscode.LgSysCurrency;

public class AcActivytCostItemList extends AbstractESSPAction {
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
        String attribute = (String) data.getInputInfo().getInputObj("attribute");
        Long acntRid = (Long) data.getInputInfo().getInputObj("acntRid");
        Long activityId = (Long) data.getInputInfo().getInputObj("activityId");
        LgActivityCost lg = new LgActivityCost();
        List costItemList = null;
        if(DtoSubject.ATTRI_LABOR_SUM.equals(attribute)){
            costItemList = lg.listActLaborCostItem(acntRid,activityId);
        }else{
            LgCost lgCost = new LgCost();
            Vector subjectList = lgCost.listCostItemSubjectCom(acntRid);
            if(subjectList ==null){
                subjectList = new Vector();
            }
            LgSysCurrency lgCurrency = new LgSysCurrency();
            Vector currencyList = lgCurrency.listComboCurrency();

            String accountCurrency = AccountFactory.create().getAccountByRID(acntRid).getCurrency();
            costItemList = lg.listActCostItemByAttr(acntRid,activityId,attribute);
            data.getReturnInfo().setReturnObj("accountCurrency",accountCurrency);
            data.getReturnInfo().setReturnObj("currencyList",currencyList);
            data.getReturnInfo().setReturnObj("subjectList",subjectList);
        }
        data.getReturnInfo().setReturnObj("costItemList",costItemList);
    }
}
