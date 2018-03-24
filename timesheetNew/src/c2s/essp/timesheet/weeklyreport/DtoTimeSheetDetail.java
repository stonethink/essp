package c2s.essp.timesheet.weeklyreport;

import java.util.Date;
import java.util.HashMap;

import c2s.dto.DtoBase;
import c2s.dto.IDto;

public class DtoTimeSheetDetail extends DtoBase {

    public final static String DTO = "TS_DtoTimeSheetDetail_Dto";

    private Long rid;
    private Long codeValueRid;
    private Long tsRid;
    private Long accountRid;
    private String status;
    private String jobDescription;
    private Long activityId;
    private Long rsrcAssignmentId;
    private String activityName;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;
    private String accountName;
    private String codeValueName;
    private HashMap tsDays = new HashMap();
    //资源分配的计划开始和结束
    private Date actPlanStart;
    private Date actPlanFinish;
    //作业的开始和结束
    private Date activityStart;
    private Date activityFinish;
    private Boolean isLeaveType;

    // Property accessors

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getCodeValueRid() {

        return codeValueRid;
    }

    public void setCodeValueRid(Long codeValueRid) {

        this.codeValueRid = codeValueRid;
    }

    public Long getTsRid() {

        return tsRid;
    }

    public void setTsRid(Long tsRid) {

        this.tsRid = tsRid;
    }

    public Long getAccountRid() {
        return this.accountRid;
    }

    public void setAccountRid(Long accountRid) {
        this.accountRid = accountRid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobDescription() {
        return this.jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public String getRcu() {
        return this.rcu;
    }

    public void setRcu(String rcu) {
        this.rcu = rcu;
    }

    public String getRuu() {
        return this.ruu;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCodeValueName() {
        return codeValueName;
    }

    public HashMap getTsDays() {
        return tsDays;
    }

    public Date getActPlanStart() {
        return actPlanStart;
    }

    public Date getActPlanFinish() {
        return actPlanFinish;
    }

    public Boolean getIsLeaveType() {
        return isLeaveType;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setCodeValueName(String codeValueName) {
        this.codeValueName = codeValueName;
    }

    public void setTsDays(HashMap tsDays) {
        this.tsDays = tsDays;
    }

    public void setActPlanStart(Date actPlanStart) {
        this.actPlanStart = actPlanStart;
    }

    public void setActPlanFinish(Date actPlanFinish) {
        this.actPlanFinish = actPlanFinish;
    }

    public void setIsLeaveType(Boolean isLeaveType) {
        this.isLeaveType = isLeaveType;
    }

    public String getStatusName() {
        return DtoTimeSheet.statusCode2Name(getStatus());
    }

    public boolean editable() {
        return DtoTimeSheet.STATUS_ACTIVE.equals(getStatus())
                || DtoTimeSheet.STATUS_REJECTED.equals(getStatus());
    }


    /**
     * 修改或新增某一天的工时
     * @param date Date
     * @param wkHour Double
     * @param otHour Double
     */
    public void setHour(Date date, Double wkHour, Double otHour) {
        DtoTimeSheetDay dto = (DtoTimeSheetDay) tsDays.get(date);
        if(dto != null) {
        	if(dto.isInsert() == false) {
        		dto.setOp(IDto.OP_MODIFY);
        	}
            dto.setWorkHours(wkHour);
            dto.setOvertimeHours(otHour);
        } else {
            dto = new DtoTimeSheetDay();
            dto.setOp(IDto.OP_INSERT);
            dto.setTsDetailRid(getRid());
            dto.setWorkDate(date);
            dto.setWorkHours(wkHour);
            dto.setOvertimeHours(otHour);
            tsDays.put(date, dto);
        }
    }

    public DtoTimeSheetDay getHour(Date date) {
        return (DtoTimeSheetDay) tsDays.get(date);
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

	public Long getRsrcAssignmentId() {
		return rsrcAssignmentId;
	}

	public void setRsrcAssignmentId(Long rsrcAssignmentId) {
		this.rsrcAssignmentId = rsrcAssignmentId;
	}
}
