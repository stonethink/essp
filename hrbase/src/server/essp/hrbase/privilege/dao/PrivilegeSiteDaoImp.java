/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.dao;

import java.sql.SQLException;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.hrbase.HrbSitePrivilege;

/**
 * �^�����DAO
 * @author TBH
 */
public class PrivilegeSiteDaoImp extends HibernateDaoSupport
implements IPrivilegeSiteDao{
    
    /**
     * �г���ЧSITE����
     * @return List
     */
    public List getEnablePrivilegeList() {
        String sql = "from HrbSite where is_enable='1' order by rid";
        return (List)this.getHibernateTemplate().find(sql);
    }
    
    /**
     * ����LOGINID�õ��x�е�SITE
     * @param loginId
     * @return List
     */
    public List getChoiceSite(String loginId) {
        String sql = "from HrbSitePrivilege where login_id=? order by rid";
        return (List)this.getHibernateTemplate().find(sql,new Object[]{loginId});
    }
    
    /**
     * �õ��ˆT����
     * @return List
     */
    public List getUserList() {
        final String sql = "select distinct h.loginId from HrbSitePrivilege as h";         
        List list = null;
        list = (List)this.getHibernateTemplate().execute(new
             HibernateCallback() {
        public Object doInHibernate(Session session) throws SQLException,
         HibernateException {
            Query query = session.createQuery(sql);
            return query.list();
          }
      });
      return list;
    }

    
    /**
     * ����LGGINID�h��
     * @param loginId
     */
    public void deleteByLoginId(final String loginId) {
        final String sql = "from HrbSitePrivilege where login_id='"+loginId+"'";
        this.getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        return session.delete(sql);
                    }
            });
    }
    
    /**
     * ���^�����
     * @param sitePrivilege
     */
    public void updateSitePrivilege(HrbSitePrivilege sitePrivilege) {
        this.getHibernateTemplate().save(sitePrivilege);
        
    }

}
