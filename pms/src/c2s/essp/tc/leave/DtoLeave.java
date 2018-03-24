package c2s.essp.tc.leave;

import java.util.Date;
import java.util.List;

import c2s.dto.DtoBase;

public class DtoLeave extends DtoBase {
    private Long rid;
    private String loginId;
    private String orgId;
    private String orgName;
    private String leaveName;

    private String cause;
    private Date actualDateFrom;
    private Date actualDateTo;
    private String actualTimeFrom;
    private String actualTimeTo;
    private String status;
    private Double actualTotalHours;
    //记录初始的actualTotalHours值,在界面中actualTotalHours可能会被修改
    private Double actualTotalHoursBak;
    private List detailList;
    public Date getActualDateFrom() {
        return actualDateFrom;
    }

    public Date getActualDateTo() {
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

    public String getLeaveName() {
        return leaveName;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public Long getRid() {
        return rid;
    }

    public List getDetailList() {
        return detailList;
    }

    public String getStatus() {
        return status;
    }

    public Double getActualTotalHoursBak() {
        return actualTotalHoursBak;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
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

    public void setActualDateTo(Date actualDateTo) {
        this.actualDateTo = actualDateTo;
    }

    public void setActualDateFrom(Date actualDateFrom) {
        this.actualDateFrom = actualDateFrom;
    }

    public void setDetailList(List detailList) {
        this.detailList = detailList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setActualTotalHoursBak(Double actualTotalHoursBak) {
        this.actualTotalHoursBak = actualTotalHoursBak;
    }


}
