package server.essp.tc.weeklyreport.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.tc.weeklyreport.logic.LgCodeList;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;

public class AcCodeList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        Long acntRid = (Long) inputInfo.getInputObj(DtoTcKey.ACNT_RID);

        LgCodeList logic = new LgCodeList();
        List[] array = logic.list(acntRid);

        returnInfo.setReturnObj(DtoTcKey.CODE_VALUE_RID_LIST, array[0]);
        returnInfo.setReturnObj(DtoTcKey.CODE_VALUE_NAME_LIST, array[1]);
        returnInfo.setReturnObj(DtoTcKey.CODE_VALUE_DESCRIPTION_LIST, array[2]);
    }
}
