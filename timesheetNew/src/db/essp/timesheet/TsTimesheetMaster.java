package db.essp.timesheet;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class TsTimesheetMaster implements java.io.Serializable {


    // Fields

    private Long rid;
    private String status;
    private Date statusDate;
    private String loginId;
    private Date beginDate;
    private Date endDate;
    private Long tsId;
    private String notes;
    private String reason;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;
    private Set tsTimesheetDetail = new HashSet(0);;

    // Property accessors

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return this.statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Date getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getTsId() {
        return this.tsId;
    }

    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Set getTsTimesheetDetail() {
        return tsTimesheetDetail;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setTsTimesheetDetail(Set tsTimesheetDetail) {
        this.tsTimesheetDetail = tsTimesheetDetail;
    }

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
