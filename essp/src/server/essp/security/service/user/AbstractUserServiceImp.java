package server.essp.security.service.user;

import c2s.essp.common.user.DtoRole;
import c2s.essp.common.user.DtoUser;
import server.framework.common.BusinessException;
import java.util.List;
import itf.user.LgEmpUserUtilImpl;
import server.framework.logic.AbstractBusinessLogic;
import server.essp.security.service.role.IRoleService;
import server.essp.common.ldap.LDAPUtil;


public abstract class AbstractUserServiceImp extends AbstractBusinessLogic implements IUserService {
    private IRoleService roleService;
    private LDAPUtil ldap;

    public DtoUser getUser(String userType, String domain, String loginId,
                           String passord) {
        ldap = new LDAPUtil(domain, loginId, passord);
        DtoUser user = getUserByLoginId(loginId);
        if(user == null)
            throw new BusinessException("error.find.user",
                                        "Can not find user["+loginId+"]'s info!");
        user.setUserType(userType);
        user.setDomain(domain);
        user.setPassword(passord);
        return user;
    }

    private LDAPUtil getLdap() {
        if(ldap == null) {
            ldap = new LDAPUtil();
        }
        return ldap;
    }

    public IRoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    /////////////////实现暂用原LgEmpUserUtilImpl中的方法
    private LgEmpUserUtilImpl lg = new LgEmpUserUtilImpl();
    public DtoUser getUserByCode(String userCode) throws BusinessException {
        DtoUser user = lg.getUserByCode(userCode);
        List roles = roleService.listUserAllRole(user.getUserLoginId());
        user.setRoles(roles);
        return user;
    }

    public DtoUser getUserByLoginId(String userLoginId) throws
            BusinessException {

        DtoUser user = getLdap().findUser(LDAPUtil.DOMAIN_ALL, userLoginId);
        String orgId = getLdap().findDirectOU(LDAPUtil.DOMAIN_ALL, userLoginId);
        user.setOrgId(orgId);
//         DtoUser user =  lg.getUserByLoginId(userLoginId);
         if(user.getUserLoginId()==null||"".equals(user.getUserLoginId())) {
            user.setUserLoginId(userLoginId);
        }
         List roles = roleService.listUserAllRole(user.getUserLoginId());
         DtoRole dtoRole = roleService.getRole(DtoRole.ROLE_EMP);
         //如果没有角色则，为该人员增加公司员工的角色
         if (roles.size() == 0) {
			if (dtoRole != null) {
				roles.add(dtoRole);
				roleService.saveOrUpdateUserRole(user.getUserLoginId(), 
						new String[]{DtoRole.ROLE_EMP}, user.getDomain());
			}
		 }
         user.setRoles(roles);
         return user;
    }

    public DtoUser getUserByCode(String userType, String userCode) throws
            BusinessException {
        DtoUser user = lg.getUserByCode(userType,userCode);
        List roles = roleService.listUserAllRole(user.getUserLoginId());
        user.setRoles(roles);
        return user;
    }

    public DtoUser getUserByLoginId(String userType, String userLoginId) throws
            BusinessException {
        DtoUser user =  lg.getUserByLoginId(userType,userLoginId);
        List roles = roleService.listUserAllRole(user.getUserLoginId());
        user.setRoles(roles);
         return user;
    }


}
