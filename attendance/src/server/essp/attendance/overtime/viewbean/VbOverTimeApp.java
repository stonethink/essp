package server.essp.attendance.overtime.viewbean;

import java.util.*;

public class VbOverTimeApp {
    private Long rid;
    private String loginId;
    private Long acntRid;
    private String accountName;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private Double totalHours;
    private Boolean isEachDay;
    private String cause;
    private String status;
    private Long wkId;
    private List accountList;
    public String getLoginId() {
        return loginId;
    }

    public Long getAcntRid() {
        return acntRid;
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

    public Double getTotalHours() {
        return totalHours;
    }

    public List getAccountList() {
        return accountList;
    }

    public String getCause() {
        return cause;
    }

    public Boolean getIsEachDay() {
        return isEachDay;
    }

    public String getStatus() {
        return status;
    }

    public Long getWkId() {
        return wkId;
    }

    public Long getRid() {
        return rid;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
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

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setIsEachDay(Boolean isEachDay) {
        this.isEachDay = isEachDay;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWkId(Long wkId) {
        this.wkId = wkId;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}
