package server.essp.attendance.workflow.define;

import c2s.workflow.*;
import com.wits.util.*;
import server.workflow.wfdefine.*;
import server.workflow.wfengine.*;
import server.framework.common.BusinessException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.attendance.leave.logic.LgLeave;
import db.essp.attendance.TcLeaveMain;
import db.essp.attendance.TcLeaveLog;
import c2s.essp.attendance.Constant;


public class LeaveReviewApplication implements IWfApplication {
    /**
     * invokeApplication
     *
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
          RequestDispatcher dispatcher = request.getRequestDispatcher("/attendance/leave/LeaveReviewPre.do");
          try {
            dispatcher.forward(request, response);
          }
          catch (Exception ex) {
              throw new BusinessException("TC_LEAVE_20000","error calling leave review action!",ex);
          }
      }
    }

    /**
     * submitApplication
     *
     * @param dtoWorkingProcess DtoWorkingProcess
     * @param para Parameter
     * @throws WfException
     * @todo Implement this server.workflow.wfdefine.IWfApplication method
     */
    public void submitApplication(DtoWorkingProcess dtoWorkingProcess,
                                  Parameter para) throws WfException {
        Boolean agree = (Boolean) para.get("agree");
        Long day =  (Long) para.get("day");
        String curActivity = dtoWorkingProcess.getCurrActivityId();
        Long wkRid = dtoWorkingProcess.getRid();
        LgLeave lg = new LgLeave();
        TcLeaveMain leave = lg.getLeaveByWkRid(wkRid);
        //设置请假记录状态
        if(agree != null && !agree.booleanValue()){
            leave.setStatus(Constant.STATUS_DISAGREED);
        }else if( isFinish(agree, curActivity,day == null?0L : day.longValue())){
            lg.finish(leave);
        }else{
            leave.setStatus(Constant.STATUS_REVIEWING);
        }
        //审核多条记录时,为每条增加加班审核增加Log
        if(para.get("submitAll") != null){
            TcLeaveLog log = lg.addReviewLog(leave,Constant.DECISION_AGREE,null);
            dtoWorkingProcess.setCurrActivityDescription(log.getDeal());
        }
    }
    //判断请假流程是否结束
    //CEO审核同意,或项目经理审核同意且小于三天
    private boolean isFinish(Boolean agree, String curActivity,long days) {
        if("ceo_review".equals(curActivity) && agree != null && agree.booleanValue())
            return true;
        if("dm_review".equals(curActivity) && agree != null && agree.booleanValue()
                && days < 3)
            return true;
        return false;
    }
}
