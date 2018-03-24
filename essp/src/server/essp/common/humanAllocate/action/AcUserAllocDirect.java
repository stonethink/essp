package server.essp.common.humanAllocate.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.common.humanAllocate.form.AfUserAllocDirect;
import server.essp.common.humanAllocate.logic.LgUserAlloc;
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
public class AcUserAllocDirect extends AbstractBusinessAction {
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
                           TransactionData data) throws BusinessException {
        AfUserAllocDirect directForm = (AfUserAllocDirect) this.getForm();
        log.info("direct search condition,loginid;" + directForm.getLoginId() + "\n" +
                 "empname:" + directForm.getEmpName() + "\n" +
                 "empcode:" + directForm.getEmpCode());

        LgUserAlloc logic = new LgUserAlloc();
        logic.setDbAccessor(this.getDbAccessor());
        List result = logic.allocDirect(directForm);

        request.setAttribute(Constant.VIEW_BEAN_KEY,result);
        data.getReturnInfo().setForwardID("success");
    }
}
