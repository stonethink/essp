package server.essp.tc.weeklyreport.logic;

import com.wits.excel.ExcelExporter;
import com.wits.util.Parameter;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.SheetExporter;
import com.wits.util.comDate;
import com.wits.util.PathGetter;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import java.io.FileOutputStream;
import java.io.File;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class WeeklyReportStatusExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE     = "Template_TC_WeeklyReport_Status.xls";
    public static final String DEFAULT_FILE_NAME = "TC_WeeklyReport_Satus.xls";
    public static final String SUB_OUT_DIR       = "excelReport/";

    public static final String SHEET_STATE       = "Status";
    public static final String SHEET_UNFILLED    = "Unfilled";
    public static final String SHEET_UNCONFIRMED = "Unconfirmed";
    public static final String SHEET_CONFIRMED   = "Confirmed";
    public static final String SHEET_UNLOCK      = "Unlock";
    public static final String SHEET_REJECTED    = "Rejected";

    public WeeklyReportStatusExporter() {
        this(DEFAULT_FILE_NAME);
    }
    public WeeklyReportStatusExporter(String fileName) {
        super(TEMPLATE_FILE, fileName);
    }
    public WeeklyReportStatusExporter(String outDir, String fileName) {
        super(TEMPLATE_FILE, outDir, fileName);
    }

    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        Date begin = (Date)inputData.get(DtoTcKey.BEGIN_PERIOD);
        Date end = (Date)inputData.get(DtoTcKey.END_PERIOD);
        Long hrAcntRid = (Long)inputData.get(DtoTcKey.ACNT_RID);

        LgWeeklyReportStatus lg = new LgWeeklyReportStatus(hrAcntRid, begin, end);
        List unfillList = lg.getUnfilled();
        List unconfirmList = lg.getLocked();
        List confirmedList = lg.getConfirmed();
        List unlockList = lg.getUnLock();
        List rejectedList = lg.getRejected();

        //Sheet Status
        int unfillCount    = 0;
        int unconfirmCount = 0;
        int confirmCount   = 0;
        int unlockCount    = 0;
        int rejectedCount  = 0;
        if(unfillList != null) {
            unfillCount = unfillList.size();
        }
        if(unconfirmList != null) {
            unconfirmCount = unconfirmList.size();
        }
        if(confirmedList != null) {
            confirmCount = confirmedList.size();
        }
        if(unlockList != null) {
            unlockCount = unlockList.size();
        }
        if(rejectedList != null) {
            rejectedCount = rejectedList.size();
        }

        Parameter statusParam = new Parameter();
        statusParam.put("period",comDate.dateToString(begin) + " ~ " + comDate.dateToString(end));
        statusParam.put("unfillCount", Integer.valueOf(unfillCount));
        statusParam.put("unconfirmCount", Integer.valueOf(unconfirmCount));
        statusParam.put("confirmCount", Integer.valueOf(confirmCount));
        statusParam.put("unlockCount", Integer.valueOf(unlockCount));
        statusParam.put("rejectedCount", Integer.valueOf(rejectedCount));

        HSSFSheet statusSheet = targetWorkbook.getSheet(SHEET_STATE);
        String statusCfgFile = getSheetCfgFileName(SHEET_STATE);
        SheetExporter status = new SheetExporter(targetWorkbook, statusSheet, statusCfgFile);
        status.export(statusParam);

        //Sheet Unfilled
        Parameter unfilledParam = new Parameter();
        unfilledParam.put("unfillList", unfillList);
        HSSFSheet unfilledSheet = targetWorkbook.getSheet(SHEET_UNFILLED);
        String unfilledCfgFile = getSheetCfgFileName(SHEET_UNFILLED);
        SheetExporter unfilled = new SheetExporter(targetWorkbook, unfilledSheet, unfilledCfgFile);
        unfilled.export(unfilledParam);

        //Sheet Unconfirmed
        Parameter unconfirmedParam = new Parameter();
        unconfirmedParam.put("unconfirmList", unconfirmList);
        HSSFSheet unconfirmedSheet = targetWorkbook.getSheet(SHEET_UNCONFIRMED);
        String unconfirmedCfgFile = getSheetCfgFileName(SHEET_UNCONFIRMED);
        SheetExporter unconfirmed = new SheetExporter(targetWorkbook, unconfirmedSheet, unconfirmedCfgFile);
        unconfirmed.export(unconfirmedParam);

        //Sheet Confirmed
        Parameter confirmedParam = new Parameter();
        confirmedParam.put("confirmedList", confirmedList);
        HSSFSheet confirmedSheet = targetWorkbook.getSheet(SHEET_CONFIRMED);
        String confirmedCfgFile = getSheetCfgFileName(SHEET_CONFIRMED);
        SheetExporter confirmed = new SheetExporter(targetWorkbook, confirmedSheet, confirmedCfgFile);
        confirmed.export(confirmedParam);

        //Sheet Unlock
        Parameter unlockParam = new Parameter();
        unlockParam.put("unlockList", unlockList);
        HSSFSheet unlockSheet = targetWorkbook.getSheet(SHEET_UNLOCK);
        String unlockCfgFile = getSheetCfgFileName(SHEET_UNLOCK);
        SheetExporter unlock = new SheetExporter(targetWorkbook, unlockSheet, unlockCfgFile);
        unlock.export(unlockParam);

        //Sheet Rejected
        Parameter rejectedParam = new Parameter();
        rejectedParam.put("rejectedList", rejectedList);
        HSSFSheet rejectedSheet = targetWorkbook.getSheet(SHEET_REJECTED);
        String rejectedCfgFile = getSheetCfgFileName(SHEET_REJECTED);
        SheetExporter rejectedfirmed = new SheetExporter(targetWorkbook, rejectedSheet, rejectedCfgFile);
        rejectedfirmed.export(rejectedParam);
    }
    /**
     * 数据写入目标文件
     * @param inputData Parameter
     * @throws Exception
     */
    public String scheduleExport(Parameter inputData) throws Exception {
        if (outDir == null) {
            outDir = "";
        }
        //---生成输入文件对应的路径----
        String targetFolderName;;
        targetFolderName= System.getProperty("user.dir").replace('\\','/') + "/" + SUB_OUT_DIR;
        String abstargetFileName = targetFolderName + targetFileName;
        log.info("outFolderName" + targetFolderName);
        log.info("absOutFileName" + abstargetFileName);

        doExport(inputData);

        //输出targetWorkbook到目标文件
        File outFolder = new File(targetFolderName);
        if (outFolder.exists() == false) {
            outFolder.mkdirs();
            log.info("create folder -- " + targetFolderName);
        }

        FileOutputStream fileOut = new FileOutputStream(abstargetFileName);
        targetWorkbook.write(fileOut);
        fileOut.close();
        return abstargetFileName;
    }

    public static void main(String[] args) {
        try {
            System.out.println(PathGetter.getStandardDir());
        } catch (Exception ex1) {
        }
        WeeklyReportStatusExporter exporter = new WeeklyReportStatusExporter();
        Parameter param = new Parameter();
        param.put(DtoTcKey.ACNT_RID, new Long(883));
        Date begin = new Date(2006-1900,7,26);
        Date end = new Date(2006-1900,8,25);
        param.put(DtoTcKey.BEGIN_PERIOD, begin);
        param.put(DtoTcKey.END_PERIOD, end);
        try {
            exporter.scheduleExport(param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
