package server.essp.timesheet.report.timesheet.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import c2s.essp.timesheet.report.DtoQueryCondition;
import c2s.essp.timesheet.report.DtoTsGatherReport;
import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;
import com.wits.util.PathGetter;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class TsGatherReportExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_TsGatherReport.xls";//ģ���ļ���
    public static final String OUT_FILE = "TimeSheet Gahter Report.xls";//�����ļ���

    //Sheet����
    public static final String SHEEET_RESULT = "TimeSheetGather";

    public TsGatherReportExporter() {
         super(TEMPLATE_FILE, OUT_FILE);
    }
    public void doExport(Parameter inputData) throws Exception {
        List list = (List) inputData.get(DtoTsGatherReport.DTO_QUERY_RESULT);
        String begin = (String) inputData.get(DtoQueryCondition.DTO_BEGIN);
        String end = (String) inputData.get(DtoQueryCondition.DTO_END);
        Parameter tsDetailParam = new Parameter();
        Object period = begin+"~"+end;
        tsDetailParam.put("period", period);
        tsDetailParam.put("tblData", list);
        HSSFSheet tsDetailSheet = targetWorkbook.getSheet(SHEEET_RESULT);
        String tcSheetConfigFile = getSheetCfgFileName(SHEEET_RESULT);
        SheetExporter se = new SheetExporter(targetWorkbook, tsDetailSheet, tcSheetConfigFile);
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
