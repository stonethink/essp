package server.essp.attendance.overtime.viewbean;



public class VbOverTime {
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

    private String decision;
    private String actualDateFrom;
    private String actualDateTo;
    private String actualTimeFrom;
    private String actualTimeTo;
    private Double actualTotalHours;
    private Boolean actualIsEachDay;
    private String comments;
    private String status;
    private Long wkId;
    private Double shiftHours;
    private Double payedHours;
    private Boolean readOnly = Boolean.FALSE;

    public String getComments() {
        return comments;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public Boolean getIsEachDay() {
        return isEachDay;
    }

    public Boolean getReadOnly() {
        return readOnly;
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

    public String getDecision() {
        return decision;
    }

    public String getAccountName() {
        return accountName;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getActualDateTo() {
        return actualDateTo;
    }

    public String getActualDateFrom() {

        return actualDateFrom;
    }

    public Boolean getActualIsEachDay() {
        return actualIsEachDay;
    }

    public String getActualTimeFrom() {
        return actualTimeFrom;
    }

    public String getActualTimeTo() {
        return actualTimeTo;
    }

    public Double getActualTotalHours() {
        return actualTotalHours;
    }

    public String getCause() {
        return cause;
    }

    public String getLoginId() {
        return loginId;
    }

    public Long getRid() {
        return rid;
    }

    public String getStatus() {
        return status;
    }

    public Double getPayedHours() {
        return payedHours;
    }

    public Double getShiftHours() {
        return shiftHours;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setIsEachDay(Boolean isEachDay) {
        this.isEachDay = isEachDay;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
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

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setActualTotalHours(Double actualTotalHours) {
        this.actualTotalHours = actualTotalHours;
    }

    public void setActualTimeTo(String actualTimeTo) {
        this.actualTimeTo = actualTimeTo;
    }

    public void setActualIsEachDay(Boolean actualIsEachDay) {
        this.actualIsEachDay = actualIsEachDay;
    }

    public void setActualTimeFrom(String actualTimeFrom) {
        this.actualTimeFrom = actualTimeFrom;
    }

    public void setActualDateFrom(String actualDateFrom) {

        this.actualDateFrom = actualDateFrom;
    }

    public void setActualDateTo(String actualDateTo) {
        this.actualDateTo = actualDateTo;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPayedHours(Double payedHours) {
        this.payedHours = payedHours;
    }

    public void setShiftHours(Double shiftHours) {
        this.shiftHours = shiftHours;
    }

}
