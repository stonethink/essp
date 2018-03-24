package client.essp.tc.hrmanager;

import client.essp.tc.weeklyreport.VwWeeklyReportListOnMonthByHr;

public class VwTcOnMonth extends VwTcBase{
    public VwTcOnMonth() {
        super( new VwTcListOnMonth(),
               new VwWeeklyReportListOnMonthByHr() );
    }
}
