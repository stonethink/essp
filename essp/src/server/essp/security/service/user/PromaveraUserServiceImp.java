package server.essp.security.service.user;

import c2s.essp.common.user.*;
import server.essp.common.primavera.PrimaveraApiBase;
import com.primavera.integration.client.bo.*;
import server.framework.common.BusinessException;
import server.essp.common.ldap.LDAPUtil;

public class PromaveraUserServiceImp extends AbstractUserServiceImp {
    /**
     * 验证登录用户是否合法且密码是否正确，并返回DtoUserBase对象 TODO: 1，userType =
     * Employee,调用PrimaveraApiBase 构造方法登陆API验证, 并通过获取用户信息,返回
     *
     * @param userType String
     * @param domain String
     * @param loginId String
     * @param passord String
     * @return DtoUserBase
     * @todo Implement this server.essp.security.service.user.IUserService
     *   method
     */
    public DtoUserBase authenticationUser(String userType, String domain,
                                          String loginId, String passWord) {
        if(!LDAPUtil.authenticate(domain,loginId,passWord)) {
            throw new BusinessException("error.login","login failed!");
        }
        PrimaveraApiBase api = new PrimaveraApiBase();
        DtoUserBase dto = new DtoUserBase();
        try {
            dto.setUserLoginId(loginId);
            dto.setUserName(api.getCurrentUser().getPersonalName());
            dto.setDomain(domain);
            dto.setUserType(userType);
            if(api.getCurrentResource() == null) {
                throw new BusinessException("PromaveraUserServiceImp",
                                            "no privilege to access resource");
            }
        } catch (BusinessObjectException ex) {
            throw new BusinessException("PromaveraUserServiceImp",
                                        "get api user name error", ex);
        }
        return dto;
    }
}
