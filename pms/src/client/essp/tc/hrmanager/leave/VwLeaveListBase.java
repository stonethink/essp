package client.essp.tc.hrmanager.leave;

import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.model.VMColumnConfig;
import java.awt.Dimension;
import c2s.essp.tc.leave.DtoLeave;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.essp.common.loginId.VWJLoginId;

public class VwLeaveListBase extends VWTableWorkArea {
    private VWJReal inputHours = new VWJReal();
    public VwLeaveListBase() {
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
                             {"Dept", "orgName", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"From", "actualDateFrom", VMColumnConfig.UNEDITABLE, new VWJDate()},
                             {"To", "actualDateTo", VMColumnConfig.UNEDITABLE,new VWJDate()},
                             {"Hours", "actualTotalHours",VMColumnConfig.UNEDITABLE, inputHours},
                             {"Type", "leaveName",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Cause", "cause",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Status", "status",VMColumnConfig.UNEDITABLE, new VWJText()},
        };

        try {
            super.jbInit(configs, DtoLeave.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
