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
 * 部門維護DAO
 * @author TBH
 */
public interface IDeptDao {
   
    /**
     * 根據查詢條件查詢
     * @param unit
     * @return List
     */
   public List query(HrbUnitBase unit,String beginDate,String endDate);
   
   /**
    * 保存
    * @param unit
    */
   public void save(HrbUnitBase unit);
   
   /**
    * 更新
    * @param unit
    */
   public void update(HrbUnitBase unit);
   
   /**
    * 根據部門代碼查詢
    * @param unitCode
    * @return HrbUnitBase
    */
   public HrbUnitBase loadByDeptId(String unitCode);
   
   /**
    * 得到所有的部門代碼
    * @return List
    */
   public List getAllUnit();
   
   /**
    * 得到除當前部門代碼的其他所有部門代碼
    * @param unitCode
    * @return List
    */
   public List getParentUnit(String unitCode);
   
   public HrbUnitBase getUnitBaseByRid(Long rid);

}
