package server.essp.issue.typedefine.scope.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.scope.form.AfScope;
import server.essp.issue.typedefine.scope.logic.LgScope;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;

public class AcScopeAdd extends AbstractBusinessAction {
    /**
     * 依据传入的AfScope新增IssueScope对象
     * Call: LgScope.add(AfScope)
     * ForwardID: success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfScope form = (AfScope)this.getForm();
        log.info("add issue scope:" + form.getScope() +
                 "\n issue type:" + form.getTypeName() +
                 "\n vision to customer:" + form.getVision() +
                 "\n sequence:" + form.getSequence() +
                 "\n description:" + form.getDescription());

        LgScope logic = new LgScope();
        logic.add(form);

        request.setAttribute("refresh", "opener.refreshSelf()");
    }

    /** @link dependency */
    /*# LgScope lnkLgScope; */
}
