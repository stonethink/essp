package server.essp.issue.issue.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import server.essp.issue.issue.viewbean.VbIssueId;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import db.essp.issue.Issue;
import net.sf.hibernate.Query;
import java.util.List;

public class LgUpdateIssueId extends AbstractISSUELogic {
    public VbIssueId getCheckIssueId(server.essp.issue.issue.form.AfIssueId
                                      afIssueId) throws
         BusinessException {
         try {
            Session session = this.getDbAccessor().getSession();
            Issue issue = new Issue();
            String accountId = afIssueId.getAccountId();
            String issueId = afIssueId.getIssueId();
            String rid=afIssueId.getRid();
            VbIssueId webVo = new VbIssueId();
            Query q;
            q = session.createQuery("from Issue s where s.accountId=" +
                                    accountId + " and s.issueId=" + issueId+" and s.rid="+rid);
            List result = q.list();
            if (result == null || result.size() == 0) {
                String checkIssueId = "Ok";
                webVo.setCheckIssueId(checkIssueId);
            } else {
                String checkIssueId = "Error";
                webVo.setCheckIssueId(checkIssueId);
            }
            return webVo;
        } catch (Exception e) {
            throw new BusinessException(e);
        }

    }

}

