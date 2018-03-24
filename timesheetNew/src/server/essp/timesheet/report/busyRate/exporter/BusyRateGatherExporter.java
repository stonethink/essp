/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.exporter;

import java.util.Iterator;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.report.DtoBusyRateQuery;
import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;

public class BusyRateGatherExporter extends ExcelExporter{

    public static final String TEMPLATE_FILE =
            "Template_BusyRate_Gather.xls"; //模板文件名
    public static final String OUT_FILE_PREFIX =
            "WITS-WH-BusyRateGather";
    public static final String OUT_FILE_POSTFIX = ".xls";
    public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

    //Sheet名称
    public static final String SHEEET_YEAR = "Year";

    public BusyRateGatherExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public BusyRateGatherExporter(String outFile) {
        super(TEMPLATE_FILE, outFile);
    }

    public void doExport(Parameter inputData) throws Exception {
        Map lst = (Map) inputData.get(DtoBusyRateQuery.DTO_DEPT_LIST);
        Iterator keys = lst.keySet().iterator();
        int sheetIndex = 0;
        while (keys.hasNext()) {
            Parameter reportParam = new Parameter();
            String year = (String) keys.next();
            String title = year + " 人員忙閑度彙總(依月份)";
            ITreeNode root = (ITreeNode) lst.get(year);
            reportParam.put("title", title);
            reportParam.put("beanList", root);

            HSSFSheet reportSheet = targetWorkbook.cloneSheet(0);
            sheetIndex++;
            String reportSheetConfigFile = getSheetCfgFileName(SHEEET_YEAR);
            SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                    reportSheet, reportSheetConfigFile);
            targetWorkbook.setSheetName(sheetIndex, year);
            reportSheetEx.export(reportParam);
        }
        targetWorkbook.removeSheetAt(0);
    }
}

