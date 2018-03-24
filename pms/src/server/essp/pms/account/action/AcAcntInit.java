package server.essp.pms.account.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import server.essp.common.syscode.LgSysCurrency;
import server.essp.common.syscode.LgSysParameter;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcAcntInit extends AbstractESSPAction {
    static final String kindAccountType = "ACCOUNT_TYPE";
    static final String kindAccountStatus = "ACCOUNT_STATUS";

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        LgSysCurrency lgSysCurrency = new LgSysCurrency();
        Vector currencyList = lgSysCurrency.listCurrencyDefaultStyle();

        LgSysParameter lgSysParameter = new LgSysParameter();
        Vector accountTypeList = lgSysParameter.listComboSysParas(kindAccountType);
        Vector accountStatusList = lgSysParameter.listComboSysParas(kindAccountStatus);

        IOrgnizationUtil orgUtil = OrgnizationFactory.createOrgnizationUtil();
        Vector orgList = orgUtil.listComboOrgnization();

        returnInfo.setReturnObj("accountTypeList", accountTypeList);
        returnInfo.setReturnObj("accountStatusList", accountStatusList);
        returnInfo.setReturnObj("currencyList", currencyList);
        returnInfo.setReturnObj("orgList", orgList);
    }
}
