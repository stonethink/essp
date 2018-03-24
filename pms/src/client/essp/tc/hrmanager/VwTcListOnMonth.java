package client.essp.tc.hrmanager;

import java.util.List;
import client.framework.model.VMColumnConfig;
import c2s.dto.InputInfo;

public class VwTcListOnMonth extends VwTcListBase {
    static final String actionIdList = "FTCHrManageWeeklyReportSumOnMonthListAction";

    public VwTcListOnMonth() {
        super();

        //set all button unVisible
        btnSave.setVisible(false);
        btnFreeze.setVisible(false);
        //set enabled mode
        List configs = getModel().getColumnConfigs();
        for (int i = 0; i < configs.size(); i++) {
            VMColumnConfig config = (VMColumnConfig) configs.get(i);
            config.setEditable(VMColumnConfig.UNEDITABLE);
        }
    }

    protected Object[][] getConfigs() {
        //OnWeek与OnMonth的区别是OnMonth不要locked列
        Object[][] configsOnWeek = super.getConfigs();
        Object[][] configsOnMonth = new Object[configsOnWeek.length-1][];
        for (int i = 0; i < configsOnMonth.length; i++) {
            configsOnMonth[i] = configsOnWeek[i+1];
        }

        return (Object[][])configsOnMonth;
    }


    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = super.createInputInfoForList();

        inputInfo.setActionId(this.actionIdList);
        return inputInfo;
    }

}


