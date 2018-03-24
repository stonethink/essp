package server.essp.timesheet.report.utilizationRate.detail.exporter;

import com.wits.excel.ExcelExporter;
import com.wits.excel.SheetExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.util.Parameter;
import java.util.List;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import c2s.essp.timesheet.report.DtoUtilRate;
import java.util.ArrayList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class UtilRateExporter extends ExcelExporter{

    public static final String TEMPLATE_FILE = "Template_TsUtilRate_Date.xls"; //模板文件名
    public static final String OUT_FILE_PREFIX ="WITS-WH-UillizationRate";
    public static final String OUT_FILE_POSTFIX =".xls";
    public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

   private static final String TILE_PREFIX_Date = "人员使用率(依期间)";

   //Sheet名称
   public static final String SHEEET_DATE = "Date";

   public UtilRateExporter() {
       super(TEMPLATE_FILE, OUT_FILE);
   }

   public UtilRateExporter(String outFile) {
       super(TEMPLATE_FILE, outFile);
   }


    public void doExport(Parameter inputData) throws Exception {
        List lst = (List)inputData.get(DtoUtilRateKey.DTO_DEPT_LIST);
        Parameter reportParam = new Parameter();
        reportParam.put("title", TILE_PREFIX_Date);
        reportParam.put("beanList", lst);

        HSSFSheet reportSheet= targetWorkbook.getSheet(SHEEET_DATE);
        String reportSheetConfigFile = getSheetCfgFileName(SHEEET_DATE);

        SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
            reportSheet, reportSheetConfigFile);
        reportSheetEx.export(reportParam);
    }

    public static void main(String[] args) {
       com.wits.util.ThreadVariant thread = com.wits.util.ThreadVariant.
                                            getInstance();
       c2s.essp.common.user.DtoUser user = new c2s.essp.common.user.DtoUser();
       user.setUserID("WH060714");
       thread.set(c2s.essp.common.user.DtoUser.SESSION_USER, user);
       UtilRateExporter exporter = new UtilRateExporter();
       Parameter param = new Parameter();
       DtoUtilRate dto = new DtoUtilRate();
       List list = new ArrayList();
       dto.setAcntId("W2001");
       dto.setAcntRid(Long.valueOf(1));
       dto.setActualHours(Double.valueOf(100));
       dto.setDateStr("2007-10-10");
       dto.setValidHours(Double.valueOf(20));
       dto.setLoginId("WH0607015");
       dto.setLoginName("ZWH");
       dto.setUtilRate(Double.valueOf(0.2));
       list.add(dto);
       param.put(DtoUtilRateKey.DTO_DEPT_LIST,list);
       try {
           exporter.export(param);
       } catch (Exception ex) {
           ex.printStackTrace();
       }

   }


}
