package server.essp.pms.account.noneLabor.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoAcntKEY;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.noneLabor.logic.LgAcntNoneLaborResItemList;
import server.framework.common.BusinessException;

public class AcAcntNoneLaborResItemList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long)inputInfo.getInputObj(DtoAcntKEY.ACNT_RID);
        Long envRid = (Long)inputInfo.getInputObj(DtoAcntKEY.ENV_RID);

        LgAcntNoneLaborResItemList logic = new LgAcntNoneLaborResItemList();

        List pbsList = logic.list(acntRid,envRid);

        returnInfo.setReturnObj(DtoAcntKEY.NONE_LABOR_RES_ITEM_LIST, pbsList);
    }
}
