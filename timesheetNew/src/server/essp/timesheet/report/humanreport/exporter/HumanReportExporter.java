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
	
	public static final String TEMPLATE_FILE = "Template_WeeklyReport.xls";//ģ���ļ���
    public static final String OUT_FILE = "TimeCard.xls";//�����ļ���

    //Sheet����
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
     * ����д��Ŀ���ļ�
     * @param inputData Parameter
     * @throws Exception
     */
    public void export(Parameter inputData) throws Exception {
        if (outDir == null) {
            outDir = "";
        }
        //---���������ļ���Ӧ��·��----
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

        //���targetWorkbook��Ŀ���ļ�
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
