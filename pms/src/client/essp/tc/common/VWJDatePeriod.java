package client.essp.tc.common;

import java.awt.Rectangle;
import java.util.Date;

import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.TestPanel;
import client.framework.common.Global;

public class VWJDatePeriod extends VWJDatePeriodBase {
    protected VWJLabel lblPeriod = new VWJLabel();

    public VWJDatePeriod() {
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

        lblPeriod.setText("Period: ");
        lblPeriod.setBounds(new Rectangle(88, 2, 48, 20));

        dteBeginPeriod.setBounds(new Rectangle(134, 2, 76, 20));
        dteEndPeriod.setBounds(new Rectangle(270, 2, 76, 20));

        btnEarly.setBounds(new Rectangle(210, 2, 15, 20));
        btnLater.setBounds(new Rectangle(253, 2, 15, 20));

        lblAnd.setBounds(new Rectangle(225, 11, 28, 10));

        this.add(lblPeriod);
    }

    public static void main(String args[]) {
        VWJDatePeriod v = new VWJDatePeriod();
        v.setTheDate(Global.todayDate);

        TestPanel.show(v);
    }

}
