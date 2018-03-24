/*
 * Created on 2007-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.dao;

import java.util.List;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.hrbase.HrbBd;
/**
 * BD�S�oDAO
 * @author TBH
 */
public class BdDaoImp extends HibernateDaoSupport implements IBdDao{
    /**
     * �õ�BD�Y��
     * @return List
     */
    public List queryBdCode() {
        String sql = "from HrbBd order by rid";
        return (List)this.getHibernateTemplate().find(sql);
    }
    
    /**
     * �õ���Ч��BD
     * @return List
     */
    public List queryEnabledBdCode() {
        String sql = "from HrbBd where status='1' order by rid";
        return (List)this.getHibernateTemplate().find(sql);
    }
    
    /**
     * ����BdCode�õ������Y��
     * @param bdCode
     * @return HrbBd
     */
    public HrbBd queryByBdCode(String bdCode) {
        String sql = "from HrbBd where bd_Code=?";
        List list = (List)this.getHibernateTemplate()
                .find(sql, new Object[] {bdCode});
        if(list != null && list.size() >0 ){
            return (HrbBd)list.get(0);
        }
        return null;
    }

    /**
     * ����
     * @param bd
     */
    public void save(HrbBd bd) {
        this.getHibernateTemplate().save(bd);
        
    }

    /**
     * ����
     * @param bd
     */
    public void update(HrbBd bd) {
        this.getHibernateTemplate().update(bd);
        
    }

    /**
     * �h��
     * @param bd
     */
    public void delete(HrbBd bd) {
        this.getHibernateTemplate().delete(bd);
    }

    /**
     * �õ��������̖
     * @return Long
     */
    public Long getMaxSeq() {
        List<HrbBd> list = this.getHibernateTemplate()
                                .find("from HrbBd order by sequence desc");
        if (list.size() > 0) {
            return list.get(0).getSequence();
        }
        return Long.valueOf(1);
    }

    public List queryAchieveBelongUnit() {
        String sql = "from HrbBd where status='1' and is_achieve_Belong='1' order by rid";
        return (List)this.getHibernateTemplate().find(sql);
    }

 
}
