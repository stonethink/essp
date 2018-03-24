package c2s.essp.tc.weeklyreport;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

/**
 * ���м���allocate hour�ı�Ҫ����
 * allocate hour = actual hour - overtime + leave
 * actual hour, overtime, leave�п���û�б�ȫ����׼������Ҫ�ֱ���㣺
 * 1. �����е����ݣ�����allocate hour
 * 2. �����б���׼�����ݣ�����allocate hour
 */
public class DtoAllocateHour extends DtoBase implements IDtoAllocateHour{
    private String userId;
    private Date beginPeriod;
    private Date endPeriod;

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

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public String getUserId() {
        return userId;
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

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
