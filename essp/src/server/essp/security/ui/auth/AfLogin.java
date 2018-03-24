package server.essp.security.ui.auth;

import org.apache.struts.action.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AfLogin extends ActionForm {
    private String userType;
    private String domain;
    private String loginId;
    private String password;

    public AfLogin() {
    }

    public void setDomain(String site) {
        this.domain = site;
    }

    public String getLoginId() {
        return loginId;
    }


    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public String getDomain() {
        return domain;
    }

    public String getUserType() {
        return userType;
    }
}
