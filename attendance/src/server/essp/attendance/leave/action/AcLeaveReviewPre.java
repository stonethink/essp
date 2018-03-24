package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.viewbean.VbLeave;
import server.essp.attendance.leave.logic.LgLeave;
import c2s.essp.attendance.Constant;

public class AcLeaveReviewPre extends AbstractESSPAction {
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
        LgLeave lg = new LgLeave();
        VbLeave webVo = null;
        String wkRid = request.getParameter("wkRid");
        String rid = request.getParameter("rid");
        if(wkRid != null && !"".equals(wkRid)){
            webVo = lg.getLeaveVBByWkRid(new Long(wkRid));
        }else if(rid != null && !"".equals(rid)){
            webVo = lg.getLeaveByRid(new Long(rid));
        }
        webVo.setDecision(Constant.DECISION_AGREE);//默认意见设置为同意
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
