package db.essp.timesheet;

import java.util.Date;


public class TsParameter implements java.io.Serializable {


    // Fields

    private Long rid;
    private Long hrDecimalCnt;
    private Long futureTsCnt;
    private Long pastTsCnt;
    private String tsApprovalLevel;
    private Long xferNostartDayCnt;
    private Long xferCompleteDayCnt;
    private Boolean futureTsHrsFlag;
    private Boolean nostartTaskHrsFlag;
    private Boolean completeTaskHrsFlag;
    private Boolean prestartTaskHrsFlag;
    private Boolean postendTaskHrsFlag;
    private Boolean genTsNeedApproval;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;
    private Long monthStartDay;
    private Long monthStartDayTwo;
    private String exportType;
    private String site;
    private String overtimeEffective;
    private String leaveEffective;

    // Property accessors

    public String getLeaveEffective() {
		return leaveEffective;
	}

	public void setLeaveEffective(String leaveEffective) {
		this.leaveEffective = leaveEffective;
	}

	public String getOvertimeEffective() {
		return overtimeEffective;
	}

	public void setOvertimeEffective(String overtimeEffective) {
		this.overtimeEffective = overtimeEffective;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public Long getMonthStartDayTwo() {
		return monthStartDayTwo;
	}

	public void setMonthStartDayTwo(Long monthStartDayTwo) {
		this.monthStartDayTwo = monthStartDayTwo;
	}

	public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getHrDecimalCnt() {
        return this.hrDecimalCnt;
    }

    public void setHrDecimalCnt(Long hrDecimalCnt) {
        this.hrDecimalCnt = hrDecimalCnt;
    }

    public Long getFutureTsCnt() {
        return this.futureTsCnt;
    }

    public void setFutureTsCnt(Long futureTsCnt) {
        this.futureTsCnt = futureTsCnt;
    }

    public Long getPastTsCnt() {
        return this.pastTsCnt;
    }

    public void setPastTsCnt(Long pastTsCnt) {
        this.pastTsCnt = pastTsCnt;
    }

    public String getTsApprovalLevel() {
        return this.tsApprovalLevel;
    }

    public void setTsApprovalLevel(String tsApprovalLevel) {
        this.tsApprovalLevel = tsApprovalLevel;
    }

    public Long getXferNostartDayCnt() {
        return this.xferNostartDayCnt;
    }

    public void setXferNostartDayCnt(Long xferNostartDayCnt) {
        this.xferNostartDayCnt = xferNostartDayCnt;
    }

    public Long getXferCompleteDayCnt() {
        return this.xferCompleteDayCnt;
    }

    public void setXferCompleteDayCnt(Long xferCompleteDayCnt) {
        this.xferCompleteDayCnt = xferCompleteDayCnt;
    }

    public Boolean getFutureTsHrsFlag() {
        return this.futureTsHrsFlag;
    }

    public void setFutureTsHrsFlag(Boolean futureTsHrsFlag) {
        this.futureTsHrsFlag = futureTsHrsFlag;
    }

    public Boolean getNostartTaskHrsFlag() {
        return this.nostartTaskHrsFlag;
    }

    public void setNostartTaskHrsFlag(Boolean nostartTaskHrsFlag) {
        this.nostartTaskHrsFlag = nostartTaskHrsFlag;
    }

    public Boolean getCompleteTaskHrsFlag() {
        return this.completeTaskHrsFlag;
    }

    public void setCompleteTaskHrsFlag(Boolean completeTaskHrsFlag) {
        this.completeTaskHrsFlag = completeTaskHrsFlag;
    }

    public Boolean getPrestartTaskHrsFlag() {
        return this.prestartTaskHrsFlag;
    }

    public void setPrestartTaskHrsFlag(Boolean prestartTaskHrsFlag) {
        this.prestartTaskHrsFlag = prestartTaskHrsFlag;
    }

    public Boolean getPostendTaskHrsFlag() {
        return this.postendTaskHrsFlag;
    }

    public void setPostendTaskHrsFlag(Boolean postendTaskHrsFlag) {
        this.postendTaskHrsFlag = postendTaskHrsFlag;
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

    public Long getMonthStartDay() {
        return monthStartDay;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setMonthStartDay(Long monthStartDay) {
        this.monthStartDay = monthStartDay;
    }

	public Boolean getGenTsNeedApproval() {
		return genTsNeedApproval;
	}

	public void setGenTsNeedApproval(Boolean genTsNeedApproval) {
		this.genTsNeedApproval = genTsNeedApproval;
	}
}
