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
 * �����ѯIssueCategoryType���߼�
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
   * ����Issue Type���ҿ��õ�IssueCategoryType������sequence�������У�������IssueCategoryValue�� <br>
   * ��ѯ������<br>
   * "from IssueCategoryType as category " +                                               <br>
      "where category.comp_id.typeName=:typeName " +                                       <br>
      "and category.status=:status order by category.sequence asc"                         <br>
   * Attention:IssueCategoryType.hbm.xml��categoryValues��Ӧ��SetԪ�ر�������lazy="true"
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
   * ����Issue Type���ҿ��õ�IssueCategoryType������sequence�������У�����IssueCategoryValue��  <br>
   * 1. ����list()����Issue Type��Ӧ������IssueCategoryType                                     <br>
   * 2. ÿһ��IssueCategoryType������categoryValues���ϣ�Hibernate���Զ����Ҷ�Ӧ������IssueCategoryValue
   * Attention:��categoryValues���ϵĹ���������status='1'������IssueCategoryType.hbm.xml<set>Ԫ��������
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
