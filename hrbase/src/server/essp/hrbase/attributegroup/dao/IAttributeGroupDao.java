/*
 * Created on 2008-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attributegroup.dao;

import java.util.List;
import db.essp.hrbase.HrbAttributeGroup;

public interface IAttributeGroupDao {

    /**
     * 列出所有AttributeGroup
     * @return List<HrbAttributeGroup>
     */
    public List listAllAttGroup();
    
    /**
     * 列出所有有效AttributeGroup
     * @return List
     */
    public List listEnableAttGroup();
    
    /**
     * 根据rid获取AttributeGroup
     * @param rid Long
     * @return HrbAttributeGroup
     */
    public HrbAttributeGroup loadAttGroup(Long rid);
    
    /**
     * 新增AttributeGroup
     * @param site HrbAttributeGroup
     */
    public void saveAttGroup(HrbAttributeGroup attGroup);
    
    /**
     * 更新AttributeGroup
     * @param site HrbAttributeGroup
     */
    public void updateAttGroup(HrbAttributeGroup attGroup);
    
    /**
     * 根据rid删除AttributeGroup
     * @param rid Long
     */
    public void deleteAttGroup(Long rid);

    public HrbAttributeGroup loadAttGroupByCode(String code);
}
