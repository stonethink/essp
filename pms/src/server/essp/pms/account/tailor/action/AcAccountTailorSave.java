package server.essp.pms.account.tailor.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.pms.account.tailor.logic.LgAcntTailor;
import c2s.dto.*;

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
public class AcAccountTailorSave extends AbstractESSPAction {
    public AcAccountTailorSave() {
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
        LgAcntTailor lg = new LgAcntTailor();
       InputInfo inputInfo=data.getInputInfo();
       String  description=(String)inputInfo.getInputObj("description");
       Long acntRid=(Long)inputInfo.getInputObj("acntRid");
       lg.saveAccountTailor(acntRid,description);

    }
}
