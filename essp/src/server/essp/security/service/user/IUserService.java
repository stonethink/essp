package server.essp.security.service.user;

import c2s.essp.common.user.DtoUserBase;
import c2s.essp.common.user.DtoUser;
import itf.user.IUserUtil;


public interface IUserService extends IUserUtil {

    /**
     * ��֤��¼�û��Ƿ�Ϸ��������Ƿ���ȷ��������DtoUserBase����
     * TODO:
     * 1��userType = Employee,����server.essp.ldap.LDAPUtil
     * public static boolean authenticate(String userDomain, String userLoginId,
                                       String userPassword) {
     * @param userType String
     * @param site String
     * @param loginId String
     * @param passord String
     * @return DtoUserBase
     * @throws BusinessException
     */
    public DtoUserBase authenticationUser(String userType, String domain,
                                          String loginId,
                                          String passord) ;

    /**
     * ��������DtoUser����Ϣ��ȡDtoUser����
     * @param dtoUserBase DtoUserBase
     * @return DtoUser
     * @throws BusinessException
     */
    public DtoUser getUser(String userType, String domain,
                                          String loginId,
                                          String passord) ;

}
