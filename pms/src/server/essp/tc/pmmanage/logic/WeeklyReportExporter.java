package server.essp.tc.pmmanage.logic;

import com.wits.excel.SheetExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.util.Parameter;
import com.wits.excel.ExcelExporter;
import c2s.dto.ITreeNode;
import com.wits.util.comDate;
import db.essp.pms.Account;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WeeklyReportExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_TC_WeeklyReport_PM.xls"; //模板文件名
    public static final String OUT_FILE = "TC_WeeklyReport_PM.xls"; //导出文件名
    //Sheet名称
    public static final String SHEEET_TIMECARD = "TimeCard";
    public WeeklyReportExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public WeeklyReportExporter(String outFile) {
        super(TEMPLATE_FILE, outFile);
    }
    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        String beginPeriod = inputData.get("beginPeriod").toString();
        String endPeriod = inputData.get("endPeriod").toString();
        String acntRid = inputData.get("acntRid").toString();
        Parameter param = new Parameter();
        LgPeriodReportExport lg = new LgPeriodReportExport();
        ITreeNode root = lg.getReportTree(acntRid, comDate.toDate(beginPeriod),
                                          comDate.toDate(endPeriod));
        Account acnt = lg.getAccount(acntRid);

        param.put("dataTree", root);
        param.put("personNum",String.valueOf(lg.getPersonCount()));
        param.put("periodBegin", beginPeriod);
        param.put("periodEnd", endPeriod);
        param.put("acntName", acnt.getId()+" -- "+acnt.getName());
        param.put("manager", acnt.getManager());

        HSSFSheet timingSheet = targetWorkbook.getSheet(SHEEET_TIMECARD);
        String timingSheetConfigFile = getSheetCfgFileName(SHEEET_TIMECARD);
        SheetExporter timingSheetEx = new SheetExporter(targetWorkbook,
            timingSheet, timingSheetConfigFile);
        timingSheetEx.export(param);

    }
    public static void main(String[] arg) {
        WeeklyReportExporter exporter = new WeeklyReportExporter();
        Parameter param = new Parameter();
        param.put("beginPeriod","2006-08-5");
        param.put("endPeriod","2006-08-11");
        param.put("acntRid","6022");
        try {
            exporter.export(param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
