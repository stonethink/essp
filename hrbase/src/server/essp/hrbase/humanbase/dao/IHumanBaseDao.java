/*
 * Created on 2007-12-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.humanbase.dao;

import java.util.List;

import server.essp.hrbase.humanbase.form.AfHumanBaseQuery;
import db.essp.hrbase.HrbHumanBase;
import db.essp.hrbase.HrbHumanBaseLog;

public interface IHumanBaseDao {
       
    public List queryByCondition(AfHumanBaseQuery af);
    
    public List listUpdateLog(Long rid);
    
    public List listAddLog(String sites);
    
    public HrbHumanBase loadHumanBase(Long rid);
    
    public HrbHumanBase findHumanBase(String employeeId);
    
    public HrbHumanBaseLog findInsertingHumanBaseLog(String employeeId);
    
    public void saveHumanBase(HrbHumanBase humanBase);
    
    public void updateHumanBase(HrbHumanBase humanBase);
    
    public HrbHumanBaseLog loadHumanBaseLog(Long rid);
    
    public void saveHumanBaseLog(HrbHumanBaseLog hrb);
    
    public void updateHumanBaseLog(HrbHumanBaseLog hrb);
    
    public void deleteHumanBase(Long rid);
    
    public void cancelHumanBaseLog(Long rid);
    
    public List<HrbHumanBase> queryHumanBaseUnderUnit(String unitCode);
    
    public List<HrbHumanBaseLog> queryHumanBaseLogForSync();
    
    /**
     * ��������Ⱥ��rid��ȡ���������Ϣ������Ⱥ����룬���Դ��룬�Ƿ���ʽ��
     * @param groupRid Long
     * @return Object[], groupRid Long, groupCode String, attributeCode String, isFormal String
     */
    public Object[] getAttributeInfoByGroupRid(Long groupRid);
    
}
