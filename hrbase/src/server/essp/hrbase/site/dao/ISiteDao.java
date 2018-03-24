package server.essp.hrbase.site.dao;

import java.util.List;

import db.essp.hrbase.HrbSite;


public interface ISiteDao {
	
	/**
	 * 列出所有Site
	 * @return List<HrbSite>
	 */
	public List listSites();
	
	/**
	 * 列出所有有效Site
	 * @return List
	 */
	public List listEnableSites();
	
	/**
	 * 根据rid获取Site
	 * @param rid Long
	 * @return HrbSite
	 */
	public HrbSite loadSite(Long rid);
	
	/**
	 * 新增Site
	 * @param site HrbSite
	 */
	public void saveSite(HrbSite site);
	
	/**
	 * 更新Site
	 * @param site HrbSite
	 */
	public void updateSite(HrbSite site);
	
	/**
	 * 根据rid删除Site
	 * @param rid Long
	 */
	public void deleteSite(Long rid);
}
