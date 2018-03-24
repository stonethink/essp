package c2s.essp.tc.weeklyreport;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoTcSearchCondition extends DtoBase{

    //by account
    private Long acntRid;

    //by OBS
    private String orgId;
    private String incSub;

    private Date beginPeriod;
    private Date endPeriod;
    private String users;
    private String noneStatus;
    private String lockedStatus;
    private String unLockedStatus;
    private String confirmedStatus;
    private String rejectedStatus;

    public final static int WEEK = 0;
    public final static int MONTH = 0;

    public Long getAcntRid() {
        return acntRid;
    }

    public Date getBeginPeriod() {
        return beginPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public String getUsers() {
        return users;
    }

    public boolean isIncSub() {
        return Boolean.valueOf(incSub).booleanValue();
    }

    public String getOrgId() {
        return orgId;
    }

    public  boolean  getNoneStatus() {
        return Boolean.valueOf(noneStatus).booleanValue();
    }

    public boolean getLockedStatus() {
        return Boolean.valueOf(lockedStatus).booleanValue();
    }

    public  boolean getUnLockedStatus() {
        return Boolean.valueOf(unLockedStatus).booleanValue();
    }

    public  boolean getConfirmedStatus() {
        return Boolean.valueOf(confirmedStatus).booleanValue();
    }

    public  boolean getRejectedStatus() {
        return Boolean.valueOf(rejectedStatus).booleanValue();
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public void setNoneStatus(String noneStatus) {
        this.noneStatus = noneStatus;
    }

    public void setLockedStatus(String lockedStatus) {

        this.lockedStatus = lockedStatus;
    }

    public void setUnLockedStatus(String unLockedStatus) {

        this.unLockedStatus = unLockedStatus;
    }

    public void setIncSub(String incSub) {
        this.incSub = incSub;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setConfirmedStatus(String confirmedStatus) {
        this.confirmedStatus = confirmedStatus;
    }

    public void setRejectedStatus(String rejectedStatus) {
        this.rejectedStatus = rejectedStatus;
    }


}
