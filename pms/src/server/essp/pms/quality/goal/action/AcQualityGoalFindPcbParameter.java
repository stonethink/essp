package server.essp.pms.quality.goal.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.code.DtoKey;
import db.essp.pms.PmsPcbParameter;
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
public class AcQualityGoalFindPcbParameter extends AbstractESSPAction {
    public AcQualityGoalFindPcbParameter() {
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
        Long acntRid = null;
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        acntRid = (Long) inputInfo.getInputObj(DtoKey.ACNT_RID);

        LgPcb lgPcb = new LgPcb();
        PmsPcbParameter pmsPcbParameter = lgPcb.findFromPcb(acntRid, "11");

        returnInfo.setReturnObj("pmsPcbParameter", pmsPcbParameter);

    }
}
