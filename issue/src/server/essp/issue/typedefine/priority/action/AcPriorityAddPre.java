package server.essp.issue.typedefine.priority.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.priority.form.AfPriority;
import server.essp.issue.typedefine.priority.viewbean.VbPriority;
import server.essp.issue.typedefine.priority.logic.LgPriority;

public class AcPriorityAddPre extends AbstractBusinessAction {
    public AcPriorityAddPre() {
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
        AfPriority issuePriorityForm = (AfPriority)this.getForm();
        LgPriority logic=new LgPriority();
        VbPriority viewBean=new VbPriority();
        viewBean.setTypeName(issuePriorityForm.getTypeName());
        viewBean.setDuration("1");
        viewBean.setSequence(logic.getMaxSequence(issuePriorityForm.getTypeName())+"");
        request.setAttribute("webVo",viewBean);
    }
}
