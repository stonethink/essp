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
     * �г�����HrbAttribute
     * @return List<HrbSite>
     */
    public List listAllHrbAtt();
    
    /**
     * �г�������ЧHrbAttribute
     * @return List
     */
    public List listEnableHrbAtt();
    
    /**
     * ����rid��ȡHrbAttribute
     * @param rid Long
     * @return HrbAttribute
     */
    public HrbAttribute loadHrbAtt(Long rid);
    
    /**
     * ����code��ȡHrbAttribute
     * @param code
     * @return HrbAttribute
     */
    public HrbAttribute loadHrbAttByCode(String code);
    
    /**
     * ����HrbAttribute
     * @param site HrbAttribute
     */
    public void saveHrbAtt(HrbAttribute hrbAttribute);
    
    /**
     * ����HrbAttribute
     * @param site HrbAttribute
     */
    public void updateHrbAtt(HrbAttribute hrbAttribute);
    
    /**
     * ����ridɾ��HrbAttribute
     * @param rid Long
     */
    public void deleteHrbAtt(Long rid);
}
