package c2s.essp.timesheet.workscope;

import java.util.List;

import c2s.dto.DtoBase;
import c2s.essp.timesheet.account.IAccountStyle;

public class DtoAccount extends DtoBase implements IAccountStyle {
    public final static String KEY_PROJECT_LIST = "projectList";
    public final static String ACCOUNT_ID = "accountId";
    public final static String DTO_ACCOUNT = "dtoAccount";
    public final static String CODE_TYPE_RID ="codeTypeRid";
    public final static String LEAVE_CODE_TYPE_RID ="leaveCodeTypeRid";
    public final static String SCOPE_LOGIN_ID = "scope_user_loginId";
    public final static String SCOPE_MODEL = "scope_model";
    public final static String SCOPE_MODEL_SESSION = "scope_model_session";
    public final static String SCOPE_MODEL_PARAM = "scope_model_param";
    
    private Long codeTypeRid;
    private Long leaveCodeTypeRid;
    private Integer id;
    private String name;
    private List activities;
    private List codeTypeList;
    private List leaveCodeList;
    private boolean isP3;
    private boolean isDept;
    private String code;
    private Long rid;
    private String p6Id;

    public String getP6Id() {
		return p6Id;
	}

	public void setP6Id(String id) {
		p6Id = id;
	}

	public List getActivities() {
        return activities;
    }

    public String getCode() {
        return code;
    }

    public boolean getIsP3() {
        return isP3;
    }

    public boolean getIsDept() {
        return isDept;
    }

    public List getLeaveCodeList() {
        return leaveCodeList;
    }

    public List getCodeTypeList() {
        return codeTypeList;
    }

    public Long getCodeTypeRid() {
        return codeTypeRid;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getRid() {
        return rid;
    }

    public void setActivities(List activities) {
        this.activities = activities;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLeaveCodeList(List leaveCodeList) {
        this.leaveCodeList = leaveCodeList;
    }

    public void setIsDept(boolean isDept) {
        this.isDept = isDept;
    }

    public void setIsP3(boolean isP3) {
        this.isP3 = isP3;
    }

    public void setCodeTypeList(List codeTypeList) {
        this.codeTypeList = codeTypeList;
    }

    public void setCodeTypeRid(Long codeTypeRid) {
        this.codeTypeRid = codeTypeRid;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }


    public String getShowName() {
        return getCode() + " -- " + getName();
    }

    public boolean isDept() {
        return isDept;
    }

    public boolean isP3Adapted() {
        return isP3;
    }

	public Long getLeaveCodeTypeRid() {
		return leaveCodeTypeRid;
	}

	public void setLeaveCodeTypeRid(Long leaveCodeTypeRid) {
		this.leaveCodeTypeRid = leaveCodeTypeRid;
	}
}
