package server.essp.tc.mail.genmail.contbean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ���ڼ�¼��Copy
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Robin.Zhang
 * @version 1.0
 */
public class VbTcExcelCopy {
   private String period;//������
   private Date beginPeriod;//��������
   private Date endPeriod;//����ֹ��
   private String name;//Ա����¼ID
   private String beginDateOfWork;//��������
   private String endDateOfWork;//����ֹ��
   private BigDecimal standardWorkTime;//��׼��ʱ
   private BigDecimal actualWorkTime;//ʵ�ʹ�ʱ
   private BigDecimal normalWorkTime;//������ʱ
   private BigDecimal subWorkTime;//��ʱ��
   private BigDecimal salaryWorkTime;//��н��ʱ
   private BigDecimal overTime;//�Ӱ�Сʱ��
   private BigDecimal fullSalaryLeave;//�ݼ٣�ȫн��Сʱ��
   private BigDecimal halfSalaryLeave;//�ݼ٣���н��Сʱ��
   private BigDecimal    leave;//�ݼ�Сʱ��
   private BigDecimal absentFromWork;//��Сʱ��
   private Long violat;//Υ���ܴ���
   private String remark;//��ע

    public BigDecimal getAbsentFromWork() {
        return absentFromWork;
    }

    public BigDecimal getActualWorkTime() {
        return actualWorkTime;
    }

    public String getBeginDateOfWork() {
        return beginDateOfWork;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public String getEndDateOfWork() {
        return endDateOfWork;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public BigDecimal getFullSalaryLeave() {
        return fullSalaryLeave;
    }

    public BigDecimal getHalfSalaryLeave() {
        return halfSalaryLeave;
    }

    public BigDecimal getLeave() {
        return leave;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getNormalWorkTime() {
        return normalWorkTime;
    }

    public BigDecimal getOverTime() {
        return overTime;
    }

    public String getPeriod() {
        return period;
    }

    public String getRemark() {
        return remark;
    }

    public BigDecimal getSalaryWorkTime() {
        return salaryWorkTime;
    }

    public BigDecimal getStandardWorkTime() {
        return standardWorkTime;
    }

    public BigDecimal getSubWorkTime() {
        return subWorkTime;
    }

    public Long getViolat() {
        return violat;
    }

    public void setViolat(Long violat) {
        this.violat = violat;
    }

    public void setSubWorkTime(BigDecimal subWorkTime) {
        this.subWorkTime = subWorkTime;
    }

    public void setStandardWorkTime(BigDecimal standardWorkTime) {
        this.standardWorkTime = standardWorkTime;
    }

    public void setSalaryWorkTime(BigDecimal salaryWorkTime) {
        this.salaryWorkTime = salaryWorkTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setOverTime(BigDecimal overTime) {
        this.overTime = overTime;
    }

    public void setNormalWorkTime(BigDecimal normalWorkTime) {
        this.normalWorkTime = normalWorkTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeave(BigDecimal leave) {
        this.leave = leave;
    }

    public void setHalfSalaryLeave(BigDecimal halfSalaryLeave) {
        this.halfSalaryLeave = halfSalaryLeave;
    }

    public void setFullSalaryLeave(BigDecimal fullSalaryLeave) {
        this.fullSalaryLeave = fullSalaryLeave;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setEndDateOfWork(String endDateOfWork) {
        this.endDateOfWork = endDateOfWork;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setBeginDateOfWork(String beginDateOfWork) {
        this.beginDateOfWork = beginDateOfWork;
    }

    public void setActualWorkTime(BigDecimal actualWorkTime) {
        this.actualWorkTime = actualWorkTime;
    }

    public void setAbsentFromWork(BigDecimal absentFromWork) {
        this.absentFromWork = absentFromWork;
    }

}
