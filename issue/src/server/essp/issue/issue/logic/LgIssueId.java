package server.essp.issue.issue.logic;

import server.essp.issue.common.logic.AbstractISSUELogic;
import server.framework.common.BusinessException;
import server.essp.issue.issue.viewbean.VbIssueId;
import net.sf.hibernate.Session;
import server.essp.issue.issue.form.AfIssueId;
import net.sf.hibernate.Query;
import java.util.List;
import db.essp.issue.Issue;

public class LgIssueId extends AbstractISSUELogic {
    public VbIssueId getCheckIssueId(server.essp.issue.issue.form.AfIssueId
                                     afIssueId) throws
        BusinessException {
        try {
            Session session = this.getDbAccessor().getSession();
            Issue issue = new Issue();
            String accountId = afIssueId.getAccountId();
            String issueId = afIssueId.getIssueId();
            VbIssueId webVo = new VbIssueId();
            Query q;
            q = session.createQuery("from Issue s where s.accountId=" +
                                    accountId + " and s.issueId='" + issueId +"'");
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
