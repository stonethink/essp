package db.essp.timesheet;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class TsTimesheetDetail implements java.io.Serializable {


    // Fields

     private Long rid;
     private TsTimesheetMaster tsTimesheetMaster;
     private Long codeValueRid;
     private Long tsRid;
     private Long accountRid;
     private String status;
     private String jobDescription;
     private Long activityId;
     private Long rsrcAssignmentId;
     private String activityName;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;
     private Set tsTimesheetDay = new HashSet(0);
     private String accountName;
     private String codeValueName;
     private Boolean isLeaveType;

    // Property accessors

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getCodeValueRid() {

        return codeValueRid;
    }

    public void setCodeValueRid(Long codeValueRid) {

        this.codeValueRid = codeValueRid;
    }

    public Long getTsRid() {

        return tsRid;
    }

    public void setTsRid(Long tsRid) {

        this.tsRid = tsRid;
    }

    public Long getAccountRid() {
        return this.accountRid;
    }

    public void setAccountRid(Long accountRid) {
        this.accountRid = accountRid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobDescription() {
        return this.jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public String getRcu() {
        return this.rcu;
    }

    public void setRcu(String rcu) {
        this.rcu = rcu;
    }

    public String getRuu() {
        return this.ruu;
    }

    public TsTimesheetMaster getTsTimesheetMaster() {
        return tsTimesheetMaster;
    }

    public Set getTsTimesheetDay() {
        return tsTimesheetDay;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCodeValueName() {
        return codeValueName;
    }

    public Boolean isIsLeaveType() {
        return isLeaveType;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setTsTimesheetMaster(TsTimesheetMaster tsTimesheetMaster) {
        this.tsTimesheetMaster = tsTimesheetMaster;
    }

    public void setTsTimesheetDay(Set tsTimesheetDay) {
        this.tsTimesheetDay = tsTimesheetDay;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setCodeValueName(String codeValueName) {
        this.codeValueName = codeValueName;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
    }

	public Long getRsrcAssignmentId() {
		return rsrcAssignmentId;
	}

	public void setRsrcAssignmentId(Long rsrcAssignmentId) {
		this.rsrcAssignmentId = rsrcAssignmentId;
	}
}
