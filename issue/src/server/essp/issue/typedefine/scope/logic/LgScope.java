package server.essp.issue.typedefine.scope.logic;

import java.math.*;
import java.util.*;
import db.essp.issue.*;
import net.sf.hibernate.*;
import server.essp.issue.typedefine.scope.form.*;
import server.framework.common.*;
import server.framework.logic.*;
import db.essp.issue.IssueScope;
/**
 * 处理IssueScope业务逻辑
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class LgScope extends AbstractBusinessLogic {
  /**
   * 依据AfScope主键typeName,scope载入IssueScope
   * @param typeName String
   * @param scope String
   * @throws BusinessException
   * @return IssueScope
   */
  public IssueScope load(String typeName,String scope) throws BusinessException {
    IssueScopePK pk = new IssueScopePK(typeName,scope);
    try {
      Session s =  this.getDbAccessor().getSession();
      IssueScope issueScope = (IssueScope)s.load(
                IssueScope.class, pk);
      return issueScope;
    } catch (Exception ex) {
      log.error(ex);
      throw new BusinessException("issue.scope.load.exception",
                                  "load issuetype scope error!");
    }
  }

  /**
   * 依据传入的AfScope新增IssueScope对象: <br>
   * 1. 判断该IssueType中是否存在同名的Issusescope,判断方法为： <br>
   * 2. 根据主键typeName和scope使用Session的get()获得IssueScope对象<br>
   * 3. if IssueScope不为空　                            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
   *          if    IssueScope状态可用,  报错退出；                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
   *          else  设置IssueScope状态为可用，并覆盖输入IssueScope属性；  <br>
   *    else  将AfScope新增到IssueScope;　
   * @param form AfScope
   * @throws BusinessException
   */
  public void add(AfScope form) throws BusinessException {
    String typeName = form.getTypeName();
    String scope = form.getScope();
    IssueScopePK pk = new IssueScopePK(typeName,scope);
    try {
      Session s = this.getDbAccessor().getSession();
      IssueScope issueScope = (IssueScope)s.get(IssueScope.class,pk);
      if (issueScope != null) {
        if(Constant.RST_NORMAL.equals(issueScope.getRst())){
          throw new BusinessException("issue.scope.exist",
                                      "Issuetype scope's sequence or name has existed!!");
        }
        else{
          c2s.dto.DtoUtil.copyProperties(issueScope, form);
          issueScope.setRst(Constant.RST_NORMAL);
          this.getDbAccessor().update(issueScope);
        }
      }else{
        issueScope = new IssueScope(pk);
        issueScope.setRst(Constant.RST_NORMAL);
        c2s.dto.DtoUtil.copyProperties(issueScope,form);
        s.save(issueScope);
      }
      s.flush();
    } catch (BusinessException ex) {
      throw ex;
    } catch (Exception ex) {
      log.error(ex);
      throw new BusinessException("issue.scope.add.exception",
                                  "add issuetype scope error!");
    }
  }

  /**
   * 依据传入的AfScope修改IssueScope对象<br>
   * 1  根据主键typeName和scope load() IssueScope对象  <br>
   * 2  设置IssueScope对象的其他属性  <br>
   * @param oldScope String
   * @param form AfScope
   * @throws BusinessException
   */
  public void update(AfScope form) throws BusinessException {
    String typeName = form.getTypeName();
    String scope = form.getScope();
    try {
      IssueScope issueScope = this.load(typeName,scope);
      c2s.dto.DtoUtil.copyProperties(issueScope,form);
      this.getDbAccessor().update(issueScope);
    } catch (Exception ex) {
      log.error(ex);
      throw new BusinessException("issue.scope.update.exception",
                                  "update issuetype scope error!");
    }
  }

  /**
   * 依据typeName，scope逻辑删除IssueScope   <br>
   * 1.  根据主键typeName，scope load() IssueScope对象   <br>
   * 2.  设置IssueScope.status为disable  <br>
   * @param typeName String
   * @param scope String
   * @throws BusinessException
   */
  public void delete(String typeName, String scope) throws BusinessException {
    //逻辑删除IssueScope，设置status=0
    try {
      IssueScope issueScope = this.load(typeName,scope);
      issueScope.setRst(Constant.RST_DELETE);
      this.getDbAccessor().update(issueScope);
    } catch (Exception ex) {
      log.error(ex);
      throw new BusinessException("issue.scope.delete.exception",
                                  "delete issuetype scope error!");
    }

  }

  public long getMaxSequence(String typeName) {
      long sequence=1;
      try {
          Session session = this.getDbAccessor().getSession();
          Query q=session.createQuery("from IssueScope s where s.comp_id.typeName='"+typeName+"' order by s.sequence desc");
          List results=q.list();
          if(results!=null && results.size()>0) {
              IssueScope firstRecord=(IssueScope)results.get(0);
              sequence=firstRecord.getSequence().longValue()+1;
          }
      } catch(Exception e) {

      }
      return sequence;
    }
}
