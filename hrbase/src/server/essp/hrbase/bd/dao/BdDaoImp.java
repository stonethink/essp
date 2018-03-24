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
 * BD維護DAO
 * @author TBH
 */
public class BdDaoImp extends HibernateDaoSupport implements IBdDao{
    /**
     * 得到BD資料
     * @return List
     */
    public List queryBdCode() {
        String sql = "from HrbBd order by rid";
        return (List)this.getHibernateTemplate().find(sql);
    }
    
    /**
     * 得到有效的BD
     * @return List
     */
    public List queryEnabledBdCode() {
        String sql = "from HrbBd where status='1' order by rid";
        return (List)this.getHibernateTemplate().find(sql);
    }
    
    /**
     * 根據BdCode得到相應資料
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
     * 保存
     * @param bd
     */
    public void save(HrbBd bd) {
        this.getHibernateTemplate().save(bd);
        
    }

    /**
     * 更新
     * @param bd
     */
    public void update(HrbBd bd) {
        this.getHibernateTemplate().update(bd);
        
    }

    /**
     * 刪除
     * @param bd
     */
    public void delete(HrbBd bd) {
        this.getHibernateTemplate().delete(bd);
    }

    /**
     * 得到最大序列號
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
