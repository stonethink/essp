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
     * �������붯��:
     * 1.����Request����ÿ�������ϸ��changeHours��Remark
     * 2.������ټ�¼��״̬ΪApplying
     * 3.������������,��������Rid���浽��ټ�¼
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
        //��Request�����������ݽ���,����Map��,Key--�����ϸ��Rid,Value--�����ϸ��ChangeHours��Remark
        Map requestData = helper.getRequestDate();
        LgLeaveModify lg = new LgLeaveModify();
        lg.appLeaveModify(leaveMain,requestData);
        data.getReturnInfo().setForwardID("success");
    }


}
