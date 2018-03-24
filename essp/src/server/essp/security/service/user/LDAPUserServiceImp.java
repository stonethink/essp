package server.essp.security.service.user;

import c2s.essp.common.user.*;
import server.essp.common.ldap.*;
import server.framework.common.*;


public class LDAPUserServiceImp extends AbstractUserServiceImp  {
    /**
     *
     * @param userType String
     * @param site String
     * @param loginId String
     * @param passord String
     * @return DtoUserBase
     * @throws BusinessException
     * @todo Implement this server.essp.security.service.user.IUserService
     *   method
     */
    public DtoUserBase authenticationUser(String userType, String domain,
                                          String loginId, String password)  {
        boolean isAuthenticated = LDAPUtil.authenticate(domain,loginId,password);
        if(isAuthenticated){
            LDAPUtil ldap = new LDAPUtil(domain,loginId,password);
            DtoUserBase user = ldap.findUser(domain,loginId);
            return user;
        }
        throw new BusinessException("error.login","login failed!");
    }
}
