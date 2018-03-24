package c2s.essp.timesheet.step.management;

import java.util.Date;

import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.step.DtoStatus;
import c2s.essp.timesheet.step.IDtoActivityFilterData;

public class DtoActivityForStep extends DtoStepBase implements IDtoActivityFilterData {
	public final static String KEY_ACTIVITY_LIST_FOR_STEP = "ActivityListForStep";
	public final static String KEY_DTO = "ActivityListForStep_DTO";
	private Integer id;
	private String code;
	private String name;
	private String wbsCode;
	private String wbsName;
	private Integer projectId;
	private String projectCode;
	private Date planStart;
	private Date planFinish;
	private Date actualStart;
	private Date actualFinish;
	private String managerId;
	private String manager;
	private String resourceIds;
	private String resources;
	private Float planDuration;
	private Double planHour;
	private String status;
	private Date estimatefinish;
	private Date earliestStart;
	private Date atLatestStart;
	private Date earliestFinish;
	private Date atLatestFinish;
	private String statusIndicator;
	private String functionId;
	private String stepType;
	private String sponsor;
	private boolean readonly=false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getPlanStart() {
		return planStart;
	}

	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}

	public Date getPlanFinish() {
		return planFinish;
	}

	public void setPlanFinish(Date planFinish) {
		this.planFinish = planFinish;
	}

	public Date getActualStart() {
		return actualStart;
	}

	public void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}

	public Date getActualFinish() {
		return actualFinish;
	}

	public void setActualFinish(Date actualFinish) {
		this.actualFinish = actualFinish;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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

	public Date getEstimatefinish() {
		return estimatefinish;
	}

	public void setEstimatefinish(Date estimatefinish) {
		this.estimatefinish = estimatefinish;
	}

	public Date getEarliestStart() {
		return earliestStart;
	}

	public void setEarliestStart(Date earliestStart) {
		this.earliestStart = earliestStart;
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

	public Date getAtLatestFinish() {
		return atLatestFinish;
	}

	public void setAtLatestFinish(Date atLatestFinish) {
		this.atLatestFinish = atLatestFinish;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getStatusIndicator() {
		return DtoStatus.getStatus(planStart, planFinish, actualStart, actualFinish);
	}

	public void setStatusIndicator(String statusIndicator) {
		this.statusIndicator = statusIndicator;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return Returns the projectId.
	 */
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId The projectId to set.
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return Returns the projectCode.
	 */
	public String getProjectCode() {
		return projectCode;
	}

	/**
	 * @param projectCode The projectCode to set.
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * @return Returns the funtionId.
	 */
	public String getFunctionId() {
		return functionId;
	}

	/**
	 * @param funtionId The funtionId to set.
	 */
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	/**
	 * @return Returns the sponsor.
	 */
	public String getSponsor() {
		return sponsor;
	}

	/**
	 * @param sponsor The sponsor to set.
	 */
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	/**
	 * @return Returns the stepType.
	 */
	public String getStepType() {
		return stepType;
	}

	/**
	 * @param stepType The stepType to set.
	 */
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	/**
	 * @return Returns the planHour.
	 */
	public Double getPlanHour() {
		return planHour;
	}

	/**
	 * @param planHour The planHour to set.
	 */
	public void setPlanHour(Double planHour) {
		this.planHour = planHour;
	}

}
