/*
 * Created on 2008-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attendance.action;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.attendance.exporter.AttendanceExporter;
import server.essp.timesheet.report.attendance.service.IAttendanceService;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.report.DtoAttendanceQuery;

public class AcAttendanceExport extends AbstractExcelAction {
        public void execute(HttpServletRequest request,
                HttpServletResponse response,
                OutputStream os, Parameter param) throws Exception {
        IAttendanceService lg = (IAttendanceService) this.getBean("attendanceService");
        String bDate = (String) param.get(DtoAttendanceQuery.DTO_BEGIN_DATE);
        String eDate = (String) param.get(DtoAttendanceQuery.DTO_END_DATE);
        String site = (String)param.get(DtoAttendanceQuery.DTO_SITE);
        DtoAttendanceQuery dtoQuery = new DtoAttendanceQuery();
        Date beginDate = null;
        Date endDate = null;
        if(bDate != null){
           beginDate = comDate.toDate(bDate, comDate.pattenDate);
        }
        if(eDate != null){
           endDate = comDate.toDate(eDate, comDate.pattenDate);
        }
        
        dtoQuery.setBegin(beginDate);
        dtoQuery.setEnd(endDate);
        dtoQuery.setSite(site);
        List resultList = lg.queryAttendance(dtoQuery);
        String fileName = AttendanceExporter.OUT_FILE_PREFIX
                      + bDate + "_" + eDate
                      + AttendanceExporter.OUT_FILE_POSTFIX;
        Parameter inputData = new Parameter();
        inputData.putAll(param);
        inputData.put(DtoAttendanceQuery.DTO_RESULT_LIST, resultList);
        try {
            AttendanceExporter exporter = new AttendanceExporter(fileName);
            exporter.webExport(response, os, inputData);
          } catch (Exception ex) {
             throw new BusinessException("Error",
                "Error when Call export() 0f SampleExcelExporter ", ex);
           }
         }
        }
