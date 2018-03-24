package server.essp.tc.hrmanage.viewbean;

import java.math.BigDecimal;

public class VbTotal {
    private BigDecimal totalStandard=new BigDecimal(0);//�ܵı�׼��ʱ
    private BigDecimal totalActual=new BigDecimal(0);//�ܵ�ʵ�ʹ�ʱ
    private BigDecimal totalNormal=new BigDecimal(0);//�ܵ�������ʱ
    private BigDecimal totalSub=new BigDecimal(0);//�ܵĹ�ʱ��
    private BigDecimal totalSalaryWorkTime=new BigDecimal(0);//�ܵķ�н��ʱ
    private BigDecimal totalOverTime=new BigDecimal(0);//�ܵļӰ�Сʱ��
    private BigDecimal totalFullSalaryLeave=new BigDecimal(0);//�ܵ��ݼ٣�ȫн��Сʱ��
    private BigDecimal totalHalhSalaryLeave=new BigDecimal(0);//�ܵ��ݼ٣���н��Сʱ��
    private BigDecimal totalLeave=new BigDecimal(0);//�ܵ��ݼ�ʱ��
    private BigDecimal totalAbsentFromWork=new BigDecimal(0);//�ܵĿ���Сʱ��
    private BigDecimal totalViolat=new BigDecimal(0);//�ܵ�Υ�����

    public BigDecimal getTotalAbsentFromWork() {
        return totalAbsentFromWork;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public BigDecimal getTotalAttendance() {
        return totalViolat;
    }

    public BigDecimal getTotalFullSalaryLeave() {
        return totalFullSalaryLeave;
    }

    public BigDecimal getTotalHalhSalaryLeave() {
        return totalHalhSalaryLeave;
    }

    public BigDecimal getTotalLeave() {
        return totalLeave;
    }

    public BigDecimal getTotalNormal() {
        return totalNormal;
    }

    public BigDecimal getTotalOverTime() {
        return totalOverTime;
    }

    public BigDecimal getTotalSalaryWorkTime() {
        return totalSalaryWorkTime;
    }

    public BigDecimal getTotalStandard() {
        return totalStandard;
    }

    public BigDecimal getTotalSub() {
        return totalSub;
    }

    public void setTotalAbsentFromWork(BigDecimal totalAbsentFromWork) {
        this.totalAbsentFromWork = totalAbsentFromWork;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public void setTotalAttendance(BigDecimal totalViolat) {
        this.totalViolat = totalViolat;
    }

    public void setTotalFullSalaryLeave(BigDecimal totalFullSalaryLeave) {
        this.totalFullSalaryLeave = totalFullSalaryLeave;
    }

    public void setTotalHalhSalaryLeave(BigDecimal totalHalhSalaryLeave) {
        this.totalHalhSalaryLeave = totalHalhSalaryLeave;
    }

    public void setTotalLeave(BigDecimal totalLeave) {
        this.totalLeave = totalLeave;
    }

    public void setTotalNormal(BigDecimal totalNormal) {
        this.totalNormal = totalNormal;
    }

    public void setTotalOverTime(BigDecimal totalOverTime) {
        this.totalOverTime = totalOverTime;
    }

    public void setTotalSalaryWorkTime(BigDecimal totalSalaryWorkTime) {
        this.totalSalaryWorkTime = totalSalaryWorkTime;
    }

    public void setTotalSub(BigDecimal totalSub) {
        this.totalSub = totalSub;
    }

    public void setTotalStandard(BigDecimal totalStandard) {
        this.totalStandard = totalStandard;
    }

}
