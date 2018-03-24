package server.essp.security.ui.role;

import java.util.*;

public class VbRole {
    private String roleId;
    private String roleName;
    private List parentIdList = new ArrayList();
    private String description;
    private String parentId;
    private int seq;
    private boolean status;
    private boolean visible;

    public String getDescription() {
        return description;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public List getParentIdList() {
        return parentIdList;
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

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setParentIdList(List parentIdList) {
        this.parentIdList = parentIdList;
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
