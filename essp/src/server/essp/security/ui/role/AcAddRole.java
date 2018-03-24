package server.essp.security.ui.role;

import server.framework.action.AbstractBusinessAction;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.framework.common.BusinessException;
import c2s.essp.common.user.DtoRole;
import server.essp.security.service.role.IRoleService;
import server.essp.framework.action.AbstractESSPAction;

public class AcAddRole extends AbstractESSPAction {

    /**
     * 添加一个角色.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
            BusinessException {
        AfRole af = (AfRole)this.getForm();
        DtoRole role = new DtoRole();
        String roleId = af.getRoleId();
        if (roleId != null && !roleId.trim().equals("")) {
            role.setRoleId(roleId.trim());
        }
        String roleName = af.getRoleName();
        if (roleName != null && !roleName.trim().equals("")) {
            role.setRoleName(roleName.trim());
        }
        String parentId = af.getParentId();
        if (parentId != null && !parentId.trim().equals("")) {
            if(!parentId.equals("默认无父角色")){
                role.setParentId(parentId.trim());
            }
        }
        String desc = af.getDescription();
        if (desc != null && !desc.trim().equals("")) {
            role.setDescription(desc.trim());
        }
        int seq = af.getSeq();
        if (seq > -1) {
            role.setSeq(seq);
        }
        role.setStatus(true);
        role.setVisible(true);
        IRoleService logic = (IRoleService)this.getBean("RoleService");
        logic.addRole(role);

    }
}
