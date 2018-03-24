package server.essp.pms.account.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.InputInfo;
import java.util.List;
import java.util.ArrayList;
import c2s.essp.pms.account.pcb.DtoPcbItem;
import c2s.dto.ReturnInfo;
import server.essp.pms.account.pcb.logic.LgPcb;

public class AcTemplatePcbItem extends AbstractESSPAction {
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
        InputInfo inputInfo = data.getInputInfo();
        Long acntRid = (Long) inputInfo.getInputObj("acntRid");
//        System.out.println("acntRid"+acntRid);
        LgPcb lgPcb = new LgPcb();
        List ResultList = lgPcb.listPcbItem(acntRid);

        ReturnInfo returnInfo = data.getReturnInfo();
        returnInfo.setReturnObj("pcbItemList",ResultList);

    }
}
