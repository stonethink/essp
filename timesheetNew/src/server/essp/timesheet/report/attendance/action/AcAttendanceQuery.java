/*
 * Created on 2008-6-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attendance.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.attendance.service.IAttendanceService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoAttendanceQuery;

public class AcAttendanceQuery extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData data) throws
         BusinessException {
         IAttendanceService lg = (IAttendanceService)this.
                                     getBean("attendanceService");
         DtoAttendanceQuery dtoQuery = (DtoAttendanceQuery) data.getInputInfo().
                         getInputObj(DtoAttendanceQuery.DTO_QUERY);
         lg.setExcelDto(false);
         try {
             List resList = lg.queryAttendance(dtoQuery);
             data.getReturnInfo().setReturnObj(DtoAttendanceQuery.
                     DTO_RESULT_LIST,resList);
         } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }
  }
