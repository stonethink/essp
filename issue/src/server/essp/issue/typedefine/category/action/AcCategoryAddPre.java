package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.issue.common.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.category.logic.LgCategory;
import server.essp.issue.typedefine.category.viewbean.VbCategory;
import server.essp.issue.typedefine.category.form.AfCategory;

public class AcCategoryAddPre extends AbstractISSUEAction {
    public AcCategoryAddPre() {
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
        AfCategory form = (AfCategory)this.getForm();
        String typeName = form.getTypeName();
        LgCategory logic = new LgCategory();
        long sequence=logic.getMaxSequence(typeName);
        VbCategory vBean = new VbCategory();
        vBean.setTypeName(typeName);
        vBean.setSequence(sequence+"");
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
