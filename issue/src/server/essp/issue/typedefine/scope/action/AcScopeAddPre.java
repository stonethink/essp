package server.essp.issue.typedefine.scope.action;

import server.framework.action.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.scope.viewbean.VbScope;
import server.framework.common.Constant;
import c2s.dto.ReturnInfo;
import server.essp.issue.typedefine.scope.logic.LgScope;
import server.essp.issue.typedefine.scope.form.AfScope;

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
public class AcScopeAddPre extends AbstractBusinessAction {
    /**
     * Call: VbScope∂‘œÛ
     * ForwardID: add
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfScope form = (AfScope)this.getForm();
        String typeName = form.getTypeName();
        VbScope vBean = new VbScope();
        vBean.setTypeName(typeName);
        LgScope logic = new LgScope();
        long sequence = logic.getMaxSequence(typeName);
        vBean.setSequence(sequence+"");
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
