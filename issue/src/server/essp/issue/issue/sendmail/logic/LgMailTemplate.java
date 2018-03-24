package server.essp.issue.issue.sendmail.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import net.sf.hibernate.Session;
import java.util.List;
import db.essp.issue.IssueMailTemplate;
import server.framework.common.BusinessException;

public class LgMailTemplate extends AbstractISSUELogic {
    public LgMailTemplate() {
    }

    public String getTemplatePath(Long templateRid) {
        String tempPath = "";
        String queryStr = "from IssueMailTemplate as t where t.rid=" +
                          templateRid;
        try {
            Session session = this.getDbAccessor().getSession();
            List list = session.createQuery(queryStr).setMaxResults(1).list();
            tempPath = ((IssueMailTemplate) list.get(0)).getTemplatefilepath();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tempPath;

    }

    public List getTempList(String issueType, String cardType) {
        List result = null;
        String queryStr = "from IssueMailTemplate as t where (t.issuetype='"
                          + issueType +
                          "' or t.issuetype='ALL' ) and cardtype='" + cardType +
                          "' order by t.rid";
        try {
            Session session = this.getDbAccessor().getSession();
            result = session.createQuery(queryStr).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "list template error");
        }
        if (result == null) {
            throw new BusinessException("",
                "not template,please define before use.");
        }
        return result;
    }

    public static void main(String[] args) {
        LgMailTemplate logic = new LgMailTemplate();
        List l = logic.getTempList("PPR", "TOPIC");
        System.out.println(logic.getTemplatePath(new Long(1)));
        System.out.println(l.size());
    }
}
