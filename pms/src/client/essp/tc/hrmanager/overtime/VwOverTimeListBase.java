package client.essp.tc.hrmanager.overtime;

import java.awt.Dimension;

import c2s.essp.tc.overtime.DtoOverTime;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJReal;
import client.essp.common.loginId.VWJLoginId;

public class VwOverTimeListBase extends VWTableWorkArea {
    private VWJReal inputHours = new VWJReal();
    public VwOverTimeListBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 260));
        //设置标题栏位
        inputHours.setMaxInputIntegerDigit(8);
        inputHours.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][] {
                             {"Worker", "loginId",VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                             {"Account", "accountName", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"From", "actualDateFrom", VMColumnConfig.UNEDITABLE, new VWJDate()},
                             {"To", "actualDateTo", VMColumnConfig.UNEDITABLE,new VWJDate()},
                             {"Is Each Day", "actualIsEachDay",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Hours", "actualTotalHours",VMColumnConfig.UNEDITABLE, inputHours},
                             {"Cause", "cause",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Status", "status",VMColumnConfig.UNEDITABLE, new VWJText()
                     },
        };

        try {
            super.jbInit(configs, DtoOverTime.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
