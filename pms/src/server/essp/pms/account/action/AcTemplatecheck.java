package server.essp.pms.account.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.account.logic.LgAccountList;
import java.util.List;

public class AcTemplatecheck extends AbstractESSPAction{

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        String code = (String)inputInfo.getInputObj("code");
        LgAccountList lgab = new LgAccountList();
        List list = (List)lgab.listbyAccountID(code);
        String str = null;
        if(list.size()>0){
            str = "true";
        }else{
            str = "false";
        }
        returnInfo.setReturnObj("check",str);
    }
}
