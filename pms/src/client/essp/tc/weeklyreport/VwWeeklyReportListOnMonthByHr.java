package client.essp.tc.weeklyreport;

import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.dto.InputInfo;
import javax.swing.table.TableColumn;
import client.framework.view.vwcomp.VWJDate;

public class VwWeeklyReportListOnMonthByHr extends VwWeeklyReportListBase implements IVWPopupEditorEvent {
    static final String actionIdList = "FTCHrManageWeeklyReportListAction";

    public VwWeeklyReportListOnMonthByHr() {
        model = new VMTableWeeklyReport(getConfigs());
        table = new TableWeeklyReport(model);
        initUI();
    }

    private static Object[][] getConfigs() {
        VWJText text = new VWJText();
        VWJDisp disp = new VWJDisp();
        VWJDate date = new VWJDate();
        VWJReal real = new VWJReal() {
            public void setUICValue(Object value) {
                if (DtoWeeklyReport.BIG_DECIMAL_0.equals(value)) {
                    super.setUICValue(null);
                } else {
                    super.setUICValue(value);
                }
            }
        };
        real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] {
                             {"Account", "acntName", VMColumnConfig.UNEDITABLE, disp},
                             {"Begin Period", "beginPeriod", VMColumnConfig.UNEDITABLE, date},
                             {"End Period", "endPeriod", VMColumnConfig.UNEDITABLE, date},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.SUMMARY, "sumHour", VMColumnConfig.UNEDITABLE, real},
                             {"Comments", "comments", VMColumnConfig.UNEDITABLE, text},
                             {"Status", "confirmStatus", VMColumnConfig.UNEDITABLE, text},
        };

        return configs;
    }

    //设置表格的外观
    protected void initUI() {
        super.initUI();

        //period列
        TableColumn beginPeriodColumn = getTable().getColumn("Begin Period");
        TableColumn endPeriodColumn = getTable().getColumn("End Period");
        beginPeriodColumn.setPreferredWidth(80);
        endPeriodColumn.setPreferredWidth(80);

        //hours
        for (int i = 0; i < VMTableWeeklyReport.WEEK_TITLE.length; i++) {
            TableColumn weekColumn = getTable().getColumn(VMTableWeeklyReport.WEEK_TITLE[i]);
            weekColumn.setPreferredWidth(40);
            weekColumn.setMaxWidth(40);
            weekColumn.setMinWidth(40);
        }
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = super.createInputInfoForList();
        inputInfo.setActionId(actionIdList);
        return inputInfo;
    }

    protected void setButtonVisible() {
        this.btnCopy.setVisible(false);
        this.btnInit.setVisible(false);
        this.btnLock.setVisible(false);
        this.btnUpdate.setVisible(false);
        this.btnAdd.setVisible(false);
        this.btnSave.setVisible(false);
        this.btnDel.setVisible(false);
    }

    protected void setEnableModel() {
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWeeklyReportListOnMonthByHr();

        w1.addTab("Weekly Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105,11,3));
        param.put(DtoTcKey.END_PERIOD, new Date(105,11,9));
        param.put(DtoTcKey.USER_ID, "stone.shi");
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
