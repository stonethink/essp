package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import com.wits.util.Parameter;
import server.essp.issue.typedefine.category.logic.LgCategory;
import server.essp.issue.typedefine.category.form.AfCategory;

public class AcCategoryDelete extends AbstractBusinessAction {
    /**
     * 依据传入的typeName,categoryName删除IssueCategoryType对象
     * Call: LgCategory.delete(typeName,categoryName)
     * ForwardID: list
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
        log.info("delete issue category and its values,typeName:" + typeName +
                 "\ncategoryName:" + categoryName);

        LgCategory logic = new LgCategory();
        logic.delete(typeName, categoryName);
    }

    /** @link dependency */
    /*# server.essp.issue.typedefine.category.logic.LgCategory lnkLgCategory; */
}
