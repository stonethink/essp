package server.essp.issue.common.form;

import org.apache.struts.action.*;

public class AfIssueOptions extends ActionForm {
    private String issueType;
    private String userType;
    private String prioritySelectbox;
    private String scopeSelectbox;
    private String statusSelectbox;

    public AfIssueOptions() {
    }

    public String getIssueType() {
        return issueType;
    }

    public String getPrioritySelectbox() {
        return prioritySelectbox;
    }

    public String getScopeSelectbox() {
        return scopeSelectbox;
    }

    public String getStatusSelectbox() {
        return statusSelectbox;
    }

    public String getUserType() {
        return userType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setPrioritySelectbox(String prioritySelectbox) {
        this.prioritySelectbox = prioritySelectbox;
    }

    public void setScopeSelectbox(String scopeSelectbox) {
        this.scopeSelectbox = scopeSelectbox;
    }

    public void setStatusSelectbox(String statusSelectbox) {
        this.statusSelectbox = statusSelectbox;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
