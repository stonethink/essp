package c2s.essp.tc.overtime;

import java.util.Date;
import java.util.List;

import c2s.dto.DtoBase;

public class DtoOverTime extends DtoBase {
    public static final String[] PAY_WAYS = new String[]{"1 Time","2 Times","3 Times"};

    private Long rid;
    private String loginId;
    private Long acntRid;
    private String accountName;

    private String cause;
    private String decision;
    private Date actualDateFrom;
    private Date actualDateTo;
    private String actualTimeFrom;
    private String actualTimeTo;
    private Double actualTotalHours;
    private Boolean actualIsEachDay;
    private String comments;
    private String status;
    private List detailList;
    public String getAccountName() {
        return accountName;
    }

    public java.util.Date getActualDateFrom() {
        return actualDateFrom;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Date getActualDateTo() {
        return actualDateTo;
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

    public String getComments() {
        return comments;
    }

    public String getDecision() {
        return decision;
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

    public List getDetailList() {
        return detailList;
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

    public void setDecision(String decision) {
        this.decision = decision;
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

    public void setActualIsEachDay(Boolean actualIsEachDay) {
        this.actualIsEachDay = actualIsEachDay;
    }

    public void setActualDateTo(Date actualDateTo) {
        this.actualDateTo = actualDateTo;
    }

    public void setActualDateFrom(java.util.Date actualDateFrom) {
        this.actualDateFrom = actualDateFrom;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setDetailList(List detailList) {
        this.detailList = detailList;
    }

}
