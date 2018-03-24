package client.essp.tc.pmmanage.onMonth;

import client.essp.tc.weeklyreport.VwWeeklyReportOnMonthByDm;

public class VwTcOnMonthByDm extends VwTcOnMonth {

    public VwTcOnMonthByDm() {
        super(new VwTcListTempOnMonthByDm(),
              new VwWeeklyReportOnMonthByDm());
    }

}
