package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;

public class DtoWeekAllocateHour extends DtoHoursOnWeek implements IDtoAllocateHour{

    private BigDecimal actualHour; //���еĹ���ʱ��
    private BigDecimal actualHourConfirmed; //���еı���׼�˵Ĺ���ʱ��
    private BigDecimal overtimeSumConfirmed; //���б���׼�˵ļӰ�ʱ��
    private BigDecimal overtimeSum; //����û�б�reject�ļӰ�ʱ��
    private BigDecimal leaveSumConfirmed; //���б���׼�˵����ʱ��
    private BigDecimal leaveSum; //����û�б�reject�����ʱ��

    public BigDecimal getActualHour() {
        return actualHour;
    }

    public BigDecimal getActualHourConfirmed() {
        return actualHourConfirmed;
    }

    public BigDecimal getLeaveSum() {
        return leaveSum;
    }

    public BigDecimal getLeaveSumConfirmed() {
        return leaveSumConfirmed;
    }

    public BigDecimal getOvertimeSum() {
        return overtimeSum;
    }

    public BigDecimal getOvertimeSumConfirmed() {
        return overtimeSumConfirmed;
    }

    public void setOvertimeSumConfirmed(BigDecimal overtimeSumConfirmed) {
        this.overtimeSumConfirmed = overtimeSumConfirmed;
    }

    public void setOvertimeSum(BigDecimal overtimeSum) {
        this.overtimeSum = overtimeSum;
    }

    public void setLeaveSumConfirmed(BigDecimal leaveSumConfirmed) {
        this.leaveSumConfirmed = leaveSumConfirmed;
    }

    public void setLeaveSum(BigDecimal leaveSum) {
        this.leaveSum = leaveSum;
    }

    public void setActualHourConfirmed(BigDecimal actualHourConfirmed) {
        this.actualHourConfirmed = actualHourConfirmed;
    }

    public void setActualHour(BigDecimal actualHour) {
        this.actualHour = actualHour;
    }

    public BigDecimal getAllocateHour(){
        BigDecimal allocateHour = new BigDecimal(0);
        if( actualHour != null ){
            allocateHour = actualHour;
        }
        if( overtimeSum != null ){
            allocateHour = allocateHour.subtract(overtimeSum);
        }
        if( leaveSum != null ){
            allocateHour = allocateHour.add(leaveSum);
        }
        return allocateHour;
    }

    public BigDecimal getAllocateHourConfirmed(){
        BigDecimal allocateHour = new BigDecimal(0);
        if( actualHourConfirmed != null ){
            allocateHour = actualHourConfirmed;
        }
        if( overtimeSumConfirmed != null ){
            allocateHour = allocateHour.subtract(overtimeSumConfirmed);
        }
        if( leaveSumConfirmed != null ){
            allocateHour = allocateHour.add(leaveSumConfirmed);
        }
        return allocateHour;
    }

}
