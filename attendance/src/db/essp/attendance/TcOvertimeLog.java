package db.essp.attendance;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;
import com.wits.util.comDate;
import c2s.essp.attendance.Constant;

/** @author Hibernate CodeGenerator */
public class TcOvertimeLog implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private String decision;

    /** nullable persistent field */
    private Date datetimeStart;

    /** nullable persistent field */
    private Date datetimeFinish;

    /** nullable persistent field */
    private Double totalHours;

    /** nullable persistent field */
    private Boolean isEachDay;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long seq;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** persistent field */
    private db.essp.attendance.TcOvertime tcOvertime;

    /** persistent field */
    private Set tcOvertimeLogDetails;
    private Date logDate;

    /** full constructor */
    public TcOvertimeLog(Long rid, String loginId, String decision, Date datetimeStart, Date datetimeFinish, Double totalHours, Boolean isEachDay, String comments, Long seq, String rst, Date rct, Date rut, db.essp.attendance.TcOvertime tcOvertime, Set tcOvertimeLogDetails) {
        this.rid = rid;
        this.loginId = loginId;
        this.decision = decision;
        this.datetimeStart = datetimeStart;
        this.datetimeFinish = datetimeFinish;
        this.totalHours = totalHours;
        this.isEachDay = isEachDay;
        this.comments = comments;
        this.seq = seq;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.tcOvertime = tcOvertime;
        this.tcOvertimeLogDetails = tcOvertimeLogDetails;
    }

    /** default constructor */
    public TcOvertimeLog() {
    }

    /** minimal constructor */
    public TcOvertimeLog(Long rid, db.essp.attendance.TcOvertime tcOvertime, Set tcOvertimeLogDetails) {
        this.rid = rid;
        this.tcOvertime = tcOvertime;
        this.tcOvertimeLogDetails = tcOvertimeLogDetails;
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

    public String getDecision() {
        return this.decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
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

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
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

    public db.essp.attendance.TcOvertime getTcOvertime() {
        return this.tcOvertime;
    }

    public void setTcOvertime(db.essp.attendance.TcOvertime tcOvertime) {
        this.tcOvertime = tcOvertime;
    }

    public Set getTcOvertimeLogDetails() {
        return this.tcOvertimeLogDetails;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setTcOvertimeLogDetails(Set tcOvertimeLogDetails) {
        this.tcOvertimeLogDetails = tcOvertimeLogDetails;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof TcOvertimeLog) ) return false;
        TcOvertimeLog castOther = (TcOvertimeLog) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

    //根据Log的各个字段,合并显示的处理状况信息
    public String getDeal(){
        StringBuffer deal = new StringBuffer();
        deal.append(getDecision());
        deal.append(" ( ");
        deal.append(comDate.dateToString(getDatetimeStart(),Constant.DATE_TIME_FORMAT));
        deal.append(" ~ ");
        deal.append(comDate.dateToString(getDatetimeFinish(),Constant.DATE_TIME_FORMAT));
        deal.append(" ; " );
        deal.append(getTotalHours() + " hours " );
        if(getIsEachDay() != null && getIsEachDay().booleanValue())
            deal.append("; each day )");
        else
            deal.append(" ) ");
        return deal.toString();
    }
}
