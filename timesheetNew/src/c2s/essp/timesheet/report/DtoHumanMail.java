package c2s.essp.timesheet.report;

import c2s.dto.DtoBase;

public class DtoHumanMail extends DtoBase {
	
	private String beginDate;
	private String endDate;
	private Double standardHours;
	private Double actualHours;
	private Double normalHours;
	private Double overtimeHours;
	private Double leaveHours;
	private Double variantHours;

	public String getEndDate() {
		return endDate;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Double getStandardHours() {
		return standardHours;
	}
	public void setStandardHours(Double standardHours) {
		this.standardHours = standardHours;
	}
	public Double getActualHours() {
		return actualHours;
	}
	public void setActualHours(Double actualHours) {
		this.actualHours = actualHours;
	}
	public Double getNormalHours() {
		return normalHours;
	}
	public void setNormalHours(Double normalHours) {
		this.normalHours = normalHours;
	}
	public Double getOvertimeHours() {
		return overtimeHours;
	}
	public void setOvertimeHours(Double overtimeHours) {
		this.overtimeHours = overtimeHours;
	}
	public Double getLeaveHours() {
		return leaveHours;
	}
	public void setLeaveHours(Double leaveHours) {
		this.leaveHours = leaveHours;
	}
	public Double getVariantHours() {
		return variantHours;
	}
	public void setVariantHours(Double variantHours) {
		this.variantHours = variantHours;
	}

}
