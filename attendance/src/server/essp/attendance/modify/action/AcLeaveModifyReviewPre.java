package server.essp.attendance.modify.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.modify.viewbean.VbLeaveModify;
import server.essp.attendance.modify.logic.LgLeaveModify;
import c2s.essp.attendance.Constant;

public class AcLeaveModifyReviewPre extends AbstractESSPAction {
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
        String wkRid = request.getParameter("wkRid");
        String rid = request.getParameter("rid");
        LgLeaveModify lg = new LgLeaveModify();
        VbLeaveModify webVo = null;
        if(wkRid != null && !"".equals(wkRid)){
            webVo = lg.getLeaveModifyByWkRid(new Long(wkRid));
        }else if(rid != null && !"".equals(rid)){
            webVo = lg.getLeaveModifyByRid(new Long(rid));
        }
        webVo.setDecision(Constant.DECISION_AGREE);
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
