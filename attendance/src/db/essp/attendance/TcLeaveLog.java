package db.essp.attendance;


import c2s.essp.attendance.Constant;
import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;
import com.wits.util.comDate;

/** @author Hibernate CodeGenerator */
public class TcLeaveLog implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String loginId;

    /** nullable persistent field */
    private Date datetimeStart;

    /** nullable persistent field */
    private Date datetimeFinish;

    /** nullable persistent field */
    private Double totalHours;

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
    private db.essp.attendance.TcLeaveMain tcLeaveMain;
    private String decision;
    private Date logDate;

    /** full constructor */
    public TcLeaveLog(Long rid, String loginId, Date datetimeStart, Date datetimeFinish, Double totalHours, String comments, Long seq, String rst, Date rct, Date rut, db.essp.attendance.TcLeaveMain tcLeaveMain) {
        this.rid = rid;
        this.loginId = loginId;
        this.datetimeStart = datetimeStart;
        this.datetimeFinish = datetimeFinish;
        this.totalHours = totalHours;
        this.comments = comments;
        this.seq = seq;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.tcLeaveMain = tcLeaveMain;
    }

    /** default constructor */
    public TcLeaveLog() {
    }

    /** minimal constructor */
    public TcLeaveLog(Long rid, db.essp.attendance.TcLeaveMain tcLeaveMain) {
        this.rid = rid;
        this.tcLeaveMain = tcLeaveMain;
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

    public db.essp.attendance.TcLeaveMain getTcLeaveMain() {
        return this.tcLeaveMain;
    }

    public String getDecision() {
        return decision;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setTcLeaveMain(db.essp.attendance.TcLeaveMain tcLeaveMain) {
        this.tcLeaveMain = tcLeaveMain;
    }

    public void setDecision(String decision) {
        this.decision = decision;
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
        if ( !(other instanceof TcLeaveLog) ) return false;
        TcLeaveLog castOther = (TcLeaveLog) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

    public String getDeal(){
        StringBuffer deal = new StringBuffer();
        deal.append(this.getDecision());
        deal.append(" ( ");
        deal.append(comDate.dateToString(this.getDatetimeStart(),Constant.DATE_TIME_FORMAT));
        deal.append(" ~ ");
        deal.append(comDate.dateToString(this.getDatetimeFinish(),Constant.DATE_TIME_FORMAT));
        deal.append(" ; " );
        deal.append(this.getTotalHours() + " hours )" );
        return deal.toString();
    }
}
