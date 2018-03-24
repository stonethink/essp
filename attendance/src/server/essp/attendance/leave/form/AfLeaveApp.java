package server.essp.attendance.leave.form;

import org.apache.struts.action.*;

public class AfLeaveApp extends ActionForm {
    private String loginId;
    private String orgId;
    private String acntRid;
    private String leaveName;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private String totalHours;
    private String usableHours;
    private String cause;
    public String getCause() {
        return cause;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getUsableHours() {
        return usableHours;
    }

    public String getAcntRid() {
        return acntRid;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public void setUsableHours(String usableHours) {
        this.usableHours = usableHours;
    }

    public void setAcntRid(String acntRid) {
        this.acntRid = acntRid;
    }

}
