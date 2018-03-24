package server.essp.attendance.overtime.form;

import org.apache.struts.action.*;

public class AfOverTimeApp extends ActionForm {
    private String loginId;
    private String acntRid;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private String totalHours;
    private String isEachDay;
    private String cause;
    private String detailInfo;
    public String getAcntRid() {
        return acntRid;
    }

    public String getCause() {
        return cause;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getIsEachDay() {
        return isEachDay;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setAcntRid(String acntRid) {
        this.acntRid = acntRid;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setIsEachDay(String isEachDay) {
        this.isEachDay = isEachDay;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

}
