/*
 * Created on 2008-6-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attendance.exporter;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import c2s.essp.timesheet.report.DtoAttendanceQuery;

import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;

public class AttendanceExporter  extends ExcelExporter {

        public static final String TEMPLATE_FILE = "Template_TsAttendanceReport.xls"; // 模板文件名
    
        public static final String OUT_FILE_PREFIX = "WITS-WH-Attendance";
    
        public static final String OUT_FILE_POSTFIX = ".xls";
    
        public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; // 导出文件名
    
        // Sheet名称
        public static final String SHEEET_ATTENDANCE = "Attendance";
    
        public AttendanceExporter() {
            super(TEMPLATE_FILE, OUT_FILE);
        }
    
        public AttendanceExporter(String outFile) {
            super(TEMPLATE_FILE, outFile);
        }
    
        public void doExport(Parameter inputData) throws Exception {
                List resList = (List) inputData.get(DtoAttendanceQuery.DTO_RESULT_LIST);
                if (resList != null && resList.size() > 0) {
                    Parameter reportParam = new Parameter();
                    reportParam.put("beanList", resList);
                    HSSFSheet reportSheet = targetWorkbook.getSheet(SHEEET_ATTENDANCE);
                    String reportSheetConfigFile = getSheetCfgFileName(SHEEET_ATTENDANCE);
                    SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                            reportSheet, reportSheetConfigFile);
                    reportSheetEx.export(reportParam);
                }
        }
}
