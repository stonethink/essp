package server.essp.issue.typedefine.scope.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.scope.logic.LgScopeList;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.scope.viewbean.VbScopeList;
import server.framework.common.Constant;
import com.wits.util.StringUtil;
import server.essp.issue.typedefine.scope.form.AfScope;

public class AcScopeList extends AbstractBusinessAction {

    /**
     * 依据传入的typeName获取该Issue Type的Scope列表
     * Call: LgScopeList.list(typeName)
     * ForwardID: list
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
        log.info("list scope of issue type:" + typeName);
        String selectedRowObj = form.getSelectedRowObj();
        //测试用
        //typeName = "t1";
        LgScopeList logic = new LgScopeList();
        List result = logic.list(typeName);

        VbScopeList vBean = new VbScopeList();
        vBean.setDetail(result);
        vBean.setTypeName(typeName);
        if (StringUtil.nvl(selectedRowObj).equals("") == true) {
            selectedRowObj = "tr0";
        }
        vBean.setSelectedRowObj(selectedRowObj);
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }


    /** @link dependency */
    /*# LgScopeList lnkLgScopeList; */
}
