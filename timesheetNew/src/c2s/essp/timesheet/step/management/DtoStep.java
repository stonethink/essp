package c2s.essp.timesheet.step.management;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.step.DtoStatus;

public class DtoStep extends DtoStepBase {

	public final static String KEY_STEP_LIST = "StepList";
	public final static String KEY_SAVE_STEP_LIST = "SaveStepList";
	public final static String KEY_UPDATE_STEP_LIST = "UpdateStepList";
	public final static String KEY_DELETE_STEP_LIST = "DeleteStepList";
	private Long rid;
	private Long stepTRid;
	private Long procId;
	private Long taskId;
	private Long seqNum;
	private Long projId;
	private Boolean completeFlag = new Boolean(false);
	private String procName;
	private Double procWt;
	private Double completePct;
	private String procDescr;
	private Date planStart;
	private Date planFinish;
	private Date actualStart;
	private Date actualFinish;
	private Double actualCostTime;
	private String resourceId;
	private String resourceName;
	private String category;
	private Boolean isCorp = new Boolean(false);
	private String function;
	private String status;

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Long getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public Double getProcWt() {
		return procWt;
	}

	public void setProcWt(Double procWt) {
		this.procWt = procWt;
	}

	public Double getCompletePct() {
		return completePct;
	}

	public void setCompletePct(Double completePct) {
		this.completePct = completePct;
	}

	public String getProcDescr() {
		return procDescr;
	}

	public void setProcDescr(String procDescr) {
		this.procDescr = procDescr;
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

	public Double getActualCostTime() {
		return actualCostTime;
	}

	public void setActualCostTime(Double actualCostTime) {
		this.actualCostTime = actualCostTime;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getCompleteFlag() {
		return completeFlag;
	}

	public void setCompleteFlag(Boolean completeFlag) {
		this.completeFlag = completeFlag;
	}

	public Boolean getIsCorp() {
		return isCorp;
	}

	public void setIsCorp(Boolean isCorp) {
		this.isCorp = isCorp;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Long getProcId() {
		return procId;
	}

	public void setProcId(Long procId) {
		this.procId = procId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return DtoStatus.getStatus(planStart, planFinish, actualStart, actualFinish);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStepTRid() {
		return stepTRid;
	}

	public void setStepTRid(Long stepTRid) {
		this.stepTRid = stepTRid;
	}



}
