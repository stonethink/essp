package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import com.wits.util.Parameter;
import server.essp.issue.typedefine.category.logic.LgCategoryValue;
import server.essp.issue.typedefine.category.form.AfCategoryValue;

public class AcCategoryValueDelete extends AbstractBusinessAction {
    /**
     * 依据传入的typeName,categoryName,categoryValue新增IssueCategoryValue对象
     * Call: LgCategoryValue.delet(AfCategoryValue)
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
        AfCategoryValue form = (AfCategoryValue)this.getForm();
        String typeName = form.getTypeName();
        String categoryName = form.getCategoryName();
        String categoryValue = form.getCategoryValue();
        log.info("delete category value,typename:" + typeName +
                 "\ncategoryName:" + categoryName +
                 "\ncategoryValue:" + categoryValue);

        LgCategoryValue logic = new LgCategoryValue();
        logic.delete(typeName, categoryName, categoryValue);
    }

    /** @link dependency */
    /*# server.essp.issue.typedefine.category.logic.LgCategoryValue lnkLgCategoryValue; */
}
