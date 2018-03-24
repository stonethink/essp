package server.essp.issue.issue.sendmail.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import db.essp.issue.IssueMailDefaultTemp;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import net.sf.hibernate.*;
import java.util.List;

public class LgDefaultTemplate extends AbstractISSUELogic {
    public LgDefaultTemplate() {
    }

    public IssueMailDefaultTemp getByCondition(Long acntRid, String issueType,
                                               String cardType) {
        IssueMailDefaultTemp defaultTemp = null;
        String querySql = "from IssueMailDefaultTemp as t where t.acntRid='"
                          + acntRid + "' and t.issuetype='" + issueType
                          + "' and t.cardtype='" + cardType + "'";
        try {
            Session session = (Session)this.getDbAccessor().getSession();
            List list = session.createQuery(querySql).list();
            if (list.size() > 0) {
                defaultTemp = (IssueMailDefaultTemp) list.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "get default template error");
        }
        return defaultTemp;
    }

    //如果getByCondition得到的对象为空的话，进行Add操作
    public void add(IssueMailDefaultTemp defaultTemp) {
        try {
            this.getDbAccessor().save(defaultTemp);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("", "add default template error");
        }
    }

    public boolean isExist(Long acntRid, String issueType,
                           String cardType) {
        boolean flag = false;
        String querySql = "from IssueMailDefaultTemp as t where t.acntRid='"
                          + acntRid + "' and t.issuetype='" + issueType
                          + "' and t.cardtype='" + cardType + "'";

        try {
            Session session = (Session)this.getDbAccessor().getSession();
            List list = session.createQuery(querySql).list();
            if (list.size() > 0) {
                flag = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("",
                "check default template record is exist error");
        }
        return flag;
    }

}
