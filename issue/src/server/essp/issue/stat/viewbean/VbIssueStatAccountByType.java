package server.essp.issue.stat.viewbean;

/**
 *
 * <p>Title: 统计Account各个每个IssueType下各状态Issue的数量</p> </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VbIssueStatAccountByType extends VbIssueStatPerAccount{
    private String issueType;
    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
}
