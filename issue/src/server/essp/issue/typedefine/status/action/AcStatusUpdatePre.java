package server.essp.issue.typedefine.status.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.issue.typedefine.status.logic.LgStatus;
import c2s.dto.ReturnInfo;
import db.essp.issue.IssueStatus;
import server.essp.issue.typedefine.status.viewbean.VbStatus;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.essp.issue.typedefine.status.form.AfStatus;

public class AcStatusUpdatePre extends AbstractBusinessAction {
    public AcStatusUpdatePre() {
    }
    /**
     * 依据传入的typeName和statusName获取该IssueStatus对象
     * Call: LgStatus.load(typeName, statusName)
     * ForwardID: to_update
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
   */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        AfStatus form=(AfStatus)this.getForm();
        String typeName = form.getTypeName();
        String statusName = form.getStatusName();

        log.info("\n UPDATE ISSUE STATUS PREPARE:" + "\n TYPE NAME :" + typeName + "\n STATUS NAME :" + statusName );

        LgStatus logic = new LgStatus();
        IssueStatus status = logic.load(typeName, statusName);
        VbStatus vBean = new VbStatus();

        vBean.setTypeName(status.getComp_id().getTypeName());
        vBean.setStatusName(status.getComp_id().getStatusName());
        vBean.setStatusBelongTo(status.getBelongTo());
        vBean.setStatusRelationDate(status.getRelationDate());
        vBean.setStatusSequence(status.getSequence().toString());
        vBean.setStatusDescription(status.getDescription());

        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
