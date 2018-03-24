package client.essp.tc.weeklyreport;

import java.util.Date;

import c2s.dto.InputInfo;
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
import java.awt.event.ActionEvent;
import client.framework.view.common.comMSG;
import client.framework.common.Constant;
import c2s.dto.ReturnInfo;
import client.essp.tc.common.TableListener;
import client.framework.view.vwcomp.VWJPopupEditor;

public class VwWeeklyReportListOnWeekByHr extends VwWeeklyReportListBase implements IVWPopupEditorEvent {
    static final String actionIdList = "FTCHrManageWeeklyReportListAction";
    static final String actionIdUpdate = "FTCHrManageWeeklyReportUpdateAction";
    static final String actionIdLockOff = "FTCHrManageWeeklyReportLockOffAction";

    VMTableWeeklyReport modelWeeklyReport;
    VwWeeklyReportOnWeekByHrDetail weeklyReportDetail;

    public VwWeeklyReportListOnWeekByHr() {
        modelWeeklyReport = new VMTableWeeklyReportOnWeekByHr(getConfigs());
        model = modelWeeklyReport;

        table = new TableWeeklyReport(model );
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
        Object[][] configs = new Object[][] {
                             {"Account", "acntName", VMColumnConfig.UNEDITABLE, disp}, {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour",
                             VMColumnConfig.EDITABLE, real}, {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.EDITABLE, real}, {VMTableWeeklyReport.SUMMARY,
                             "sumHour", VMColumnConfig.UNEDITABLE, real},
                             {"Comments", "comments", VMColumnConfig.UNEDITABLE, text},
                             {"Status", "confirmStatus", VMColumnConfig.UNEDITABLE, text},
        };

        return configs;
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = super.createInputInfoForList();
        inputInfo.setActionId(actionIdList);
        return inputInfo;
    }

    protected InputInfo createInputInfoForUpdate() {
        InputInfo inputInfo = super.createInputInfoForUpdate();

        inputInfo.setActionId(this.actionIdUpdate);
        return inputInfo;
    }

    /*
        protected void setButtonVisible() {
            this.btnCopy.setVisible(false);
            this.btnInit.setVisible(false);
            this.btnLock.setVisible(false);
            this.btnUpdate.setVisible(false);
        }

        protected void setEnableModel() {
            if (isParameterValid == false) {
                this.btnAdd.setEnabled(false);
                this.btnSave.setEnabled(false);
                this.btnDel.setEnabled(false);

                setDataEditable(false);
            } else {
                this.btnAdd.setEnabled(true);
                this.btnSave.setEnabled(true);

                DtoWeeklyReport selectDto = (DtoWeeklyReport) getSelectedData();
                if (selectDto != null) {
                    this.btnDel.setEnabled(true);
                    setDataEditable(true);
                } else {
                    this.btnDel.setEnabled(false);
                    setDataEditable(false);
                }
            }
        }
     */


    protected void setButtonVisible() {
        this.btnAdd.setVisible(false);
        this.btnSave.setVisible(false);
        this.btnDel.setVisible(false);
        this.btnUpdate.setVisible(true);
        this.btnCopy.setVisible(false);
        this.btnInit.setVisible(false);
        this.btnLock.setVisible(false);
        this.btnUnLock.setVisible(true);
    }

    protected void setEnableModel() {
        if (isParameterValid == false) {
            this.btnUnLock.setEnabled(false);
            this.btnUpdate.setEnabled(false);
            return;
        }

        btnUpdate.setEnabled(true);
        if (this.isLocked) {
            this.btnUnLock.setEnabled(true);
        }else{
            this.btnUnLock.setEnabled(false);
        }
    }

    protected void actionPerformedUnLock(ActionEvent e) {
        int opt = comMSG.dispConfirmDialog("Do you want to unlock the [" + getUserId() + "]'s weekly report?");
        if (opt == Constant.OK) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdLockOff);
            inputInfo.setInputObj(DtoTcKey.USER_ID, getUserId());
            inputInfo.setInputObj(DtoTcKey.BEGIN_PERIOD, getBeginPeriod());
            inputInfo.setInputObj(DtoTcKey.END_PERIOD, getEndPeriod());

            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                super.resetUI();

                //comMSG.dispMessageDialog("Unlock Ok!");
            }
        }
    }

    protected void actionPerformedUpdate(ActionEvent e) {
        if( getUserId() == null || getBeginPeriod() == null || getEndPeriod() == null ){
            return;
        }

        if( weeklyReportDetail == null ){
            weeklyReportDetail = new VwWeeklyReportOnWeekByHrDetail();
        }

        Parameter p = new Parameter();
        p.put(DtoTcKey.USER_ID, getUserId());
        p.put(DtoTcKey.BEGIN_PERIOD, getBeginPeriod());
        p.put(DtoTcKey.END_PERIOD, getEndPeriod());
        this.weeklyReportDetail.setParameter(p);

        weeklyReportDetail.refreshWorkArea();
        VWWorkArea w = new VWWorkArea();
        w.addTab("Weekly Report", weeklyReportDetail);
        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(), "Weekly Report"
                , w, this);
        popupEditor.show();

        reloadData();
    }

    public boolean onClickOK(ActionEvent e) {
        weeklyReportDetail.vwWeeklyReportList.saveWorkAreaDirectly();

        return weeklyReportDetail.vwWeeklyReportList.isSaveOk();
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    private void reloadData(){
        if( weeklyReportDetail.isUpdated() ){
            this.resetUI();
        }
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWeeklyReportListOnWeekByHr();

        w1.addTab("Weekly Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 11, 3));
        param.put(DtoTcKey.END_PERIOD, new Date(105, 11, 9));
        param.put(DtoTcKey.USER_ID, "stone.shi");
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
