package server.essp.timesheet.outwork.action;

import com.wits.excel.ExcelExporter;
import com.wits.util.Parameter;
import server.essp.timesheet.outwork.logic.LgOutWork;
import server.essp.timesheet.outwork.form.AfSearchForm;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.SheetExporter;

public class OutWorkerExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_OutWorker.xls"; //模板文件名

    public static final String CONFIGFILE = "Travels"; //配置文件

    public static final String OUT_FILE = "TravelsReport.xls"; //导出文件名

    public OutWorkerExporter() {
        super(TEMPLATE_FILE, OUT_FILE, false);
    }

    public OutWorkerExporter(String outFile) {
        super(TEMPLATE_FILE, outFile, false);
    }

    public void doExport(Parameter param) throws Exception {
        String sheetConfigFile = this.getSheetCfgFileName(CONFIGFILE);
        LgOutWork ged = new LgOutWork();
        List exportData = ged.listAll((AfSearchForm) param.get(
                "searchConditionForm"),false);

//        String sheetName = "Travels";
        HSSFSheet sheet = tplWorkbook.getSheetAt(0);
//            this.createTargetSheetByName("Travels", sheetName);
        SheetExporter se = new SheetExporter(this.targetWorkbook, sheet,
                                             sheetConfigFile);
        Parameter attParam = new Parameter();

        attParam.put("tblData", exportData);

        se.export(attParam);

    }

    public static void main(String[] args) {
//        com.wits.util.ThreadVariant thread = com.wits.util.ThreadVariant.
//                                             getInstance();
//        c2s.essp.common.user.DtoUser user = new c2s.essp.common.user.DtoUser();
//        user.setUserID("000010");
//        thread.set(c2s.essp.common.user.DtoUser.SESSION_NAME, user);
//
//        lgGetExportData gd = new lgGetExportData();
//        AfIssueSearch searchForm = new AfIssueSearch();
////       searchForm.setFillBy("stone.shi");
//        //此测试不可用，差session中的用户信息
//        IssueListExporter ie = new IssueListExporter();
//        Parameter p = new Parameter();
//        p.put("searchConditionForm", searchForm);
//        try {
//            ie.export(p);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

    }


}
