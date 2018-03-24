package server.essp.pms.account.action;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import java.util.Vector;
import server.essp.common.syscode.LgSysParameter;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import java.util.ArrayList;
import java.util.List;

public class AcTemplateGeneral extends AbstractESSPAction{

    static final String kindAccountType = "ACCOUNT_TYPE";
    public AcTemplateGeneral(){
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();

        LgSysParameter lgSysParameter = new LgSysParameter();
        Vector accountTypeList = lgSysParameter.listComboSysParas(kindAccountType);

        returnInfo.setReturnObj("accountTypeList", accountTypeList);
    }

}
