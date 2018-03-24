package server.essp.hrbase.site.dao;

import java.util.List;

import net.sf.hibernate.type.LongType;

import org.springframework.orm.hibernate.support.HibernateDaoSupport;

import db.essp.hrbase.HrbSite;

public class SiteDaoImp extends HibernateDaoSupport implements ISiteDao {

	/**
	 * 列出所有Site
	 * @return List<HrbSite>
	 */
	public List listSites() {
		return this.getHibernateTemplate().find("from HrbSite order by name");
	}
	
	/**
	 * 列出所有有效Site
	 * @return List
	 */
	public List listEnableSites() {
		return this.getHibernateTemplate().find("from HrbSite where is_enable='1' order by name");
	}
	
	/**
	 * 根据rid获取Site
	 * @param rid Long
	 * @return HrbSite
	 */
	public HrbSite loadSite(Long rid) {
		return (HrbSite) this.getHibernateTemplate().load(HrbSite.class, rid);
	}
	
	/**
	 * 新增Site
	 * @param site HrbSite
	 */
	public void saveSite(HrbSite site) {
		this.getHibernateTemplate().saveOrUpdate(site);
	}
	
	/**
	 * 更新Site
	 * @param site HrbSite
	 */
	public void updateSite(HrbSite site) {
		this.getHibernateTemplate().update(site);
	}
	
	/**
	 * 根据rid删除Site
	 * @param rid Long
	 */
	public void deleteSite(Long rid) {
		this.getHibernateTemplate()
		.delete(loadSite(rid));
	}

}
