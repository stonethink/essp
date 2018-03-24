package client.essp.tc;

import java.util.Date;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.tc.dmView.byAcnt.VwDmViewByAcnt;
import client.essp.tc.dmView.byWorker.VwDmViewByWorker;
import client.essp.tc.pmmanage.VwPmManage;
import client.essp.tc.weeklyreport.VwWeeklyReport;
import com.wits.util.Parameter;

public class TestTc extends VWGeneralWorkArea {
    public TestTc() {
    }

    public static void main(String args[]) {
        final VWWorkArea vwWeeklyReport = new VwWeeklyReport();
        VWWorkArea w1 = new VWWorkArea() {
            public void setParameter(Parameter p) {
                vwWeeklyReport.setParameter(p);
            }

            public void refreshWorkArea() {
                vwWeeklyReport.refreshWorkArea();
            }
        };

        VwPmManage vwPmManage = new VwPmManage();
        VwDmViewByWorker vwDmView = new VwDmViewByWorker();
        VwDmViewByAcnt vwDmViewByAcnt = new VwDmViewByAcnt();

        w1.addTab("Weekly Report", vwWeeklyReport);
        w1.addTab("vwPmManage", vwPmManage);
        w1.addTab("vwDmView", vwDmView);
        w1.addTab("vwDmViewByAcnt", vwDmViewByAcnt);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105, 11, 3));
        Date d2 = new Date(105, 11, 9);
        param.put(DtoTcKey.END_PERIOD, d2);

        vwWeeklyReport.setParameter(param);
        vwWeeklyReport.refreshWorkArea();
        vwPmManage.setParameter(param);
        vwPmManage.refreshWorkArea();
        vwDmView.refreshWorkArea();
        vwDmViewByAcnt.refreshWorkArea();

        TestPanelParam.show(w1);
    }

}
