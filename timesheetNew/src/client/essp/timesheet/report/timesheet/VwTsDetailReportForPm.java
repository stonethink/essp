package client.essp.timesheet.report.timesheet;



/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VwTsDetailReportForPm extends VwTsDetailReportBase{
    protected VwTsDetailReportTopBase getTop() {
        return new VwTsDetailReportTopForPm();
    }
    protected void jbInit() {
        this.getTopArea().addTab("rsid.timesheet.tsReportProjectQuery", top);
        this.getDownArea().addTab("rsid.timesheet.tsReportProject", down);
    }

}
