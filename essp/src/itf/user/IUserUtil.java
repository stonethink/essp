package itf.user;

import c2s.essp.common.user.*;
import server.framework.common.BusinessException;

public interface IUserUtil  {
    /**
     * 依据用户Code(系统内的编号)实现一个内部用户(USER_TYPE_EMPLOYEE)
     * @param userCode String
     * @return DtoUser*/
    public DtoUser getUserByCode(String userCode) throws
            BusinessException;


    /**
     * 依据用户登录名及用户类型获取一个内部用户(USER_TYPE_EMPLOYEE)
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser getUserByLoginId(String userLoginId) throws
            BusinessException;


    /**
     * 依据用户Code(系统内的编号)及用户类型获取一个用户
     * @param userCode String
     * @return DtoUser*/
    public DtoUser getUserByCode(String userType, String userCode) throws
            BusinessException;


    /**
     * 依据用户登录名及用户类型获取一个用户
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser getUserByLoginId(String userType, String userLoginId) throws
            BusinessException;
}
