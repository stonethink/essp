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
 * 區域權限DAO
 * @author TBH
 */
public interface IPrivilegeSiteDao {
   /**
    * 列出有效SITE集合
    * @return List
    */
   public List getEnablePrivilegeList();
   
   /**
    * 根據LOGINID得到選中的SITE
    * @param loginId
    * @return List
    */
   public List getChoiceSite(String loginId);
   
   /**
    * 得到人員集合
    * @return List
    */
   public List getUserList();

   /**
    * 根據LGGINID刪除
    * @param loginId
    */
   public void deleteByLoginId(String loginId);
   
   /**
    * 更新區域權限
    * @param sitePrivilege
    */
   public void updateSitePrivilege(HrbSitePrivilege sitePrivilege);

   
}
