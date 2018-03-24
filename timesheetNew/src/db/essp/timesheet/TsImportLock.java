package db.essp.timesheet;

import java.util.Date;

public class TsImportLock implements java.io.Serializable {
	private Long rid;
	private Date beginDate;
    private Date endDate;
    private String status;
    private String lockScope;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;
    
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getLockScope() {
		return lockScope;
	}
	public void setLockScope(String lockScope) {
		this.lockScope = lockScope;
	}
	public Date getRct() {
		return rct;
	}
	public void setRct(Date rct) {
		this.rct = rct;
	}
	public String getRcu() {
		return rcu;
	}
	public void setRcu(String rcu) {
		this.rcu = rcu;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public Date getRut() {
		return rut;
	}
	public void setRut(Date rut) {
		this.rut = rut;
	}
	public String getRuu() {
		return ruu;
	}
	public void setRuu(String ruu) {
		this.ruu = ruu;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
