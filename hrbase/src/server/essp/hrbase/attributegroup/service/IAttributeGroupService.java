/*
 * Created on 2008-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attributegroup.service;

import java.util.List;

import server.essp.hrbase.attributegroup.form.AfAttributeGroup;

public interface IAttributeGroupService {

    /**
     * 列出所有AttributeGroup
     * @return List<AfSite>
     */
    public List listAllAttributeGroup();
    
    /**
     * 列出所有有效AttributeGroup
     * @return List
     */
    public List listEnableAttributeGroup();
    
    /**
     * 根据rid获取AttributeGroup
     * @param rid Long
     * @return AfAttributeGroup
     */
    public AfAttributeGroup loadAttributeGroup(Long rid);
    
    
    /**
     * 根据rid删除AttributeGroup
     * @param rid Long
     */
    public void deleteAttributeGroup(Long rid);
    
    /**
     * 
     * @return List
     */
    public List listEnabledAttGroupOption();
    
    /**
     * 新增或修改
     * @param af
     */
    public void saveAttGroup(AfAttributeGroup af);
    
    public List listEnabledSiteOption();

}
