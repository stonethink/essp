package server.essp.security.ui.auth;

import org.apache.struts.action.*;

public class AfAuthorize extends ActionForm {
    private String loginId;
    private String loginName;
    public String getLoginId() {
        return loginId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginId(String LoginId) {
        this.loginId = LoginId;
    }

    public void setLoginName(String LoginName) {
        this.loginName = LoginName;
    }
}
