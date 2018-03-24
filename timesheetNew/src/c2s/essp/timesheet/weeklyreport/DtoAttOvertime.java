package c2s.essp.timesheet.weeklyreport;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoAttOvertime extends DtoBase {

	private String employeeId;

	private Date overtimeDate;

	private Double hours = 0D;

	private Double actualHours = 0D;

	private String tipText;

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Date getOvertimeDate() {
		return this.overtimeDate;
	}

	public void setOvertimeDate(Date overtimeDate) {
		this.overtimeDate = overtimeDate;
	}

	public Double getHours() {
		return this.hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public Double getActualHours() {
		return this.actualHours;
	}

	public void setActualHours(Double actualHours) {
		this.actualHours = actualHours;
	}

	public String getTipText() {
		return tipText;
	}

	public void setTipText(String tipText) {
		this.tipText = tipText;
	}

	public String getShowHours() {
		Double ah = this.getActualHours();
		Double h = this.getHours();
		if (ah == null || "".equals(ah) || ah.equals(0) || ah.equals(h)) {
			return h.toString();
		} else {
			return ah + "/" + h;
		}
	}
	public String toString() {
		return getShowHours();
	}
}
