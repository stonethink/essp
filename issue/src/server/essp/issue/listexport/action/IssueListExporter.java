package server.essp.issue.listexport.action;

import com.wits.excel.ExcelExporter;
import com.wits.util.comDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.SheetExporter;
import com.wits.util.Parameter;
import server.essp.issue.listexport.logic.lgGetExportData;
import server.essp.issue.issue.form.AfIssueSearch;
import c2s.dto.DtoTreeNode;

public class IssueListExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_IssueReport.xls"; //模板文件名
    //配置文件
    public static final String CONFIGFILE_FOR_TEMP1 = "Account_Name";

    public static final String CONFIGFILE_FOR_TEMP2 = "withDetail";

    public static final String OUT_FILE = "IssueListReport.xls"; //导出文件名

    public IssueListExporter() {
        super(TEMPLATE_FILE, OUT_FILE, true);
    }

    public IssueListExporter(String outFile) {
        super(TEMPLATE_FILE, outFile, true);
    }

    public void doExport(Parameter param) throws Exception {

        lgGetExportData ged = new lgGetExportData();

        //区分是导出带讨论详细信息报表还是不还详细讨论信息的报表

        String type = (String) param.get("ExportType");

        if (type.equals("withDetail")) {
            String sheetConfigFile = this.getSheetCfgFileName(CONFIGFILE_FOR_TEMP2);
            HashMap exportData = ged.getListExportDateForDetail((AfIssueSearch)
                param.get("searchConditionForm"),
                (String) param.get("serverRoot"));

            for (Iterator iter = exportData.entrySet().iterator(); iter.hasNext(); ) {
                Entry item = (Entry) iter.next();
                String sheetName = (String) item.getKey();
                DtoTreeNode dataByAccount = (DtoTreeNode) item.getValue();
                HSSFSheet sheet = this.createTargetSheetByName(CONFIGFILE_FOR_TEMP2,
                    sheetName);
                sheet.createFreezePane(3, 5);
                SheetExporter se = new SheetExporter(this.targetWorkbook, sheet,
                    sheetConfigFile);
                Parameter attParam = new Parameter();

                attParam.put("tblData", dataByAccount);

                se.export(attParam);
            }

        } else {
            String sheetConfigFile = this.getSheetCfgFileName(CONFIGFILE_FOR_TEMP1);
            HashMap exportData = ged.getListExportDate((AfIssueSearch) param.
                get("searchConditionForm"), (String) param.get("serverRoot"));

            for (Iterator iter = exportData.entrySet().iterator(); iter.hasNext(); ) {
                Entry item = (Entry) iter.next();
                String sheetName = (String) item.getKey();
                List dataByAccount = (List) item.getValue();
                HSSFSheet sheet = this.createTargetSheetByName(CONFIGFILE_FOR_TEMP1,
                    sheetName);
                sheet.createFreezePane(3, 5);
                SheetExporter se = new SheetExporter(this.targetWorkbook, sheet,
                    sheetConfigFile);
                Parameter attParam = new Parameter();

                attParam.put("tblData", dataByAccount);

                se.export(attParam);
            }
        }
    }

    public static void main(String[] args) {
        com.wits.util.ThreadVariant thread = com.wits.util.ThreadVariant.
                                             getInstance();
        c2s.essp.common.user.DtoUser user = new c2s.essp.common.user.DtoUser();
        user.setUserID("000010");
        thread.set(c2s.essp.common.user.DtoUser.SESSION_USER, user);

        lgGetExportData gd = new lgGetExportData();
        AfIssueSearch searchForm = new AfIssueSearch();
//       searchForm.setFillBy("stone.shi");
        //此测试不可用，差session中的用户信息
        IssueListExporter ie = new IssueListExporter();
        Parameter p = new Parameter();
        p.put("searchConditionForm", searchForm);
        try {
            ie.export(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        HashMap hm = gd.getListExportDate(searchForm);
//        System.out.println(hm);
    }


}
