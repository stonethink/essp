/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.service;

import java.util.List;
/**
 * �^������O��SERVICE
 * @author TBH
 */
public interface IPrivilegeSiteService {

    /**
     * �г�������Ч�ą^�򼯺�
     * @param loginId
     * @return List
     */
    public List listEnabled2VisibleSite(String loginId);
    
    /**
     * ����LOGINID�г����x�еą^��
     * @param loginId
     * @return List
     */
    public List listAllChoiceSite(String loginId);
    
    /**
     * �������^���O��
     * @param loginId
     * @param sites
     */
    public void saveOrUpdateBlongSite(String loginId,String loginName,String domain, String[] sites);
    
    /**
     * �õ�USER�б���Ϣ
     * @return List
     */
    public List getUserList();
    
    /**
     * ����loginIdɾ����Ӧ��SITEȨ��
     * @param loginId
     */
    public void deleteByLoginId(String loginId);

}
