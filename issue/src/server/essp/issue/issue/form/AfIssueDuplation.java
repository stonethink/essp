package server.essp.issue.issue.form;

import java.util.*;

import org.apache.struts.action.*;

public class AfIssueDuplation extends ActionForm{
    private String accountRid;
    private String rid;
    private String typeName;
    private String statusName;

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

    public String getAccountRid() {
        return accountRid;
    }

    public String getRid() {
        return rid;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setDuplationIssue(String duplationIssue) {
        this.duplationIssue = duplationIssue;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setAccountRid(String accountRid) {
        this.accountRid = accountRid;
    }

}
