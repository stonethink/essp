package c2s.essp.timesheet.step.management;

import java.util.Date;

import c2s.essp.timesheet.step.DtoStatus;

public class DtoActivityAndStep {
	
	public final static String ACTIVITY_LIST="ActivityList";
	
	private String category;
	private String name;
	private String wbsCode;
	private String wbsName;
	private String managerId;
	private String manager;
	private Date planStart;
	private Date planFinish;
	private Date actualStart;
	private Date actualFinish;
	private String resourceIds;
	private String resources;
	private String status;
	private String function;
	private Boolean isCorp=new Boolean(false);
	private Double procWt;	
	private Double actualCostTime;	
	private Float planDuration;		
	private Date earliestStart;
	private Date atLatestStart;
	private Date earliestFinish;
	private Date atLatestFinish;
	private Date estimatefinish;
	private short colorIndex;

	public short getColorIndex() {
		return colorIndex;
	}
	public void setColorIndex(short colorIndex) {
		this.colorIndex = colorIndex;
	}
	public String getStatusIndicator() {
		return DtoStatus.getStatus(planStart, planFinish, actualStart, actualFinish);
	}
	public Date getAtLatestFinish() {
		return atLatestFinish;
	}
	public void setAtLatestFinish(Date atLatestFinish) {
		this.atLatestFinish = atLatestFinish;
	}
	public Date getAtLatestStart() {
		return atLatestStart;
	}
	public void setAtLatestStart(Date atLatestStart) {
		this.atLatestStart = atLatestStart;
	}
	public Date getEarliestFinish() {
		return earliestFinish;
	}
	public void setEarliestFinish(Date earliestFinish) {
		this.earliestFinish = earliestFinish;
	}
	public Date getEarliestStart() {
		return earliestStart;
	}
	public void setEarliestStart(Date earliestStart) {
		this.earliestStart = earliestStart;
	}
	public Date getEstimatefinish() {
		return estimatefinish;
	}
	public void setEstimatefinish(Date estimatefinish) {
		this.estimatefinish = estimatefinish;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPlanDuration() {
		return planDuration;
	}
	public void setPlanDuration(Float planDuration) {
		this.planDuration = planDuration;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public Double getActualCostTime() {
		return actualCostTime;
	}
	public void setActualCostTime(Double actualCostTime) {
		this.actualCostTime = actualCostTime;
	}
	public Date getActualFinish() {
		return actualFinish;
	}
	public void setActualFinish(Date actualFinish) {
		this.actualFinish = actualFinish;
	}
	public Date getActualStart() {
		return actualStart;
	}
	public void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public Boolean getIsCorp() {
		return isCorp;
	}
	public void setIsCorp(Boolean isCorp) {
		this.isCorp = isCorp;
	}
	public Date getPlanFinish() {
		return planFinish;
	}
	public void setPlanFinish(Date planFinish) {
		this.planFinish = planFinish;
	}
	public Date getPlanStart() {
		return planStart;
	}
	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}

	public Double getProcWt() {
		return procWt;
	}
	public void setProcWt(Double procWt) {
		this.procWt = procWt;
	}

	public String getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	public String getResources() {
		return resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	} 

}
