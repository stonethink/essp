package c2s.essp.timesheet.weeklyreport;

import java.util.*;

import c2s.essp.timesheet.preference.DtoPreference;

public class DtoTimeSheet extends c2s.dto.DtoBase {

    public final static String DTO_RID = "TS_DtoTimeSheet_Rid";
    public final static String DTO = "TS_DtoTimeSheet_Dto";
    public final static String DTO_NOTES = "TS_DtoTimeSheet_NOTES";

    public final static String STATUS_ACTIVE = "TS_Active";
    public final static String STATUS_SUBMITTED = "TS_Review";
    public final static String STATUS_REJECTED = "TS_Reject";
    public final static String STATUS_PM_APPROVED = "TS_PMApprov";
    public final static String STATUS_RM_APPROVED = "TS_RMApprov";
    public final static String STATUS_APPROVED = "TS_Approv";
    public final static String STATUS_NOT_STARTED = "TS_NotStarted";
    
    public final static String REASON_INGOING = "reasonIngoing";
    public final static String REASON_DIMISSION = "reasonDimission";
    
    public final static String MODEL_FILL = "timesheet_model_fill";
    public final static String MODEL_VIEW = "timesheet_model_view";
    public final static String MODEL_MODIFY = "timesheet_model_modify";

    private static Map<String, String> statusNameMap;

    private Long rid;
    private Long tsId;
    private String status = STATUS_ACTIVE;
    private String notes;
    private List tsDetails;
    private List standarHours;
    private List leaveHours;
    private List overtimeHours;
    private int decimalDigit;
    //是否允许将工时填写在Activity开始之前和结束之后
    private boolean canBeforeActivityStart;
    private boolean canAfterActivityFinish;
    private DtoPreference preference;
    private String loginId;
    private Date beginDate;
    private Date endDate;
    private Date statusDate;
    private String reason;
    private String uiModel = MODEL_FILL;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;


    public void setTsId(Long tsId) {
        this.tsId = tsId;
    }

    public Long getTsId() {
        return tsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getNotes() {

        return notes;
    }

    public void setNotes(String notes) {

        this.notes = notes;
    }

    public int getDecimalDigit() {
        return decimalDigit;
    }

    public String getLoginId() {
        return loginId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getRid() {
        return rid;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setDecimalDigit(int decimalDigit) {
        this.decimalDigit = decimalDigit;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public void setStatusNameMap(Map statusNameMap) {
        this.statusNameMap = statusNameMap;
    }

    public void setTsDetails(List tsDetails) {
        this.tsDetails = tsDetails;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setRcu(String rcu) {
        this.rcu = rcu;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setStandarHours(List standarHours) {
        this.standarHours = standarHours;
    }

    public boolean editable() {
    	if(MODEL_VIEW.equals(uiModel)) {
    		return false;
    	} else if(MODEL_MODIFY.equals(uiModel)) {
    		return true;
    	}
        return STATUS_ACTIVE.equals(getStatus())
                || STATUS_REJECTED.equals(getStatus());
    }

    public String getStatusName() {
        return statusCode2Name(getStatus());
    }
    
    public String getReasonName() {
    	if(getReason() == null) {
    		return "";
    	} else if(REASON_INGOING.equals(getReason())) {
    		return "rsid.timesheet.ingoing";
    	} else if(REASON_DIMISSION.equals(getReason())) {
    		return "rsid.timesheet.dimission";
    	} else {
    		return "";
    	}
    }

    public Map getStatusNameMap() {
        return statusNameMap;
    }

    public List getTsDetails() {
        return tsDetails;
    }

    public List getDateList() {
        return DtoTimeSheetPeriod.period2DateList(getBeginDate(), getEndDate());
    }

    public Date getRut() {
        return rut;
    }

    public String getRuu() {
        return ruu;
    }

    public Date getRct() {
        return rct;
    }

    public String getRcu() {
        return rcu;
    }

    public List getStandarHours() {
        return standarHours;
    }

    public static String statusCode2Name(String statusCode) {
        return statusNameMap.get(statusCode);
    }

    static {
        statusNameMap = new HashMap();
        statusNameMap.put(STATUS_ACTIVE, "Active");
        statusNameMap.put(STATUS_SUBMITTED, "Submitted");
        statusNameMap.put(STATUS_REJECTED, "Rejected");
        statusNameMap.put(STATUS_PM_APPROVED, "PM Approved");
        statusNameMap.put(STATUS_RM_APPROVED, "RM Approved");
        statusNameMap.put(STATUS_APPROVED, "Approved");
        statusNameMap.put(STATUS_NOT_STARTED, "Not Started");
    }

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isCanAfterActivityFinish() {
		return canAfterActivityFinish;
	}

	public void setCanAfterActivityFinish(boolean canAfterActivityFinish) {
		this.canAfterActivityFinish = canAfterActivityFinish;
	}

	public boolean isCanBeforeActivityStart() {
		return canBeforeActivityStart;
	}

	public void setCanBeforeActivityStart(boolean canBeforeActivityStart) {
		this.canBeforeActivityStart = canBeforeActivityStart;
	}

	public List getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(List leaveHours) {
		this.leaveHours = leaveHours;
	}

	public List getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(List overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public String getUiModel() {
		return uiModel;
	}

	public void setUiModel(String uiModel) {
		this.uiModel = uiModel;
	}

	public DtoPreference getPreference() {
		return preference;
	}

	public void setPreference(DtoPreference preference) {
		this.preference = preference;
	}
}
