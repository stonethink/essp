package server.essp.attendance.modify.viewbean;

import java.util.*;

public class VbLeaveDetail {
    private Long rid;
    private Date leaveDay;
    private Double hours;
    private Double changeHours;
    private String remark;
    public Double getHours() {
        return hours;
    }

    public Date getLeaveDay() {
        return leaveDay;
    }

    public String getRemark() {
        return remark;
    }

    public Long getRid() {
        return rid;
    }

    public Double getChangeHours() {

        return changeHours;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public void setChangeHours(Double changeHours) {

        this.changeHours = changeHours;
    }

}
