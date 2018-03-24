package server.essp.attendance.leave.viewbean;

public class VbLeave {
    private Long rid;
    private String loginId;
    private String orgId;
    private String organization;
    private Long acntRid;
    private String accountName;
    private String leaveName;
    private Double maxHours;//假别一次最多可请时间
    private String settlementWay;

    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private Double totalHours;
    private Object usableHours;//提交请假申请时的该假别可用时间
    private String cause;

    private Object currUsableHours;//当前的该假别可用时间
    private String decision;
    private String actualDateFrom;
    private String actualDateTo;
    private String actualTimeFrom;
    private String actualTimeTo;
    private Double actualTotalHours;
    private String comments;

    private String status;
    private Long wkId;
    private Boolean readOnly = Boolean.FALSE;
    public VbLeave() {
    }

    public String getActualDateFrom() {
        return actualDateFrom;
    }

    public String getActualDateTo() {
        return actualDateTo;
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

    public String getComments() {
        return comments;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public String getOrganization() {
        return organization;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getOrgId() {
        return orgId;
    }

    public Long getRid() {
        return rid;
    }

    public String getStatus() {
        return status;
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

    public Long getWkId() {
        return wkId;
    }

//    public List getLeaveOptList() {
//        return leaveOptList;
//    }
//
//    public VbLeavePersonalStatus getLeaveStatus() {
//        return leaveStatus;
//    }
//
//    public List getSettlementWayList() {
//        return settlementWayList;
//    }

    public String getDecision() {
        return decision;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public Object getUsableHours() {
        return usableHours;
    }

    public Object getCurrUsableHours() {
        return currUsableHours;
    }

    public Double getMaxHours() {
        return maxHours;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setWkId(Long wkId) {
        this.wkId = wkId;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public void setTimeTo(String timeTo) {

        this.timeTo = timeTo;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public void setActualTimeFrom(String actualTimeFrom) {
        this.actualTimeFrom = actualTimeFrom;
    }

    public void setActualDateTo(String actualDateTo) {
        this.actualDateTo = actualDateTo;
    }

    public void setActualDateFrom(String actualDateFrom) {
        this.actualDateFrom = actualDateFrom;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public void setUsableHours(Object usableHours) {
        this.usableHours = usableHours;
    }

    public void setCurrUsableHours(Object currUsableHours) {
        this.currUsableHours = currUsableHours;
    }

    public void setMaxHours(Double maxHours) {
        this.maxHours = maxHours;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

}
