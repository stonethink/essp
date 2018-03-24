/*
 * Created on 2008-1-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.exporter;


import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;

public class SalaryWkTsExporter extends ExcelExporter{

    public static final String TEMPLATE_FILE = "Template_TsSalaryApportion_Timesheet.xls"; //模板文件名
    public static final String OUT_FILE_PREFIX ="WITS-WH-SalaryTimesheet";
    public static final String OUT_FILE_POSTFIX =".xls";
    public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

   //Sheet名称
   public static final String SHEEET_TIME_SHEET = "Timesheet";

   public SalaryWkTsExporter() {
       super(TEMPLATE_FILE, OUT_FILE);
   }

   public SalaryWkTsExporter(String outFile) {
       super(TEMPLATE_FILE, outFile);
   }


    public void doExport(Parameter inputData) throws Exception {
        List lst = (List)inputData.get(DtoSalaryWkHrQuery.DTO_QUERY_LIST);
        Parameter reportParam = new Parameter();
        reportParam.put("beanList", lst);

        HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_TIME_SHEET);
        String reportSheetConfigFile = getSheetCfgFileName(SHEEET_TIME_SHEET);

        SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
            reportSheet, reportSheetConfigFile);
        reportSheetEx.export(reportParam);
    }


}
