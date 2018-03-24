package server.essp.issue.typedefine.risk.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.risk.form.AfRisk;
import server.essp.issue.typedefine.risk.logic.LgRisk;
import c2s.dto.ReturnInfo;

public class AcRiskUpdate extends AbstractBusinessAction {
    public AcRiskUpdate() {
    }
    /**
     * 依据传入的AfRisk修改IssueRisk对象
     * Call: LgRisk.update(AfRisk)
     * ForwardID: update_success
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
   */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        AfRisk  issueRiskForm = (AfRisk)this.getForm();
        log.info("EDIT DELETE RISK INFLUENCE !" +
             "\n RISK INFLUENCE :" + issueRiskForm.getRiskInfluence() +
             "\n MIN LEVEL :" + issueRiskForm.getRiskMinLevel() +
             "\n MAX LEVEL :" + issueRiskForm.getRiskMaxLevel() +
             "\n WEIGHT :" + issueRiskForm.getRiskWeight() +
             "\n SEQUENCE :" + issueRiskForm.getRiskSequence() +
         "\n DESCRIPTION :" + issueRiskForm.getRiskDescription());

        LgRisk logic = new LgRisk();

        logic.update(issueRiskForm);
        request.setAttribute("refresh", "opener.refreshSelf()");
    }

}
