/*
 * Created on 2008-3-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheet.exporter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import c2s.essp.timesheet.report.DtoTimesheetReport;
import c2s.essp.timesheet.report.DtoTsByPeriod;
import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;

public class TsReportPeriodExporter extends ExcelExporter{

    public static final String TEMPLATE_FILE =
            "Template_TimesheetPeriod.xls"; //模板文件名
    public static final String OUT_FILE_PREFIX =
            "WITS-WH-TimesheetPeriod";
    public static final String OUT_FILE_POSTFIX = ".xls";
    public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

    //Sheet名称
    public static final String SHEEET_PERIOD = "Period";

    public TsReportPeriodExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public TsReportPeriodExporter(String outFile) {
        super(TEMPLATE_FILE, outFile);
    }

    public void doExport(Parameter inputData) throws Exception {
        Map resMap = (Map) inputData.get(DtoTimesheetReport.DTO_RESULT_LIST);
        Iterator keys = resMap.keySet().iterator();
        int sheetIndex = 0;
        while (keys.hasNext()) {
            Parameter reportParam = new Parameter();
            String dateStr = (String) keys.next();
            DtoTsByPeriod dtoTs = (DtoTsByPeriod)resMap.get(dateStr);
            String empName = dtoTs.getEmpName();
            String empId = dtoTs.getEmpId();
            String unitCode = dtoTs.getUnitCode();
            List actList = dtoTs.getActHoursList();
            List leaveList = dtoTs.getLeaveHoursList();
            List otList = dtoTs.getOtHoursList();
            List standList = dtoTs.getStandardHoursList();
            reportParam.put("empName",empName);
            reportParam.put("empId",empId);
            reportParam.put("unitCode",unitCode);
            reportParam.put("dateStr",dateStr);
            reportParam.put("actHoursList",actList);
            reportParam.put("otHoursList", otList);
            reportParam.put("leaveHoursList", leaveList);
            reportParam.put("standardHoursList", standList);
            HSSFSheet reportSheet = targetWorkbook.cloneSheet(0);
            sheetIndex++;
            String reportSheetConfigFile = getSheetCfgFileName(SHEEET_PERIOD);
            SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                    reportSheet, reportSheetConfigFile);
            targetWorkbook.setSheetName(sheetIndex, dateStr);
            reportSheetEx.export(reportParam);
        }
        targetWorkbook.removeSheetAt(0);
    }
}