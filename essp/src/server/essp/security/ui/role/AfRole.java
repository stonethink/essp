package server.essp.security.ui.role;

import org.apache.struts.action.*;

public class AfRole extends ActionForm{
    private String roleId ;
    private String roleName;
    private String ParentId;
    private String description;
    private String status;
    private String visible;
    private int seq;

    public String getDescription() {
        return description;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getSeq() {
        return seq;
    }

    public String getStatus() {
        return status;
    }

    public String getVisible() {
        return visible;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }
}
