package server.essp.pms.account.baseline.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pms.account.DtoAcntBL;
import server.essp.pms.account.baseline.logic.LgAccountBaseline;
import server.framework.action.AbstractBusinessAction;
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
public class AcAcntBLBase extends AbstractBusinessAction {


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

        ReturnInfo returnInfo = data.getReturnInfo();
        InputInfo inputInfo = data.getInputInfo();

        Long lRid = (Long) inputInfo.getInputObj("acntRid");

        LgAccountBaseline acntBL = new LgAccountBaseline();

        //List arry = acntBL.listbyLoginID("stone.shi");
        DtoAcntBL dtoAcntBL = acntBL.queryBaselineInfo(lRid);
        List acntLists = null;
        if (dtoAcntBL != null ){
            acntLists = acntBL.listApprovalLog(lRid, dtoAcntBL.getBaselineId());
        }
        returnInfo.setReturnObj("AcntBaseLine", dtoAcntBL);
        returnInfo.setReturnObj("BaseLineProve",acntLists);

    }
}
