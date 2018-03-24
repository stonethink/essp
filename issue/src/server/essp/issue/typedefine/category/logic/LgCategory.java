package server.essp.issue.typedefine.category.logic;

import java.util.*;

import db.essp.issue.*;
import net.sf.hibernate.*;
import server.essp.issue.typedefine.category.form.*;
import server.framework.common.*;
import server.framework.logic.*;
/**
 * 处理IssueCategoryType业务逻辑
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class LgCategory extends AbstractBusinessLogic {
  public LgCategory() {
  }
  /**
   * 根据AfCategory新增IssueCategoryType对象                                 <br>
   * 1. 判断该IssueType中是否存在同名的IssueCategoryType,判断方法为：            <br>
   * 2. 根据主键typeName和categoryName使用Session的get()获得IssueCategoryType对象<br>
   * 3. if IssueCategoryType不为空　            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
   *          if    IssueCategoryType状态可用,  报错退出；                        <br>&ensp;&ensp;&ensp;&ensp;&ensp;
   *          else  设置IssueCategoryType状态为可用，并覆盖输入IssueCategoryType属性； <br>
   *    else  将AfCategory新增到IssueCategoryType;　
   * @param form AfCategory
   * @throws BusinessException
   */
  public void add(AfCategory form)  throws BusinessException {
      String typeName = form.getTypeName();
      String categoryName = form.getCategoryName();
      IssueCategoryTypePK pk = new IssueCategoryTypePK(typeName,categoryName);
      try {
          Session s = this.getDbAccessor().getSession();
          IssueCategoryType category = (IssueCategoryType) s.get(IssueCategoryType.class,pk);
        if(category != null ) {
            if(Constant.RST_NORMAL.equals(category.getRst())){
                throw new BusinessException("issue.category.exist","Issuetype category's name has existed!!");
            }
            else{
                c2s.dto.DtoUtil.copyProperties(category, form);
                category.setRst(Constant.RST_NORMAL);
                this.getDbAccessor().update(category);
            }
        }else{
            category = new IssueCategoryType(pk);
            c2s.dto.DtoUtil.copyProperties(category, form);
            category.setRst(Constant.RST_NORMAL);
            s.save(category);
        }
        s.flush();
      } catch (Exception ex) {
          log.error(ex);
          throw new BusinessException("issue.category.add.exception","add issuetype category error!");
      }
  }

  /**
   * 依据typeName，categoryName逻辑删除IssueCategoryType            <br>
   * 1.  根据主键typeName，categoryName load() IssueCategoryType对象<br>
   * 2.  设置IssueCategoryType.status为disable                     <br>
   * 3.  设置IssueCategoryType对应的IssueCategoryValue.status为disable
   * @param typeName String
   * @param categoryName String
   * @throws BusinessException
   */
  public void delete(String typeName,String categoryName) throws BusinessException {
      try{
          IssueCategoryType category = this.load(typeName,categoryName);
          category.setRst(Constant.RST_DELETE);
          Iterator i = category.getCategoryValues().iterator();
          while(i.hasNext()){
              IssueCategoryValue value = (IssueCategoryValue) i.next();
              value.setRst(Constant.RST_DELETE);
          }
          this.getDbAccessor().update(category);
      }catch(Exception ex){
          log.error(ex);
          throw new BusinessException("issue.category.delete.exception","delete issuetype category error!");
      }
  }
  /**
   * 依据传入的AfCategory修改IssueCategoryType对象<br>
   * 1  根据主键typeName和categoryName load() IssueCategoryType对象<br>
   * 2  设置IssueCategoryType对象的其他属性<br>
   * @param form AfCategory
   * @throws BusinessException
   */
  public void update(AfCategory form) throws BusinessException {
      try{
          IssueCategoryType category = this.load(form.getTypeName(),
                                       form.getCategoryName());
          c2s.dto.DtoUtil.copyProperties(category,form);
          this.getDbAccessor().update(category);
      }catch(Exception ex){
          log.error(ex);
          throw new BusinessException("issue.category.update.exception","update issuetype category  error!");
      }
  }
  /**
   * 根据主键typeName,categoryName查找IssueCategoryType
   * @param typeName String
   * @param categoryName String
   * @throws BusinessException
   * @return IssueCategoryType
   */
  public IssueCategoryType load(String typeName,String categoryName) throws BusinessException {
    IssueCategoryTypePK pk = new IssueCategoryTypePK(typeName,categoryName);
    try {
        Session s = this.getDbAccessor().getSession();
        IssueCategoryType category = (IssueCategoryType) s.load(IssueCategoryType.class,pk);
        return category;
    } catch (Exception ex) {
        log.error(ex);
        throw new BusinessException("issue.category.load.exception","load category error!");
    }
  }

  public long getMaxSequence(String typeName) {
      long sequence=1;
      try {
          Session session = this.getDbAccessor().getSession();
          Query q=session.createQuery("from IssueCategoryType s where s.comp_id.typeName='"+typeName+"' order by s.sequence desc");
          List results=q.list();
          if(results!=null && results.size()>0) {
              IssueCategoryType firstRecord=(IssueCategoryType)results.get(0);
              sequence=firstRecord.getSequence().longValue()+1;
          }
      } catch(Exception e) {

      }
      return sequence;
    }
}
