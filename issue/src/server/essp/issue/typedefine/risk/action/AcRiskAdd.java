package server.essp.issue.typedefine.risk.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.risk.form.AfRisk;
import server.essp.issue.typedefine.risk.logic.LgRisk;
import c2s.dto.ReturnInfo;

public class AcRiskAdd extends AbstractBusinessAction {
    public AcRiskAdd() {
    }

    /**
     * 依据传入的AfRisk新增IssueRisk对象
     * Call: LgRisk.add(AfRisk)
     * ForwardID: add_success
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
        log.info("ADD NEW RISK INFLUENCE !" +
                 "\n Risk INFLUENCE : " + issueRiskForm.getRiskInfluence() +
                 "\n MIN LEVEL :" + issueRiskForm.getRiskMinLevel() +
                 "\n MAX LEVEL :" + issueRiskForm.getRiskMaxLevel() +
                 "\n WEIGHT :" + issueRiskForm.getRiskWeight() +
                 "\n SEQUENCE :" + issueRiskForm.getRiskSequence() +
                 "\n DESCRIPTION :" + issueRiskForm.getRiskDescription());

        LgRisk logic = new LgRisk();

        logic.add(issueRiskForm);
        request.setAttribute("refresh", "opener.refreshSelf()");
    }

}
