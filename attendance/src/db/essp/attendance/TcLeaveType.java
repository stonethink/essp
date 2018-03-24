package db.essp.attendance;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;

/** @author Hibernate CodeGenerator */
public class TcLeaveType implements Serializable {

    /** identifier field */
    private String leaveName;

    /** nullable persistent field */
    private Long rid;

    /** nullable persistent field */
    private String settlementWay;

    /** nullable persistent field */
    private Long maxDay;

    private Long seq;
    /** nullable persistent field */
    private String relation;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public TcLeaveType(String leaveName, Long rid, String settlementWay, Long maxDay,String status, String description, String rst, Date rct, Date rut) {
        this.leaveName = leaveName;
        this.rid = rid;
        this.settlementWay = settlementWay;
        this.maxDay = maxDay;
        this.status = status;
        this.description = description;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public TcLeaveType() {
    }

    /** minimal constructor */
    public TcLeaveType(String leaveName) {
        this.leaveName = leaveName;
    }

    public String getLeaveName() {
        return this.leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getSettlementWay() {
        return this.settlementWay;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public Long getMaxDay() {
        return this.maxDay;
    }

    public void setMaxDay(Long maxDay) {
        this.maxDay = maxDay;
    }

    public String getRelation() {

        return relation;
    }

    public void setRelation(String relation) {

        this.relation = relation;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getSeq() {
        return seq;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("leaveName", getLeaveName())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcLeaveType) ) return false;
        TcLeaveType castOther = (TcLeaveType) other;
        return new EqualsBuilder()
            .append(this.getLeaveName(), castOther.getLeaveName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getLeaveName())
            .toHashCode();
    }

}
