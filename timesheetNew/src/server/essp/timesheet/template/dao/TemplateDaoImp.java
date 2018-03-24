package server.essp.timesheet.template.dao;

import java.util.List;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import db.essp.timesheet.TsMethodTMap;
import db.essp.timesheet.TsStepT;

public class TemplateDaoImp extends HibernateDaoSupport implements ITemplateDao{

	public void deleteTemplate(TsStepT template) {
		this.getHibernateTemplate().delete(template);		
	}

	public TsStepT getTemplate(Long rid) {
		 return (TsStepT) this.getHibernateTemplate()
         .load(TsStepT.class, rid);
	}

	public void updateTemplate(TsStepT template) {
		 this.getHibernateTemplate().update(template);		
	}

	public List listAllTemplate() {
		final String sql="from TsStepT";
		List list = (List)this.getHibernateTemplate().find(sql);
		return list;
	}

	public void addTemplate(TsStepT templayte) {
		 this.getHibernateTemplate().save(templayte);
	}

	public List getAllMethodByTid(Long rid) {
		final String sql="from TsMethodTMap t where t.id.templateRid = ?";
		List list = (List)this.getHibernateTemplate().find(sql,rid);
		return list;
	}

	public void deleteAllMapByTid(Long rid) {
		
		this.getHibernateTemplate().delete("from TsMethodTMap t where t.id.templateRid =" + rid);		
	}
	
	/**
	 * 最好是查找到最大的rid
	 */
	public TsStepT getTemplateByName(String name) {
		final String sql="from TsStepT t where t.templateName = '" + name+"'";
		return (TsStepT)this.getHibernateTemplate().find(sql).get(0);
	}

	public void saveMapId(TsMethodTMap map) {		
		 this.getHibernateTemplate().save(map);			 
	}




}
