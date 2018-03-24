package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;

public class DtoWeeklyReportSumByPm extends DtoWeekAllocateHour implements IDtoAllocateHourInTheAcnt{

    private Long rid;
    private Long acntRid;

    private String jobCode;

    private String confirmStatus;

    private String comments;

    private BigDecimal overtimeSumConfirmedInTheAcnt; //在本项目的，所有被批准了的加班时间
    private BigDecimal overtimeSumInTheAcnt; //在本项目的，所有没有被reject的加班时间

    public Long getAcntRid() {
        return acntRid;
    }

    public String getComments() {
        return comments;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public String getJobCode() {
        return jobCode;
    }

    public BigDecimal getOvertimeSumConfirmedInTheAcnt() {
        return overtimeSumConfirmedInTheAcnt;
    }

    public BigDecimal getOvertimeSumInTheAcnt() {
        return overtimeSumInTheAcnt;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setOvertimeSumInTheAcnt(BigDecimal overtimeSumInTheAcnt) {
        this.overtimeSumInTheAcnt = overtimeSumInTheAcnt;
    }

    public void setOvertimeSumConfirmedInTheAcnt(BigDecimal overtimeSumConfirmedInTheAcnt) {
        this.overtimeSumConfirmedInTheAcnt = overtimeSumConfirmedInTheAcnt;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public BigDecimal getActualHourConfirmedInTheAcnt() {
        return null;
    }

    public BigDecimal getActualHourInTheAcnt(){
        return null;
    }

    public void setActualHourInTheAcnt(BigDecimal actualHourInTheAcnt){}

    public void setActualHourConfirmedInTheAcnt(BigDecimal actualHourConfirmedInTheAcnt){}

}
