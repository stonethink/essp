package itf.issue;

import c2s.essp.common.issue.IDtoIssue;
import c2s.essp.common.account.IDtoAccount;

public interface IIssueUtil {
    public String addIssue(IDtoIssue issue, boolean isMail);

    public String updateIssue(IDtoIssue issue, boolean isMail);

    public IDtoIssue getIssue(String issueId);

    public String generateIssueId(String issueType, Long acntRid, IDtoAccount account);
}
