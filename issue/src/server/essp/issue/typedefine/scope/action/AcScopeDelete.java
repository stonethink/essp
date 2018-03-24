package server.essp.issue.typedefine.scope.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.scope.logic.LgScope;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.scope.form.AfScope;

public class AcScopeDelete extends AbstractBusinessAction {
    /**
     * 依据传入的typeName,scope删除IssueScope对象
     * Call: LgScope.delete(typeName,scope)
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
        String typeName = form.getTypeName();
        String scope = form.getScope();
        log.info("delete isssue type scope,typeName:" + typeName +
                 "\nscope:" + scope);

        LgScope logic = new LgScope();
        logic.delete(typeName, scope);
    }

    /** @link dependency */
    /*# LgScope lnkLgScope; */
}
