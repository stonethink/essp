package client.essp.pwm.wp;

import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.framework.model.VMColumnConfig;
import client.essp.common.loginId.VWJLoginId;

public class VMTblCheckpointLog extends VMTable2 {


    public VMTblCheckpointLog(Class dtoClass) {
        //super(tblConfig);
        VWJText txtNo = new VWJText();
        VWJDate dteDate = new VWJDate();
        VWJText txtFrom = new VWJLoginId();
        VWJText txtReason = new VWJText();
        VWJDate dteBaseLineCheck = new VWJDate();
        VWJDate dteActCheck = new VWJDate();

//        dteDate.setDataType("YYYYMMDD");
//        dteBaseLineCheck.setDataType("YYYYMMDD");
//        dteActCheck.setDataType("YYYYMMDD");

        Object[][] tblConfig = { {"No", "No", VMColumnConfig.UNEDITABLE, txtNo}
                               , {"Date", "wpchklogsDate", VMColumnConfig.UNEDITABLE, dteDate}
                               , {"From", "wpchklogsFrom", VMColumnConfig.UNEDITABLE, txtFrom}
                               , {"Reason", "wpchklogsReason", VMColumnConfig.UNEDITABLE, txtReason}
                               , {"Baseline Check",
                               "wpchklogsBaselinechk", VMColumnConfig.UNEDITABLE, dteBaseLineCheck}
                               , {"Actual Check", "wpchklogsActchk", VMColumnConfig.UNEDITABLE, dteActCheck}
        };
        super.setDtoType(dtoClass);
        super.setColumnConfigs(tblConfig);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return String.valueOf(row + 1);
        }

        return super.getValueAt(row, col);
    }

}
