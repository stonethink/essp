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

public class AcAcntNoneLaborResItemUpdate extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
         InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        List noneLaborResItemList = (List) inputInfo.getInputObj(DtoAcntKEY.NONE_LABOR_RES_ITEM_LIST);

        LgAcntNoneLaborResItemList logic = new LgAcntNoneLaborResItemList();
        logic.setDbAccessor(this.getDbAccessor());
        logic.update(noneLaborResItemList);

        returnInfo.setReturnObj(DtoAcntKEY.NONE_LABOR_RES_ITEM_LIST, noneLaborResItemList);
    }
}
