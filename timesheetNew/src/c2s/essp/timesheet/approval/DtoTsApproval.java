package c2s.essp.timesheet.approval;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;

import java.util.HashMap;
import java.util.Map;

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
public class DtoTsApproval extends DtoBase {

    public final static String DTO_COMBOX_ITEM = "Ts_ComboxItem";
    public final static String DTO_APPROVAL_LIST = "Ts_ApprovalList";
    public final static String DTO_APPROVAL_PMONLY = "PMonly";
    public final static String DTO_APPROVAL_RMONLY = "RMonly";
    public final static String DTO_APPROVAL_PMBEFORERM = "PMBeforeRM";
    public final static String DTO_APPROVAL_PMANDRM = "PMAndRM";
    public final static String STATUS_ACTIVE = "TS_Active";
    public final static String STATUS_SUBMITTED = "TS_Review";
    public final static String STATUS_REJECTED = "TS_Reject";
    public final static String STATUS_PM_APPROVED = "TS_PMApprov";
    public final static String STATUS_RM_APPROVED = "TS_RMApprov";
    public final static String STATUS_APPROVED = "TS_Approv";
    public final static String STATUS_NOT_STARTED = "TS_NotStarted";
    public final static String DTO_APPROVAL_LEVEL = "TS_ApprovalLevel";
    public final static String DTO_QUERY_WAY = "Dto_QueryWay";
    public final static String DTO_QUERY_WAY_TODEAL = "Dto_QueryWayToDeal";
    public final static String DTO_QUERY_WAY_DEALED = "Dto_QueryWayDealed";
    public final static String DTO_QUERY_WAY_ALL = "Dto_QueryWayAll";
    public final static String DTO_REJECT_REASON = "Dto_RejectReason";

    private String loginId;
    private Date startDate;
    private Date finishDate;
    private Double standarHours;
    private Double actualHours;
    private Double overtimeHours;
    private Double leaveHours;
    private Long tsRid;
    private Long acntRid;
    private String status;
    private boolean canOp;
    private String acntCode;
   

	public String getAcntCode() {
		return acntCode;
	}

	public void setAcntCode(String acntCode) {
		this.acntCode = acntCode;
	}

	public String getLoginId() {
        return loginId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public Double getStandarHours() {
        return standarHours;
    }

    public Double getActualHours() {
        return actualHours;
    }

    public Double getOvertimeHours() {
        return overtimeHours;
    }

    public Double getLeaveHours() {
        return leaveHours;
    }

    public Long getTsRid() {
        return tsRid;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getStatus() {
        return status;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setStandarHours(Double standarHours) {
        this.standarHours = standarHours;
    }

    public void setActualHours(Double actualHours) {
        this.actualHours = actualHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public void setLeaveHours(Double leaveHours) {
        this.leaveHours = leaveHours;
    }

    public void setTsRid(Long tsRid) {
        this.tsRid = tsRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public static String statusCode2Name(String statusCode) {
       return DtoTimeSheet.statusCode2Name(statusCode);
   }
   public String getStatusName() {
          return statusCode2Name(getStatus());
    }
	public boolean isCanOp() {
		return canOp;
	}

	public void setCanOp(boolean canOp) {
		this.canOp = canOp;
	}

}
