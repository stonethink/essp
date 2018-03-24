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
     * ����û�������Ϣ����¼ID���û������û����ͣ�Site)
     * 1������û����ͣ�USER_TYPE_EMPLOYEE,
     * �� ���SiteΪ�գ�������Default Site��Ӧ��LDAP Server�����Ϣ��
     * ���������Ը�Site��Ӧ��LDAP Server�����Ϣ��
     * 2������û����ͣ�USER_TYPE_CUST
     *    �����Ӧ�ı���ȡ�ͻ���Ϣ
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getUser(DtoUserBase user);

    /**
     * ��ȡԱ��������Ϣ����¼ID���û������û����ͣ�Site)
     * �û�����Ϊ��USER_TYPE_EMPLOYEE,
     * �� ���SiteΪ�գ�������Default Site��Ӧ��LDAP Server�����Ϣ��
     * ���������Ը�Site��Ӧ��LDAP Server�����Ϣ��
     * @param domain String
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getEmployee(String domain, String userLoginId);

    /**
     * ��ȡԱ��������Ϣ����¼ID���û������û����ͣ�Site)
     * �û�����Ϊ��USER_TYPE_EMPLOYEE,
     * ��SiteΪ�գ�������Default Site��Ӧ��LDAP Server�����Ϣ��
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getEmployee(String userLoginId);

    /**
     * ��ȡ�ͻ���ϵ�˵Ļ�����Ϣ����¼ID���û������û����ͣ�Site)
     * �û����ͣ�USER_TYPE_CUST
     *    �����Ӧ�ı���ȡ�ͻ���Ϣ
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUserBase getCusomterContactor(String userLoginId);

}
