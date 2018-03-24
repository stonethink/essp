package c2s.essp.timesheet.dailyreport;

import java.util.List;

import c2s.dto.DtoBase;

public class DtoDrActivity extends DtoBase {
	
	public static final String DTO = "Dto";
	public static final String DTO_ACTIVITYID = "DtoRid";
	public static final String DTO_ACTIVITY_LIST = "DtoActivityList";
	public static final String DTO_DAY = "DtoDay";
	public static final String DTO_ACCOUNTRID = "DtoAccountRid";
	
	private Long activityId;
    private String accountName;
    private Long codeValueRid;
    private String codeValueName;
    private String activityName;
	private Double workTime;
	private boolean isActivity = false;
	private List stepList;
	private Long accountRid;
	
	public Long getAccountRid() {
		return accountRid;
	}
	public void setAccountRid(Long accountRid) {
		this.accountRid = accountRid;
	}
	public List getStepList() {
		return stepList;
	}
	public void setStepList(List stepList) {
		this.stepList = stepList;
	}
	public boolean isActivity() {
		return isActivity;
	}
	public void setActivity(boolean isActivity) {
		this.isActivity = isActivity;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
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
	public Double getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
	public Long getCodeValueRid() {
		return codeValueRid;
	}
	public void setCodeValueRid(Long codeValueRid) {
		this.codeValueRid = codeValueRid;
	}

}
