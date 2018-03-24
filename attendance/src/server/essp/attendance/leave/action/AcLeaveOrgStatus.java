package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.form.AfLeaveOrgStatus;
import server.essp.attendance.leave.viewbean.VbLeaveOrgStatus;
import itf.orgnization.OrgnizationFactory;
import java.util.List;
import server.essp.attendance.leave.logic.LgLeave;

public class AcLeaveOrgStatus extends AbstractESSPAction {
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
        AfLeaveOrgStatus form = (AfLeaveOrgStatus) this.getForm();
        String orgId = form.getOrgId();
        Boolean includeSub = new Boolean(form.getIncludeSub());
        Double minUsableHours = null;
        if(form.getMinUsableHours() == null)
            minUsableHours = new Double(0);
        else
            minUsableHours = new Double(form.getMinUsableHours());
        LgLeave lg = new LgLeave();
        VbLeaveOrgStatus webVo = lg.getOrgLeaveStatus(orgId,includeSub.booleanValue(),
                                 minUsableHours.doubleValue());
        List allOrg = OrgnizationFactory.create().listOptOrgnization();
        webVo.setAllOrg(allOrg);
        webVo.setOrgId(orgId);
        webVo.setIncludeSub(includeSub);
        webVo.setMinUsableHours(form.getMinUsableHours());
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
