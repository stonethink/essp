package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import com.wits.util.Parameter;
import server.essp.issue.typedefine.category.form.AfCategoryValue;
import db.essp.issue.IssueCategoryValue;
import server.essp.issue.typedefine.category.logic.LgCategoryValue;
import server.essp.issue.typedefine.category.viewbean.VbCategoryValue;

public class AcCategoryValueUpdatePre extends AbstractBusinessAction {
    /**
     * 依据传入的typeName,categoryName,categoryValue查找IssueCategoryValue
     * Call: LgCategoryValue.load()
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
        AfCategoryValue form = (AfCategoryValue)this.getForm();
        String typeName = form.getTypeName();
        String categoryName = form.getCategoryName();
        String categoryValue = form.getCategoryValue();

        log.info("update category value,typename:" + typeName +
                 "\ncategoryName:" + categoryName +
                 "\ncategoryValue:" + categoryValue);

        LgCategoryValue logic = new LgCategoryValue();
        IssueCategoryValue value = logic.load(typeName, categoryName,
                                              categoryValue);
        VbCategoryValue vBean = new VbCategoryValue();
        vBean.setTypeName(value.getComp_id().getTypeName());
        vBean.setCategoryName(value.getComp_id().getCategoryName());
        vBean.setCategoryValue(value.getComp_id().getCategoryValue());
        try {
            c2s.dto.DtoUtil.copyProperties(vBean, value);
        } catch (Exception ex) {
            throw new BusinessException(
                "issue.category.value.updatepre.copy.exception",
                "copy properties error!");
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }

    /** @link dependency */
    /*# server.essp.issue.typedefine.category.logic.LgCategoryValue lnkLgCategoryValue; */
}
