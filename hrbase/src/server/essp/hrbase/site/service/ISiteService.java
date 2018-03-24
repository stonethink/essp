package server.essp.hrbase.site.service;

import java.util.List;

import server.essp.hrbase.site.form.AfSite;

/**
 * 据点维护Service
 * @author lipengxu
 *
 */
public interface ISiteService {
	
	/**
	 * 列出所有Site
	 * @return List<AfSite>
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
	 * @return AfSite
	 */
	public AfSite loadSite(Long rid);
	
	/**
	 * 保存（新增、更新）Site
	 * @param af AfSite
	 */
	public void saveSite(AfSite af);
	
	/**
	 * 根据rid删除Site
	 * @param rid Long
	 */
	public void deleteSite(Long rid);
    
    public List listEnabledSiteOption();

}
