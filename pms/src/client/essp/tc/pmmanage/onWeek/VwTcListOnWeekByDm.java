package client.essp.tc.pmmanage.onWeek;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import client.essp.tc.weeklyreport.VMTableWeeklyReport;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import client.essp.common.loginId.VWJLoginId;

public class VwTcListOnWeekByDm extends VwTcListOnWeekBase {
    static final String actionIdList = "FTCDmViewWeeklyReportSumListAction";
    static final String actionIdIncSubList = "FTCDmViewWeeklyReportSumIncSubListAction";
    static final String actionIdGetForUser = "FTCDmViewWeeklyReportSumGetForUserAction";

    String orgId = null;
    boolean isIncSub = false;

    public VwTcListOnWeekByDm() {
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
        real.setMaxInputDecimalDigit(1);

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
                             VMColumnConfig.UNEDITABLE, real}, {VMTableTcOnWeek.ALLOCATE_TITLE, "", VMColumnConfig.UNEDITABLE, real},
        };

        this.model = new VMTableTcOnWeekByDm();
        model.setColumnConfigs(configs);
        this.table = new TableTcOnWeekByDm(model);

        super.initUI();
    }


    protected void setButtonVisible() {
        this.btnSave.setVisible(false);
    }

    protected boolean setParameterExtend(Parameter param) {
        boolean parameterChanged = false;

        //judge whether the parameter is changed
        String newOrgId = (String) param.get(DtoTcKey.ORG);
        Boolean newIncSubFlag = (Boolean) param.get(DtoTcKey.INC_SUB);
        if (newIncSubFlag == null) {
            newIncSubFlag = Boolean.FALSE;
        }
        if (newOrgId == null) {
            parameterChanged = true;
        } else {
            if (newOrgId.equals(orgId) == false ||
                newIncSubFlag.booleanValue() != isIncSub) {
                parameterChanged = true;
            }
        }

        //set parameter to local variant
        this.orgId = (String) param.get(DtoTcKey.ORG);
        if (orgId == null) {
            this.isParameterValid = false;
        }
        Boolean isIncSub = (Boolean) param.get(DtoTcKey.INC_SUB);
        if (Boolean.TRUE.equals(isIncSub)) {
            this.isIncSub = true;
        } else {
            this.isIncSub = false;
        }

        return parameterChanged;
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

                    ((VMTableTcOnWeekByDm) getModel()).updateRow(row, newDto);
                }
            }
        }
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = super.createInputInfoForList();
        inputInfo.setInputObj(DtoTcKey.ORG, this.orgId);
        if (isIncSub) {
            inputInfo.setActionId(actionIdIncSubList);
        } else {
            inputInfo.setActionId(actionIdList);
        }

        return inputInfo;
    }

    protected InputInfo createInputInfoForUser(String userId) {
        InputInfo inputInfo = createInputInfoForUser(userId);
        inputInfo.setActionId(actionIdGetForUser);

        inputInfo.setActionId(this.actionIdGetForUser);
        inputInfo.setInputObj(DtoTcKey.USER_ID, userId);

        return inputInfo;
    }
}
