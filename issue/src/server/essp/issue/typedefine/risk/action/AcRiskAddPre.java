package server.essp.issue.typedefine.risk.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.issue.common.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.risk.logic.LgRisk;
import server.essp.issue.typedefine.risk.viewbean.VbRisk;
import server.essp.issue.typedefine.risk.form.AfRisk;

public class AcRiskAddPre extends AbstractISSUEAction {
    public AcRiskAddPre() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        AfRisk issueRiskForm = (AfRisk)this.getForm();
        String typeName = issueRiskForm.getTypeName();
        VbRisk vBean = new VbRisk();
        vBean.setTypeName(typeName);
        vBean.setRiskMinLevel("1");
        vBean.setRiskMaxLevel("1");
        vBean.setRiskWeight("1");
        LgRisk logic = new LgRisk();
        long sequence = logic.getMaxSequence(typeName);
        vBean.setRiskSequence(sequence+"");
        request.setAttribute(Constant.VIEW_BEAN_KEY, vBean);
    }
}
