package db.essp.attendance;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;
import c2s.essp.attendance.Constant;
import itf.hr.HrFactory;
import com.wits.util.comDate;

/** @author Hibernate CodeGenerator */
public class TcLeaveMain implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private String orgId;

    private Long acntRid;
    /** nullable persistent field */
    private String leaveName;

    /** nullable persistent field */
    private Date datetimeStart;

    /** nullable persistent field */
    private Date datetimeFinish;

    /** nullable persistent field */
    private Double totalHours;

    private Double usableHours;//申请该请假时的可用假
    /** nullable persistent field */
    private String cause;

    /** nullable persistent field */
    private Date actualDatetimeStart;

    /** nullable persistent field */
    private Date actualDatetimeFinish;

    /** nullable persistent field */
    private Double actualTotalHours;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private Long wkId;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private Set tcLeaveLogs;

    private Set tcLeaveDetails;
    /** full constructor */
    public TcLeaveMain(Long rid, String loginId, String leaveName, Date datetimeStart, Date datetimeFinish, Double totalHours, String cause, Date actualDatetimeStart, Date actualDatetimeFinish, Double actualTotalHours, String comments, String status, Long wkId, String rst, Date rct, Date rut, Set tcLeaveLogs) {
        this.rid = rid;
        this.loginId = loginId;
        this.leaveName = leaveName;
        this.datetimeStart = datetimeStart;
        this.datetimeFinish = datetimeFinish;
        this.totalHours = totalHours;
        this.cause = cause;
        this.actualDatetimeStart = actualDatetimeStart;
        this.actualDatetimeFinish = actualDatetimeFinish;
        this.actualTotalHours = actualTotalHours;
        this.comments = comments;
        this.status = status;
        this.wkId = wkId;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.tcLeaveLogs = tcLeaveLogs;
    }

    /** default constructor */
    public TcLeaveMain() {
    }

    /** minimal constructor */
    public TcLeaveMain(Long rid, Set tcLeaveLogs) {
        this.rid = rid;
        this.tcLeaveLogs = tcLeaveLogs;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getOrgId() {

        return orgId;
    }

    public void setOrgId(String orgId) {

        this.orgId = orgId;
    }

    public String getLeaveName() {
        return this.leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public Date getDatetimeStart() {
        return this.datetimeStart;
    }

    public void setDatetimeStart(Date datetimeStart) {
        this.datetimeStart = datetimeStart;
    }

    public Date getDatetimeFinish() {
        return this.datetimeFinish;
    }

    public void setDatetimeFinish(Date datetimeFinish) {
        this.datetimeFinish = datetimeFinish;
    }

    public Double getTotalHours() {
        return this.totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }

    public String getCause() {
        return this.cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getActualDatetimeStart() {
        return this.actualDatetimeStart;
    }

    public void setActualDatetimeStart(Date actualDatetimeStart) {
        this.actualDatetimeStart = actualDatetimeStart;
    }

    public Date getActualDatetimeFinish() {
        return this.actualDatetimeFinish;
    }

    public void setActualDatetimeFinish(Date actualDatetimeFinish) {
        this.actualDatetimeFinish = actualDatetimeFinish;
    }

    public Double getActualTotalHours() {
        return this.actualTotalHours;
    }

    public void setActualTotalHours(Double actualTotalHours) {
        this.actualTotalHours = actualTotalHours;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWkId() {
        return this.wkId;
    }

    public void setWkId(Long wkId) {
        this.wkId = wkId;
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

    public Set getTcLeaveLogs() {
        return this.tcLeaveLogs;
    }

    public Set getTcLeaveDetails() {
        return tcLeaveDetails;
    }

    public Double getUsableHours() {
        return usableHours;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public void setTcLeaveLogs(Set tcLeaveLogs) {
        this.tcLeaveLogs = tcLeaveLogs;
    }

    public void setTcLeaveDetails(Set tcLeaveDetails) {
        this.tcLeaveDetails = tcLeaveDetails;
    }

    public void setUsableHours(Double usableHours) {
        this.usableHours = usableHours;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcLeaveMain) ) return false;
        TcLeaveMain castOther = (TcLeaveMain) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

    public String getWFDescription(){
        StringBuffer buf = new StringBuffer();
        String org = HrFactory.create().findResouce(this.getLoginId()).getOrganization();;
        buf.append(org);
        buf.append(" ( ");
        buf.append(comDate.dateToString(this.getDatetimeStart(),Constant.DATE_TIME_FORMAT));
        buf.append(" ~ ");
        buf.append(comDate.dateToString(this.getDatetimeFinish(),Constant.DATE_TIME_FORMAT));
        buf.append(" , ");
        buf.append(this.getTotalHours());
        buf.append(" hours )");
        return buf.toString();
    }
}
