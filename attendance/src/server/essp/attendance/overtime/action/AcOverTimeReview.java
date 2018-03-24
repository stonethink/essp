package server.essp.attendance.overtime.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import server.essp.attendance.overtime.form.AfOverTimeReview;
import server.essp.attendance.overtime.logic.LgOverTime;
import db.essp.attendance.TcOvertime;
import db.essp.attendance.TcOvertimeLog;
import java.util.Date;
import c2s.essp.attendance.Constant;
import java.util.Calendar;
import java.util.List;
import c2s.essp.common.calendar.WorkCalendar;
import com.wits.util.comDate;
import db.essp.attendance.TcOvertimeDetail;
import com.wits.util.StringUtil;
import java.util.Map;
import java.util.HashMap;
import server.workflow.wfengine.WfEngine;
import server.workflow.wfengine.WfProcess;
import server.workflow.wfengine.*;
import com.wits.util.Parameter;

public class AcOverTimeReview extends AbstractESSPAction {
    /**
     * executeAct
     * 新增加班审核记录
     * 1.根据审核决定(decision)生成Log
     *   Agree和Disgree根据加班实际数据生成Log及LogDetail;
     *   Modify根据客户端传的数据生成Log及LogDetail
     * 2.保存Log及LogDetail
     * 3.完成工作流当前的步骤
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

        AfOverTimeReview form = (AfOverTimeReview) this.getForm();
        String decision = form.getDecision();

        LgOverTime lg = new LgOverTime();
        TcOvertime overTime = lg.getOverTime(new Long(form.getRid()));
        if(Constant.STATUS_FINISHED.equals(overTime.getStatus()) ||
           Constant.STATUS_DISAGREED.equals(overTime.getStatus())){
            throw new BusinessException("TC_OVER_2000","This over time flow has been finished!");
        }

        TcOvertimeLog log = new TcOvertimeLog();
        log.setTcOvertime(overTime);
        log.setComments(form.getComments());
        log.setLoginId(user.getUserLoginId());//审核人
        log.setLogDate(new Date());
        log.setDecision(decision);
        if(Constant.DECISION_AGREE.equals(decision) ||
           Constant.DECISION_DISAGREE.equals(decision)){
            //Agree和DisAgree根据加班申请获得Log
            log.setDatetimeStart(overTime.getActualDatetimeStart());
            log.setDatetimeFinish(overTime.getActualDatetimeFinish());
            log.setIsEachDay(overTime.getActualIsEachDay());
            log.setTotalHours(overTime.getActualTotalHours());
        }else if(Constant.DECISION_MODIFY.equals(decision)){
            Boolean actualIsEachDay = new Boolean(form.getActualIsEachDay());
            Date datetimeStart = Constant.parseDate(form.getActualDateFrom(),form.getActualTimeFrom());
            Date datetimeFinish = Constant.parseDate(form.getActualDateTo(),form.getActualTimeTo());
            Double actualTotalHours = new Double(form.getActualTotalHours());

            log.setIsEachDay(actualIsEachDay);
            log.setDatetimeStart(datetimeStart);
            log.setDatetimeFinish(datetimeFinish);
            log.setTotalHours(actualTotalHours);

            overTime.setActualIsEachDay(actualIsEachDay);
            overTime.setActualDatetimeStart(datetimeStart);
            overTime.setActualDatetimeFinish(datetimeFinish);
            overTime.setActualTotalHours(actualTotalHours);

            try {
                lg.getDbAccessor().delete(overTime.getTcOvertimeDetails());
            } catch (Exception ex) {
                throw new BusinessException(ex);
            }

            Calendar calStart = Calendar.getInstance();
            Calendar calFinish = Calendar.getInstance();
            calStart.setTime(datetimeStart);
            calFinish.setTime(datetimeFinish);
            List days = WorkCalendar.getDays(calStart, calFinish);
            if(days != null || days.size() > 0){
                String detailInfo = form.getDetailInfo();
                parseDetailParam(detailInfo);
                for(int i = 0 ;i < days.size();i ++){
                    Calendar perDay = (Calendar) days.get(i);
                    String dayStr = comDate.dateToString(perDay.getTime(),"yyyyMMdd");
                    String hours  = getHours(dayStr);
                    String remark = getRemark(dayStr);
                    TcOvertimeDetail detail = new TcOvertimeDetail();
                    detail.setTcOvertime(overTime);
                    if("".equals(hours))
                        detail.setHours(new Double(0));
                    else
                        detail.setHours(new Double(hours));
                    detail.setOvertimeDay(perDay.getTime());
                    detail.setRemark(remark);
                    lg.addOverTimeDetail(detail);
                }
            }
        }else{
            throw new BusinessException("TC_OVER_1000","illegal review decision:["+decision+"]");
        }

        overTime.setComments(form.getComments());
//        if(Constant.DECISION_AGREE.equals(decision) ||
//           Constant.DECISION_MODIFY.equals(decision))
//            overTime.setStatus(Constant.STATUS_REVIEWING);
//        else if(Constant.DECISION_DISAGREE.equals(decision))
//            overTime.setStatus(Constant.STATUS_DISAGREED);

        lg.addOverTimeReviewLog(log);

        //完成工作流当前的步骤
        try {
            String currActivityRid = request.getParameter("currActivityRid");
            WfEngine engine = new WfEngine();
            WfProcess process = engine
                                .getProcessInstance(overTime.getWkId(),new Long(currActivityRid));
            process.getDtoWorkingProcess().setCurrActivityDescription(log.getDeal());
            Parameter param = new Parameter();
            boolean agree = Constant.DECISION_AGREE.equals(decision) ||
                                   Constant.DECISION_MODIFY.equals(decision);
            param.put("agree", new Boolean(agree));
            process.setParameter(param);
            process.finishActivity();
        } catch (WfException ex1) {
            throw new BusinessException("TC_OVERTME_20000","error get work flow intance!",ex1);
        }
        data.getReturnInfo().setForwardID("success");
    }
    private String getHours(String dayStr){
        String hours = (String) logDetailMap.get("hours" + dayStr);
        return StringUtil.nvl(hours);
    }
    private String getRemark(String dayStr){
        String remark = (String) logDetailMap.get("remark" + dayStr);
        return StringUtil.nvl(remark);
    }
    //解析参数detailInfo,
    private void parseDetailParam(String detailInfo){
//        String detailInfo = StringUtil.nvl(getRequest().getParameter("detailInfo"));
        String[] parameters = detailInfo.split("&");
        for(int i = 0;i < parameters.length;i ++){
            int splitIndex = parameters[i].indexOf("=");
            String name = parameters[i].substring(0,splitIndex );
            String value = parameters[i].substring(splitIndex + 1);
            logDetailMap.put(name,value);
        }
    }
    private Map logDetailMap = new HashMap();
}
