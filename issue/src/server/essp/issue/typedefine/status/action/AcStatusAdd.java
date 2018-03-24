package server.essp.issue.typedefine.status.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.status.form.AfStatus;
import server.essp.issue.typedefine.status.logic.LgStatus;
import c2s.dto.ReturnInfo;
import server.framework.common.BusinessException;

public class AcStatusAdd extends AbstractBusinessAction {
    public AcStatusAdd() {
    }
    /**
     * 依据传入的typeName和新增Status信息新增Issue Type的status
     * Call: LgStatus.add(typeName)
     * ForwardID: add_success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {

        AfStatus  issueStatusForm = (AfStatus)this.getForm();
        log.info("\n ADD NEW ISSUE STATUS :" + issueStatusForm.getStatusName() +
        "\n BELONG TO :" + issueStatusForm.getStatusBelongTo() +
        "\n RELATION DATE :" + issueStatusForm.getStatusRelationDate() +
        "\n SEQUENCE :" + issueStatusForm.getStatusSequence()+
        "\n DESCRIPTION :" + issueStatusForm.getStatusDescription());

        LgStatus logic = new LgStatus();
        logic.add(issueStatusForm);
        request.setAttribute("refresh", "opener.refreshSelf()");
    }
}
