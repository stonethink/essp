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
     * �г�����HrbAttribute
     * @return List<AfSite>
     */
    public List listAllHrbAttribute();
    
    /**
     * �г�������ЧHrbAttribute
     * @return List
     */
    public List listEnableHrbAttribute();
    
    /**
     * ����rid��ȡSite
     * @param rid Long
     * @return AfSite
     */
    public AfAttribute loadHrbAttribute(Long rid);
    
    /**
     * ����HrbAttribute
     * @param af AfHrbAttribute
     */
    public void saveHrbAttribute(AfAttribute af);
    
    /**
     * ����ridɾ��HrbAttribute
     * @param rid Long
     */
    public void deleteHrbAttribute(Long rid);
    
    /**
     * 
     * @return List
     */
    public List listEnabledHrbAttOption();

}
