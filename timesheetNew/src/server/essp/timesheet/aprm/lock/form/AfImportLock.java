package server.essp.timesheet.aprm.lock.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class AfImportLock  extends ActionForm {
	private String rid;
	private String beginDate;
    private String endDate;
    private String status;
    private String lockScope;
    
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getLockScope() {
		return lockScope;
	}
	public void setLockScope(String lockScope) {
		this.lockScope = lockScope;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}