package server.essp.issue.typedefine.status.logic;

import server.framework.logic.AbstractBusinessLogic;
import java.util.List;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import com.wits.util.Parameter;
import server.framework.common.Constant;
import db.essp.issue.IssueScope;
import java.util.Iterator;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;
import db.essp.issue.IssueStatus;

public class LgStatusList extends AbstractBusinessLogic {
    public LgStatusList() {
    }

    /**
       * ����TypeName��ѯStatus���õ�IssueStatus�б�����sequence��������
       * ��ѯ������
       "from IssueStatus as status " +
       "where status.comp_id.typeName =: typeName " +
       "and status.rst=:rst order by status.sequence asc"
       * @param typeName String
       * @throws BusinessException
       * @return List
       */
      public List list(String typeName) throws BusinessException {
          try {
              //����TypeName��ѯStatus���õ�IssueStatus
              Session session = this.getDbAccessor().getSession();
              List result = session.createQuery("from IssueStatus as status " +
                          "where status.comp_id.typeName=:typeName " +
                          "and status.rst=:rst order by status.sequence asc")
                         .setString("typeName", typeName)
                         .setString("rst", Constant.RST_NORMAL)
                         .list();
                return result;

          } catch (Exception ex) {
              log.error(ex);
              ex.printStackTrace();
              throw new BusinessException("issue.status.list.error","list Issuetype Status error");
          }
      }

      /**
       * �г����п��õ�IssueStatus,��ת����IssueScope����ѡ��SelectOptionImpl(name,value)
       *   name = IssueStatus.typeName                                          <br>
       *   value = IssueStatus.stattusName
       * @param typeName String
       * @throws BusinessException
       * @return List {optxxx(name,value)}
       */
      public List listAllOpt(String typeName) throws BusinessException {
          List models = this.listAll();
          List result = new ArrayList(models.size());
          Iterator i = models.iterator();
          while(i.hasNext()){
             IssueStatus status = (IssueStatus) i.next();
             SelectOptionImpl opt = new SelectOptionImpl(status.getComp_id().getTypeName(),
                                                          status.getComp_id().getStatusName());
             result.add(opt);
          }
          return result;
      }
      /**
       * �г����п��õ�IssueStatus������sequence��������
       * @return List
       * @throws BusinessException
       */
      public List listAll()throws BusinessException {
          try {
              Session session = this.getDbAccessor().getSession();
              List result = session.createQuery("from IssueStatus as scope " +
                                                "where status.rst=:rst " +
                                                "order by status.sequence")
                            .setString("rst",Constant.RST_NORMAL)
                            .list();
              return result;
          } catch (Exception ex) {
              log.error(ex);
              ex.printStackTrace();
              throw new BusinessException("issue.status.list.all.exception",
                                          "List all issue status error!");
        }
      }
}
