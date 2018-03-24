package server.essp.security.ui.role;

import java.util.*;

import javax.servlet.http.*;

import c2s.dto.*;
import c2s.essp.common.user.*;
import server.essp.security.service.role.*;
import server.framework.action.*;
import server.framework.common.*;
import server.framework.taglib.util.TagUtils;
import server.essp.framework.action.AbstractESSPAction;


public class AcPreviewRole extends AbstractESSPAction {
        private final static String RoleId="roleId";

        /**
         * 显示相应的角色信息
         * @param request HttpServletRequest
         * @param response HttpServletResponse
         * @param data TransactionData
         * @throws BusinessException
         */
        public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data)
                        throws BusinessException {

                        String RoldId = (String)request.getAttribute("RoleId");
                        String roleId=null;
                        if (request.getParameter(RoleId) != null) {
                                roleId = (String) request.getParameter(RoleId);
                        }

                        IRoleService logic = (IRoleService)this
                                             .getBean("RoleService");
                        VbRole vr = new VbRole();
                        DtoRole role =null;
                        if(roleId!=null && roleId.length()>0){
                            role = logic.getRole(roleId);
                        }else{
                            role = logic.getRole(RoldId);
                        }
                        if(role !=null){
                            vr.setRoleId(role.getRoleId());
                            vr.setRoleName(role.getRoleName());
                            vr.setDescription(role.getDescription());
                            vr.setStatus(role.isStatus());
                            vr.setVisible(role.isVisible());
                            if (role.getParentId() != null &&
                                role.getParentId().length() > 0) {
                                vr.setParentId(role.getParentId());
                            }
                            vr.setSeq(role.getSeq());
                        }
                        List allRole = logic.listAllRole();
                        for(int i=0;i<allRole.size();i++){
                            DtoRole dtoRole = (DtoRole)allRole.get(i);
                            if(roleId!=null && roleId.equals(dtoRole.getRoleId())){
                                allRole.remove(i);
                            }
                        }
                        DtoRole dto = new DtoRole();
                        dto.setRoleId("默认无父角色");
                        allRole.add(0,dto);
                        List RoleList = (List)TagUtils.list2Options(allRole,"roleId", "roleId");
                        vr.setParentIdList(RoleList);
                        request.setAttribute(Constant.VIEW_BEAN_KEY, vr);
        }

}
