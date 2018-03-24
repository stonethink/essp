package server.essp.pms.report.viewbean;

import java.util.ArrayList;
import java.util.List;

public class VbProjectWeeklyReportForm {
    private List accounts = new ArrayList();
    private String reportDate;
    private String reportBegin;
    private String reportEnd;
    private String reportedBy;
    /**
     *
     */
    private String submitFlag = "";
    public List getAccounts() {

        return accounts;
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

    public String getReportedBy() {
        return reportedBy;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setAccounts(List accounts) {

        this.accounts = accounts;
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

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

}
