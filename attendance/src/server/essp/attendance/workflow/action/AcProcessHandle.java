package server.essp.attendance.workflow.action;

import javax.servlet.http.*;

import c2s.dto.*;
import com.wits.util.*;
import server.essp.framework.action.*;
import server.framework.common.*;
import server.workflow.wfengine.*;
import c2s.essp.common.calendar.WrokTimeFactory;
import c2s.essp.common.calendar.WorkTime;
import server.essp.attendance.leave.logic.LgLeave;
import db.essp.attendance.TcLeaveMain;

public class AcProcessHandle extends AbstractESSPAction {
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
        String submitAll = request.getParameter("submitAll");
        //处理单个
        if(submitAll == null){
            submitSingleWork();
            data.getReturnInfo().setForwardID("single");
        }else{
            submitMulti();
            data.getReturnInfo().setForwardID("multi");
        }
    }
    /**
     * 处理多个工作流,工作流Rid参数名都为wkRid
     * 工作流当前的ActivityRid参数名为currActivityRid + wkRid
     */
    private void submitMulti() {
        String[] wkRids = getRequest().getParameterValues("wkRid");
        if(wkRids != null)
            for(int i = 0;i < wkRids.length ;i ++){
                String wkRid = wkRids[i];
                String currActivityRid = getRequest().getParameter("currActivityRid" + wkRid);
                try {
                    WfEngine engine = new WfEngine();
                    WfProcess process = engine.getProcessInstance(new Long(wkRid),
                            new Long(currActivityRid));
                    Parameter param = new Parameter();
                    param.put("request", getRequest());
                    param.put("response", getResponse());
                    param.put("submitAll","true");
                    param.put("agree",Boolean.TRUE);
                    String activityId = process.getDtoWorkingProcess().getCurrActivityId();
                    String proccessId = process.getDtoWorkingProcess().getProcessId();
                    //请假流程且是DM审核,参数必须包含请假天数
                    if("dm_review".equals(activityId) && c2s.essp.attendance.Constant.WF_LEAVE_PROCESS_ID.equals(proccessId)){
                        LgLeave lg = new LgLeave();
                        TcLeaveMain leave = lg.getLeaveByWkRid(process.getDtoWorkingProcess().getRid());
                        Double hours = leave.getActualTotalHours();
                        WorkTime wt = WrokTimeFactory.serverCreate();
                        float dayWorkHours = wt.getDailyWorkHours();
                        long days = hours == null ? 0 : (long)(hours.doubleValue()/dayWorkHours);
                        param.put("day",new Long(days));
                    }
                    process.setParameter(param);
                    process.finishActivity();
                } catch (WfException ex) {
                    throw new BusinessException("WF",
                                                "can not finish work flow activity",
                                        ex);
                }
            }
    }
    private void submitSingleWork() {
        String wkRid = getRequest().getParameter("wkRid");
        String currActivityRid = getRequest().getParameter("currActivityRid");
        try {
            WfEngine engine = new WfEngine();
            WfProcess process = engine.getProcessInstance(new Long(wkRid),
                    new Long(currActivityRid));
            Parameter param = new Parameter();
            param.put("request", getRequest());
            param.put("response", getResponse());
            process.setParameter(param);
            process.execActivity();
        } catch (WfException ex) {
            throw new BusinessException("WF",
                                        "can not execute work flow instance",
                                        ex);
        }
    }

}
