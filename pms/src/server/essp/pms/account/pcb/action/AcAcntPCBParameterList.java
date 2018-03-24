package server.essp.pms.account.pcb.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.pcb.logic.LgPcb;
import server.framework.common.BusinessException;
import c2s.essp.pms.account.DtoAcntKEY;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcAcntPCBParameterList extends AbstractESSPAction {
    public AcAcntPCBParameterList() {
    }

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
                           HttpServletResponse response,
                           TransactionData transData) throws
        BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long acntRid = (Long)inputInfo.getInputObj(DtoAcntKEY.ACNT_RID);
        Long itemRid = (Long) inputInfo.getInputObj("itemRid");

        LgPcb lgpcb = new LgPcb();

//        List pcbParameterList = lgpcb.listPcbParameter(itemRid);


         List pcbParameterList = lgpcb.listPcbParameter(acntRid,itemRid);

        returnInfo.setReturnObj("pcbParameterList", pcbParameterList);

    }
}
