package server.essp.timesheet.report.humanreport.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import c2s.essp.timesheet.report.*;

import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;
import com.wits.util.PathGetter;

public class HumanReportExporter extends ExcelExporter {
	
	public static final String TEMPLATE_FILE = "Template_WeeklyReport.xls";//模板文件名
    public static final String OUT_FILE = "TimeCard.xls";//导出文件名

    //Sheet名称
    public static final String SHEEET_RESULT = "TimeCard";
    public static final String SHEEET_TIME = "Statistics";
    
    public HumanReportExporter() {
    	super(TEMPLATE_FILE, OUT_FILE);
    }
    
    public HumanReportExporter(String outFile) {
    	super(TEMPLATE_FILE, outFile);
    }
    public void doExport(Parameter inputData) throws Exception {
    	List list = (List) inputData.get(DtoHumanReport.DTO_QUERY_RESULT);
        String begin = (String) inputData.get(DtoHumanReport.DTO_BEGIN);
        String end = (String) inputData.get(DtoHumanReport.DTO_END);
        
        //sheet1
        Parameter tsDetailParam = new Parameter();
        Object period = begin+"~"+end;
        tsDetailParam.put("period", period);
        tsDetailParam.put("tblData", list);
        HSSFSheet tsDetailSheet = targetWorkbook.getSheet(SHEEET_RESULT);
        String tcSheetConfigFile = getSheetCfgFileName(SHEEET_RESULT);
        SheetExporter se = new SheetExporter(targetWorkbook, tsDetailSheet, tcSheetConfigFile);
        se.export(tsDetailParam);
        
        //sheet2
        List timesList = (List)inputData.get(DtoHumanTimes.DTO_QUERY_LIST);
        tsDetailParam = new Parameter();
        tsDetailParam.put("tblData", timesList);
        tsDetailSheet = targetWorkbook.getSheet(SHEEET_TIME);
        tcSheetConfigFile = getSheetCfgFileName(SHEEET_TIME);
        se = new SheetExporter(targetWorkbook, tsDetailSheet, tcSheetConfigFile);
        se.export(tsDetailParam);
    }
    /**
     * 数据写入目标文件
     * @param inputData Parameter
     * @throws Exception
     */
    public void export(Parameter inputData) throws Exception {
        if (outDir == null) {
            outDir = "";
        }
        //---生成输入文件对应的路径----
        String targetFolderName;
        if(useOut_Dir==true){
         targetFolderName=PathGetter.getStandardDir()+out_Dir;
         useOut_Dir=false;
        }else{
            targetFolderName= PathGetter.getStandardDir() + outDir;
        }
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
    }
}
