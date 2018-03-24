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
import c2s.essp.attendance.Constant;
import db.essp.attendance.TcLeaveDetail;
import java.util.Iterator;
import java.util.Set;

public class ModifyLeaveReviewApplication implements IWfApplication {
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
          RequestDispatcher dispatcher = request.getRequestDispatcher("/attendance/modify/ModifyLeaveReviewPre.do");
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
        Long wkRid = dtoWorkingProcess.getRid();
        LgLeave lgLeave = new LgLeave();
        TcLeaveMain leaveMain = lgLeave.getLeaveByWkRid(wkRid);
        if(agree != null && agree.booleanValue() && !Constant.STATUS_FINISHED.equals(leaveMain.getStatus())){
            leaveMain.setStatus(Constant.STATUS_FINISHED);
            Set detailSet = leaveMain.getTcLeaveDetails();
            if (detailSet != null && detailSet.size() > 0) {
                double totalHours = 0D;
                for (Iterator it = detailSet.iterator(); it.hasNext(); ) {
                    TcLeaveDetail detail = (TcLeaveDetail) it.next();
                    Double changeHours = detail.getChangeHours();
                    detail.setHours(changeHours);
                    detail.setHours(changeHours);
                    totalHours += (changeHours == null ? 0D : changeHours.doubleValue());
                }
                leaveMain.setActualTotalHours(new Double(totalHours));
            }
        }
        //审核多条记录时,为审核增加Log
        if(para.get("submitAll") != null){
            String decision = Constant.DECISION_AGREE;
            lgLeave.addReviewLog(leaveMain,decision,null);
        }
    }
}
