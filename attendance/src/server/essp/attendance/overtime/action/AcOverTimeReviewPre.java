package server.essp.attendance.overtime.action;

import javax.servlet.http.*;

import c2s.dto.*;
import com.wits.util.*;
import itf.account.*;
import server.essp.attendance.overtime.logic.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import c2s.essp.attendance.Constant;
public class AcOverTimeReviewPre extends AbstractESSPAction {
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
        LgOverTime lgOverTime = new LgOverTime();
        VbOverTime webVo = null;
        //工作流实例Rid
        String wkRid = StringUtil.nvl(request.getParameter("wkRid"));
        String rid = StringUtil.nvl(request.getParameter("rid"));
        if(!"".equals(wkRid)){
            webVo = lgOverTime.getOverTimeVBByWkRid(new Long(wkRid));
        }else{
            webVo = lgOverTime.getOverTimeByRid(new Long(rid));
        }
        webVo.setDecision(Constant.DECISION_AGREE);//默认意见设置为同意
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
