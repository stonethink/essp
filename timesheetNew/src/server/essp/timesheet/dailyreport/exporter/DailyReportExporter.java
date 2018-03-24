package server.essp.timesheet.dailyreport.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.dailyreport.DtoDailyExport;

import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;
import com.wits.util.comDate;

public class DailyReportExporter extends ExcelExporter {
	
	public static final String TEMPLATE_FILE = "Template_DailyReport.xls";//模板文件名
    public static final String OUT_FILE = "DailyReport.xls";//导出文件名

    //Sheet名称
    public static final String SHEEET_PERSON = "Person";
    public static final String SHEEET_PROJECT = "Project";
    
    public DailyReportExporter() {
    	super(TEMPLATE_FILE, OUT_FILE);
    }
    
    public DailyReportExporter(String outFile) {
    	super(TEMPLATE_FILE, outFile);
    }
    
    public void doExport(Parameter inputData) throws Exception {
    	Map resultMap = (Map) inputData.get(DtoDailyExport.DTO_RESULTMAP);
    	String accountName = (String)resultMap.get("name");
    	String resourceId = (String)inputData.get("resourceId");
    	Map yesterdayDataMap = (Map) resultMap.get("yesterday");
    	Map todayMap = (Map) resultMap.get("today");
    	Map yesterdayPerson = (Map) yesterdayDataMap.get("person");
    	Map todayPerson = (Map) todayMap.get("person");
    	Date today = (Date)inputData.get("today");
    	Date yesterday = (Date)inputData.get("yesterday");
    	//sheet1
        Parameter tsDetailParam = new Parameter();
        Object name = accountName;
        tsDetailParam.put("accountName", name);
        tsDetailParam.put("yesterdayData", (ITreeNode)yesterdayPerson.get(resourceId));
        tsDetailParam.put("todayData", (ITreeNode)todayPerson.get(resourceId));
        tsDetailParam.put("yesterday", comDate.dateToString(yesterday));
        tsDetailParam.put("today", comDate.dateToString(today));
        HSSFSheet tsDetailSheet = targetWorkbook.getSheet(SHEEET_PERSON);
        String tcSheetConfigFile = getSheetCfgFileName(SHEEET_PERSON);
        SheetExporter se = new SheetExporter(targetWorkbook, tsDetailSheet, tcSheetConfigFile);
        se.export(tsDetailParam);
        
        //sheet2
        tsDetailParam = new Parameter();
        tsDetailParam.put("accountName", name);
        tsDetailParam.put("yesterdayData", (ITreeNode)yesterdayDataMap.get("project"));
        tsDetailParam.put("todayData", (ITreeNode)todayMap.get("project"));
        tsDetailParam.put("yesterday", comDate.dateToString(yesterday));
        tsDetailParam.put("today", comDate.dateToString(today));
        tsDetailSheet = targetWorkbook.getSheet(SHEEET_PROJECT);
        tcSheetConfigFile = getSheetCfgFileName(SHEEET_PROJECT);
        se = new SheetExporter(targetWorkbook, tsDetailSheet, tcSheetConfigFile);
        se.export(tsDetailParam);
    }
 
    public File getExportFile(Parameter inputData) throws Exception {
    	doExport(inputData);
    	File myFile = new File("DailyReport.xls");
    	FileOutputStream fileOut = new FileOutputStream(myFile);
        targetWorkbook.write(fileOut);
        fileOut.close();
    	return myFile;
    }


}
