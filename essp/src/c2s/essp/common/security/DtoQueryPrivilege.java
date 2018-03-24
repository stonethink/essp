package c2s.essp.common.security;

import c2s.dto.DtoBase;

/**
 * <p>Title: DtoQueryPrivilege</p>
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
public class DtoQueryPrivilege extends DtoBase {
    private String loginId;
    private String executeUnitName;
    private Long executeUnitRid;
    private Boolean orgQueryFlag;
    private Boolean vCodeQueryFlag;
    private String executeUnitId;
    public DtoQueryPrivilege() {
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setExecuteUnitName(String executeUnitName) {
        this.executeUnitName = executeUnitName;
    }

    public void setExecuteUnitRid(Long executeUnitRid) {
        this.executeUnitRid = executeUnitRid;
    }

    public void setOrgQueryFlag(Boolean orgQueryFlag) {
        this.orgQueryFlag = orgQueryFlag;
        if(orgQueryFlag != null && !orgQueryFlag) {
            setVCodeQueryFlag(false);
        }
    }

    public void setVCodeQueryFlag(Boolean vCodeQueryFlag) {
        this.vCodeQueryFlag = vCodeQueryFlag;
        if(vCodeQueryFlag != null && vCodeQueryFlag) {
            setOrgQueryFlag(true);
        }
    }

    public void setExecuteUnitId(String executeUnitId) {
        this.executeUnitId = executeUnitId;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getExecuteUnitName() {
        return executeUnitName;
    }

    public Long getExecuteUnitRid() {
        return executeUnitRid;
    }

    public Boolean getOrgQueryFlag() {
        return orgQueryFlag;
    }

    public Boolean getVCodeQueryFlag() {
        return vCodeQueryFlag;
    }

    public String getExecuteUnitId() {
        return executeUnitId;
    }
}
