package db.essp.attendance;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/** @author Hibernate CodeGenerator */
public class TcLeaveDetail implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Date leaveDay;

    /** nullable persistent field */
    private Double hours;

    private Double changeHours;

    private String remark;
    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.attendance.TcLeaveMain tcLeave;

    /** full constructor */
    public TcLeaveDetail(Long rid, Date leaveDay, Double hours, String rst, Date rct, Date rut, db.essp.attendance.TcLeaveMain tcLeave) {
        this.rid = rid;
        this.leaveDay = leaveDay;
        this.hours = hours;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.tcLeave = tcLeave;
    }

    /** default constructor */
    public TcLeaveDetail() {
    }

    /** minimal constructor */
    public TcLeaveDetail(Long rid, db.essp.attendance.TcLeaveMain tcLeave) {
        this.rid = rid;
        this.tcLeave = tcLeave;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Date getLeaveDay() {
        return this.leaveDay;
    }

    public void setLeaveDay(Date leaveDay) {
        this.leaveDay = leaveDay;
    }

    public Double getHours() {
        return this.hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
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

    public db.essp.attendance.TcLeaveMain getTcLeave() {
        return this.tcLeave;
    }

    public String getRemark() {
        return remark;
    }

    public Double getChangeHours() {

        return changeHours;
    }

    public void setTcLeave(db.essp.attendance.TcLeaveMain tcLeave) {
        this.tcLeave = tcLeave;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setChangeHours(Double changeHours) {

        this.changeHours = changeHours;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcLeaveDetail) ) return false;
        TcLeaveDetail castOther = (TcLeaveDetail) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .append(getLeaveDay().getTime())
            .toHashCode();
    }

}
