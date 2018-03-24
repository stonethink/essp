package server.essp.issue.typedefine.priority.action;

import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import server.essp.issue.typedefine.priority.form.AfPriority;
import server.essp.issue.typedefine.priority.logic.LgPriority;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author QianLiu
 * @version 1.0*/
public class AcPriorityAdd extends AbstractBusinessAction {
    /**
     * 依据传入的AfPriority新增IssuePriority
     * Call: LgPriority.add(AfPriority)
     * ForwardID: list
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction method*/
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
            AfPriority issuePriorityForm = (AfPriority)this.getForm();
            LgPriority logic = new LgPriority();
            logic.add(issuePriorityForm);

            request.setAttribute("refresh", "opener.refreshSelf()");
            //data.getReturnInfo().setForwardID("success");
    }
}
