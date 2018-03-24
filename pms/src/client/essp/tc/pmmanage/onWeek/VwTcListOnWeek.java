package client.essp.tc.pmmanage.onWeek;

import java.util.Date;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.weeklyreport.VMTableWeeklyReport;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.common.Global;
import client.essp.common.loginId.VWJLoginId;

public class VwTcListOnWeek extends VwTcListOnWeekBase {
    static final String actionIdList = "FTCPmManageWeeklyReportSumListAction";
    static final String actionIdGetForUser = "FTCPmManageWeeklyReportSumGetForUserAction";

    Long acntRid = null;

    public VwTcListOnWeek() {
        VWJText text = new VWJText();
        VWJReal real = new VWJReal() {
            public void setUICValue(Object value) {
                if (DtoWeeklyReport.BIG_DECIMAL_0.equals(value)) {
                    super.setUICValue(null);
                } else {
                    super.setUICValue(value);
                }
            }
        };

        real.setMaxInputIntegerDigit(4);
        real.setMaxInputDecimalDigit(1);

        VWJComboBox cmbStatus = new VWJComboBox();
        VMComboBox vmStatus = VMComboBox.
                              toVMComboBox(new String[] {DtoWeeklyReport.STATUS_LOCK
                                           , DtoWeeklyReport.STATUS_CONFIRMED
                                           , DtoWeeklyReport.STATUS_REJECTED});
        cmbStatus.setModel(vmStatus);
//        cmbStatus.addPopupMenuListener(new PopupMenuListener() {
//            public void popupMenuWillBecomeVisible(PopupMenuEvent e){
//                System.out.println("popupMenuWillBecomeVisible"+System.currentTimeMillis());
//            }
//            public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
//                System.out.println("popupMenuWillBecomeInvisible"+System.currentTimeMillis());
//            }
//
//            /**
//             * This method is called when the popup menu is canceled
//             */
//            public void popupMenuCanceled(PopupMenuEvent e){
//                System.out.println("popupMenuCanceled"+System.currentTimeMillis());
//            }
//
//            public void actionPerformed(ActionEvent e) {
//                actionPerformedConfirm(e);
//            }
//        });

        Object[][] configs = new Object[][] {
                             {"Worker", "userId", VMColumnConfig.UNEDITABLE, new VWJLoginId()}, {"Job Code", "jobCode", VMColumnConfig.UNEDITABLE, text},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.UNEDITABLE, real},
                             {VMTableTcOnWeek.SUMMARY_TITLE, "", VMColumnConfig.UNEDITABLE, real}, {VMTableTcOnWeek.OVERTIME_TITLE, "",
                             VMColumnConfig.UNEDITABLE, real}, {VMTableTcOnWeek.ALLOCATE_TITLE, "", VMColumnConfig.UNEDITABLE, real}, {"Confirm",
                             "confirmStatus", VMColumnConfig.EDITABLE, cmbStatus}, {"Comments", "comments", VMColumnConfig.EDITABLE, text},
        };
        this.model = new VMTableTcOnWeek();
        model.setColumnConfigs(configs);
        this.table = new TableTcOnWeek(model);

        super.initUI();
    }

    protected boolean setParameterExtend(Parameter param) {
        Long newAcntRid = (Long) param.get(DtoTcKey.ACNT_RID);
        if (newAcntRid == null) {
            this.acntRid = newAcntRid;
            this.isParameterValid = false;
            return true;
        } else {
            if (newAcntRid.equals(this.acntRid)) {
                return false;
            } else {
                this.acntRid = newAcntRid;
                return true;
            }
        }
    }

    //刷新行row的数据，该数据重新从数据库中取得
    public void refreshRow(int row) {
        if (this.isParameterValid == true) {
            DtoWeeklyReportSumByPm dto = (DtoWeeklyReportSumByPm) getModel().getRow(row);
            if (dto != null) {
                InputInfo inputInfo = createInputInfoForUser(dto.getUserId());

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    DtoWeeklyReportSumByPm newDto = (DtoWeeklyReportSumByPm) returnInfo.getReturnObj(DtoTcKey.DTO);

                    ((VMTableTcOnWeek) getModel()).updateRow(row, newDto);
                }
            }
        }
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = super.createInputInfoForList();

        inputInfo.setActionId(actionIdList);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, this.acntRid);

        return inputInfo;
    }

    protected InputInfo createInputInfoForUser(String userId) {
        InputInfo inputInfo = super.createInputInfoForUser(userId);

        inputInfo.setActionId(this.actionIdGetForUser);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, this.acntRid);

        return inputInfo;
    }

    protected Long getAcntRidForConfirm() {
        return this.acntRid;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwTcListOnWeek();

        w1.addTab("Timecard", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, Global.todayDate);
        param.put(DtoTcKey.END_PERIOD, Global.todayDate);
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }
}
