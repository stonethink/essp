package server.essp.hrbase.site.service;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;

import db.essp.hrbase.HrbBd;
import db.essp.hrbase.HrbSite;

import server.essp.hrbase.site.dao.ISiteDao;
import server.essp.hrbase.site.form.AfSite;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;

public class SiteServiceImp implements ISiteService {
	
	private ISiteDao siteDao;
	
	/**
	 * 列出所有Site
	 * @return List<AfSite>
	 */
	public List listSites() {
		return beanList2AfList(siteDao.listSites());
	}
	
	/**
	 * 列出所有有效Site
	 * @return List
	 */
	public List listEnableSites() {
		return beanList2AfList(siteDao.listEnableSites());
	}
	
	/**
	 * 根据rid获取Site
	 * @param rid Long
	 * @return AfSite
	 */
	public AfSite loadSite(Long rid) {
		HrbSite site = null;
		try {
		site= siteDao.loadSite(rid);
		} catch(Exception e) {
			new BusinessException("error.hrbase.logic.site.loadSiete",
					"load site error");
		}
		return bean2Af(siteDao.loadSite(rid));
	}
	
	/**
	 * 保存（新增、更新）Site
	 * @param af AfSite
	 */
	public void saveSite(AfSite af) {
		HrbSite site = af2Bean(af);
		if(site.getRid() == null) {
			siteDao.saveSite(site);
		} else {
			HrbSite s = siteDao.loadSite(site.getRid());
			s.setName(site.getName());
			s.setDescription(site.getDescription());
			s.setIsEnable(site.getIsEnable());
			siteDao.updateSite(s);
		}
		
	}
	
	/**
	 * 根据rid删除Site
	 * @param rid Long
	 */
	public void deleteSite(Long rid) {
		siteDao.deleteSite(rid);
	}

	public void setSiteDao(ISiteDao siteDao) {
		this.siteDao = siteDao;
	}
	
	private static AfSite bean2Af(HrbSite bean) {
		AfSite af = new AfSite();
		DtoUtil.copyBeanToBean(af, bean);
		return af;
	}
	
	private static HrbSite af2Bean(AfSite af) {
		HrbSite bean = new HrbSite();
		DtoUtil.copyBeanToBean(bean, af);
		if(af.getRid() == null || "".equals(af.getRid())) {
			bean.setRid(null);
		}
		if(af.getIsEnable() == null || "".equals(af.getIsEnable())) {
			bean.setIsEnable("0");
		}
		return bean;
	}
	
	private static List beanList2AfList(List bealist) {
		List afList = null;
		try {
			afList = DtoUtil.list2List(bealist, AfSite.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return afList;
	}

    public List listEnabledSiteOption() {
        List siteList = siteDao.listEnableSites();
        SelectOptionImpl siteOption = new SelectOptionImpl("--Please select--","");
        List sList = new ArrayList();
        sList.add(siteOption);
        if(siteList != null && siteList.size() > 0){
            for(int i=0;i<siteList.size();i++){
                HrbSite site = (HrbSite)siteList.get(i);
                siteOption = new SelectOptionImpl(site.getName(),site.getName());
                sList.add(siteOption);
            }
        }
        return sList;
    }
}
