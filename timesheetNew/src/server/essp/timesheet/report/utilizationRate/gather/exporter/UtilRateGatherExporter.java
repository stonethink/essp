package server.essp.timesheet.report.utilizationRate.gather.exporter;

import com.wits.util.Parameter;
import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import c2s.dto.ITreeNode;
import java.util.Map;
import java.util.Iterator;

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
public class UtilRateGatherExporter extends ExcelExporter{

    public static final String TEMPLATE_FILE =
            "Template_TsUtilRate_Gather_Months.xls"; //模板文件名
    public static final String OUT_FILE_PREFIX =
            "WITS-WH-UillizationRateGather";
    public static final String OUT_FILE_POSTFIX = ".xls";
    public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

    //Sheet名称
    public static final String SHEEET_MONTH = "Months";

    public UtilRateGatherExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public UtilRateGatherExporter(String outFile) {
        super(TEMPLATE_FILE, outFile);
    }

    public void doExport(Parameter inputData) throws Exception {
        Map lst = (Map) inputData.get(DtoUtilRateKey.DTO_DEPT_LIST);
        Iterator keys = lst.keySet().iterator();
        int sheetIndex = 0;
        while (keys.hasNext()) {
            Parameter reportParam = new Parameter();
            String year = (String) keys.next();
            String title = year + " 人員使用率彙總(依月份)";
            ITreeNode root = (ITreeNode) lst.get(year);
            reportParam.put("title", title);
            reportParam.put("beanList", root);

            HSSFSheet reportSheet = targetWorkbook.cloneSheet(0);
            sheetIndex++;
            String reportSheetConfigFile = getSheetCfgFileName(SHEEET_MONTH);
            SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                    reportSheet, reportSheetConfigFile);
            targetWorkbook.setSheetName(sheetIndex, year);
            reportSheetEx.export(reportParam);
        }
        targetWorkbook.removeSheetAt(0);
    }
}
