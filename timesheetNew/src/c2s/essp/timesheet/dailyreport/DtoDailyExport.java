package c2s.essp.timesheet.dailyreport;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.essp.timesheet.report.ISumLevel;
import c2s.essp.timesheet.step.DtoStatus;

public class DtoDailyExport extends DtoBase {
	
	public static final String DTO_RESULTMAP = "DtoResultMap";
	public static final String DTO_ACNT_NAME = "DtoAcntName";
	public static final String DTO_STATUS_FINISH = "Finished";
	public static final String DTO_STATUS_NONFINISH = "Not Finished";
	public static final String DTO_YESTERDAY = "Y";
	public static final String DTO_TODAY = "T";
	
	private String accountName;
	private String activityName;
	private String codeValueName;
	private String item;
	private String resourceId;
	private String resourceName;
	private Date planStart;
	private Date planFinish;
	private Date actualStart;
	private Date actualFinish;
	private Double weight;
	private String status;
	private String completePcr;
	private Boolean isAcitvity;
	
	public Boolean getIsAcitvity() {
		return isAcitvity;
	}
	public void setIsAcitvity(Boolean isAcitvity) {
		this.isAcitvity = isAcitvity;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getCodeValueName() {
		return codeValueName;
	}
	public void setCodeValueName(String codeValueName) {
		this.codeValueName = codeValueName;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
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
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCompletePcr() {
		return completePcr;
	}
	public void setCompletePcr(String completePcr) {
		this.completePcr = completePcr;
	}
	public void setActualFinish(Date actualFinish) {
		this.actualFinish = actualFinish;
	}

	public String getStatusIndicator() {
		return DtoStatus.getStatus(planStart, planFinish, actualStart, actualFinish);
	}
}
