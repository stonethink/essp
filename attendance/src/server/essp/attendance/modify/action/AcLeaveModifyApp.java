package server.essp.attendance.modify.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.logic.LgLeave;
import db.essp.attendance.TcLeaveMain;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import db.essp.attendance.TcLeaveDetail;
import server.essp.attendance.modify.logic.LgLeaveModify;
import c2s.essp.attendance.Constant;

public class AcLeaveModifyApp extends AbstractESSPAction {
    /**
     * 销假申请动作:
     * 1.根据Request更新每天请假明细的changeHours和Remark
     * 2.设置请假记录的状态为Applying
     * 3.启动销假流程,并将流程Rid保存到请假记录
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
        LgLeave lgLeave = new LgLeave();
        TcLeaveMain leaveMain = lgLeave.getLeave(new Long(leaveRid));
        if(!Constant.STATUS_FINISHED.equals(leaveMain.getStatus())){
            throw new BusinessException("TC_LEAVE_MODIFY_00002","can not modify unfinished leave!");
        }
        RequestParseHelper helper = new RequestParseHelper(leaveMain,request);
        //将Request传过来的数据解析,放入Map中,Key--请假明细的Rid,Value--请假明细的ChangeHours和Remark
        Map requestData = helper.getRequestDate();
        LgLeaveModify lg = new LgLeaveModify();
        lg.appLeaveModify(leaveMain,requestData);
        data.getReturnInfo().setForwardID("success");
    }


}
