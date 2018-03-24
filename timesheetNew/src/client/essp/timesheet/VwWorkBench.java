package client.essp.timesheet;

import client.essp.common.view.VWWorkArea;
import java.awt.Dimension;
import java.awt.BorderLayout;
import com.wits.util.Parameter;

public class VwWorkBench extends VWWorkArea {
    private VwLeftBench vwLeftBench = new VwLeftBench();
    private VwRightBench rightBench = new VwRightBench();
    public VwWorkBench() {
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        addUICEvent();
    }

    /**
     * jbInit
     */
    private void jbInit() {
        this.setPreferredSize(new Dimension(800, 600));
        this.add(vwLeftBench, BorderLayout.WEST);
        this.add(rightBench, BorderLayout.CENTER);
    }

    /**
     * addUICEvent
     */
    private void addUICEvent() {
            vwLeftBench.addCalendarSelectDateListener(
            rightBench.getCalendarSelectDateListener());
            vwLeftBench.addCalendarSelectDateListener(
            rightBench.getDailyReportListener());
            vwLeftBench.addActivityChangedListener(rightBench);
    }

    public void setParameter(Parameter p) {
        vwLeftBench.setParameter(p);
        rightBench.setParameter(p);
    }

    public void refreshWorkArea() {
        vwLeftBench.refreshWorkArea();
        rightBench.refreshWorkArea();
    }



}
