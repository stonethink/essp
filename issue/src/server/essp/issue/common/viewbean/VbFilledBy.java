package server.essp.issue.common.viewbean;

import java.io.*;

public class VbFilledBy implements Serializable {
    private String loginid;
    private String name;
    private String phone;
    private String fax;
    private String email;

    public VbFilledBy() {
    }

    public String getEmail() {
        return email;
    }

    public String getFax() {
        return fax;
    }

    public String getPhone() {
        return phone;
    }

    public String getLoginid() {
        return loginid;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
