package server.essp.pms.account.tailor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.pms.account.tailor.logic.*;

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
public class AcAccountTailorDescription extends AbstractESSPAction {
    public AcAccountTailorDescription() {
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
                           HttpServletResponse response, TransactionData transactionData) throws
        BusinessException {

        Long acntRid = Long.valueOf(transactionData.getInputInfo().getInputObj(
            "acntRid").toString());

        String description;

        LgAcntTailor lg = new LgAcntTailor();

        description = lg.getDescription(acntRid);
        transactionData.getReturnInfo().setReturnObj("description", description);
//
    }
}
