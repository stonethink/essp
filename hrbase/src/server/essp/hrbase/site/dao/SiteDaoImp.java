package server.essp.hrbase.site.dao;

import java.util.List;

import net.sf.hibernate.type.LongType;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import db.essp.hrbase.HrbSite;

public class SiteDaoImp extends HibernateDaoSupport implements ISiteDao {

	/**
	 * �г�����Site
	 * @return List<HrbSite>
	 */
	public List listSites() {
		return this.getHibernateTemplate().find("from HrbSite order by name");
	}
	
	/**
	 * �г�������ЧSite
	 * @return List
	 */
	public List listEnableSites() {
		return this.getHibernateTemplate().find("from HrbSite where is_enable='1' order by name");
	}
	
	/**
	 * ����rid��ȡSite
	 * @param rid Long
	 * @return HrbSite
	 */
	public HrbSite loadSite(Long rid) {
		return (HrbSite) this.getHibernateTemplate().load(HrbSite.class, rid);
	}
	
	/**
	 * ����Site
	 * @param site HrbSite
	 */
	public void saveSite(HrbSite site) {
		this.getHibernateTemplate().saveOrUpdate(site);
	}
	
	/**
	 * ����Site
	 * @param site HrbSite
	 */
	public void updateSite(HrbSite site) {
		this.getHibernateTemplate().update(site);
	}
	
	/**
	 * ����ridɾ��Site
	 * @param rid Long
	 */
	public void deleteSite(Long rid) {
		this.getHibernateTemplate()
		.delete(loadSite(rid));
	}

}
