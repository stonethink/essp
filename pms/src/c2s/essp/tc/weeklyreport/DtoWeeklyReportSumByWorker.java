package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

public class DtoWeeklyReportSumByWorker extends DtoBase{

    //account
    private Long acntRid;
    private String acntName;
    private String startType;

    //user
    private String userId;
    private String jobCode;

    private Date beginPeriod;
    private Date endPeriod;

    private String confirmStatus;
    private String comments;

    private BigDecimal actualHour;

    public String getAcntName() {
        return acntName;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public String getComments() {
        return comments;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getStartType() {
        return startType;
    }

    public String getUserId() {
        return userId;
    }

    public BigDecimal getActualHour() {
        return actualHour;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }

    public void setActualHour(BigDecimal actualHour) {
        this.actualHour = actualHour;
    }

}
