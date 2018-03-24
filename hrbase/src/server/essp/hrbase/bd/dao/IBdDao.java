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
 * BD�S�oDAO
 * @author TBH
 */
public interface IBdDao {
      /**
       * �õ�BD�Y��
       * @return List
       */
       public List queryBdCode();
       
       /**
        * �õ���Ч��BD
        * @return List
        */
       public List queryEnabledBdCode();
       
       /**
        * �õ���Ч��ҵ��������λ
        * @return List
        */
       public List queryAchieveBelongUnit();
       
       /**
        * ����BdCode�õ������Y��
        * @param bdCode
        * @return HrbBd
        */
       public HrbBd queryByBdCode(String bdCode);
       
       /**
        * ����
        * @param bd
        */
       public void save(HrbBd bd);
       
       /**
        * ����
        * @param bd
        */
       public void update(HrbBd bd);
       
       /**
        * �h��
        * @param bd
        */
       public void delete(HrbBd bd);

       /**
        * �õ��������̖
        * @return Long
        */
       public Long getMaxSeq();
   
}
