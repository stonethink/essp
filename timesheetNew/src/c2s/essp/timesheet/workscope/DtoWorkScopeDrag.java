package c2s.essp.timesheet.workscope;

import java.util.Date;

import c2s.dto.DtoBase;

/**
 * <p>Title: DtoWorkScopeDrag</p>
 *
 * <p>Description: Work Scope��Weekly Report�Ϸŵ�����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DtoWorkScopeDrag extends DtoBase {

    private Long accountRid;
    private Long codeValueRid;
    private Long activityId;
    private Long assignmentId;
    private String accountName;
    private String codeValueName;
    private String activityName;
    //��Դ����ļƻ���ʼ�ͽ���
    private Date plannedFinishDate;
    private Date plannedStartDate;
    //��ҵ�Ŀ�ʼ�ͽ���
    private Date activityFinishDate;
    private Date activityStartDate;
    private boolean isActivity = false;
    private Boolean isLeaveType;

    public Long getAccountRid() {

        return accountRid;
    }

    public Long getCodeValueRid() {

        return codeValueRid;
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getAccountName() {

        return accountName;
    }

    public String getCodeValueName() {

        return codeValueName;
    }

    public String getActivityName() {
        return activityName;
    }

    public Boolean getIsLeaveType() {
        return isLeaveType;
    }

    public Date getPlannedStartDate() {
        return plannedStartDate;
    }

    public Date getPlannedFinishDate() {
        return plannedFinishDate;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void setAccountRid(Long accountRid) {

        this.accountRid = accountRid;
    }

    public void setCodeValueRid(Long codeValueRid) {

        this.codeValueRid = codeValueRid;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setAccountName(String accountName) {

        this.accountName = accountName;
    }

    public void setCodeValueName(String codeValueName) {

        this.codeValueName = codeValueName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setIsActivity(boolean isActivity) {
        this.isActivity = isActivity;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
    }

    public void setPlannedFinishDate(Date plannedFinishDate) {
        this.plannedFinishDate = plannedFinishDate;
    }

    public void setPlannedStartDate(Date plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

	public Date getActivityFinishDate() {
		return activityFinishDate;
	}

	public void setActivityFinishDate(Date activityFinishDate) {
		this.activityFinishDate = activityFinishDate;
	}

	public Date getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

}
