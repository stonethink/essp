package client.essp.timesheet;

import c2s.essp.timesheet.activity.DtoActivityKey;
import client.essp.common.view.VWTDWorkArea;
import client.essp.timesheet.activity.VwActivityDetail;
import client.essp.timesheet.calendar.CalendarSelectDateListener;
import client.essp.timesheet.dailyreport.VwDailyReport;
import client.essp.timesheet.weeklyreport.VwWeeklyReport;

import com.wits.util.Parameter;

public class VwRightBench extends VWTDWorkArea
        implements ActivityChangedListener{

    private VwWeeklyReport vwWeeklyReport = new VwWeeklyReport();
    private VwActivityDetail vwActivitDetail = new VwActivityDetail();
    private VwDailyReport vwDailyReport = new VwDailyReport();

    public VwRightBench() {
        super(300);
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        addUICEvent();
    }

    private void jbInit() throws Exception {
        this.getTopArea().addTab("rsid.timesheet.timesheet", vwWeeklyReport);
        this.getTopArea().addTab("rsid.timesheet.dailyReport", vwDailyReport);
        this.setDownArea(vwActivitDetail);
  }

    /**
     * addUICEvent
     */
    private void addUICEvent() {
        vwWeeklyReport.addActivityChangedListener(this);
        vwDailyReport.addActivityChangedListener(this);
    }

    public void setParameter(Parameter p) {
        vwWeeklyReport.setParameter(p);
        vwActivitDetail.setParameter(p);
        vwDailyReport.setParameter(p);
    }

    public void refreshWorkArea() {
        this.getTopArea().getSelectedWorkArea().refreshWorkArea();
        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    public CalendarSelectDateListener getCalendarSelectDateListener() {
        return this.vwWeeklyReport;
    }
    public CalendarSelectDateListener getDailyReportListener() {
    	return this.vwDailyReport;
    }
    /**
     * Activity变化，传递变化后的ActivityRid
     *         监听Activity List 和 DetailList
     * @param activityRid Long
     */
    public void processActivityChanged(Long activityRid) {
        Parameter param = new Parameter();
        if(this.getTopArea().getSelectedWorkArea() instanceof VwWeeklyReport) {
        	activityRid = vwWeeklyReport.getSelectActivityId();
        } else if(this.getTopArea().getSelectedWorkArea() instanceof VwDailyReport) {
        	activityRid = vwDailyReport.getSelectActivityId();
        }
        param.put(DtoActivityKey.DTO_RID, activityRid);
        vwActivitDetail.setParameter(param);
        vwActivitDetail.refreshWorkArea();
    }

}
