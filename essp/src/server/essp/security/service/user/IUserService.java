package server.essp.security.service.user;

import c2s.essp.common.user.DtoUserBase;
import c2s.essp.common.user.DtoUser;
import itf.user.IUserUtil;


public interface IUserService extends IUserUtil {

    /**
     * 验证登录用户是否合法且密码是否正确，并返回DtoUserBase对象
     * TODO:
     * 1，userType = Employee,调用server.essp.ldap.LDAPUtil
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
     * 依据输入DtoUser的信息获取DtoUser对象
     * @param dtoUserBase DtoUserBase
     * @return DtoUser
     * @throws BusinessException
     */
    public DtoUser getUser(String userType, String domain,
                                          String loginId,
                                          String passord) ;

}
