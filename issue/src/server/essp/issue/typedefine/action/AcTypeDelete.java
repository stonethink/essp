package server.essp.issue.typedefine.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.form.AfType;
import server.essp.issue.typedefine.logic.LgType;

public class AcTypeDelete extends AbstractBusinessAction {
    public AcTypeDelete() {
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
        AfType issueTypeForm = (AfType)this.getForm();
        LgType logic = new LgType();
        logic.delete(issueTypeForm);
    }
}
