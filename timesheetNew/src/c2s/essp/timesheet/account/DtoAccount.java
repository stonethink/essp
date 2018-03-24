package c2s.essp.timesheet.account;

import java.util.Date;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class DtoAccount extends DtoBase implements IAccountStyle {

    public static final String DTO_RID = "Ts_DtoAccountRid";
    public static final String DTO = "Ts_DtoAccount";
    public static final String DTO_LIST = "Ts_DtoAccountList";
    public static final String DTO_CONDITION = "Ts_DtoCondition";
    public static final String DTO_LOGINIDS = "Ts_DtoLoginIds";
    
    public static final String STATUS_IN = "In";
    public static final String STATUS_OUT = "Out";

    public static final String DEPT_FLAG_DEPT = "1";
    public static final String DEPT_FLAG_PROJECT = "0";
    
    public static final String PRIMAVERA_FLAG_TRUE = "1";
    public static final String PRIMAVERA_FLAG_FALSE = "0";

    public static final String STATUS_NORMAL = "N";
    public static final String STATUS_CLOSED = "Y";
    public static final String STATUS_ENABLE = "N";
    public static final String STATUS_DISABLE = "Y";
    public static final String DTO_PMO = "DtoPmo";
    public static final String DTO_METHOD = "DtoMethod";

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
     private Double actAggregateHours;
     private Double estWorkHours;
     private String codeType;
     private String primaveraAdapted;
     private String achieveBelong;
     private Long methodRid;
     private Boolean isMail;
     private String p6Id;



    // Property accessors

    public String getP6Id() {
		return p6Id;
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

	public String getStatusName() {
    	if(getAccountStatus() != null && STATUS_CLOSED.equals(getAccountStatus())) {
    		return "Yes";
    	} 
    	if(getAccountStatus() != null && STATUS_NORMAL.equals(getAccountStatus())) {
    		return "No";
    	}
		return "";
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

    public Double getActAggregateHours() {
        return actAggregateHours;
    }

    public Double getEstWorkHours() {
        return estWorkHours;
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

    public void setActAggregateHours(Double actAggregateHours) {
        this.actAggregateHours = actAggregateHours;
    }

    public void setEstWorkHours(Double estWorkHours) {
        this.estWorkHours = estWorkHours;
    }

    public boolean isDept() {
    	String dept = getDeptFlag();
        return dept != null && DEPT_FLAG_DEPT.equals(dept);
    }

    public boolean isP3Adapted() {
    	String adapted = getPrimaveraAdapted();
        return adapted != null && PRIMAVERA_FLAG_TRUE.equals(adapted);
    }

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public Long getLeaveCodeTypeRid() {
		return leaveCodeTypeRid;
	}

	public void setLeaveCodeTypeRid(Long leaveCodeTypeRid) {
		this.leaveCodeTypeRid = leaveCodeTypeRid;
	}

}
