package db.essp.attendance;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class TcOvertime implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private Long acntRid;

    /** nullable persistent field */
    private Date datetimeStart;

    /** nullable persistent field */
    private Date datetimeFinish;

    /** nullable persistent field */
    private Double totalHours;

    /** nullable persistent field */
    private Boolean isEachDay;

    /** nullable persistent field */
    private String cause;

    /** nullable persistent field */
    private Date actualDatetimeStart;

    /** nullable persistent field */
    private Date actualDatetimeFinish;

    /** nullable persistent field */
    private Double actualTotalHours;

    /** nullable persistent field */
    private Boolean actualIsEachDay;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private Long wkId;

    /** nullable persistent field */
    private String changeDesc;

    /** nullable persistent field */
    private String changeType;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private Set tcOvertimeDetails;

    /** persistent field */
    private Set tcOvertimeLogs;

    /** full constructor */
    public TcOvertime(Long rid, String loginId, Long acntRid, Date datetimeStart, Date datetimeFinish, Double totalHours, Boolean isEachDay, String cause, Date actualDatetimeStart, Date actualDatetimeFinish, Double actualTotalHours, Boolean actualIsEachDay, String comments, String status, Long wkId, String changeDesc, String changeType, String rst, Date rct, Date rut, Set tcOvertimeDetails, Set tcOvertimeLogs) {
        this.rid = rid;
        this.loginId = loginId;
        this.acntRid = acntRid;
        this.datetimeStart = datetimeStart;
        this.datetimeFinish = datetimeFinish;
        this.totalHours = totalHours;
        this.isEachDay = isEachDay;
        this.cause = cause;
        this.actualDatetimeStart = actualDatetimeStart;
        this.actualDatetimeFinish = actualDatetimeFinish;
        this.actualTotalHours = actualTotalHours;
        this.actualIsEachDay = actualIsEachDay;
        this.comments = comments;
        this.status = status;
        this.wkId = wkId;
        this.changeDesc = changeDesc;
        this.changeType = changeType;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.tcOvertimeDetails = tcOvertimeDetails;
        this.tcOvertimeLogs = tcOvertimeLogs;
    }

    /** default constructor */
    public TcOvertime() {
    }

    /** minimal constructor */
    public TcOvertime(Long rid, Set tcOvertimeDetails, Set tcOvertimeLogs) {
        this.rid = rid;
        this.tcOvertimeDetails = tcOvertimeDetails;
        this.tcOvertimeLogs = tcOvertimeLogs;
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

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
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

    public Boolean getIsEachDay() {
        return this.isEachDay;
    }

    public void setIsEachDay(Boolean isEachDay) {
        this.isEachDay = isEachDay;
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

    public Boolean getActualIsEachDay() {
        return this.actualIsEachDay;
    }

    public void setActualIsEachDay(Boolean actualIsEachDay) {
        this.actualIsEachDay = actualIsEachDay;
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

    public String getChangeDesc() {
        return this.changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
    }

    public String getChangeType() {
        return this.changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
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

    public Set getTcOvertimeDetails() {
        return this.tcOvertimeDetails;
    }

    public void setTcOvertimeDetails(Set tcOvertimeDetails) {
        this.tcOvertimeDetails = tcOvertimeDetails;
    }

    public Set getTcOvertimeLogs() {
        return this.tcOvertimeLogs;
    }

    public void setTcOvertimeLogs(Set tcOvertimeLogs) {
        this.tcOvertimeLogs = tcOvertimeLogs;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcOvertime) ) return false;
        TcOvertime castOther = (TcOvertime) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
