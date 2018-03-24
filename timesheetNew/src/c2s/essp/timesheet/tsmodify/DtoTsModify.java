package c2s.essp.timesheet.tsmodify;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;

public class DtoTsModify extends DtoBase {
	
	public static final String DTO_CONDITION = "DtoCondition";
	public static final String DTO_QUERY_RESULT = "DtoQueryResult";
	
	private String loginId;
	private Date startDate;
	private Date finishDate;
	private Double standarHours;
	private Double actualHours;
	private Double overtimeHours;
	private Double leaveHours;
	private String status;
	
	public static String statusCode2Name(String statusCode) {
	       return DtoTimeSheet.statusCode2Name(statusCode);
	   }
	   public String getStatusName() {
	          return statusCode2Name(getStatus());
	    }
	public Double getActualHours() {
		return actualHours;
	}
	public void setActualHours(Double actualHours) {
		this.actualHours = actualHours;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Double getLeaveHours() {
		return leaveHours;
	}
	public void setLeaveHours(Double leaveHours) {
		this.leaveHours = leaveHours;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public Double getOvertimeHours() {
		return overtimeHours;
	}
	public void setOvertimeHours(Double overtimeHours) {
		this.overtimeHours = overtimeHours;
	}
	public Double getStandarHours() {
		return standarHours;
	}
	public void setStandarHours(Double standarHours) {
		this.standarHours = standarHours;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
