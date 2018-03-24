package server.essp.security.service.user;

import c2s.essp.common.user.*;
import server.essp.common.primavera.PrimaveraApiBase;
import com.primavera.integration.client.bo.*;
import server.framework.common.BusinessException;
import server.essp.common.ldap.LDAPUtil;

public class PromaveraUserServiceImp extends AbstractUserServiceImp {
    /**
     * ��֤��¼�û��Ƿ�Ϸ��������Ƿ���ȷ��������DtoUserBase���� TODO: 1��userType =
     * Employee,����PrimaveraApiBase ���췽����½API��֤, ��ͨ����ȡ�û���Ϣ,����
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
