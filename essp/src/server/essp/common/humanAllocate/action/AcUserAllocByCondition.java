package server.essp.common.humanAllocate.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.common.humanAllocate.logic.LgUserAlloc;
import server.essp.common.humanAllocate.form.AfUserAllocCondition;
import server.essp.common.humanAllocate.viewbean.VbAllocByCon;
import java.util.List;

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
public class AcUserAllocByCondition extends AbstractBusinessAction {
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
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        AfUserAllocCondition form = (AfUserAllocCondition) this.getForm();
        LgUserAlloc logic = new LgUserAlloc();
        logic.setDbAccessor(this.getDbAccessor());
        VbAllocByCon vb =  logic.allocByCondition(form);

        request.setAttribute(Constant.VIEW_BEAN_KEY,vb);
        request.setAttribute("userAllocConditionForm",form);
        data.getReturnInfo().setForwardID("success");
    }
}
