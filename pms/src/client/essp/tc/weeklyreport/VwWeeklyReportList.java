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
import client.framework.view.vwcomp.NodeViewManager;
import java.awt.Color;
import client.framework.common.Global;

public class VwWeeklyReportList extends VwWeeklyReportListBase implements IVWPopupEditorEvent {

    public VwWeeklyReportList() {
        model = new VMTableWeeklyReport(getConfigs());
        table = new TableWeeklyReport(model);

        initUI();
    }

    private static Object[][] getConfigs() {
        VWJText text = new VWJText();
        VWJDisp disp = new VWJDisp();
        VWJReal real = new VWJReal() {
            public void setUICValue(Object value) {
                if (DtoWeeklyReport.BIG_DECIMAL_0.equals(value)) {
                    super.setUICValue(null);
                } else {
                    super.setUICValue(value);
                }
            }
        };
        real.setMaxInputIntegerDigit(2);
        real.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] { {"Account", "acntName", VMColumnConfig.UNEDITABLE, disp}, {"Activity", "activityDisp", VMColumnConfig.UNEDITABLE,
                             disp}, {"Job Description", "jobDescription", VMColumnConfig.EDITABLE, text},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.SUMMARY, "sumHour", VMColumnConfig.UNEDITABLE, real},
                             {"Comments", "comments", VMColumnConfig.UNEDITABLE, text},
        };

        return configs;
    }

    protected void initUI() {
        super.initUI();
        setColumnnWidth("Account", 70);
        setColumnnWidth("Activity", 70);
        setColumnnWidth("Job Description", 100);
        setColumnnWidth("Comments", 70);
    }


    protected void setEnableModel() {
        if (isParameterValid == false) {
            this.btnAdd.setEnabled(false);
            this.btnCopy.setEnabled(false);
            this.btnInit.setEnabled(false);
            this.btnSave.setEnabled(false);
            this.btnLock.setEnabled(false);
            this.btnDel.setEnabled(false);
            this.btnUpdate.setEnabled(false);
            setDataEditable(false);
            return;
        }

        if (isLocked == false) {
            //没锁的话，可以修改周报
            this.btnAdd.setEnabled(true);
            this.btnCopy.setEnabled(true);
            this.btnInit.setEnabled(true);
            this.btnSave.setEnabled(true);
            this.btnLock.setEnabled(true);

            DtoWeeklyReport selectDto = (DtoWeeklyReport) getSelectedData();
            if (selectDto == null) {
                this.btnDel.setEnabled(false);
                this.btnUpdate.setEnabled(false);
                setDataEditable(false);
            } else {
                if (selectDto.isConfirmed() || selectDto.isLocked()) {
                    this.btnDel.setEnabled(false);
                    this.btnUpdate.setEnabled(false);
                    setDataEditable(false);
                } else {
                    this.btnDel.setEnabled(true);
                    this.btnUpdate.setEnabled(true);
                    setDataEditable(true);
                }
            }

        }else{
//            DtoWeeklyReport selectDto = (DtoWeeklyReport) getSelectedData();
//            if (hasRejected) {
//                this.btnAdd.setEnabled(true);
//                this.btnCopy.setEnabled(false);
//                this.btnInit.setEnabled(false);
//                this.btnSave.setEnabled(true);
//                this.btnLock.setEnabled(false);
//
//                if (selectDto != null &&
//                    (selectDto.isRejected() == true || selectDto.isNoneStatus() || selectDto.isUnLocked() == true )) {
//                    this.btnDel.setEnabled(true);
//                    this.btnUpdate.setEnabled(true);
//                    setDataEditable(true);
//                } else {
//                    this.btnDel.setEnabled(false);
//                    this.btnUpdate.setEnabled(false);
//                    setDataEditable(false);
//                }
//            } else {
//                this.btnAdd.setEnabled(false);
//                this.btnCopy.setEnabled(false);
//                this.btnInit.setEnabled(false);
//                this.btnSave.setEnabled(false);
//                this.btnLock.setEnabled(false);
//                this.btnDel.setEnabled(false);
//                this.btnUpdate.setEnabled(false);
//                setDataEditable(false);
//            }

            this.btnAdd.setEnabled(false);
            this.btnCopy.setEnabled(false);
            this.btnInit.setEnabled(false);
            this.btnSave.setEnabled(false);
            this.btnLock.setEnabled(false);
            this.btnDel.setEnabled(false);
            this.btnUpdate.setEnabled(false);
            setDataEditable(false);
        }


    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWeeklyReportList();

        w1.addTab("Weekly Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, Global.todayDate);
        param.put(DtoTcKey.END_PERIOD, Global.todayDate);
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
