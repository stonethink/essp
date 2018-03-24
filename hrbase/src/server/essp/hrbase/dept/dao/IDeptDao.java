/*
 * Created on 2007-12-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.dept.dao;

import java.util.List;

import db.essp.hrbase.HrbUnitBase;
/**
 * ���T�S�oDAO
 * @author TBH
 */
public interface IDeptDao {
   
    /**
     * ������ԃ�l����ԃ
     * @param unit
     * @return List
     */
   public List query(HrbUnitBase unit,String beginDate,String endDate);
   
   /**
    * ����
    * @param unit
    */
   public void save(HrbUnitBase unit);
   
   /**
    * ����
    * @param unit
    */
   public void update(HrbUnitBase unit);
   
   /**
    * �������T���a��ԃ
    * @param unitCode
    * @return HrbUnitBase
    */
   public HrbUnitBase loadByDeptId(String unitCode);
   
   /**
    * �õ����еĲ��T���a
    * @return List
    */
   public List getAllUnit();
   
   /**
    * �õ�����ǰ���T���a���������в��T���a
    * @param unitCode
    * @return List
    */
   public List getParentUnit(String unitCode);
   
   public HrbUnitBase getUnitBaseByRid(Long rid);

}
