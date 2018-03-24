package server.essp.security.ui.role;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import c2s.essp.common.user.DtoUser;
import c2s.essp.common.user.DtoUserBase;
import server.essp.common.ldap.LDAPUtil;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.security.service.role.IRoleService;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

public class AcRoleUserList extends AbstractESSPAction {

	public void executeAct(HttpServletRequest request, HttpServletResponse response,
			TransactionData data) throws BusinessException {
		String roleId = (String)request.getParameter("roleId");
		IRoleService logic = (IRoleService)this.getBean("RoleService");
        List<DtoUserBase> listRole = logic.listLoginIdUnderRole(new String[] {roleId});
        LDAPUtil ldap = new LDAPUtil();
        List vbList = new ArrayList();
        for(DtoUserBase dto : listRole) {
        	String loginId = dto.getUserLoginId();
        	DtoUser adDto = ldap.findUser(loginId);
        	VbRole vb = new VbRole();
        	if(adDto != null) {
        		vb.setRoleId(adDto.getUserLoginId());
        		vb.setRoleName(adDto.getUserName());
        	} else {
        		vb.setRoleId(loginId);
        	}
        	vbList.add(vb);
        }
        request.setAttribute(Constant.VIEW_BEAN_KEY, vbList);
	}

}
