/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.privilege.service;

import java.util.List;
/**
 * 區域權限設定SERVICE
 * @author TBH
 */
public interface IPrivilegeSiteService {

    /**
     * 列出所有有效的區域集合
     * @param loginId
     * @return List
     */
    public List listEnabled2VisibleSite(String loginId);
    
    /**
     * 根據LOGINID列出被選中的區域
     * @param loginId
     * @return List
     */
    public List listAllChoiceSite(String loginId);
    
    /**
     * 保存或更新區域設置
     * @param loginId
     * @param sites
     */
    public void saveOrUpdateBlongSite(String loginId,String loginName,String domain, String[] sites);
    
    /**
     * 得到USER列表信息
     * @return List
     */
    public List getUserList();
    
    /**
     * 根据loginId删除对应的SITE权限
     * @param loginId
     */
    public void deleteByLoginId(String loginId);

}
