/*
 * Created on 2007-10-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.aprm.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;
import org.springframework.orm.hibernate.HibernateCallback;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.timesheet.TsImportTemp;

public class APRMDaoImp extends HibernateDaoSupport implements IAPRMDao{
    /**
     * ����
     * @param temp
     */
    public void save(TsImportTemp temp) {
        this.getHibernateTemplate().save(temp);
    }

    /**
     * ���˺�ʱ��õ��ݴ浵�еļ�¼
     * @return
     */
    public List getImportInfoList() {
          final String sql = "select t.loginId,t.beginDate,t.endDate,sum(t.sumStandardHours)"
                              +" from TsImportTemp t group by t.loginId, t.beginDate,t.endDate order by t.loginId, t.beginDate,t.endDate";
          List list = (List)this.getHibernateTemplate().execute(new
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
     * ����loginId��beginDate��endDate��ѯ�ݴ浵�з��������ļ�¼
     * @param loginId
     * @param beginDate
     * @param endDate
     * @return List
     */
    public List getImportTemp(String loginId,Date beginDate,Date endDate){
        String sql = "from TsImportTemp where login_id=? and begin_date=?"
                     +" and end_date=?";
        List list = (List)this.getHibernateTemplate()
           .find(sql, new Object[]{loginId,beginDate,endDate});
        return list;
    }
    
    /**
     * ɾ���ݴ浵�е����м�¼
     *
     */
    public void deleteAllTemp() {
        final String sql =  "from TsImportTemp";
        this.getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        return session.delete(sql);
                    }
            });
    }
}
