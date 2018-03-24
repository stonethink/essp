package server.essp.tc.mail.genmail.contbean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 差勤记录表Copy
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Robin.Zhang
 * @version 1.0
 */
public class VbTcExcelCopy {
   private String period;//考核期
   private Date beginPeriod;//考核起日
   private Date endPeriod;//考核止日
   private String name;//员工登录ID
   private String beginDateOfWork;//工作起日
   private String endDateOfWork;//工作止日
   private BigDecimal standardWorkTime;//标准工时
   private BigDecimal actualWorkTime;//实际工时
   private BigDecimal normalWorkTime;//正常工时
   private BigDecimal subWorkTime;//工时差
   private BigDecimal salaryWorkTime;//发薪工时
   private BigDecimal overTime;//加班小时数
   private BigDecimal fullSalaryLeave;//休假（全薪）小时数
   private BigDecimal halfSalaryLeave;//休假（半薪）小时数
   private BigDecimal    leave;//休假小时数
   private BigDecimal absentFromWork;//矿工小时数
   private Long violat;//违规总次数
   private String remark;//备注

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
