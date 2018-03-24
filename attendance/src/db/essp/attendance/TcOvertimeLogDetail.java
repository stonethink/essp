package db.essp.attendance;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TcOvertimeLogDetail implements Serializable {

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
    private db.essp.attendance.TcOvertimeLog tcOvertimeLog;

    /** full constructor */
    public TcOvertimeLogDetail(Long rid, Date overtimeDay, Double hours, String remark, String rst, Date rct, Date rut, db.essp.attendance.TcOvertimeLog tcOvertimeLog) {
        this.rid = rid;
        this.overtimeDay = overtimeDay;
        this.hours = hours;
        this.remark = remark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.tcOvertimeLog = tcOvertimeLog;
    }

    /** default constructor */
    public TcOvertimeLogDetail() {
    }

    /** minimal constructor */
    public TcOvertimeLogDetail(Long rid, db.essp.attendance.TcOvertimeLog tcOvertimeLog) {
        this.rid = rid;
        this.tcOvertimeLog = tcOvertimeLog;
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
        return this.hours;
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

    public db.essp.attendance.TcOvertimeLog getTcOvertimeLog() {
        return this.tcOvertimeLog;
    }

    public void setTcOvertimeLog(db.essp.attendance.TcOvertimeLog tcOvertimeLog) {
        this.tcOvertimeLog = tcOvertimeLog;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcOvertimeLogDetail) ) return false;
        TcOvertimeLogDetail castOther = (TcOvertimeLogDetail) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .append(getOvertimeDay().getTime())
            .toHashCode();
    }

}
