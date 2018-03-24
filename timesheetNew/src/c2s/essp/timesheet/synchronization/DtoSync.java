package c2s.essp.timesheet.synchronization;

import c2s.dto.DtoBase;

public class DtoSync extends DtoBase {
	
	private String projectId;
	private String employeeId;
	private boolean isDept;
	private String deptFlag;
	
	public String getDeptFlag() {
		return deptFlag;
	}
	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public boolean isDept() {
		if("1".equals(getDeptFlag())) {
			return true;
		}
		return false;
	}
	public void setDept(boolean isDept) {
		this.isDept = isDept;
	}

}
