package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.issue.common.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.category.logic.LgCategoryValue;
import server.essp.issue.typedefine.category.viewbean.VbCategoryValue;
import server.essp.issue.typedefine.category.form.AfCategoryValue;

public class AcCategoryValueAddPre extends AbstractISSUEAction {
    public AcCategoryValueAddPre() {
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
        AfCategoryValue form = (AfCategoryValue)this.getForm();
        String typeName = form.getTypeName();
        String categoryName = form.getCategoryName();
        LgCategoryValue logic=new LgCategoryValue();
        long sequence=logic.getMaxSequence(typeName,categoryName);
        VbCategoryValue vBean = new VbCategoryValue();
        vBean.setTypeName(typeName);
        vBean.setCategoryName(categoryName);
        vBean.setSequence(sequence+"");
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
