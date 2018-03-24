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
 * ����IssueScopeҵ���߼�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class LgScope extends AbstractBusinessLogic {
  /**
   * ����AfScope����typeName,scope����IssueScope
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
   * ���ݴ����AfScope����IssueScope����: <br>
   * 1. �жϸ�IssueType���Ƿ����ͬ����Issusescope,�жϷ���Ϊ�� <br>
   * 2. ��������typeName��scopeʹ��Session��get()���IssueScope����<br>
   * 3. if IssueScope��Ϊ�ա�                            <br>&ensp;&ensp;&ensp;&ensp;&ensp;
   *          if    IssueScope״̬����,  �����˳���                               <br>&ensp;&ensp;&ensp;&ensp;&ensp;
   *          else  ����IssueScope״̬Ϊ���ã�����������IssueScope���ԣ�  <br>
   *    else  ��AfScope������IssueScope;��
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
   * ���ݴ����AfScope�޸�IssueScope����<br>
   * 1  ��������typeName��scope load() IssueScope����  <br>
   * 2  ����IssueScope�������������  <br>
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
   * ����typeName��scope�߼�ɾ��IssueScope   <br>
   * 1.  ��������typeName��scope load() IssueScope����   <br>
   * 2.  ����IssueScope.statusΪdisable  <br>
   * @param typeName String
   * @param scope String
   * @throws BusinessException
   */
  public void delete(String typeName, String scope) throws BusinessException {
    //�߼�ɾ��IssueScope������status=0
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
