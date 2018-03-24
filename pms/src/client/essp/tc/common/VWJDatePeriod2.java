package client.essp.tc.common;

import java.awt.Rectangle;
import java.util.Date;

import com.wits.util.TestPanel;
import client.framework.common.Global;

public class VWJDatePeriod2 extends VWJDatePeriodBase {
    public VWJDatePeriod2() {
        super();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        radWeek.setBounds(new Rectangle(0, 0, 79, 12));
        radMonth.setBounds(new Rectangle(0, 12, 79, 12));

        dteBeginPeriod.setBounds(new Rectangle(80, 2, 70, 20));
        dteEndPeriod.setBounds(new Rectangle(202, 2, 70, 20));

        btnEarly.setBounds(new Rectangle(150, 2, 15, 20));
        btnLater.setBounds(new Rectangle(185, 2, 15, 20));

        lblAnd.setBounds(new Rectangle(165, 11, 20, 10));
    }

    public static void main(String args[]) {
        VWJDatePeriod2 v = new VWJDatePeriod2();
        v.setTheDate(Global.todayDate);

        TestPanel.show(v);
    }

}
