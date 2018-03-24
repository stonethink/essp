package itf.webservices;

public class EmployeeBase {
	
	private String empId;
	private String empName;
	private String deptId;
	private String inJobDate;
	private String quitDate;
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getInJobDate() {
		return inJobDate;
	}
	public void setInJobDate(String inJobDate) {
		this.inJobDate = inJobDate;
	}
	public String getQuitDate() {
		return quitDate;
	}
	public void setQuitDate(String quitDate) {
		this.quitDate = quitDate;
	}
	
}
