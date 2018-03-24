package server.essp.tc.hrmanage.viewbean;

import java.math.BigDecimal;

public class VbPartTcExcel {
    private String period;//考核期
    private String name;//员工姓名
    private String chineseName;
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

    public BigDecimal getOverTime() {
        return overTime;
    }

    public String getPeriod() {
        return period;
    }

    public String getRemark() {
        return remark;
    }

    public Long getViolat() {
        return violat;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setAbsentFromWork(BigDecimal absentFromWork) {
        this.absentFromWork = absentFromWork;
    }

    public void setFullSalaryLeave(BigDecimal fullSalaryLeave) {
        this.fullSalaryLeave = fullSalaryLeave;
    }

    public void setHalfSalaryLeave(BigDecimal halfSalaryLeave) {
        this.halfSalaryLeave = halfSalaryLeave;
    }

    public void setLeave(BigDecimal leave) {
        this.leave = leave;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOverTime(BigDecimal overTime) {
        this.overTime = overTime;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setViolat(Long violat) {
        this.violat = violat;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }
}
