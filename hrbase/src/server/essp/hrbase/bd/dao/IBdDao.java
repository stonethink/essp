/*
 * Created on 2007-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.dao;

import java.util.List;

import db.essp.hrbase.HrbBd;
/**
 * BD維護DAO
 * @author TBH
 */
public interface IBdDao {
      /**
       * 得到BD資料
       * @return List
       */
       public List queryBdCode();
       
       /**
        * 得到有效的BD
        * @return List
        */
       public List queryEnabledBdCode();
       
       /**
        * 得到有效的业绩归属单位
        * @return List
        */
       public List queryAchieveBelongUnit();
       
       /**
        * 根據BdCode得到相應資料
        * @param bdCode
        * @return HrbBd
        */
       public HrbBd queryByBdCode(String bdCode);
       
       /**
        * 保存
        * @param bd
        */
       public void save(HrbBd bd);
       
       /**
        * 更新
        * @param bd
        */
       public void update(HrbBd bd);
       
       /**
        * 刪除
        * @param bd
        */
       public void delete(HrbBd bd);

       /**
        * 得到最大序列號
        * @return Long
        */
       public Long getMaxSeq();
   
}
