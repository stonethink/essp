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
public class DtoTsGatherReport extends DtoBase implements ISumLevel{
    public final static String DTO_CONDITION = "Ts_DtoCondition";
    public final static String DTO_QUERY_RESULT = "Ts_DtoQueryResult";
    public final static String DTO_LEAVE = "1";
    public final static String DTO_NO_LEAVE = "0";
    public final static String DTO_DEPT = "1";
    public final static String DTO_PROJECT = "0";
    
    private String projectId;
    private String projectName;
    private String deptId;
    private Double normalWorkHours;
    private Double workHours;
    private Double leaveHours;
    private Double overTimeHours;
    private Double total;
    private Boolean isLeaveType;
    private int sumLevel;
    private Double estWorkHours;

    public String getDeptId() {
        return deptId;
    }

    public Double getEstWorkHours() {
        return estWorkHours;
    }

    public Boolean getIsLeaveType() {
        return isLeaveType;
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

    public Double getOverTimeHours() {
        return overTimeHours;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }


    public Double getTotal() {
        if(this.getSumLevel() == 1 || this.getSumLevel() == 2) {
           return total;
       }
        return workHours;

    }

    public Double getWorkHours() {
        return workHours;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setEstWorkHours(Double estWorkHours) {
        this.estWorkHours = estWorkHours;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
    }

    public void setLeaveHours(Double leaveHours) {
        this.leaveHours = leaveHours;
    }

    public void setNormalWorkHours(Double normalWorkHours) {
        this.normalWorkHours = normalWorkHours;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setOverTimeHours(Double overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

    public void setSumLevel(int sumLevel) {
        this.sumLevel = sumLevel;
    }

    public int getSumLevel() {
        return sumLevel;
    }


}
