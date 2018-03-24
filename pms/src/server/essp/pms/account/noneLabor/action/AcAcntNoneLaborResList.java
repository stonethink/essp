package server.essp.pms.account.noneLabor.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoAcntKEY;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.noneLabor.logic.LgAcntNoneLaborResList;
import server.framework.common.BusinessException;

public class AcAcntNoneLaborResList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long)inputInfo.getInputObj(DtoAcntKEY.ACNT_RID);

        LgAcntNoneLaborResList lgPbsList = new LgAcntNoneLaborResList();

        List pbsList = lgPbsList.list(acntRid);

        returnInfo.setReturnObj(DtoAcntKEY.NONE_LABOR_RES_LIST, pbsList);
    }
}
