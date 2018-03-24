package db.essp.attendance;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;
import server.framework.common.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/** @author Hibernate CodeGenerator */
public class TcOvertimeDetail implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Date overtimeDay;

    /** nullable persistent field */
    private Double hours;

    /** nullable persistent field */
    private String remark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.attendance.TcOvertime tcOvertime;
    private Double shiftHours;
    private Double payedHours;
    private String payedWay;
    /** full constructor */
    public TcOvertimeDetail(Long rid, Date overtimeDay, Double hours, String remark, String rst, Date rct, Date rut, db.essp.attendance.TcOvertime tcOvertime) {
        this.rid = rid;
        this.overtimeDay = overtimeDay;
        this.hours = hours;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.tcOvertime = tcOvertime;
    }

    /** default constructor */
    public TcOvertimeDetail() {
    }

    /** minimal constructor */
    public TcOvertimeDetail(Long rid, db.essp.attendance.TcOvertime tcOvertime) {
        this.rid = rid;
        this.tcOvertime = tcOvertime;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Date getOvertimeDay() {
        return this.overtimeDay;
    }

    public void setOvertimeDay(Date overtimeDay) {
        this.overtimeDay = overtimeDay;
    }

    public Double getHours() {
        return hours == null ? new Double(0) : hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    /**
     *            @hibernate.many-to-one
     *             not-null="true"
     *            @hibernate.column name="OVERTIME_ID"
     *
     */
    public db.essp.attendance.TcOvertime getTcOvertime() {
        return this.tcOvertime;
    }

    public Double getShiftHours() {
        return shiftHours == null ? new Double(0) : shiftHours;
    }

    public Double getPayedHours() {
        return payedHours == null ? new Double(0) : payedHours;
    }

    public String getPayedWay() {
        return payedWay;
    }

    public void setTcOvertime(db.essp.attendance.TcOvertime tcOvertime) {
        this.tcOvertime = tcOvertime;
    }

    public void setShiftHours(Double shiftHours) {
        this.shiftHours = shiftHours;
    }

    public void setPayedHours(Double payedHours) {
        this.payedHours = payedHours;
    }

    public void setPayedWay(String payedWay) {
        this.payedWay = payedWay;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcOvertimeDetail) ) return false;
        TcOvertimeDetail castOther = (TcOvertimeDetail) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getOvertimeDay().getTime())
            .append(getRid())
            .toHashCode();
    }
    //增加调休时间
    public void addShiftHours(double hours){
        if(getUsableHours().doubleValue() < hours)
            throw new BusinessException("TC_OVER_0014", "there is not enough hours to shift!");
        double newShiftHours = shiftHours == null ? 0D : shiftHours.doubleValue();
        newShiftHours +=hours;
        setShiftHours(new Double(newShiftHours));
    }
    //返还(减少)调休时间
    public void returnShiftHours(double returnHr){
        double newShiftHours = shiftHours == null ? 0D : shiftHours.doubleValue();
        newShiftHours -= returnHr;
        if(newShiftHours < 0)
            throw new BusinessException("TC_OVER_0020", "The return shift hours ["+returnHr+"] is larger!");
        setShiftHours(new Double(newShiftHours));
    }
    //增加付钱时间
    public void addPayedHours(double hours){
        if(getUsableHours().doubleValue() < hours)
            throw new BusinessException("TC_OVER_0014", "there is not enough hours to pay!");
        double newPayedHours = payedHours == null ? 0D : payedHours.doubleValue();
        newPayedHours +=hours;
        setPayedHours(new Double(newPayedHours));
    }
    //返还(减少)付钱时间
    public void returnPayedHours(double returnHr){
        double newPayedHours = payedHours == null ? 0D : payedHours.doubleValue();
        newPayedHours -= returnHr;
        if(newPayedHours < 0)
            throw new BusinessException("TC_OVER_0020", "The return payed hours ["+returnHr+"] is larger!");
        setPayedHours(new Double(newPayedHours));
    }
    //可用时间
    public Double getUsableHours(){
        double usable = hours == null ? 0D: hours.doubleValue();
        usable -= getUsedHours().doubleValue();
        BigDecimal big = new BigDecimal(usable);
        return new Double(big.setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
    //已用时间
    public Double getUsedHours(){
        double used = (shiftHours == null ? 0D : shiftHours.doubleValue());
        used += (payedHours == null ? 0D : payedHours.doubleValue());
        BigDecimal big = new BigDecimal(used);
        return new Double(big.setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}
