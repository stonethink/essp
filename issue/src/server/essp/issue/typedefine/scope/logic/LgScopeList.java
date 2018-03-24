package server.essp.issue.typedefine.scope.logic;

import java.util.List;

import com.wits.util.Parameter;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import server.framework.logic.AbstractBusinessLogic;
import java.util.Iterator;
import java.util.ArrayList;
import db.essp.issue.IssuePriority;
import server.framework.taglib.util.SelectOptionImpl;
import db.essp.issue.IssueScope;

public class LgScopeList extends AbstractBusinessLogic {
    /**
     * ����TypeName��ѯStatus���õ�IssueScope�б�����sequence��������  <br>
     * ��ѯ������<br>
      "from IssueScope as scope " +                                 <br>
      "where scope.comp_id.typeName=:typeName " +                    <br>
      "and scope.status=:status order by scope.sequence asc"
     * @param typeName String
     * @throws BusinessException
     * @return List
     */
    public List list(String typeName) throws BusinessException {
        try {
            //����TypeName��ѯStatus���õ�IssueScope
            Session session = this.getDbAccessor().getSession();
            List result = session.createQuery("from IssueScope as scope " +
                                              "where scope.comp_id.typeName=:typeName " +
                                              "and scope.rst=:rst order by scope.sequence asc")
                          .setString("typeName", typeName)
                          .setString("rst", Constant.RST_NORMAL)
                          .list();
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.scope.list.exception",
                                        "List issuetype scope error!");
        }
    }

    /**
     * e��ѯ���п��õ�IssueScope������sequence�������� <br>
     * @param typeName String
     * @throws BusinessException
     * @return List {optxxx(name,value)}
     */
    public List listAll() throws BusinessException {
        try {
            Session session = this.getDbAccessor().getSession();
            List result = session.createQuery("from IssueScope as scope " +
                                              "where scope.rst=:rst " +
                                              "order by scope.sequence")
                          .setString("rst",Constant.RST_NORMAL)
                          .list();
            return result;
        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.scope.list.all.exception",
                                        "List all issue scope error!");
        }
    }

    /**
     * �г����п���IssueScope����sequence�������У���ת����IssueScope����ѡ��SelectOptionImpl(name,value)
     *   name = IssueScope.typeName                                          <br>
     *   value = IssueScope.scope
     * @return List
     * @throws BusinessException
     */
    public List listAllOpt() throws BusinessException {
        List models = this.listAll();
        List result = new ArrayList(models.size());
        Iterator i = models.iterator();
        while(i.hasNext()){
           IssueScope scope = (IssueScope) i.next();
           SelectOptionImpl opt = new SelectOptionImpl(scope.getComp_id().getTypeName(),
                                                        scope.getComp_id().getScope());
           result.add(opt);
        }
        return result;
    }
}
