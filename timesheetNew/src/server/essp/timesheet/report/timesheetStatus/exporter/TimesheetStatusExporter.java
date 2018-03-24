/*
 * Created on 2007-11-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.timesheetStatus.exporter;

import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.report.DtoTsStatusQuery;
import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;
/**
 * <p>Title:TimesheetStatusExporter</p>
 *
 * <p>Description:���r��B��팧�� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class TimesheetStatusExporter extends ExcelExporter{

        public static final String TEMPLATE_FILE =
                "Template_Timesheet_Status.xls"; //ģ���ļ���
        public static final String OUT_FILE_PREFIX =
                "WITS-WH-TimesheetStatus";
        public static final String OUT_FILE_POSTFIX = ".xls";
        public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //�����ļ���
    
        //Sheet����
        public static final String SHEEET_FILLED_RATE = "FillRate";
        public static final String SHEEET_SUBMITTED = "Submitted";
        public static final String SHEEET_APPROVED = "Approved";
        public static final String SHEEET_ACTIVE = "Active";
        public static final String SHEEET_REJECTED = "Rejected";
        public static final String SHEEET_UNFILLED = "Unfilled";
        public static final String SHEEET_PM_APPROVED = "PMApproved";
        public static final String SHEEET_RM_APPROVED = "RMApproved";
        public static final String SHEEET_STATUS_HUMAN_STAT = "StatusHumanStat";
    
        public TimesheetStatusExporter() {
            super(TEMPLATE_FILE, OUT_FILE);
        }
    
        public TimesheetStatusExporter(String outFile) {
            super(TEMPLATE_FILE, outFile);
        }
    
        /**
         * ����
         */
        public void doExport(Parameter inputData) throws Exception {
            
            Map map = (Map) inputData.get(DtoTsStatusQuery.DTO_RESULT_LIST);
            //��SITE�õ���д��
            List fillRateList = (List)map.get(DtoTsStatusQuery.DTO_FILLED_RATE);
            String dateScope = (String)map.get(DtoTsStatusQuery.DTO_DATE_SCOPE);
            if(fillRateList != null){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", fillRateList);
                reportParam.put("dateScope",dateScope);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_FILLED_RATE);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_FILLED_RATE);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            
            //δ����r��
            List unfilled = (List)map.get(DtoTsStatusQuery.DTO_UNFILLED);
            if(unfilled != null && unfilled.size()>0){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", unfilled);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_UNFILLED);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_UNFILLED);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            //���ύ
            List submittedList = (List)map.get(DtoTsStatusQuery.DTO_SUBMITTED);
            if(submittedList != null && submittedList.size()>0){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", submittedList);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_SUBMITTED);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_SUBMITTED);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            //�ь���
            List approvedList = (List)map.get(DtoTsStatusQuery.DTO_APPROVED);
            if(approvedList != null && approvedList.size()>0){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", approvedList);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_APPROVED);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_APPROVED);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            //δ�ύ
            List activeList = (List)map.get(DtoTsStatusQuery.DTO_ACTIVE);
            if(activeList != null && activeList.size()>0){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", activeList);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_ACTIVE);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_ACTIVE);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            //���ܽ^
            List rejectList = (List)map.get(DtoTsStatusQuery.DTO_REJECTED);
            if(rejectList != null && rejectList.size()>0){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", rejectList);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_REJECTED);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_REJECTED);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            //PM�ь���
            List pmAppList = (List)map.get(DtoTsStatusQuery.DTO_PM_APPROVED);
            if(pmAppList != null && pmAppList.size()>0){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", pmAppList);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_PM_APPROVED);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_PM_APPROVED);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            //RM�ь���
            List rmAppList = (List)map.get(DtoTsStatusQuery.DTO_RM_APPROVED);
            if(rmAppList != null && rmAppList.size()>0){
                Parameter reportParam = new Parameter();
                reportParam.put("beanList", rmAppList);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_RM_APPROVED);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_RM_APPROVED);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
            //��B�ˆT�yӋ
            List fillList = (List)map.get(DtoTsStatusQuery.DTO_IS_FILL);//������r���ˆT�yӋ
            List unfillList = (List)map.get(DtoTsStatusQuery.DTO_UNFILL);//��������r���ˆT�yӋ
            if((fillList != null && fillList.size() > 0) &&
                    (unfillList != null && unfillList.size()>0)){
                String createDate = (String)map.get(DtoTsStatusQuery.DTO_CURRENT_DATE);
                Parameter reportParam = new Parameter();
                reportParam.put("fillList", fillList);
                reportParam.put("unfillList",unfillList);
                reportParam.put("createDate",createDate);
                reportParam.put("dateScope",dateScope);
                HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_STATUS_HUMAN_STAT);        
                String reportSheetConfigFile = getSheetCfgFileName(SHEEET_STATUS_HUMAN_STAT);
                SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
                        reportSheet, reportSheetConfigFile);
                reportSheetEx.export(reportParam);
            }
        }
}
