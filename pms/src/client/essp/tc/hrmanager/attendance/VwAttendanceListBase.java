package client.essp.tc.hrmanager.attendance;

import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import java.awt.Dimension;
import c2s.essp.tc.attendance.DtoAttendance;
import client.essp.common.loginId.VWJLoginId;

public class VwAttendanceListBase extends VWTableWorkArea {
    public VwAttendanceListBase() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 260));
        //设置标题栏位
        Object[][] configs = new Object[][] {
                             {"Worker", "loginId",VMColumnConfig.UNEDITABLE, new VWJLoginId()},
                             {"Type", "attendanceType", VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Date", "attendanceDate", VMColumnConfig.UNEDITABLE,new VWJDate()},
                             {"Remark", "Remark",VMColumnConfig.UNEDITABLE, new VWJText()},
        };

        try {
            super.jbInit(configs, DtoAttendance.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
