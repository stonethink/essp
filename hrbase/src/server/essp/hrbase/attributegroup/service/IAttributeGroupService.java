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
     * �г�����AttributeGroup
     * @return List<AfSite>
     */
    public List listAllAttributeGroup();
    
    /**
     * �г�������ЧAttributeGroup
     * @return List
     */
    public List listEnableAttributeGroup();
    
    /**
     * ����rid��ȡAttributeGroup
     * @param rid Long
     * @return AfAttributeGroup
     */
    public AfAttributeGroup loadAttributeGroup(Long rid);
    
    
    /**
     * ����ridɾ��AttributeGroup
     * @param rid Long
     */
    public void deleteAttributeGroup(Long rid);
    
    /**
     * 
     * @return List
     */
    public List listEnabledAttGroupOption();
    
    /**
     * �������޸�
     * @param af
     */
    public void saveAttGroup(AfAttributeGroup af);
    
    public List listEnabledSiteOption();

}
