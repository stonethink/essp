package db.essp.tc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import db.essp.pms.Account;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TcByWorkerAccount implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String userId;

    /** nullable persistent field */
    private Long acntRid;

    /** nullable persistent field */
    private Date beginPeriod;

    /** nullable persistent field */
    private Date endPeriod;

    /** nullable persistent field */
    private BigDecimal satHour;

    /** nullable persistent field */
    private BigDecimal sunHour;

    /** nullable persistent field */
    private BigDecimal monHour;

    /** nullable persistent field */
    private BigDecimal tueHour;

    /** nullable persistent field */
    private BigDecimal wedHour;

    /** nullable persistent field */
    private BigDecimal thuHour;

    /** nullable persistent field */
    private BigDecimal friHour;

    /** nullable persistent field */
    private BigDecimal overtimeHour;

    private String confirmStatus;

    private String comments;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Account account;


    /** full constructor */
    public TcByWorkerAccount(Long rid, String userId, Long acntRid, Date beginPeriod, Date endPeriod, BigDecimal satHour, BigDecimal sunHour, BigDecimal monHour, BigDecimal tueHour, BigDecimal wedHour, BigDecimal thuHour, BigDecimal friHour, BigDecimal overtimeHour, Date rct, Date rut, String rst, Account account) {
        this.rid = rid;
        this.userId = userId;
        this.acntRid = acntRid;
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
        this.satHour = satHour;
        this.sunHour = sunHour;
        this.monHour = monHour;
        this.tueHour = tueHour;
        this.wedHour = wedHour;
        this.thuHour = thuHour;
        this.friHour = friHour;
        this.overtimeHour = overtimeHour;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
        this.account = account;
    }

    /** default constructor */
    public TcByWorkerAccount() {
    }

    /** minimal constructor */
    public TcByWorkerAccount(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public Date getBeginPeriod() {
        return this.beginPeriod;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public Date getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public BigDecimal getSatHour() {
        return this.satHour;
    }

    public void setSatHour(BigDecimal satHour) {
        this.satHour = satHour;
    }

    public BigDecimal getSunHour() {
        return this.sunHour;
    }

    public void setSunHour(BigDecimal sunHour) {
        this.sunHour = sunHour;
    }

    public BigDecimal getMonHour() {
        return this.monHour;
    }

    public void setMonHour(BigDecimal monHour) {
        this.monHour = monHour;
    }

    public BigDecimal getTueHour() {
        return this.tueHour;
    }

    public void setTueHour(BigDecimal tueHour) {
        this.tueHour = tueHour;
    }

    public BigDecimal getWedHour() {
        return this.wedHour;
    }

    public void setWedHour(BigDecimal wedHour) {
        this.wedHour = wedHour;
    }

    public BigDecimal getThuHour() {
        return this.thuHour;
    }

    public void setThuHour(BigDecimal thuHour) {
        this.thuHour = thuHour;
    }

    public BigDecimal getFriHour() {
        return this.friHour;
    }

    public void setFriHour(BigDecimal friHour) {
        this.friHour = friHour;
    }

    public BigDecimal getOvertimeHour() {
        return this.overtimeHour;
    }

    public void setOvertimeHour(BigDecimal overtimeHour) {
        this.overtimeHour = overtimeHour;
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

    public String getRst() {
        return this.rst;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public Account getAccount() {
        return account;
    }

    public String getComments() {
        return comments;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
