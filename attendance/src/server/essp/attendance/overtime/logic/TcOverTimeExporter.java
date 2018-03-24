package server.essp.attendance.overtime.logic;

import java.util.*;

import com.wits.excel.*;
import com.wits.util.*;
import org.apache.poi.hssf.usermodel.*;

public class TcOverTimeExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_TC_Overtime.xls"; //模板文件名
    public static final String OUT_FILE = "TC_Overtime.xls"; //导出文件名
    //Sheet名称
    public static final String SHEEET_REPORT = "Report";
    private static final String TITLE_TAIL = " 加班汇总统计表";

    public TcOverTimeExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public TcOverTimeExporter(String outFileName) {
        super(TEMPLATE_FILE, outFileName);
    }

    public void doExport(Parameter inputData) throws Exception {
        String acntRidStr = (String) inputData.get("acntRid");
        String beginDateStr = (String) inputData.get("beginDate");
        String endDateStr = (String) inputData.get("endDate");
        Long acntRid = null;
        Date beginDate = null;
        Date endDate = null;
        LgOverTimeReport lg = new LgOverTimeReport();
        if (acntRidStr != null && !"".equals(acntRidStr)) {
            acntRid = Long.valueOf(acntRidStr);
        }
        if (beginDateStr != null && !"".equals(beginDateStr)) {
            beginDate = comDate.toDate(beginDateStr);
        }
        if (endDateStr != null && !"".equals(endDateStr)) {
            endDate = comDate.toDate(endDateStr);
        }

        String title = comDate.dateToString(beginDate) + " ~ " +
                       comDate.dateToString(endDate) + TITLE_TAIL;

        List list = lg.getData(acntRid, beginDate, endDate);

        Parameter reportParam = new Parameter();
        reportParam.put("title", title);
        reportParam.put("report", list);
        HSSFSheet reportSheet = targetWorkbook.getSheet(SHEEET_REPORT);
        String reportSheetConfigFile = getSheetCfgFileName(SHEEET_REPORT);
        SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
            reportSheet, reportSheetConfigFile);
        reportSheetEx.export(reportParam);
    }

    public static void main(String[] args) {
        TcOverTimeExporter exporter = new TcOverTimeExporter();
        Parameter param = new Parameter();
        param.put("acntRid", "883");
        param.put("beginDate", "2006-06-26");
        param.put("endDate", "2006-12-25");
        try {
            exporter.export(param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
