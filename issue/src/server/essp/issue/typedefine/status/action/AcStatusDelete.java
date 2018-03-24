package server.essp.issue.typedefine.status.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.status.logic.LgStatus;
import c2s.dto.ReturnInfo;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.status.viewbean.VbStatus;
import server.framework.common.Constant;
import server.essp.issue.typedefine.status.form.AfStatus;

public class AcStatusDelete extends AbstractBusinessAction {
    public AcStatusDelete() {
    }
    /**
     * 依据传入的typeName,statusName删除IssueStatus对象
     * Call: LgScope.delete(typeName,statusName)
     * ForwardID: delete_success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        AfStatus  issueStatusForm = (AfStatus)this.getForm();
        String typeName = issueStatusForm.getTypeName();
        String statusName = issueStatusForm.getStatusName();
        String selectedRow = issueStatusForm.getSelectedRow();

        log.info("\n DELETE ISSUE STATUS :" + "\n TYPE NAME :" + typeName + "\n STATUS NAME :" + statusName );

        LgStatus logic = new LgStatus();
        logic.delete(typeName, statusName);

        VbStatus vBean = new VbStatus();
        vBean.setDeleterefresh("YES");
        vBean.setTypeName(typeName);
        vBean.setSelectedRow(selectedRow);

        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
