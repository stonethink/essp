package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.category.form.AfCategory;
import com.wits.util.Parameter;
import db.essp.issue.IssueCategoryType;
import server.essp.issue.typedefine.category.logic.LgCategory;
import server.essp.issue.typedefine.category.viewbean.VbCategory;

public class AcCategoryUpdatePre extends AbstractBusinessAction {
    /**
     * 依据传入的typeName,categoryName查找IssueCategoryType
     * Call: LgCategoryList.listWithValue(typeName)
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
        AfCategory form = (AfCategory)this.getForm();
        String typeName = form.getTypeName();
        String categoryName = form.getCategoryName();
        log.info("update issue category and its values,typeName:" + typeName +
                 "\ncategoryName:" + categoryName);

        LgCategory logic = new LgCategory();
        IssueCategoryType category = logic.load(typeName, categoryName);
        VbCategory vBean = new VbCategory();
        vBean.setTypeName(category.getComp_id().getTypeName());
        vBean.setCategoryName(category.getComp_id().getCategoryName());
        try {
            c2s.dto.DtoUtil.copyProperties(vBean, category);
        } catch (Exception ex) {
            throw new BusinessException(
                "issue.category.updatepre.copy.exception",
                "copy properties error!");
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }

    /** @link dependency */
    /*# server.essp.issue.typedefine.category.logic.LgCategory lnkLgCategory; */
}
