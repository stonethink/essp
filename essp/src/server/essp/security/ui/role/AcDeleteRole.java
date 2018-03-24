package server.essp.security.ui.role;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.framework.action.AbstractBusinessAction;
import server.essp.security.service.role.IRoleService;
import server.essp.framework.action.AbstractESSPAction;

public  class AcDeleteRole extends AbstractESSPAction {
       public static final String RoleId="roleId";

       /**
        * ¸ù¾ÝRoleIdÉ¾³ý½ÇÉ«.
        * @param request HttpServletRequest
        * @param response HttpServletResponse
        * @param data TransactionData
        * @throws BusinessException
        */
       public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data)
                        throws BusinessException {
              String roleId = "";
              if (request.getParameter(RoleId) != null
				&& !request.getParameter(RoleId).equals("")) {
                   roleId = (String)request.getParameter(RoleId);
              }
              IRoleService logic = (IRoleService)this.getBean("RoleService");
              logic.deleteRole(roleId);
        }
}
