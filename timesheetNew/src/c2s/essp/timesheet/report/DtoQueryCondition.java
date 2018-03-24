package c2s.essp.timesheet.report;

import java.util.Date;
import java.util.Vector;
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
 * @author wenhaizheng
 * @version 1.0
 */
public class DtoQueryCondition extends DtoBase{
    public final static String DTO_LOGIN_ID = "Ts_DtoLoginId";
    public final static String DTO_BEGIN = "Ts_DtoBegin";
    public final static String DTO_END = "Ts_DtoEnd";
    public final static String DTO_DEPT = "Ts_DtoDept";
    public final static String DTO_QUERY_WAY_EMP = "Ts_DtoQueryWayEmp";
    public final static String DTO_QUERY_WAY_PM = "Ts_DtoQueryWayPm";
    public final static String DTO_QUERY_WAY_RM = "Ts_DtoQueryWayRm";
    public final static String DTO_QUERY_WAY_RMP = "Ts_DtoQueryWayRmP";
    public final static String DTO_QUERY_WAY = "Ts_DtoQueryWay";

    private Date begin;
    private Date end;
    private Long accountRid;
    private String deptId;
    private String loginId;
    private String projectId;
    private String queryWay;
    private Vector projectItem;
    private Vector personItem;
    private boolean includeSub;
    private boolean pmo;
    public boolean isIncludeSub() {
		return includeSub;
	}

	public void setIncludeSub(boolean includeSub) {
		this.includeSub = includeSub;
	}

	public Date getBegin() {
        return begin;
    }

    public String getDeptId() {
        return deptId;
    }

    public Date getEnd() {
        return end;
    }


    public String getLoginId() {
        return loginId;
    }

    public String getQueryWay() {
        return queryWay;
    }

    public Long getAccountRid() {
        return accountRid;
    }

    public Vector getProjectItem() {
        return projectItem;
    }

    public String getProjectId() {
        return projectId;
    }

    public Vector getPersonItem() {
        return personItem;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setQueryWay(String queryWay) {
        this.queryWay = queryWay;
    }

    public void setAccountRid(Long accountRid) {
        this.accountRid = accountRid;
    }

    public void setProjectItem(Vector projectItem) {
        this.projectItem = projectItem;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setPersonItem(Vector personItem) {
        this.personItem = personItem;
    }

	public boolean isPmo() {
		return pmo;
	}

	public void setPmo(boolean pmo) {
		this.pmo = pmo;
	}
}
