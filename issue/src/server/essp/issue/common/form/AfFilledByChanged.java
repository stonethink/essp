package server.essp.issue.common.form;

import org.apache.struts.action.*;

public class AfFilledByChanged extends ActionForm {
    private String accountRid;
    private String filledBy;

    public AfFilledByChanged() {
    }

    public String getAccountRid() {
        return accountRid;
    }

    public String getFilledBy() {
        return filledBy;
    }

    public void setAccountRid(String accountRid) {
        this.accountRid = accountRid;
    }

    public void setFilledBy(String filledBy) {
        this.filledBy = filledBy;
    }
}
