package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.viewbean.VbLeaveList;
import c2s.essp.common.user.DtoUser;
import server.essp.attendance.leave.logic.LgLeave;
import java.util.List;

public class AcLeavePersonalList extends AbstractESSPAction {
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
        DtoUser user = this.getUser();
        if(user == null)
            throw new BusinessException("unknown","The user does not login in!");
        VbLeaveList webVo = new VbLeaveList();
        String yearStr = request.getParameter("year");
        if(yearStr != null && !"".equals(yearStr)){
            webVo.setSelectedYear(Integer.parseInt(yearStr));
        }

        //查找当前人员所有的Leave记录
        LgLeave lg = new LgLeave();
        List leaveList = lg.listLeave(user.getUserLoginId(),webVo.getSelectedYear());
        webVo.setLeaveList( leaveList );
        request.setAttribute("webVo",webVo);
        data.getReturnInfo().setForwardID("success");
    }
}
