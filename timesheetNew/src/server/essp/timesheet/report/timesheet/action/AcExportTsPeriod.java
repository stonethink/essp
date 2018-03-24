/*
 * Created on 2008-3-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheet.action;

import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.essp.timesheet.report.timesheet.exporter.TsReportPeriodExporter;
import server.essp.timesheet.report.timesheet.service.ITsReportService;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoUser;
import c2s.essp.timesheet.report.DtoTimesheetReport;
import com.wits.util.Parameter;
import com.wits.util.ThreadVariant;
import com.wits.util.comDate;

   public class AcExportTsPeriod extends AbstractExcelAction {
        public void execute(HttpServletRequest request,
                HttpServletResponse response,
                OutputStream os, Parameter param) throws Exception {
    ThreadVariant thread = ThreadVariant.getInstance();
    DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
    thread.set(DtoUser.SESSION_USER, user);
    Date begin = comDate.toDate((String)param.get("begin"));
    Date end = comDate.toDate((String)param.get("end"));
    ITsReportService service = (ITsReportService)this.getBean("tsReportService");
    service.setExcelDto(true);
    Map resMap = service.queryByPeriodForExport(begin,end,user.getUserLoginId());
    Parameter inputData = new Parameter();
    inputData.put(DtoTimesheetReport.DTO_RESULT_LIST, resMap);
    TsReportPeriodExporter exporter = new TsReportPeriodExporter();
    try{
       exporter.webExport(response, os, inputData);
    }catch(Exception e){
       e.printStackTrace();
       throw new BusinessException("Export Error!" + e);
    }
   }
 }      

