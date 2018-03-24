package server.essp.pms.qa.monitoring.logic;

import com.wits.excel.ExcelExporter;
import com.wits.util.Parameter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import com.wits.excel.SheetExporter;
import java.util.Date;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.dto.*;
import client.essp.pms.qa.monitoring.VwMonitoringList;
import com.wits.util.comDate;
import java.util.List;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.pms.qa.DtoQaCheckAction;

/**
 * TimeCard导出Excel报表类.报表分为Attendance(差勤表)和TimeCard(人力表)两个Sheet.
 * 导出有两种方式:同时导出两个Sheet的数据;只导出Attendance的数据
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Rong.Xiao
 * @version 1.0
 */
public class QaMonitoringExporter extends ExcelExporter {
    public static final String TEMPLATE_FILE = "Template_QA_Report.xls"; //模板文件名
    public static final String OUT_FILE_PREFIX ="WITS-WH-QA-R090 QA Log";
    public static final String OUT_FILE_POSTFIX =".xls";
    public static final String OUT_FILE = OUT_FILE_PREFIX + OUT_FILE_POSTFIX; //导出文件名

    private static final String TILE_PREFIX = "QA Log of ";

    //Sheet名称
    public static final String SHEEET_REPORT = "Report";
    public static final String SHEEET_NCR_LIST = "NCR_List";


    public QaMonitoringExporter() {
        super(TEMPLATE_FILE, OUT_FILE);
    }

    public QaMonitoringExporter(String outFile) {
        super(TEMPLATE_FILE, outFile);
    }

    public void doExport(Parameter inputData) throws Exception {
        //获得数据所需参数
        Long accountId = (Long) inputData.get(DtoTcKey.ACNT_RID);

        //准备Report Sheet所需数据并导出
        String filterType = (String) inputData.get(VwMonitoringList.
            KEY_FILTER_TYPE);
        String filterStatus = (String) inputData.get(VwMonitoringList.
            KEY_FILTER_STATUS);
        String planPerformer = (String) inputData.get(VwMonitoringList.
            KEY_PLAN_PERFORMER);
        String planDateStr = (String) inputData.get(VwMonitoringList.KEY_PLAN_DATE);
        String planFinishDateStr = (String) inputData.get(VwMonitoringList.
            KEY_PLAN_FINISH_DATE);
        String actualPerformer = (String) inputData.get(VwMonitoringList.
            KEY_ACTUAL_PERFORMER);
        String actualDateStr = (String) inputData.get(VwMonitoringList.KEY_ACTUAL_DATE);
        String actualFinishDateStr = (String) inputData.get(VwMonitoringList.
            KEY_ACTUAL_FINISH_DATE);
        Date planDate = comDate.toDate(planDateStr);
        Date planFinishDate = comDate.toDate(planFinishDateStr);
        Date actualDate = comDate.toDate(actualDateStr);
        Date actualFinishDate = comDate.toDate(actualFinishDateStr);
        String accountName = (String) inputData.get("accountName");
        String title = TILE_PREFIX + accountName;
        String filter = (String) inputData.get("filter");
        if(filter == null || "".equals(filter)) {
            filter = "All";
        }

        LgMonitoring lgMonitoring = new LgMonitoring(filterType, filterStatus,
            planPerformer, planDate, planFinishDate, actualPerformer,
            actualDate, actualFinishDate);
        //获取树
        ITreeNode root = lgMonitoring.getCheckTree(accountId, false);
        List list = root.listAllChildrenByPostOrder();
        for(int i=0; i < list.size();i++) {
            ITreeNode node = (ITreeNode)list.get(i);
            DtoQaMonitoring data = (DtoQaMonitoring)node.getDataBean();
            String loginId = data.getPlanPerformer();
            if(DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE.equals(data.getType())
                && loginId != null) {
                String name = itf.hr.HrFactory.create().getChineseName(loginId);
                data.setPlanPerformer(name + "(" + loginId + ")");
            }
        }
        Parameter reportParam = new Parameter();
        reportParam.put("title", title);
        reportParam.put("filter", filter);
        reportParam.put("dataTree", root);
        HSSFSheet reportSheet = targetWorkbook.getSheet(SHEEET_REPORT);
        String reportSheetConfigFile = getSheetCfgFileName(SHEEET_REPORT);
        SheetExporter reportSheetEx = new SheetExporter(targetWorkbook,
            reportSheet, reportSheetConfigFile);
        reportSheetEx.export(reportParam);

        //sheet ncr list
        LgNcrIssue lgNcr = new LgNcrIssue();
        List ncrList = lgNcr.list(accountId);
        Parameter ncrListParam = new Parameter();
        ncrListParam.put("ncrList", ncrList);
        HSSFSheet ncrListSheet = targetWorkbook.getSheet(SHEEET_NCR_LIST);
        String ncrListSheetConfigFile = getSheetCfgFileName(SHEEET_NCR_LIST);
        SheetExporter ncrListSheetEx = new SheetExporter(targetWorkbook,
            ncrListSheet, ncrListSheetConfigFile);
        ncrListSheetEx.export(ncrListParam);

    }

    public static void main(String[] args) {
        com.wits.util.ThreadVariant thread = com.wits.util.ThreadVariant.
                                             getInstance();
        c2s.essp.common.user.DtoUser user = new c2s.essp.common.user.DtoUser();
        user.setUserID("U001");
        thread.set(c2s.essp.common.user.DtoUser.SESSION_USER, user);
        QaMonitoringExporter exporter = new QaMonitoringExporter();
        Parameter param = new Parameter();
        param.put(DtoTcKey.ACNT_RID, new Long(50025));
        try {
            exporter.export(param);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
