package server.essp.issue.typedefine.category.logic;

import server.framework.logic.AbstractBusinessLogic;
import java.util.List;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import server.framework.common.Constant;
import java.util.Set;
import db.essp.issue.IssueCategoryType;
import java.util.Iterator;
/**
 * 处理查询IssueCategoryType的逻辑
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 * @property0*/
public class LgCategoryList extends AbstractBusinessLogic{
  public LgCategoryList() {
  }
  /**
   * 根据Issue Type查找可用的IssueCategoryType，并按sequence升序排列（不查找IssueCategoryValue） <br>
   * 查询方法：<br>
   * "from IssueCategoryType as category " +                                               <br>
      "where category.comp_id.typeName=:typeName " +                                       <br>
      "and category.status=:status order by category.sequence asc"                         <br>
   * Attention:IssueCategoryType.hbm.xml中categoryValues对应的Set元素必须设置lazy="true"
   * @param typeName String
   * @throws BusinessException
   * @return List
   */
  public List list(String typeName) throws BusinessException{
    Session s = null;
    try {
        s = this.getDbAccessor().getSession();
        List result = s.createQuery("from IssueCategoryType as category " +
                        "where category.comp_id.typeName=:typeName " +
                        "and category.rst=:rst " +
                        "order by category.sequence asc")
          .setString("typeName",typeName)
          .setString("rst",Constant.RST_NORMAL)
          .list();
        return result;
    } catch (Exception ex) {
        log.error(ex);
        throw new BusinessException("issue.category.list.exception","list issue category error!");
    }
  }
  /**
   * 根据Issue Type查找可用的IssueCategoryType，并按sequence升序排列（查找IssueCategoryValue）  <br>
   * 1. 根据list()查找Issue Type对应的所有IssueCategoryType                                     <br>
   * 2. 每一个IssueCategoryType遍历其categoryValues集合，Hibernate将自动查找对应的所有IssueCategoryValue
   * Attention:对categoryValues集合的过滤条件（status='1'）放在IssueCategoryType.hbm.xml<set>元素配置中
   * @throws BusinessException
   * @return List
   */
  public List listWithValue(String typeName) throws BusinessException{
    List result = this.list(typeName);
    Iterator it = result.iterator();
    while(it.hasNext()){
        IssueCategoryType categoryType = (IssueCategoryType)it.next();
        Set values = categoryType.getCategoryValues();
        values.iterator();
    }
    return result;
  }
}
