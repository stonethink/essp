package c2s.essp.timesheet.report;

import c2s.dto.DtoBase;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class DtoTsDetailReport extends DtoBase implements ISumLevel{

    public final static String DTO_QUERY_TYPE = "Ts_DtoQueryType";
    public final static String DTO_CONDITION = "Ts_DtoCondition";
    public final static String DTO_QUERY_RESULT = "Ts_DtoQueryResult";
    public final static String DTO_PROJECT_LIST = "Ts_DtoProjectList";
    public final static String DTO_DEPT_LIST = "Ts_DtoDeptList";
    public final static String DTO_PERSON_LIST = "Ts_DtoPersonList";
    public final static String DTO_RELATION_MAP = "Ts_DtoRelationMap";
    public final static String DTO_DEPT_ID = "Ts_DtoDeptId";
    public final static String DTO_IS_PMO = "Ts_DtoIsPmo";

    private String workDate;
    private String projectId;
    private String projectName;
    private String deptId;
    private String loginId;
    private String name;
    private String jobCode;
    private String jobCodeDesc;
    private String jobDesc;
    private Double normalWorkHours;
    private Double workHours;
    private Double leaveHours;
    private Double overTimeHours;
    private Double total;
    private Boolean isLeaveType;
    private int sumLevel;


    public int getSumLevel() {
        return sumLevel;
    }

    public String getJobCode() {
        return jobCode;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }
    public Double getLeaveHours() {
        if(this.getSumLevel() == 1 || this.getSumLevel() == 2) {
            return leaveHours;
        }
        if(this.getIsLeaveType()) {
            return workHours;
        } else {
            return new Double(0);
        }
    }

    public Double getOverTimeHours() {
        return overTimeHours;
    }

    public Double getWorkHours() {
        return workHours;
    }

    public Double getTotal() {
        if(this.getSumLevel() == 1 || this.getSumLevel() == 2) {
           return total;
       }
        return workHours;
    }

    public Boolean getIsLeaveType() {
        return isLeaveType;
    }

    public String getWorkDate() {
        return workDate;
    }

    public Double getNormalWorkHours() {
        if(this.getSumLevel() == 1 || this.getSumLevel() == 2) {
            return normalWorkHours;
        }
        if(!this.getIsLeaveType()) {
            if(this.getOverTimeHours()==null){
                return workHours;
            } else {
                return workHours-this.getOverTimeHours();
            }
        } else {
            return new Double(0);
        }

    }

    public void setSumLevel(int sumLevel) {
        this.sumLevel = sumLevel;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setLeaveHours(Double leaveHours) {
        this.leaveHours = leaveHours;
    }

    public void setOverTimeHours(Double overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public void setNormalWorkHours(Double normalWorkHours) {
        this.normalWorkHours = normalWorkHours;
    }

	public String getJobCodeDesc() {
		return jobCodeDesc;
	}

	public void setJobCodeDesc(String jobCodeDesc) {
		this.jobCodeDesc = jobCodeDesc;
	}

}
