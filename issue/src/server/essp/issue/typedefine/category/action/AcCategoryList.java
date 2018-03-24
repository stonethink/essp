package server.essp.issue.typedefine.category.action;

import javax.servlet.http.*;
import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.category.logic.LgCategoryList;
import java.util.List;
import com.wits.util.StringUtil;
import server.essp.issue.typedefine.category.viewbean.VbCategoryList;
import server.essp.issue.typedefine.category.form.AfCategory;

public class AcCategoryList extends AbstractBusinessAction {
    /**
     * ���ݴ����typeName��ȡ��Issue Type��CategoryType�б�
     * Call: LgCategoryList.listWithValue(typeName)
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
        log.info("list categories of issue type:" + typeName);
        String selectedRowObj = form.getSelectedRowObj();

        //������
        //typeName = "t1";

        LgCategoryList logic = new LgCategoryList();
        List detail = logic.listWithValue(typeName);
        if (StringUtil.nvl(selectedRowObj).equals("") == true) {
            selectedRowObj = "tr0";
        }

        VbCategoryList vBean = new VbCategoryList();
        vBean.setDetail(detail);
        vBean.setSelectedRowObj(selectedRowObj);
        vBean.setTypeName(typeName);

        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }

    /** @link dependency */
    /*# server.essp.issue.typedefine.category.logic.LgCategoryList lnkLgCategoryList; */
}
