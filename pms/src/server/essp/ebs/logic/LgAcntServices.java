package server.essp.ebs.logic;

import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.ebs.DtoPmsAcnt;
import db.essp.pms.Account;
import net.sf.hibernate.Query;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import c2s.dto.TransactionData;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import server.essp.common.syscode.LgSysParameter;
import server.essp.common.syscode.LgSysCurrency;
import java.util.Vector;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;

public class LgAcntServices extends AbstractBusinessLogic {
    static final String kindAccountType = "ACCOUNT_TYPE";

    public void init(TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        LgSysCurrency lgSysCurrency = new LgSysCurrency();
        Vector currencyList = lgSysCurrency.listCurrencyDefaultStyle();

        LgSysParameter lgSysParameter = new LgSysParameter();
        Vector accountTypeList = lgSysParameter.listComboSysParas(kindAccountType);

        IOrgnizationUtil orgUtil = OrgnizationFactory.createOrgnizationUtil();
        Vector orgList = orgUtil.listComboOrgnization();

        returnInfo.setReturnObj("accountTypeList", accountTypeList);
        returnInfo.setReturnObj("currencyList", currencyList);
        returnInfo.setReturnObj("orgList", orgList);
    }



}
