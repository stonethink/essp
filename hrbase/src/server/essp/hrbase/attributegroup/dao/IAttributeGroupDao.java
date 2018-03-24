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
     * �г�����AttributeGroup
     * @return List<HrbAttributeGroup>
     */
    public List listAllAttGroup();
    
    /**
     * �г�������ЧAttributeGroup
     * @return List
     */
    public List listEnableAttGroup();
    
    /**
     * ����rid��ȡAttributeGroup
     * @param rid Long
     * @return HrbAttributeGroup
     */
    public HrbAttributeGroup loadAttGroup(Long rid);
    
    /**
     * ����AttributeGroup
     * @param site HrbAttributeGroup
     */
    public void saveAttGroup(HrbAttributeGroup attGroup);
    
    /**
     * ����AttributeGroup
     * @param site HrbAttributeGroup
     */
    public void updateAttGroup(HrbAttributeGroup attGroup);
    
    /**
     * ����ridɾ��AttributeGroup
     * @param rid Long
     */
    public void deleteAttGroup(Long rid);

    public HrbAttributeGroup loadAttGroupByCode(String code);
}
