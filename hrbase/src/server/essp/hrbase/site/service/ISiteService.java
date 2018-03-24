package server.essp.hrbase.site.service;

import java.util.List;

import server.essp.hrbase.site.form.AfSite;

/**
 * �ݵ�ά��Service
 * @author lipengxu
 *
 */
public interface ISiteService {
	
	/**
	 * �г�����Site
	 * @return List<AfSite>
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
	 * @return AfSite
	 */
	public AfSite loadSite(Long rid);
	
	/**
	 * ���棨���������£�Site
	 * @param af AfSite
	 */
	public void saveSite(AfSite af);
	
	/**
	 * ����ridɾ��Site
	 * @param rid Long
	 */
	public void deleteSite(Long rid);
    
    public List listEnabledSiteOption();

}
