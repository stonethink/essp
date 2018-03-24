package server.essp.security.ui.role;

import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoRole;
import server.framework.action.AbstractBusinessAction;
import server.framework.common.BusinessException;
import server.essp.security.service.role.IRoleService;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;

public class AcUpdateRole extends AbstractESSPAction {

        private final static String RoleId="roleId";

        /**
         * 根据RoleId更新角色
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data)
                        throws BusinessException {
                    AfRole af = (AfRole) this.getForm();
                    String roleId = null;
                    if (request.getParameter(RoleId) != null) {
                        roleId = (String) request.getParameter(RoleId);
                    }

                    DtoRole role = new DtoRole();
                    String roleIds = af.getRoleId();
                    if (roleIds!= null && !roleIds.trim().equals("")) {
                        role.setRoleId(roleIds.trim());
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
                    if(desc !=null && !desc.trim().equals("")){
                        role.setDescription(desc.trim());
                    }
                    int seq = af.getSeq();
                    if(seq >-1){
                        role.setSeq(seq);
                    }
                    String status = af.getStatus();
                    if(status!=null && !status.trim().equals("")){
                        if(status.trim().equals("true")){
                            role.setStatus(true);
                        }else{
                            role.setStatus(false);
                        }
                    }
                    String visible = af.getVisible();
                    if(visible!=null && !visible.trim().equals("")){
                        if(visible.trim().equals("true")){
                            role.setVisible(true);
                        }else{
                            role.setVisible(false);
                        }
                    }

                    IRoleService logic = (IRoleService)this.getBean("RoleService");
                    logic.updateRole(role,roleId);
                    request.setAttribute("RoleId",roleIds);

      }
}

