package client.essp.tc.weeklyreport;

import java.util.Date;

import c2s.dto.InputInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import java.awt.Color;
import client.framework.view.vwcomp.NodeViewManager;

public class VwWeeklyReportListOnMonthByPm extends VwWeeklyReportListBase {
    public static final String actionIdList = "FTCPmManageWeeklyReportListAction";

    Long acntRid = null;

    public VwWeeklyReportListOnMonthByPm() {
        model = new VMTableWeeklyReportOnMonth(getConfigs());
        table = new TableWeeklyReportOnMonth(model, new WeeklyReportOnWeekNodeViewManager());

        initUI();
    }

    private static Object[][] getConfigs() {
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();
        real.setMaxInputIntegerDigit(2);
        real.setMaxInputDecimalDigit(2);

        Object[][] configs = new Object[][] { {"Account", "acntName", VMColumnConfig.UNEDITABLE, text}, {"Activity", "activityDisp", VMColumnConfig.UNEDITABLE,
                             text}, {"Job Description", "jobDescription", VMColumnConfig.UNEDITABLE, text}, {"Period", "periodInfo", VMColumnConfig.UNEDITABLE,
                             text}, {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.UNEDITABLE, real}, {VMTableWeeklyReport.SUMMARY,
                             "sumHour", VMColumnConfig.UNEDITABLE, real},
                             {"Comments", "comments", VMColumnConfig.UNEDITABLE, text},
        };

        return configs;
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        acntRid = (Long) param.get(DtoTcKey.ACNT_RID);
        if (acntRid == null) {
            this.isParameterValid = false;
        }

    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = super.createInputInfoForList();
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, acntRid);
        inputInfo.setActionId(actionIdList);
        return inputInfo;
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

    public class WeeklyReportOnWeekNodeViewManager extends NodeViewManager{
        Color c = new Color(200, 200, 200);
        public DtoWeeklyReport getWDto(){
            return (DtoWeeklyReport)getDto();
        }

        public Color getOddBackground() {
            if( getWDto() != null && getWDto().isWeeklyReportSum() ){
                return c;
            }else{
                return super.getOddBackground();
            }
        }

        public Color getEvenBackground() {
            if( getWDto() != null && getWDto().isWeeklyReportSum() ){
                return c;
            }else{
                return super.getEvenBackground();
            }
        }

    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWeeklyReportListOnMonthByPm();

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
