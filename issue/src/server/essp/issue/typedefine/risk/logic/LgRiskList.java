package server.essp.issue.typedefine.risk.logic;

import server.framework.logic.AbstractBusinessLogic;
import java.util.List;
import server.framework.common.BusinessException;
import server.framework.common.Constant;
import net.sf.hibernate.Session;
public class LgRiskList extends AbstractBusinessLogic {

    public LgRiskList() {
    }

   /**
    * ����TypeName��ѯRisk���õ�IssueRisk�б�����sequence��������<br>
    * ��ѯ������<br>
    "from IssueRisk as risk " +
    "where risk.comp_id.typeName=:typeName " +
    "and risk.status=:status order by risk.sequence asc"
    * @param typeName String
    * @throws BusinessException
    * @return List
    */
    public List list(String typeName) throws BusinessException {
        try {
            //����TypeName��ѯStatus���õ�IssueStatus
            Session session = this.getDbAccessor().getSession();
            List result = session.createQuery("from IssueRisk as risk " +
                        "where risk.comp_id.typeName=:typeName " +
                        "and risk.rst=:rst order by risk.sequence asc")
                       .setString("typeName", typeName)
                       .setString("rst", Constant.RST_NORMAL)
                       .list();
              return result;

        } catch (Exception ex) {
            log.error(ex);
            ex.printStackTrace();
            throw new BusinessException("issue.risk.list.error","list Issuetype risk error");
          }
    }
}
