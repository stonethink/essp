package server.essp.tc.attreport;

import java.util.List;

public class VbAttendanceReport {
    private List accountList;
    private String reportBegin;
    private String reportEnd;
    public List getAccountList() {
        return accountList;
    }

    public String getReportBegin() {
        return reportBegin;
    }

    public String getReportEnd() {

        return reportEnd;
    }

    public void setReportEnd(String reportEnd) {

        this.reportEnd = reportEnd;
    }

    public void setReportBegin(String reportBegin) {
        this.reportBegin = reportBegin;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }

}
