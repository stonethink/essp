package c2s.essp.tc.leave;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoLeaveDetail extends DtoBase {
    private Long rid;
    private Date leaveDay;
    private Double hours;
    private String remark;
    public Double getHours() {
        return hours;
    }

    public Date getLeaveDay() {
        return leaveDay;
    }

    public Long getRid() {
        return rid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setLeaveDay(Date leaveDay) {
        this.leaveDay = leaveDay;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
