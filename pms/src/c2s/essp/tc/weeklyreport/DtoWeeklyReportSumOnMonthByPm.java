package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;

public class DtoWeeklyReportSumOnMonthByPm extends DtoAllocateHour implements IDtoAllocateHourInTheAcnt{

    public static final String SUMMARY_TITLE = "Actual Hours";

    private Long acntRid;

    private String jobCode;

    private BigDecimal actualHourConfirmedInTheAcnt; //在本项目的，所有被批准了的工作时间
    private BigDecimal actualHourInTheAcnt; //在本项目的，所有没有被reject的工作时间

    private BigDecimal overtimeSumConfirmedInTheAcnt; //在本项目的，所有被批准了的加班时间
    private BigDecimal overtimeSumInTheAcnt; //在本项目的，所有没有被reject的加班时间

    //private BigDecimal sumHour; //在本项目的，所有的工作时间，actual hour含有这部分时间；如果该时间已经被批准，则actual confirmed hour含有这部分时间

    public Long getAcntRid() {
        return acntRid;
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

    public BigDecimal getActualHourConfirmedInTheAcnt() {
        return actualHourConfirmedInTheAcnt;
    }

    public BigDecimal getActualHourInTheAcnt() {
        return actualHourInTheAcnt;
    }

    public BigDecimal getSumHour() {
        return null;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setSumHour(BigDecimal sumHour) {
//        this.sumHour = sumHour;
    }

    public void setOvertimeSumInTheAcnt(BigDecimal overtimeSumInTheAcnt) {
        this.overtimeSumInTheAcnt = overtimeSumInTheAcnt;
    }

    public void setOvertimeSumConfirmedInTheAcnt(BigDecimal overtimeSumConfirmedInTheAcnt) {
        this.overtimeSumConfirmedInTheAcnt = overtimeSumConfirmedInTheAcnt;
    }

    public void setActualHourInTheAcnt(BigDecimal actualHourInTheAcnt) {
        this.actualHourInTheAcnt = actualHourInTheAcnt;
    }

    public void setActualHourConfirmedInTheAcnt(BigDecimal actualHourConfirmedInTheAcnt) {
        this.actualHourConfirmedInTheAcnt = actualHourConfirmedInTheAcnt;
    }

}
