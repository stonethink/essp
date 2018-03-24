package server.essp.attendance.modify.viewbean;

import java.util.*;

public class VbLeaveModify {
    private Long rid;
    private String loginId;
    private String orgId;
    private String organization;
    private Long acntRid;
    private String accountName;
    private String leaveName;
    private String actualDateFrom;
    private String actualDateTo;
    private String actualTimeFrom;
    private String actualTimeTo;
    private Double actualTotalHours;
    private String cause;
    private List detailList;
    private String decision;
    public String getAccountName() {
        return accountName;
    }

    public Long getAcntRid() {
        return acntRid;
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

    public List getDetailList() {
        return detailList;
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

    public Long getRid() {
        return rid;
    }

    public String getOrganization() {
        return organization;
    }

    public String getDecision() {
        return decision;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActualDateFrom(String actualDateFrom) {
        this.actualDateFrom = actualDateFrom;
    }

    public void setActualDateTo(String actualDateTo) {
        this.actualDateTo = actualDateTo;
    }

    public void setActualTimeFrom(String actualTimeFrom) {
        this.actualTimeFrom = actualTimeFrom;
    }

    public void setActualTimeTo(String actualTimeTo) {
        this.actualTimeTo = actualTimeTo;
    }

    public void setActualTotalHours(Double actualTotalHours) {
        this.actualTotalHours = actualTotalHours;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setDetailList(List detailList) {
        this.detailList = detailList;
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

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
