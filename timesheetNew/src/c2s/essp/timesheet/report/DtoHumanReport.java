package c2s.essp.timesheet.report;

import java.util.*;

import c2s.dto.DtoBase;

public class DtoHumanReport extends DtoBase implements ISumLevel{
	
	public static final String DTO_BEGIN = "DtoBegin";
	public static final String DTO_END= "DtoEnd";
	public static final String DTO_SITE= "DtoSite";
	public static final String DTO_QUERY_RESULT = "DtoQueryResult";
	public static final String DTO_IS_PMO = "Ts_DtoIsPmo";
	public static final String DTO_SITESLIST= "DtoSitesList";
	public static final String DTO_NAME_TYPE_DIRECT = "D";
	public static final String DTO_NAME_TYPE_INDIRECT = "I";
	public static final String DTO_NAME_PROPERTY_P = "Permanent";
	public static final String DTO_NAME_PROPERTY_C = "Contract";
	public static final String DTO_NAME_PROPERTY_S = "实习生";
	public static final String DTO_NAME_PROPERTY_B = "采购";
	public static final String DTO_NAME_TYPE_PROJECT = "0";
	public static final String DTO_NAME_TYPE_DEPT = "1";
	
	private static Map<String, String> nameMap;
	private static Map<String, String> typeMap;
	private String projectId;
	private String projectName;
	private String projectType;
	private String deptId;
	private String deptName;
	private String empId;
	private String empName;
	private String empType;
	private String empProperty;
	private Date beginDate;
	private Date endDate;
	private Double actualHours;
	private Double overtimeHours;
	private Double assignedHours;
	private int sumLevel;
	private String queryFlag;
	
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	public int getSumLevel() {
		return sumLevel;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
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
	public Double getAssignedHours() {
		return assignedHours;
	}
	public void setAssignedHours(Double assignedHours) {
		this.assignedHours = assignedHours;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setSumLevel(int sumLevel) {
		this.sumLevel = sumLevel;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpProperty() {
		return empProperty;
	}
	public void setEmpProperty(String empProperty) {
		this.empProperty = empProperty;
	}
	public static String e2c(String eName) {
	    return nameMap.get(eName);
	}
	public static String codeToName(String code) {
		return typeMap.get(code);
	}
	 static {
		 nameMap = new HashMap();
		 typeMap = new HashMap();
		 nameMap.put(DTO_NAME_TYPE_DIRECT, "Direct");
		 nameMap.put(DTO_NAME_TYPE_INDIRECT, "Indirect");
		 nameMap.put(DTO_NAME_PROPERTY_P, "专职");
		 nameMap.put(DTO_NAME_PROPERTY_C, "兼职");
		 nameMap.put(DTO_NAME_PROPERTY_S, "实习生");
		 nameMap.put(DTO_NAME_PROPERTY_B, "采购");
		 typeMap.put(DTO_NAME_TYPE_PROJECT, "项目");
		 typeMap.put(DTO_NAME_TYPE_DEPT, "部门");
	    }
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public Double getNormalHours() {
		return this.actualHours-this.overtimeHours;
	}

	public Double getOvertimeHours() {
		return overtimeHours;
	}
	public void setOvertimeHours(Double overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

}
