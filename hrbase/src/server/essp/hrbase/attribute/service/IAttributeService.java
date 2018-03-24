/*
 * Created on 2008-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attribute.service;

import java.util.List;
import server.essp.hrbase.attribute.form.AfAttribute;

public interface IAttributeService {

    /**
     * 列出所有HrbAttribute
     * @return List<AfSite>
     */
    public List listAllHrbAttribute();
    
    /**
     * 列出所有有效HrbAttribute
     * @return List
     */
    public List listEnableHrbAttribute();
    
    /**
     * 根据rid获取Site
     * @param rid Long
     * @return AfSite
     */
    public AfAttribute loadHrbAttribute(Long rid);
    
    /**
     * 新增HrbAttribute
     * @param af AfHrbAttribute
     */
    public void saveHrbAttribute(AfAttribute af);
    
    /**
     * 根据rid删除HrbAttribute
     * @param rid Long
     */
    public void deleteHrbAttribute(Long rid);
    
    /**
     * 
     * @return List
     */
    public List listEnabledHrbAttOption();

}
