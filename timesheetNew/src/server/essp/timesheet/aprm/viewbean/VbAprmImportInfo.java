/*
 * Created on 2007-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.aprm.viewbean;

public class VbAprmImportInfo {

    private String totalRows;
    private String timeSheetRows;
    private String beginDate;
    private String endDate;
    private String remark;
    public VbAprmImportInfo() {
    }

    public void setTotalRows(String totalRows) {
        this.totalRows = totalRows;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotalRows() {
        return totalRows;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTimeSheetRows() {
        return timeSheetRows;
    }

    public void setTimeSheetRows(String timeSheetRows) {
        this.timeSheetRows = timeSheetRows;
    }

    public String getRemark() {
        return remark;
    }
    }
