package server.essp.issue.typedefine.risk.action;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.issue.typedefine.risk.logic.LgRisk;
import db.essp.issue.IssueRisk;
import server.essp.issue.typedefine.status.viewbean.VbStatus;
import server.essp.issue.typedefine.risk.viewbean.VbRisk;
import c2s.dto.ReturnInfo;
import server.framework.common.Constant;
import server.essp.issue.typedefine.risk.form.AfRisk;

public class AcRiskUpdatePre extends AbstractBusinessAction {
    public AcRiskUpdatePre() {
    }
    /**
     * 依据传入的typeName和riskInfluence获取该IssueRisk对象
     * Call: LgScope.load(typeName,riskInfluence)
     * ForwardID: to_update
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
   */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfRisk issueRiskForm = (AfRisk)this.getForm();
        String typeName = issueRiskForm.getTypeName();
        String riskInfluence = issueRiskForm.getRiskInfluence();


        log.info("\n UPDATE ISSUE RISK PREPARE:" + "\n TYPE NAME :" + typeName + "\n RISK NAME :" + riskInfluence );

        LgRisk logic = new LgRisk();

        IssueRisk risk = logic.load(typeName, riskInfluence);
        VbRisk vBean = new VbRisk();

        vBean.setTypeName(risk.getComp_id().getTypeName());
        vBean.setRiskInfluence(risk.getComp_id().getInfluence());
        vBean.setRiskMinLevel(risk.getMinLevel().toString());
        vBean.setRiskMaxLevel(risk.getMaxLevel().toString());
        vBean.setRiskWeight(risk.getWeight().toString());
        vBean.setRiskSequence(risk.getSequence().toString());
        vBean.setRiskDescription(risk.getDescription());

        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }


}
