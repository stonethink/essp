/*
 * Created on 2008-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attribute.dao;

import java.util.List;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.hrbase.HrbAttribute;

public class AttributeDaoImp extends HibernateDaoSupport implements IAttributeDao{

    public List listAllHrbAtt() {
        String sql = "from HrbAttribute order by code";
        return this.getHibernateTemplate().find(sql);
    }

    public List listEnableHrbAtt() {
        String sql = "from HrbAttribute where is_enable='1' order by code";
        return this.getHibernateTemplate().find(sql);
    }

    public HrbAttribute loadHrbAtt(Long rid) {
        String sql = "from HrbAttribute where rid=?";
        List list = (List) this.getHibernateTemplate().find(sql,new Object[] {rid});
        if(list != null && list.size() > 0){
            return (HrbAttribute)list.get(0);
        }else{
            return null;
        }
    }

    public void saveHrbAtt(HrbAttribute hrbAttribute) {
        this.getHibernateTemplate().saveOrUpdate(hrbAttribute);
    }

    public void updateHrbAtt(HrbAttribute hrbAttribute) {
        this.getHibernateTemplate().update(hrbAttribute);
    }

    public void deleteHrbAtt(Long rid) {
        this.getHibernateTemplate().delete(loadHrbAtt(rid));
    }

    public HrbAttribute loadHrbAttByCode(String code) {
        String sql = "from HrbAttribute where code=?";
        List list = (List)this.getHibernateTemplate().find(sql,new Object[] {code});
        if(list != null && list.size() > 0){
            HrbAttribute hrbAtt = (HrbAttribute)list.get(0);
            return hrbAtt;
        }else{
            return null;
        }
    }
   
}
