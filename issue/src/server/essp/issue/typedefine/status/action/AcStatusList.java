package server.essp.issue.typedefine.status.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.status.logic.LgStatusList;
import java.util.List;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import c2s.dto.ReturnInfo;
import server.essp.issue.typedefine.status.viewbean.VbStatus;
import server.essp.issue.typedefine.status.form.AfStatus;

public class AcStatusList extends AbstractBusinessAction {
    public AcStatusList() {
    }
    /**
     * 依据传入的typeName获取该Issue Type的Status列表
     * Call: LgStatusList.list(typeName)
     * ForwardID: list
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        AfStatus  issueStatusForm = (AfStatus)this.getForm();
        String selectedRow = "";
        selectedRow = issueStatusForm.getSelectedRow();
        String typeName = issueStatusForm.getTypeName();
        if (typeName.equals("null")){
            typeName = " ";
        }
        log.info("list status of issue type:" + typeName);

        LgStatusList logic = new LgStatusList();

        List result = logic.list(typeName);

        VbStatus vBean = new VbStatus();
        vBean.setStatusList(result);
        vBean.setTypeName(typeName);
        if ( selectedRow == null  || selectedRow.equals("") || selectedRow.equalsIgnoreCase("null")){
            vBean.setSelectedRow("tr0");
        }else {
            if ((new Integer(selectedRow.substring(2))).intValue() >= result.size()){
                vBean.setDeleterefresh("tr0");
            }else
            vBean.setSelectedRow(selectedRow);
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
