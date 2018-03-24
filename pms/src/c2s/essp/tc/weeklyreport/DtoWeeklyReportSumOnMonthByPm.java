package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;

public class DtoWeeklyReportSumOnMonthByPm extends DtoAllocateHour implements IDtoAllocateHourInTheAcnt{

    public static final String SUMMARY_TITLE = "Actual Hours";

    private Long acntRid;

    private String jobCode;

    private BigDecimal actualHourConfirmedInTheAcnt; //�ڱ���Ŀ�ģ����б���׼�˵Ĺ���ʱ��
    private BigDecimal actualHourInTheAcnt; //�ڱ���Ŀ�ģ�����û�б�reject�Ĺ���ʱ��

    private BigDecimal overtimeSumConfirmedInTheAcnt; //�ڱ���Ŀ�ģ����б���׼�˵ļӰ�ʱ��
    private BigDecimal overtimeSumInTheAcnt; //�ڱ���Ŀ�ģ�����û�б�reject�ļӰ�ʱ��

    //private BigDecimal sumHour; //�ڱ���Ŀ�ģ����еĹ���ʱ�䣬actual hour�����ⲿ��ʱ�䣻�����ʱ���Ѿ�����׼����actual confirmed hour�����ⲿ��ʱ��

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
