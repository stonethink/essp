/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoSalaryApportion extends DtoBase{
      private String compId;
      private String empId;
      private String empName;
      private String prjName;
      private String prjCode;
      private String phaseCode;
      private int year;
      private int month;
      private Double workHours;
      private Double leaveHours;
      private Double overtimeHours;
      private Date beginDate;
      private Date endDate;
      private String achieveBelong;
      
    public Date getBeginDate() {
        return beginDate;
    }
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }
    public String getCompId() {
        return compId;
    }
    public void setCompId(String compId) {
        this.compId = compId;
    }
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Double getLeaveHours() {
        return leaveHours;
    }
    public void setLeaveHours(Double leaveHours) {
        this.leaveHours = leaveHours;
    }

    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public Double getOvertimeHours() {
        return overtimeHours;
    }
    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }
    public String getPhaseCode() {
        return phaseCode;
    }
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    public String getPrjCode() {
        return prjCode;
    }
    public void setPrjCode(String prjCode) {
        this.prjCode = prjCode;
    }
    public Double getWorkHours() {
        return workHours;
    }
    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public String getPrjName() {
        return prjName;
    }
    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }
	public String getAchieveBelong() {
		return achieveBelong;
	}
	public String getShowAchieveBelong() {
		if(achieveBelong != null && achieveBelong.length() >= 2) {
			return achieveBelong.substring(0, 2);
		} else {
			return "";
		}
	}
	public void setAchieveBelong(String achieveBelong) {
		this.achieveBelong = achieveBelong;
	}
}
