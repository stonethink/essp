package server.essp.pms.account.pcb.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.pcb.DtoPcbItem;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.pcb.logic.LgPcb;
import server.framework.common.BusinessException;

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
public class AcAcntPCBItemDelete extends AbstractESSPAction {
    public AcAcntPCBItemDelete() {
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
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();

        DtoPcbItem dtoPcbItem = (DtoPcbItem) inputInfo.getInputObj("dtoPcbItem");

        LgPcb logic = new LgPcb();
        logic.deletePcbItem(dtoPcbItem);

    }
}
