package c2s.essp.timesheet.workscope;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoActivity extends DtoBase {
	
	public final static String NOT_STARTED = "Not Started";
	public final static String IN_PROGRESS = "In Progress"; 
	public final static String COMPLETED = "Completed"; 
	
    private Integer id;
    private Integer assignmentId;
    private String code;
    private String name;
    private Integer projectId;
    private Date PlannedFinishDate;
    private Date PlannedStartDate;
    private Date activityStart;
    private Date activityFinish;
    private String roleName;
    private String activityStatus;
    private boolean isPrimaryResource;
    private Long codeValueRid;
    private String codeValueName;
    private String codeShowName;
    private String wbsCode;
    private String wbsName;

    public String getWbsCode() {
		return wbsCode;
	}

	public void setWbsCode(String wbsCode) {
		this.wbsCode = wbsCode;
	}

	public String getWbsName() {
		return wbsName;
	}

	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}

	public String getCodeValueName() {
        return codeValueName;
    }

    public void setCodeValueName(String codeValueName) {
        this.codeValueName = codeValueName;
    }

    public Long getCodeValueRid() {
        return codeValueRid;
    }

    public void setCodeValueRid(Long codeValueRid) {
        this.codeValueRid = codeValueRid;
    }

    public boolean isPrimaryResource() {
		return isPrimaryResource;
	}

	public void setIsPrimaryResource(boolean isPrimaryResource) {
		this.isPrimaryResource = isPrimaryResource;
	}

	public Date getActivityFinish() {
		return activityFinish;
	}

	public void setActivityFinish(Date activityFinish) {
		this.activityFinish = activityFinish;
	}

	public Date getActivityStart() {
		return activityStart;
	}

	public void setActivityStart(Date activityStart) {
		this.activityStart = activityStart;
	}

	public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public Date getPlannedFinishDate() {
        return PlannedFinishDate;
    }

    public Date getPlannedStartDate() {
        return PlannedStartDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setPlannedStartDate(Date PlannedStartDate) {
        this.PlannedStartDate = PlannedStartDate;
    }

    public void setPlannedFinishDate(Date PlannedFinishDate) {
        this.PlannedFinishDate = PlannedFinishDate;
    }

    public String getShowName() {
        return getCode() + " -- " + getName();
    }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getActivityStatus() {
		return activityStatus;
	}
	
	public boolean isNotStart() {
		return NOT_STARTED.equals(this.getActivityStatus());
	}
	
	public boolean isInProgress() {
		return IN_PROGRESS.equals(this.getActivityStatus());
	}
	
	public boolean isCompleted() {
		return COMPLETED.equals(this.getActivityStatus());
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Integer getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getCodeShowName() {
		return codeShowName;
	}

	public void setCodeShowName(String codeShowName) {
		this.codeShowName = codeShowName;
	}
	
	public String getWbsShowName() {
		return getWbsCode() + " -- " + getWbsName();
	}

}
