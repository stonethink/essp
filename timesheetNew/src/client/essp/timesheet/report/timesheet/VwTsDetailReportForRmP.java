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
public class VwTsDetailReportForRmP extends VwTsDetailReportBase{
    protected VwTsDetailReportTopBase getTop() {
        return new VwTsDetailReportTopForRmP();
    }
    protected void jbInit() {
        this.getTopArea().addTab("rsid.timesheet.tsReportDeptQuery", top);
        this.getDownArea().addTab("rsid.timesheet.tsReportDept", down);
    }
}
