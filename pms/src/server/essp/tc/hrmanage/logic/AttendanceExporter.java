package server.essp.tc.hrmanage.logic;

import com.wits.excel.ExcelExporter;
import java.util.Date;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;
import com.wits.excel.SheetExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import java.util.List;
import java.util.Calendar;

public class AttendanceExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_Attendance.xls";//模板文件名
    public static final String OUT_FILE = "AttendanceReport.xls";//导出文件名
    public AttendanceExporter(){
        super(TEMPLATE_FILE,OUT_FILE);
    }
    public AttendanceExporter(String outFile){
        super(TEMPLATE_FILE,outFile);
    }
    //Sheet名称
    public static final String SHEEET_ATTENDANCE = "Attendance";

    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        Long accountId = (Long) inputData.get(DtoTcKey.ACNT_RID);
        Date fromTime = (Date) inputData.get(DtoTcKey.BEGIN_PERIOD);
        Date toTime = (Date) inputData.get(DtoTcKey.END_PERIOD);
        //写入差勤数据
        LgTcExcel lg=new LgTcExcel();
        List resultList=lg.partDetailList(accountId,fromTime,toTime);
        Parameter attParam = new Parameter();
        Object periodCellData, tblData;
        if(resultList.size()>1){
             periodCellData = resultList.get(0);
             tblData = resultList.get(1);
        }else{
           periodCellData=null;
           tblData=null;
        }
        attParam.put("periodCellData", periodCellData);
        attParam.put("tblData", tblData);
        HSSFSheet attendSheet = targetWorkbook.getSheet(SHEEET_ATTENDANCE);
        String attendSheetConfigFile = getSheetCfgFileName(SHEEET_ATTENDANCE);
        SheetExporter attendSheetEx = new SheetExporter(targetWorkbook,attendSheet, attendSheetConfigFile);
        attendSheetEx.export(attParam);

    }
    public static void main(String[] args) {
        Parameter inputData = new Parameter();
        Calendar begin = Calendar.getInstance();
        begin.set(2006,1,18,0,0,0);
        begin.set(Calendar.MILLISECOND,0);
        Calendar end = Calendar.getInstance();
        end.set(2006,1,24,0,0,0);
        end.set(Calendar.MILLISECOND,0);

        inputData.put(DtoTcKey.ACNT_RID,new Long(883));
        inputData.put(DtoTcKey.BEGIN_PERIOD,begin.getTime());
        inputData.put(DtoTcKey.END_PERIOD,end.getTime());
        AttendanceExporter exp = new AttendanceExporter();
        try {
            exp.export(inputData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
