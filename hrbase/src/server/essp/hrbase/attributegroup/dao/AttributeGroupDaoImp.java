/*
 * Created on 2008-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attributegroup.dao;

import java.util.List;
import org.springframework.orm.hibernate.support.HibernateDaoSupport;
import db.essp.hrbase.HrbAttributeGroup;

public class AttributeGroupDaoImp extends HibernateDaoSupport 
implements IAttributeGroupDao{

    public List listAllAttGroup() {
        String sql = "from HrbAttributeGroup order by site, code";
        return this.getHibernateTemplate().find(sql);
    }

    public List listEnableAttGroup() {
        String sql = "from HrbAttributeGroup where is_enable='1' order by site, code";
        return this.getHibernateTemplate().find(sql);
    }
    
    public HrbAttributeGroup loadAttGroupByCode(String code) {
        String sql = "from HrbAttributeGroup where code=?";
        List list = (List)this.getHibernateTemplate().find(sql,new Object[] {code});
        if(list != null && list.size() > 0){
            HrbAttributeGroup attGroup = (HrbAttributeGroup)list.get(0);
            return attGroup;
        }else{
            return null;
        }
    }

    public HrbAttributeGroup loadAttGroup(Long rid) {
        return (HrbAttributeGroup)getHibernateTemplate().
        load(HrbAttributeGroup.class,rid);
    }

    public void saveAttGroup(HrbAttributeGroup attGroup) {
         this.getHibernateTemplate().save(attGroup);
    }

    public void updateAttGroup(HrbAttributeGroup attGroup) {
          this.getHibernateTemplate().update(attGroup);
    }

    public void deleteAttGroup(Long rid) {
        this.getHibernateTemplate().delete(loadAttGroup(rid));
    }

 
}
