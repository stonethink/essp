package server.essp.attendance.workflow.define;

import c2s.workflow.*;
import com.wits.util.*;
import server.workflow.wfdefine.*;
import server.workflow.wfengine.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import server.framework.common.BusinessException;
import server.essp.attendance.overtime.logic.LgOverTime;
import db.essp.attendance.TcOvertime;
import java.util.Date;
import db.essp.attendance.TcOvertimeLog;
import c2s.essp.attendance.Constant;

public class OverTimeReviewApplication implements IWfApplication {
    /**
     * invokeApplication
     * 开始执行加班审核时调用,forward到Action:/attendance/overtime/OverTimeReviewPre
     * @param dtoWorkingProcess DtoWorkingProcess
     * @param para Parameter
     * @throws WfException
     * @todo Implement this server.workflow.wfdefine.IWfApplication method
     */
    public void invokeApplication(DtoWorkingProcess dtoWorkingProcess,
                                  Parameter para) throws WfException {
     HttpServletRequest request = (HttpServletRequest) para.get("request");
     HttpServletResponse response = (HttpServletResponse) para.get("response");
     if(request != null && response != null){
       RequestDispatcher dispatcher = request.getRequestDispatcher("/attendance/overtime/OverTimeReviewPre.do");
       try {
         dispatcher.forward(request, response);
       }
       catch (Exception ex) {
           throw new BusinessException("TC_OVERTIME_20000","error calling overtime review action!",ex);
       }
     }
    }

    /**
     * submitApplication
     * 审核加班后调用
     * @param dtoWorkingProcess DtoWorkingProcess
     * @param para Parameter
     * @throws WfException
     * @todo Implement this server.workflow.wfdefine.IWfApplication method
     */
    public void submitApplication(DtoWorkingProcess dtoWorkingProcess,
                                  Parameter para) throws WfException {
        Boolean agree = (Boolean) para.get("agree");
        String curActivity = dtoWorkingProcess.getCurrActivityId();
        LgOverTime lg = new LgOverTime();
        Long wkRid = dtoWorkingProcess.getRid();
        TcOvertime overTime = lg.getOverTimeByWkRid(wkRid);
        //DM审核且同意设置加班记录结束
        if("dm_review".equals(curActivity) && agree != null && agree.booleanValue()){
            lg.finish(overTime);
        }else if(agree != null && !agree.booleanValue()){
            overTime.setStatus(Constant.STATUS_DISAGREED);
        }else{
            overTime.setStatus(Constant.STATUS_REVIEWING);
        }
        //审核多条记录时,为每条增加加班审核增加Log
        if(para.get("submitAll") != null){
            String loginId = lg.getUser().getUserLoginId();
            String decision = Constant.DECISION_AGREE;
            Date logDate = new Date();
            TcOvertimeLog log = new TcOvertimeLog();
            log.setTcOvertime(overTime);
            log.setLoginId(loginId);
            log.setLogDate(logDate);
            log.setDecision(decision);
            log.setIsEachDay(overTime.getActualIsEachDay());
            log.setDatetimeStart(overTime.getActualDatetimeStart());
            log.setDatetimeFinish(overTime.getActualDatetimeFinish());
            log.setTotalHours(overTime.getActualTotalHours());
            lg.addOverTimeReviewLog(log);
            dtoWorkingProcess.setCurrActivityDescription(log.getDeal());
        }
    }
}
