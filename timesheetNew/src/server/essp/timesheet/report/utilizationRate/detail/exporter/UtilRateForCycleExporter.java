package server.essp.timesheet.report.utilizationRate.detail.exporter;

import com.wits.excel.SheetExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.util.Parameter;
import java.util.List;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import com.wits.excel.ExcelExporter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class UtilRateForCycleExporter extends ExcelExporter{

    public static final String TEMPLATE_FILE = "Template_TsUtilRate_Cycle.xls"; //模板文件名
   public static final String OUT_FILE_PREFIX ="WITS-WH-UillizationRate-Gather";
   public static final String OUT_FILE_POSTFIX =".xls";
   public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

   private static final String TILE_PREFIX_CYCLE = "人员使用率(依工時單周期)";
   //Sheet名称
   public static final String SHEEET_CYCLE = "TimesheetCycle";


   public UtilRateForCycleExporter() {
       super(TEMPLATE_FILE, OUT_FILE);
   }

   public UtilRateForCycleExporter(String outFile) {
       super(TEMPLATE_FILE, outFile);
   }

   public void doExport(Parameter inputData) throws Exception {
        List lst = (List)inputData.get(DtoUtilRateKey.DTO_DEPT_LIST);
        Parameter reportParam = new Parameter();
        reportParam.put("title", TILE_PREFIX_CYCLE);
        reportParam.put("beanList", lst);

        HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_CYCLE);
        String reportSheetConfigFile = getSheetCfgFileName(SHEEET_CYCLE);

        SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
            reportSheet, reportSheetConfigFile);
        reportSheetEx.export(reportParam);
    }




}
