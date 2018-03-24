package server.essp.attendance.modify.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import java.util.Map;
import server.essp.attendance.leave.logic.LgLeave;
import db.essp.attendance.TcLeaveMain;
import server.essp.attendance.modify.logic.LgLeaveModify;

public class AcLeaveModifyReview extends AbstractESSPAction {
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
        String leaveRid = request.getParameter("leaveRid");
        String decision = request.getParameter("decision");
        String currActivityRid = request.getParameter("currActivityRid");
        LgLeave lgLeave = new LgLeave();
        TcLeaveMain leaveMain = lgLeave.getLeave(new Long(leaveRid));
        RequestParseHelper helper = new RequestParseHelper(leaveMain,request);
        //将Request传过来的数据解析,放入Map中,Key--请假明细的Rid,Value--请假明细的ChangeHours和Remark
        Map requestData = helper.getRequestDate();
        LgLeaveModify lg = new LgLeaveModify();
        lg.reviewLeaveModify(leaveMain,decision,requestData,new Long(currActivityRid));
    }
}
