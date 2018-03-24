package server.essp.hrbase.site.dao;

import java.util.List;

import db.essp.hrbase.HrbSite;


public interface ISiteDao {
	
	/**
	 * �г�����Site
	 * @return List<HrbSite>
	 */
	public List listSites();
	
	/**
	 * �г�������ЧSite
	 * @return List
	 */
	public List listEnableSites();
	
	/**
	 * ����rid��ȡSite
	 * @param rid Long
	 * @return HrbSite
	 */
	public HrbSite loadSite(Long rid);
	
	/**
	 * ����Site
	 * @param site HrbSite
	 */
	public void saveSite(HrbSite site);
	
	/**
	 * ����Site
	 * @param site HrbSite
	 */
	public void updateSite(HrbSite site);
	
	/**
	 * ����ridɾ��Site
	 * @param rid Long
	 */
	public void deleteSite(Long rid);
}
