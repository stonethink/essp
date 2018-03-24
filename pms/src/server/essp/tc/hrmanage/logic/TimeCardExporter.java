package server.essp.tc.hrmanage.logic;

import com.wits.excel.ExcelExporter;
import com.wits.util.Parameter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.SheetExporter;
import java.util.Calendar;
import server.essp.tc.hrmanage.viewbean.VbTcLaborReport;
import java.util.ArrayList;
import java.util.List;
import com.wits.util.comDate;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import java.util.Date;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.tc.outwork.logic.LgOutWork;
import c2s.dto.ITreeNode;

/**
 * TimeCard导出Excel报表类.报表分为Attendance(差勤表)和TimeCard(人力表)两个Sheet.
 * 导出有两种方式:同时导出两个Sheet的数据;只导出Attendance的数据
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class TimeCardExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_TC.xls";//模板文件名
    public static final String OUT_FILE = "Weekly Report.xls";//导出文件名

    private final static String ORG_UT_RATE_TITLE_POSTFIX = " 人员使用率报表";

    //Sheet名称
    public static final String SHEEET_ATTENDANCE = "Attendance";
    public static final String SHEET_ORG_UT_RATE = "OrgUtRate";
    public static final String SHEEET_TIMECARD = "TimeCard";
    public static final String SHEET_TRAVELS = "Travels";


    public TimeCardExporter(){
        super(TEMPLATE_FILE,OUT_FILE);
    }
    public TimeCardExporter(String outFile){
        super(TEMPLATE_FILE,outFile);
    }

    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        Long accountId = (Long) inputData.get(DtoTcKey.ACNT_RID);
        Date fromTime = (Date) inputData.get(DtoTcKey.BEGIN_PERIOD);
        Date toTime = (Date) inputData.get(DtoTcKey.END_PERIOD);
        String flag=(String)inputData.get(DtoTcKey.PERIOD_TYPE);
        //准备Attendance Sheet所需数据并导出
        LgTcExcel lgTcExcel = new LgTcExcel();
        List Result = lgTcExcel.AllDetailList(accountId, fromTime, toTime, flag);
        Parameter attParam = new Parameter();
        Object periodCellData = Result.get(0);
        Object tblData = Result.get(1);
        attParam.put("periodCellData", periodCellData);
        attParam.put("tblData", tblData);
        HSSFSheet attendSheet = targetWorkbook.getSheet(SHEEET_ATTENDANCE);
        String attendSheetConfigFile = getSheetCfgFileName(SHEEET_ATTENDANCE);
        SheetExporter attendSheetEx = new SheetExporter(targetWorkbook,attendSheet, attendSheetConfigFile);
        attendSheetEx.export(attParam);

        //部门人员使用率报表
        exportOrgUtRate(accountId, fromTime, toTime);

        //准备TimeCard Sheet所需数据并导出
        LgTcLaborReport lg = new LgTcLaborReport();
        VbTcLaborReport report = lg.getLaborReport(accountId,fromTime,toTime);
        Parameter tcParam = new Parameter();
        List cellData2 = new ArrayList();
        cellData2.add(report);
//        List tbl2Data = report.getLaborsList();
        tcParam.put("cellData2",report);
        tcParam.put("tblData2",report.getRoot());
        HSSFSheet tcSheet = targetWorkbook.getSheet(SHEEET_TIMECARD);
        String tcSheetConfigFile = getSheetCfgFileName(SHEEET_TIMECARD);
        SheetExporter se = new SheetExporter(targetWorkbook,tcSheet, tcSheetConfigFile);
        se.export(tcParam);

        //导出出差信息
        LgOutWork lgOutWork=new LgOutWork();
        Parameter travelsParam=new Parameter();
        travelsParam.put("period",comDate.dateToString(fromTime)+"~"+comDate.dateToString(toTime));
        travelsParam.put("tblData2",lgOutWork.listAllForTCReport(fromTime,toTime));
        HSSFSheet travelsSheet=targetWorkbook.getSheet(SHEET_TRAVELS);
        String travelSheetConfigFile=getSheetCfgFileName(SHEET_TRAVELS);
        SheetExporter travelsExporter=new SheetExporter(targetWorkbook,travelsSheet,travelSheetConfigFile);
        travelsExporter.export(travelsParam);
    }

    private void exportOrgUtRate(Long hrAcntRid, Date beginDate, Date endDate) {
        LgOrgUtRate lg = new LgOrgUtRate(hrAcntRid, beginDate, endDate);
        ITreeNode root = lg.getUtRateTree(true);
        String periodStr = comDate.dateToString(beginDate) + " ~ "
                            + comDate.dateToString(endDate);

        Parameter reportParam = new Parameter();
        reportParam.put("title", periodStr + ORG_UT_RATE_TITLE_POSTFIX);
        reportParam.put("dataTree", root);

        HSSFSheet reportSheet = targetWorkbook.getSheet(SHEET_ORG_UT_RATE);
         String reportSheetConfigFile = getSheetCfgFileName(SHEET_ORG_UT_RATE);
         SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
             reportSheet, reportSheetConfigFile);
         reportSheetEx.export(reportParam);

    }


    public static void main(String[] args) {

        Calendar begin = Calendar.getInstance();
        begin.set(2006,3,26,0,0,0);
        begin.set(Calendar.MILLISECOND,0);
        Calendar end = Calendar.getInstance();
        end.set(2006,4,25,0,0,0);
        end.set(Calendar.MILLISECOND,0);
        String flg = DtoWeeklyReportSumByHr.ON_MONTH;

        Parameter inputData = new Parameter();
        inputData.put(DtoTcKey.ACNT_RID,new Long(6024));
        inputData.put(DtoTcKey.BEGIN_PERIOD,begin.getTime());
        inputData.put(DtoTcKey.END_PERIOD,end.getTime());
        inputData.put(DtoTcKey.PERIOD_TYPE,DtoWeeklyReportSumByHr.ON_MONTH);

        LgTcExcel lgTcExcel = new LgTcExcel();
        List Result = lgTcExcel.AllDetailList(new Long(6024), begin.getTime(), end.getTime(), flg);
        Parameter attParam = new Parameter();
        Object periodCellData = Result.get(0);
        Object tblData = Result.get(1);
        attParam.put("periodCellData", periodCellData);
        attParam.put("tblData", tblData);

        TimeCardExporter timecardexporter = new TimeCardExporter();
        try {
            timecardexporter.export(inputData);


//            HSSFWorkbook workbook = timecardexporter.getWorkbook();
//            FileOutputStream out = new FileOutputStream(new File("/out.xls"));
//            workbook.write(out);
//            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class CellData{

    }
}
