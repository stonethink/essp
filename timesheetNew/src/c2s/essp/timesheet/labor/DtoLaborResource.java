package c2s.essp.timesheet.labor;

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
public class DtoLaborResource extends DtoBase {

    public final static String DTO = "Ts_DtoLaborResource";
    public final static String DTO_LIST = "Ts_DtoLaborResourceList";
    public final static String DTO_STATUS_IN = "In";
    public final static String DTO_STATUS_OUT = "Out";

    // Fields

    private Long rid;
    private Long accountRid;
    private String loginId;
    private String name;
    private String rm;
    private String status;
    private String remark;
    private Date rct;
    private Date rut;
    private String rcu;
    private String ruu;
    private Long levelRid;
    private Long roleRid;
    private String level;
    private String role;


    // Property accessors

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getAccountRid() {

        return accountRid;
    }

    public void setAccountRid(Long accountRid) {

        this.accountRid = accountRid;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRm() {
        return this.rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getLevelRid() {
        return levelRid;
    }

    public Long getRoleRid() {
        return roleRid;
    }

    public void setRuu(String ruu) {
        this.ruu = ruu;
    }

    public void setLevelRid(Long levelRid) {
        this.levelRid = levelRid;
    }

    public void setRoleRid(Long roleRid) {
        this.roleRid = roleRid;
    }

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


}
