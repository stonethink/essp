package server.essp.attendance.leave.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.essp.attendance.leave.form.AfLeaveReview;
import server.essp.attendance.leave.logic.LgLeave;
import db.essp.attendance.TcLeaveMain;
import server.workflow.wfengine.WfProcess;
import server.workflow.wfengine.WfException;
import server.workflow.wfengine.WfEngine;
import com.wits.util.Parameter;
import c2s.essp.common.calendar.WrokTimeFactory;
import c2s.essp.common.calendar.WorkTime;
import db.essp.attendance.TcLeaveLog;
import c2s.essp.attendance.Constant;

public class AcLeaveReview extends AbstractESSPAction {
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
        AfLeaveReview form = (AfLeaveReview) this.getForm();
        LgLeave lg = new LgLeave();
        TcLeaveMain leave = lg.reviewLeave(form);
        TcLeaveLog log = lg.addReviewLog(leave,form.getDecision(),form.getComments());
        //完成工作流当前的步骤
        String decision = form.getDecision();
        try {
            String currActivityRid = request.getParameter("currActivityRid");
            WfEngine engine = new WfEngine();
            WfProcess process = engine
                                .getProcessInstance(leave.getWkId(),new Long(currActivityRid));
            process.getDtoWorkingProcess().setCurrActivityDescription(log.getDeal());
            String activityId = process.getDtoWorkingProcess().getCurrActivityId();

            Parameter param = new Parameter();
            boolean agree = Constant.DECISION_AGREE.equals(decision) ||
                            Constant.DECISION_MODIFY.equals(decision);
            param.put("agree", new Boolean(agree));
            if("dm_review".equals(activityId)){
                Double hours = leave.getActualTotalHours();
                WorkTime wt = WrokTimeFactory.serverCreate();
                float dayWorkHours = wt.getDailyWorkHours();
                long days = hours == null ? 0 : (long)(hours.doubleValue()/dayWorkHours);
                param.put("day",new Long(days));
            }
            process.setParameter(param);
            process.finishActivity();
        } catch (WfException ex1) {
            throw new BusinessException("TC_OVERTME_20000","error get work flow intance!",ex1);
        }
    }
}
