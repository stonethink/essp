package db.essp.timesheet;

import java.util.Date;

public class TsAccount implements java.io.Serializable {


    // Fields
     private Long rid;
     private Long codeTypeRid;
     private Long leaveCodeTypeRid;
     private String accountId;
     private String accountName;
     private Date plannedStart;
     private Date plannedFinish;
     private String manager;
     private Date actualStart;
     private Date actualFinish;
     private String accountBrief;
     private String accountStatus;
     private String accountType;
     private String orgCode;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;
     private String deptFlag;
     private Double estWorkHours;
     private String parentId;
     private String billable;
     private String primaveraAdapted;
     private String achieveBelong;
     private Long methodRid;
     private Boolean isMail;
     private String p6Id;

    // Property accessors

    public String getP6Id() {
    	if((p6Id == null || "".equals(p6Id))) {
    		return accountId;
    	} else {
    		return p6Id;
    	}
	}

	public void setP6Id(String id) {
		p6Id = id;
	}

	public Boolean getIsMail() {
		return isMail;
	}

	public void setIsMail(Boolean isMail) {
		this.isMail = isMail;
	}

	public Long getMethodRid() {
		return methodRid;
	}

	public void setMethodRid(Long methodRid) {
		this.methodRid = methodRid;
	}

	public String getAchieveBelong() {
		return achieveBelong;
	}

	public void setAchieveBelong(String achieveBelong) {
		this.achieveBelong = achieveBelong;
	}

	public String getPrimaveraAdapted() {
		return primaveraAdapted;
	}

	public void setPrimaveraAdapted(String primaveraAdapted) {
		this.primaveraAdapted = primaveraAdapted;
	}

	public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getCodeTypeRid() {

        return codeTypeRid;
    }

    public void setCodeTypeRid(Long codeTypeRid) {

        this.codeTypeRid = codeTypeRid;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getPlannedStart() {
        return this.plannedStart;
    }

    public void setPlannedStart(Date plannedStart) {
        this.plannedStart = plannedStart;
    }

    public Date getPlannedFinish() {
        return this.plannedFinish;
    }

    public void setPlannedFinish(Date plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getActualStart() {
        return this.actualStart;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    public Date getActualFinish() {
        return this.actualFinish;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    public String getAccountBrief() {
        return this.accountBrief;
    }

    public void setAccountBrief(String accountBrief) {
        this.accountBrief = accountBrief;
    }

    public String getAccountStatus() {
        return this.accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public String getOrgCode() {
        return orgCode;
    }

    public String getDeptFlag() {
        return deptFlag;
    }

    public Double getEstWorkHours() {
        return estWorkHours;
    }

    public String getParentId() {
        return parentId;
    }

    public String getBillable() {
        return billable;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setDeptFlag(String deptFlag) {
        this.deptFlag = deptFlag;
    }

    public void setEstWorkHours(Double estWorkHours) {
        this.estWorkHours = estWorkHours;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setBillable(String billable) {
        this.billable = billable;
    }

	public Long getLeaveCodeTypeRid() {
		return leaveCodeTypeRid;
	}

	public void setLeaveCodeTypeRid(Long leaveCodeTypeRid) {
		this.leaveCodeTypeRid = leaveCodeTypeRid;
	}
}
