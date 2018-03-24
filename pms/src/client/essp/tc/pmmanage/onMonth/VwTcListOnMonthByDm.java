package client.essp.tc.pmmanage.onMonth;

import c2s.dto.InputInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;

public class VwTcListOnMonthByDm extends VwTcListOnMonth {
    static final String actionIdList = "FTCDmViewWeeklyReportSumOnMonthListAction";
    static final String actionIdIncSubList = "FTCDmViewWeeklyReportSumOnMonthIncSubListAction";
    static final String actionIdGetForUser = "FTCDmViewWeeklyReportSumOnMonthGetForUserAction";

    String orgId = null;
    boolean isIncSub = false;

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
        InputInfo inputInfo = super.createInputInfoForUser(userId);

        inputInfo.setActionId(this.actionIdGetForUser);

        return inputInfo;
    }

}
