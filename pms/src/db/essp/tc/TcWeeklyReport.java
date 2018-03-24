package db.essp.tc;

import db.essp.code.CodeValue;
import db.essp.pms.Account;
import db.essp.pms.Activity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TcWeeklyReport implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String userId;

    /** nullable persistent field */
    private Long acntRid;

    /** nullable persistent field */
    private Long activityRid;

    /** nullable persistent field */
    private Long codeValueRid;

    /** nullable persistent field */
    private String isActivity;

    /** nullable persistent field */
    private Date beginPeriod;

    /** nullable persistent field */
    private Date endPeriod;

    /** nullable persistent field */
    private String jobDescription;

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
    private String isLocked;

    /** nullable persistent field */
    private String confirmStatus;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Account account;

    /** nullable persistent field */
    private Activity activity;

    /** nullable persistent field */
    private CodeValue codeValue;

    /** full constructor */
    public TcWeeklyReport(Long rid, String userId, Long acntRid, Long activityRid, Long codeValueRid, String isActivity, Date beginPeriod, Date endPeriod, String jobDescription, BigDecimal satHour, BigDecimal sunHour, BigDecimal monHour, BigDecimal tueHour, BigDecimal wedHour, BigDecimal thuHour, BigDecimal friHour, String isLocked, String confirmStatus, String comments, Date rct, Date rut, String rst, Account account, Activity activity, CodeValue codeValue) {
        this.rid = rid;
        this.userId = userId;
        this.acntRid = acntRid;
        this.activityRid = activityRid;
        this.codeValueRid = codeValueRid;
        this.isActivity = isActivity;
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
        this.jobDescription = jobDescription;
        this.satHour = satHour;
        this.sunHour = sunHour;
        this.monHour = monHour;
        this.tueHour = tueHour;
        this.wedHour = wedHour;
        this.thuHour = thuHour;
        this.friHour = friHour;
        this.isLocked = isLocked;
        this.confirmStatus = confirmStatus;
        this.comments = comments;
        this.rct = rct;
        this.rut = rut;
        this.rst = rst;
        this.account = account;
        this.activity = activity;
        this.codeValue = codeValue;
    }

    /** default constructor */
    public TcWeeklyReport() {
    }

    /** minimal constructor */
    public TcWeeklyReport(Long rid) {
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

    public Long getActivityRid() {
        return this.activityRid;
    }

    public void setActivityRid(Long activityRid) {
        this.activityRid = activityRid;
    }

    public Long getCodeValueRid() {
        return this.codeValueRid;
    }

    public void setCodeValueRid(Long codeValueRid) {
        this.codeValueRid = codeValueRid;
    }

    public String getIsActivity() {
        return this.isActivity;
    }

    public void setIsActivity(String isActivity) {
        this.isActivity = isActivity;
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

    public String getJobDescription() {
        return this.jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
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

    public String getIsLocked() {
        return this.isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public String getConfirmStatus() {
        return this.confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public CodeValue getCodeValue() {
        return this.codeValue;
    }

    public void setCodeValue(CodeValue codeValue) {
        this.codeValue = codeValue;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
