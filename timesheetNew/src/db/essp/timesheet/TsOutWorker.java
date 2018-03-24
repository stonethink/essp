package db.essp.timesheet;

import java.io.Serializable;
import java.util.Date;

public class TsOutWorker implements Serializable {
    private Long rid;
    private Long acntRid;
    private String loginId;
    private Date beginDate;
    private Date endDate;
    private Long days;
    private Boolean isAutoWeeklyReport;
    private String destAddress;
    private String rst;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;
    private Long activityRid;
    private Long codeRid;
    private String isTravellingAllowance;
    private String evectionType;

	public Long getAcntRid() {
        return acntRid;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Long getDays() {
        return days;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Boolean getIsAutoWeeklyReport() {
        return isAutoWeeklyReport;
    }

    public String getLoginId() {
        return loginId;
    }

    public Date getRct() {
        return rct;
    }

    public String getRst() {
        return rst;
    }

    public Date getRut() {
        return rut;
    }

    public Long getActivityRid() {

        return activityRid;
    }

    public Long getRid() {
        return rid;
    }

    public String getIsTravellingAllowance() {
        return isTravellingAllowance;
    }

    public String getEvectionType() {
        return evectionType;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setIsAutoWeeklyReport(Boolean isAutoWeeklyReport) {
        this.isAutoWeeklyReport = isAutoWeeklyReport;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActivityRid(Long activityRid) {

        this.activityRid = activityRid;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setIsTravellingAllowance(String isTravellingAllowance) {
        this.isTravellingAllowance = isTravellingAllowance;
    }

    public void setEvectionType(String evectionType) {
        this.evectionType = evectionType;
    }

	public Long getCodeRid() {
		return codeRid;
	}

	public void setCodeRid(Long codeRid) {
		this.codeRid = codeRid;
	}

	public String getRcu() {
		return rcu;
	}

	public void setRcu(String rcu) {
		this.rcu = rcu;
	}

	public String getRuu() {
		return ruu;
	}

	public void setRuu(String ruu) {
		this.ruu = ruu;
	}
}
