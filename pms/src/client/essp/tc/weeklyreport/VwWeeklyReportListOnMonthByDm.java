package client.essp.tc.weeklyreport;

import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import javax.swing.table.TableColumn;

public class VwWeeklyReportListOnMonthByDm extends VwWeeklyReportListBase {
    public VwWeeklyReportListOnMonthByDm() {
        model = new VMTableWeeklyReportOnMonth(getConfigs());
        table = new TableWeeklyReportOnMonth(model);

        initUI();

        //period¡–
        TableColumn periodColumn = getTable().getColumn("Period");
        if (periodColumn != null) {
            periodColumn.setPreferredWidth(150);
            periodColumn.setMaxWidth(150);
        }
    }

    private static Object[][] getConfigs() {
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();
        real.setMaxInputIntegerDigit(2);
        real.setMaxInputDecimalDigit(2);

        Object[][] configs = new Object[][] { {"Account", "acntName", VMColumnConfig.UNEDITABLE, text}, {"Activity", "activityDisp", VMColumnConfig.UNEDITABLE,
                             text}, {"Job Description", "jobDescription", VMColumnConfig.UNEDITABLE, text},
                             {"Period", "periodInfo", VMColumnConfig.UNEDITABLE,text},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.UNEDITABLE, real}, {VMTableWeeklyReport.SUMMARY,
                             "sumHour", VMColumnConfig.UNEDITABLE, real},
                             {"Comments",
                             "comments",
                             VMColumnConfig.UNEDITABLE, text},
        };

        return configs;
    }

    protected void setButtonVisible() {
        this.btnAdd.setVisible(false);
        this.btnSave.setVisible(false);
        this.btnDel.setVisible(false);
        this.btnUpdate.setVisible(false);
        this.btnCopy.setVisible(false);
        this.btnInit.setVisible(false);
        this.btnLock.setVisible(false);
    }

    protected void setEnableModel() {
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWeeklyReportListOnMonthByDm();

        w1.addTab("Weekly Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.USER_ID, "stone.shi");
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 10, 26));
        param.put(DtoTcKey.END_PERIOD, new Date(105, 11, 25));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
