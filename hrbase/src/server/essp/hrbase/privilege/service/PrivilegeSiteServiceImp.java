/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.service;

import java.util.ArrayList;
import java.util.List;
import c2s.dto.DtoUtil;
import c2s.essp.hrbase.maintenance.DtoHrbSitePrivilege;
import db.essp.hrbase.HrbSite;
import db.essp.hrbase.HrbSitePrivilege;
import server.essp.hrbase.privilege.dao.IPrivilegeSiteDao;
import server.framework.common.BusinessException;
/**
 * 區域權限設定SERVICE
 * @author TBH
 */
public class PrivilegeSiteServiceImp implements IPrivilegeSiteService{
    
    private IPrivilegeSiteDao privilegeDao;

    /**
     * 列出所有有效的區域集合
     * @param loginId
     * @return List
     */
    public List listEnabled2VisibleSite(String loginId) {
        List enbleList = privilegeDao.getEnablePrivilegeList();
        List resList = new ArrayList();
        if(enbleList != null && enbleList.size() > 0){
            for(int i=0;i<enbleList.size();i++){
                DtoHrbSitePrivilege sitePri = new DtoHrbSitePrivilege();
                HrbSite site = (HrbSite)enbleList.get(i);
                sitePri.setBelongSite(site.getName());
                sitePri.setLoginId(loginId);
                resList.add(sitePri);
            }
        }
        return resList;
    }

    /**
     * 根據LOGINID列出被選中的區域
     * @param loginId
     * @return List
     */
    public List listAllChoiceSite(String loginId) {
        List choiceList = privilegeDao.getChoiceSite(loginId);
        List resultList = new ArrayList();
        if(choiceList != null && choiceList.size()>0){
            for(int i=0;i<choiceList.size();i++){
                DtoHrbSitePrivilege dtoSite = new DtoHrbSitePrivilege();
                HrbSitePrivilege site = (HrbSitePrivilege)choiceList.get(i);
                DtoUtil.copyBeanToBean(dtoSite,site);
                dtoSite.setBelongSite(site.getSiteName());
                resultList.add(dtoSite);
            }
        }
        return resultList;
    }

    /**
     * 保存或更新區域設置
     * @param loginId
     * @param sites
     */
    public void saveOrUpdateBlongSite(String loginId,String loginName,
            String domain,String[] sites) {
          try{
          privilegeDao.deleteByLoginId(loginId);
          if(sites != null && sites.length > 0){
              for(int i=0;i<sites.length;i++){
                  HrbSitePrivilege sitePri = new HrbSitePrivilege();
                  sitePri.setLoginId(loginId);
                  sitePri.setLoginName(loginName);
                  sitePri.setDomain(domain);
                  sitePri.setSiteName(sites[i]);
                  privilegeDao.updateSitePrivilege(sitePri);
              }
          }
          }catch(BusinessException ex){
              throw new BusinessException(ex.getErrorCode(), ex.getLocalizedMessage());
          }
    }
    
    
    
    /**
     * 得到USER列表信息
     * @return List
     */
    public List getUserList() {
        List userList = privilegeDao.getUserList();
        List resultList = new ArrayList();
        if(userList != null){
            for(int i=0;i<userList.size();i++){
                String loginId = (String)userList.get(i);
               if(loginId != null){
                List siteList = privilegeDao.getChoiceSite(loginId);
                HrbSitePrivilege site = (HrbSitePrivilege)siteList.get(0);
                site.setLoginId(loginId);
                resultList.add(site);
               }
            }
        }
        return resultList;
    }

    /**
     * 根据loginId删除对应的SITE权限
     * @param loginId
     */
    public void deleteByLoginId(String loginId) {
        try{
        privilegeDao.deleteByLoginId(loginId);
        }catch(BusinessException e){
            throw new BusinessException(e.getErrorCode(),
                    e.getLocalizedMessage());
        }
    }

    
    public void setPrivilegeDao(IPrivilegeSiteDao privilegeDao) {
        this.privilegeDao = privilegeDao;
    }

   
   
}
