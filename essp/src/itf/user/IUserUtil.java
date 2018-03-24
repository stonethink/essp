package itf.user;

import c2s.essp.common.user.*;
import server.framework.common.BusinessException;

public interface IUserUtil  {
    /**
     * �����û�Code(ϵͳ�ڵı��)ʵ��һ���ڲ��û�(USER_TYPE_EMPLOYEE)
     * @param userCode String
     * @return DtoUser*/
    public DtoUser getUserByCode(String userCode) throws
            BusinessException;


    /**
     * �����û���¼�����û����ͻ�ȡһ���ڲ��û�(USER_TYPE_EMPLOYEE)
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser getUserByLoginId(String userLoginId) throws
            BusinessException;


    /**
     * �����û�Code(ϵͳ�ڵı��)���û����ͻ�ȡһ���û�
     * @param userCode String
     * @return DtoUser*/
    public DtoUser getUserByCode(String userType, String userCode) throws
            BusinessException;


    /**
     * �����û���¼�����û����ͻ�ȡһ���û�
     * @param userLoginName String
     * @return DtoUser*/
    public DtoUser getUserByLoginId(String userType, String userLoginId) throws
            BusinessException;
}
