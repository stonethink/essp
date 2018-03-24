package server.essp.attendance.overtime.viewbean;

import java.util.*;



public class VbOverTimePerDay {
    private Long rid;
    private Long overtimeRid;
    private Date overtimeDay;
    private String hours;
    private String shiftHours;
    private String payedHours;
    private String remark;
    public String getHours() {
        return hours;
    }

    public Date getOvertimeDay() {
        return overtimeDay;
    }

    public Long getOvertimeRid() {
        return overtimeRid;
    }

    public String getRemark() {
        return remark;
    }

    public Long getRid() {
        return rid;
    }

    public String getPayedHours() {
        return payedHours;
    }

    public String getShiftHours() {
        return shiftHours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setOvertimeDay(Date overtimeDay) {
        this.overtimeDay = overtimeDay;
    }

    public void setOvertimeRid(Long overtimeRid) {
        this.overtimeRid = overtimeRid;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setPayedHours(String payedHours) {
        this.payedHours = payedHours;
    }

    public void setShiftHours(String shiftHours) {
        this.shiftHours = shiftHours;
    }

}
