package server.essp.tc.attreport;

import javax.servlet.http.*;

import server.framework.common.*;
import com.wits.util.Parameter;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.comDate;
import java.util.Date;
import com.wits.excel.ExcelExporter;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import server.essp.tc.hrmanage.logic.AttendanceExporter;

import java.io.OutputStream;
import server.essp.common.excelUtil.AbstractExcelAction;

public class AcAttendanceReport extends AbstractExcelAction {


    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, OutputStream outputStream, Parameter parameter) throws
            Exception {
        String reportBegin = (String) parameter.get(DtoTcKey.BEGIN_PERIOD);
        String reportEnd = (String) parameter.get(DtoTcKey.END_PERIOD);
        String acntRid = (String) parameter.get(DtoTcKey.ACNT_RID);

        Parameter inputParam = new Parameter();
        Date begin = comDate.toDate(reportBegin);
        Date end = comDate.toDate(reportEnd);
        inputParam.put(DtoTcKey.ACNT_RID,new Long(acntRid));
        inputParam.put(DtoTcKey.BEGIN_PERIOD,begin);
        inputParam.put(DtoTcKey.END_PERIOD,end);
        inputParam.put(DtoTcKey.PERIOD_TYPE,DtoWeeklyReportSumByHr.ON_MONTH);

        String reportName = "AttendanceReport("+reportBegin+"-"+reportEnd+").xls";
        ExcelExporter export = new AttendanceExporter(reportName);
        try {
            export.webExport(httpServletResponse, outputStream, inputParam);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("","error exporting data!",ex);
        }

    }
}
