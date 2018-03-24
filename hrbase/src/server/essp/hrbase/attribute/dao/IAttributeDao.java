/*
 * Created on 2008-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attribute.dao;

import java.util.List;
import db.essp.hrbase.HrbAttribute;

public interface IAttributeDao {
    
    /**
     * 列出所有HrbAttribute
     * @return List<HrbSite>
     */
    public List listAllHrbAtt();
    
    /**
     * 列出所有有效HrbAttribute
     * @return List
     */
    public List listEnableHrbAtt();
    
    /**
     * 根据rid获取HrbAttribute
     * @param rid Long
     * @return HrbAttribute
     */
    public HrbAttribute loadHrbAtt(Long rid);
    
    /**
     * 根据code获取HrbAttribute
     * @param code
     * @return HrbAttribute
     */
    public HrbAttribute loadHrbAttByCode(String code);
    
    /**
     * 新增HrbAttribute
     * @param site HrbAttribute
     */
    public void saveHrbAtt(HrbAttribute hrbAttribute);
    
    /**
     * 更新HrbAttribute
     * @param site HrbAttribute
     */
    public void updateHrbAtt(HrbAttribute hrbAttribute);
    
    /**
     * 根据rid删除HrbAttribute
     * @param rid Long
     */
    public void deleteHrbAtt(Long rid);
}
