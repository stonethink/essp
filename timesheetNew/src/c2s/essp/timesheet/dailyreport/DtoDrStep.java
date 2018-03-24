package c2s.essp.timesheet.dailyreport;

import c2s.dto.DtoBase;

public class DtoDrStep extends DtoBase {
	
	public static final String DTO_RESULT = "DtoResult";
	public static final String DTO_STEPS = "DtoSteps";
	public static final String DTO = "DtoStep";
	
	private Long rid;
	private Long taskId;
	private Long stepRid;
	private String taskName;
	private Long projId;
	private String projName;
	private Long codeValueRid;
	private String item;
	private Double workTime;
	private String resourceId;
	private Boolean isFinish;
	private Boolean isDBData;
	private Boolean isEditable;
	private String description;
	private Boolean isAssigned;
	private Long accountRid;
	

	public Long getAccountRid() {
		return accountRid;
	}
	public void setAccountRid(Long accountRid) {
		this.accountRid = accountRid;
	}
	public Boolean getIsAssigned() {
		return isAssigned;
	}
	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getStepRid() {
		return stepRid;
	}
	public void setStepRid(Long stepRid) {
		this.stepRid = stepRid;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getProjId() {
		return projId;
	}
	public void setProjId(Long projId) {
		this.projId = projId;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public Long getCodeValueRid() {
		return codeValueRid;
	}
	public void setCodeValueRid(Long codeValueRid) {
		this.codeValueRid = codeValueRid;
	}
	public Double getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Boolean getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}
	public Boolean getIsDBData() {
		return isDBData;
	}
	public void setIsDBData(Boolean isDBData) {
		this.isDBData = isDBData;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public Boolean getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
