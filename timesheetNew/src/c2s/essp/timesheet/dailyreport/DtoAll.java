package c2s.essp.timesheet.dailyreport;

import c2s.dto.DtoBase;

public class DtoAll extends DtoBase {
	
	public static final String DTO_RESULTS = "DtoResults";
	
    private String accountName;
    private String codeValueName;
    private String activityName;
	private Double activityWorkTime;
	private String taskName;
	private String projName;
	private String item;
	private Double stepWorkTime;
	private Boolean isFinish;
	public Boolean getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getCodeValueName() {
		return codeValueName;
	}
	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Double getActivityWorkTime() {
		return activityWorkTime;
	}
	public void setActivityWorkTime(Double activityWorkTime) {
		this.activityWorkTime = activityWorkTime;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getStepWorkTime() {
		return stepWorkTime;
	}
	public void setStepWorkTime(Double stepWorkTime) {
		this.stepWorkTime = stepWorkTime;
	}


}
