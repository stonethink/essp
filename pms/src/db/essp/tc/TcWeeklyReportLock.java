package db.essp.tc;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class TcWeeklyReportLock implements Serializable {

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
    private String isLocked;

    /** full constructor */
    public TcWeeklyReportLock(Long rid, String userId, Long acntRid, Date beginPeriod, Date endPeriod, String isLocked) {
        this.rid = rid;
        this.userId = userId;
        this.acntRid = acntRid;
        this.beginPeriod = beginPeriod;
        this.endPeriod = endPeriod;
        this.isLocked = isLocked;
    }

    /** default constructor */
    public TcWeeklyReportLock() {
    }

    /** minimal constructor */
    public TcWeeklyReportLock(Long rid) {
        this.rid = rid;
    }


    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public Long getRid() {
        return rid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

}
