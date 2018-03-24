package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;
import com.wits.util.comDate;


public class DtoWeeklyReportSumByDmAcntWorker extends DtoAllocateHour implements IDtoAllocateHourInTheAcnt{
    private String jobCode;
    private String confirmStatus;

    private Long acntRid;
    private String comments;

    private BigDecimal overtimeSumConfirmedInTheAcnt; //在本项目的，所有被批准了的加班时间
    private BigDecimal overtimeSumInTheAcnt; //在本项目的，所有没有被reject的加班时间

    private BigDecimal sumHour;

    public String getJobCode() {
        return jobCode;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public BigDecimal getOvertimeSumConfirmedInTheAcnt() {
        return overtimeSumConfirmedInTheAcnt;
    }

    public BigDecimal getOvertimeSumInTheAcnt() {
        return overtimeSumInTheAcnt;
    }

    public BigDecimal getSumHour() {
        return sumHour;
    }

    public String getComments() {
        return comments;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setSumHour(BigDecimal sumHour) {
        this.sumHour = sumHour;
    }

    public void setOvertimeSumInTheAcnt(BigDecimal overtimeSumInTheAcnt) {
        this.overtimeSumInTheAcnt = overtimeSumInTheAcnt;
    }

    public void setOvertimeSumConfirmedInTheAcnt(BigDecimal overtimeSumConfirmedInTheAcnt) {
        this.overtimeSumConfirmedInTheAcnt = overtimeSumConfirmedInTheAcnt;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPeriodInfo(){
        if( getBeginPeriod() != null && getEndPeriod() != null ){
            String beginStr = comDate.dateToString(getBeginPeriod());
            String endStr = comDate.dateToString(getEndPeriod());
            return beginStr + " ~ " + endStr;
        }else{
            return null;
        }
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
