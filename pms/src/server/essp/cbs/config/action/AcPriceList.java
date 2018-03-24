package server.essp.cbs.config.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.cbs.CbsConstant;
import server.essp.cbs.config.logic.LgPrice;
import java.util.List;
import server.essp.common.syscode.LgSysCurrency;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import server.essp.cbs.config.logic.LgCbs;
import c2s.essp.cbs.DtoCbs;
import java.util.Vector;
import itf.account.AccountFactory;

public class AcPriceList extends AbstractESSPAction {
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
        String priceScope = (String) data.getInputInfo().getInputObj("priceScope");
        LgPrice logic = new LgPrice();
        List priceList = null;
        //系统配置的价格
        if(CbsConstant.SCOPE_GLOBAL.equals(priceScope)){
            priceList = logic.listSysPriceDto();
            if(priceList == null || priceList.size() == 0){
                priceList = logic.initSysPrice();
            }
        }//项目配置的价格
        else if(CbsConstant.SCOPE_ACCOUNT.equals(priceScope)){
            priceList = logic.listAcntPriceDto(acntRid);
            if(priceList == null || priceList.size() == 0){
                priceList = logic.intAcntPrice(acntRid);
            }
            String accountCurrency = AccountFactory.create().getAccountByRID(acntRid).getCurrency();
            data.getReturnInfo().setReturnObj("accountCurrency",accountCurrency);
        }else{
            throw new BusinessException("CBS_PRICE","illegal price scope["+priceScope+"]");
        }
        //Client显示所需内容，货币列表，JobCode列表，科目列表
        LgSysCurrency lgCurrency = new LgSysCurrency();
        Vector currencyList = lgCurrency.listCurrencyDefaultStyle();
        IHrUtil hr = HrFactory.create();
        Vector jobCodeList = hr.listComboJobCode();
        LgCbs lgCBS = new LgCbs();
        Vector subjectList = lgCBS.listComboSubject(DtoCbs.DEFAULT_TYPE);
        data.getReturnInfo().setReturnObj("priceList",priceList);
        data.getReturnInfo().setReturnObj("currencyList",currencyList);
        data.getReturnInfo().setReturnObj("jobCodeList",jobCodeList);
        data.getReturnInfo().setReturnObj("subjectList",subjectList);
    }
}
