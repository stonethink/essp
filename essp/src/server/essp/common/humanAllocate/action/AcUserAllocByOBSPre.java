package server.essp.common.humanAllocate.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.common.humanAllocate.logic.LgUserAlloc;
import java.util.List;
import server.essp.common.humanAllocate.viewbean.VbAllocByOBS;

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
public class AcUserAllocByOBSPre extends AbstractBusinessAction {
    /**
     * executeAct
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse reponse,
                           TransactionData data) throws
            BusinessException {
        LgUserAlloc logic = new LgUserAlloc();
        logic.setDbAccessor(this.getDbAccessor());
        List result = logic.listAllOrg();

        VbAllocByOBS vb = (VbAllocByOBS) request.getAttribute(Constant.VIEW_BEAN_KEY);
        if(vb == null)
               vb = new VbAllocByOBS();
        vb.setOrgnizations(result);
        request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
        data.getReturnInfo().setForwardID("success");
    }
}
