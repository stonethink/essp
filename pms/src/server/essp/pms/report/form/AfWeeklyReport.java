package server.essp.pms.report.form;

import org.apache.struts.action.ActionForm;

public class AfWeeklyReport extends ActionForm {
    private String acntRid;
    private String acntId;
    private String acntName;
    private String reportedBy;
    private String reportDate;
    private String reportBegin;
    private String reportEnd;
    public String getAcntRid() {
        return acntRid;
    }

    public String getAcntId() {
        return acntId;
    }

    public String getAcntName() {
        return acntName;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public String getReportDate() {

        return reportDate;
    }

    public String getReportBegin() {

        return reportBegin;
    }

    public String getReportEnd() {

        return reportEnd;
    }

    public void setAcntRid(String acntRid) {
        this.acntRid = acntRid;
    }

    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public void setReportDate(String reportDate) {

        this.reportDate = reportDate;
    }

    public void setReportBegin(String reportBegin) {

        this.reportBegin = reportBegin;
    }

    public void setReportEnd(String reportEnd) {

        this.reportEnd = reportEnd;
    }
}
