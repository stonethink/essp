package server.essp.issue.typedefine.risk.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.risk.logic.LgRisk;
import c2s.dto.ReturnInfo;
import server.essp.issue.typedefine.risk.viewbean.VbRisk;
import server.framework.common.Constant;
import server.essp.issue.typedefine.risk.form.AfRisk;

public class AcRiskDelete extends AbstractBusinessAction {
    public AcRiskDelete() {
    }
    /**
     * 依据传入的typeName,riskInfluence删除IssueRisk对象
     * Call: LgRisk.delete(typeName,influence)
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
        AfRisk issueRiskForm = (AfRisk)this.getForm();
        String typeName = issueRiskForm.getTypeName();
        String riskInfluence = issueRiskForm.getRiskInfluence();
        String selectedRow = issueRiskForm.getSelectedRow();

        log.info("\n DELETE ISSUE RISK :" + "\n TYPE NAME :" + typeName + "\n STATUS NAME :" + riskInfluence );

        LgRisk logic = new LgRisk();

        logic.delete(typeName, riskInfluence);

        VbRisk vBean = new VbRisk();
        vBean.setDeleterefresh("YES");
        vBean.setTypeName(typeName);
        vBean.setSelectedRow(selectedRow);
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }

}
