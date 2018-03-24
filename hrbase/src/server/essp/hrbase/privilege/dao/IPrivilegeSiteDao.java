/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.dao;

import java.util.List;
import db.essp.hrbase.HrbSitePrivilege;
/**
 * �^�����DAO
 * @author TBH
 */
public interface IPrivilegeSiteDao {
   /**
    * �г���ЧSITE����
    * @return List
    */
   public List getEnablePrivilegeList();
   
   /**
    * ����LOGINID�õ��x�е�SITE
    * @param loginId
    * @return List
    */
   public List getChoiceSite(String loginId);
   
   /**
    * �õ��ˆT����
    * @return List
    */
   public List getUserList();

   /**
    * ����LGGINID�h��
    * @param loginId
    */
   public void deleteByLoginId(String loginId);
   
   /**
    * ���^�����
    * @param sitePrivilege
    */
   public void updateSitePrivilege(HrbSitePrivilege sitePrivilege);

   
}
