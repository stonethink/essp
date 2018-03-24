package c2s.essp.tc.overtime;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoOverTimeDetail extends DtoBase {
    private Long rid;
    private Date overtimeDay;
    private Double hours;
    private Double shiftHours;
    private Double payedHours;
    private String payedWay;
    private String remark;
    public Double getHours() {
        return hours;
    }

    public Date getOvertimeDay() {
        return overtimeDay;
    }

    public Double getPayedHours() {
        return payedHours;
    }

    public String getPayedWay() {
        return payedWay;
    }

    public String getRemark() {
        return remark;
    }

    public Long getRid() {
        return rid;
    }

    public Double getShiftHours() {
        return shiftHours;
    }

    public void setShiftHours(Double shiftHours) {
        this.shiftHours = shiftHours;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPayedWay(String payedWay) {
        this.payedWay = payedWay;
    }

    public void setPayedHours(Double payedHours) {
        this.payedHours = payedHours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public void setOvertimeDay(Date overtimeDay) {
        this.overtimeDay = overtimeDay;
    }

}
