package itf.user;

import server.framework.logic.IBusinessLogic;
import c2s.essp.common.user.DtoUserBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IUserBaseUtil  {
    /**
     * 获得用户基本信息（登录ID，用户名，用户类型，Site)
     * 1、如果用户类型：USER_TYPE_EMPLOYEE,
     * 　 如果Site为空，即依据Default Site对应的LDAP Server获得信息；
     * 　　否则以该Site对应的LDAP Server获得信息；
     * 2、如果用户类型：USER_TYPE_CUST
     *    则从相应的表格获取客户信息
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getUser(DtoUserBase user);

    /**
     * 获取员工基本信息（登录ID，用户名，用户类型，Site)
     * 用户类型为：USER_TYPE_EMPLOYEE,
     * 　 如果Site为空，即依据Default Site对应的LDAP Server获得信息；
     * 　　否则以该Site对应的LDAP Server获得信息；
     * @param domain String
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getEmployee(String domain, String userLoginId);

    /**
     * 获取员工基本信息（登录ID，用户名，用户类型，Site)
     * 用户类型为：USER_TYPE_EMPLOYEE,
     * 且Site为空，即依据Default Site对应的LDAP Server获得信息；
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getEmployee(String userLoginId);

    /**
     * 获取客户联系人的基本信息（登录ID，用户名，用户类型，Site)
     * 用户类型：USER_TYPE_CUST
     *    则从相应的表格获取客户信息
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getCusomterContactor(String userLoginId);

}
