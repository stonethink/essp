package server.essp.issue.issue.viewbean;

import java.util.List;

public class VbIssueDuplation {
    private String duplationIssue;
    private String status;
    private List statusList;
    public String getDuplationIssue() {
        return duplationIssue;
    }

    public String getStatus() {
        return status;
    }

    public List getStatusList() {
        return statusList;
    }

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDuplationIssue(String duplationIssue) {
        this.duplationIssue = duplationIssue;
    }

}
