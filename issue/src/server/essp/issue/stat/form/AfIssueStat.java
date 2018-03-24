package server.essp.issue.stat.form;

import org.apache.struts.action.ActionForm;
public class AfIssueStat extends ActionForm {

    /** identifier field */
    private String accountId;
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
