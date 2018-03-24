package c2s.essp.common.user;

import c2s.dto.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoRole extends DtoBase {
    
    public static final String ROLE_SYSUSER = "R0000SU00";//系统管理员
    public static final String ROLE_EMP = "R00000000";//公司员工
    public static final String ROLE_XFI = "R000XFI00";//财务人员
    public static final String ROLE_UAP = "RU00APO00";//项目管理办公室
    public static final String ROLE_HAP = "RH00APO00";//项目管理办公室(HQ)
    public static final String ROLE_APO = "R000APO00";//项目管理办公室
    public static final String ROLE_TSM = "R00XTSM00";//工時管理者
    
    private String roleId;
    private String roleName;
    private String parentId;
    private String description;
    private int seq;
    private boolean status;
    private boolean visible;

    public DtoRole() {
    }

    public String getDescription() {
            return description;
    }
    public void setDescription(String description) {
            this.description = description;
    }

    public String getRoleId() {
            return roleId;
    }
    public void setRoleId(String roleId) {
            this.roleId = roleId;
    }

    public String getRoleName() {

        return roleName;
    }

    public String getParentId() {
        return parentId;
    }

    public int getSeq() {
        return seq;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setStatus(boolean status) {
            this.status = status;
    }

    public void setRoleName(String roleName) {

        this.roleName = roleName;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
