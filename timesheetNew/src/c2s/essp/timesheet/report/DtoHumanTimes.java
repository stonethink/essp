package c2s.essp.timesheet.report;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.DtoBase;

public class DtoHumanTimes extends DtoBase implements ISumLevel{
	
	public static final String DTO_QUERY_LIST = "DtoQueryList";
	public static final String DTO_SEND_LIST = "DtoSendList";

	private String unitCode;
	private String unitName;
	private String empId;
	private String empName;
	private String empProperty;
	private Date beginDate;
	private Date endDate;
	private Double actualHours;
	private Double overtimeHours;
	private Double standarHours;
	private Double leaveHours;
	private int sumLevel;
	private String sumBalance;
	private boolean checked;
	public boolean isBalanced() {
		if(this.getBalance().doubleValue() != 0) {
			return true;
		}
		return false;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getSumBalance() {
		if(sumLevel == 0) {
			return ""+this.getBalance();
		}
		return sumBalance;
	}
	public void setSumBalance(String sumBalance) {
		this.sumBalance = sumBalance;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getActualHours() {
		return actualHours;
	}
	public void setActualHours(Double actualHours) {
		this.actualHours = actualHours;
	}
	public Double getOvertimeHours() {
		return overtimeHours;
	}
	public void setOvertimeHours(Double overtimeHours) {
		this.overtimeHours = overtimeHours;
	}
	public Double getNormalHours() {
		return this.actualHours-this.overtimeHours;
	}
	public Double getStandarHours() {
		return standarHours;
	}
	public void setStandarHours(Double standarHours) {
		this.standarHours = standarHours;
	}
	public Double getLeaveHours() {
		return leaveHours;
	}
	public void setLeaveHours(Double leaveHours) {
		this.leaveHours = leaveHours;
	}
	public BigDecimal getBalance() {
		BigDecimal balanceD = new BigDecimal((this.getNormalHours() + this.leaveHours) - this.standarHours);
		balanceD = balanceD.setScale(2, BigDecimal.ROUND_HALF_UP);
		return balanceD;
	}
	
	public int getSumLevel() {
		return sumLevel;
	}
	public void setSumLevel(int sumLevel) {
		this.sumLevel = sumLevel;
	}
	public String getEmpProperty() {
		return empProperty;
	}
	public void setEmpProperty(String empProperty) {
		this.empProperty = empProperty;
	}
	
}
