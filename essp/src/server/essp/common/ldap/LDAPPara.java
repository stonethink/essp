package server.essp.common.ldap;

import java.util.List;


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
public class LDAPPara {
    private String domain;
    private List sites;
    private String ldapUrl;
    private String ldapBaseDN;
    private String ldapConnectFlag;

    public LDAPPara() {
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setSites(List sites) {
        this.sites = sites;
    }

    public void setLdapUrl(String ldapUrl) {
        this.ldapUrl = ldapUrl;
    }

    public void setLdapBaseDN(String ldapBaseDN) {
        this.ldapBaseDN = ldapBaseDN;
    }

    public void setLdapConnectFlag(String ldapConnectFlag) {
        this.ldapConnectFlag = ldapConnectFlag;
    }

    public String getDomain() {
        return domain;
    }

    public List getSites() {
        return sites;
    }

    public String getLdapUrl() {
        return ldapUrl;
    }

    public String getLdapBaseDN() {
        return ldapBaseDN;
    }

    public String getLdapConnectFlag() {
        return ldapConnectFlag;
    }

//    public static void main(String[] args) {
//        LDAPConfig ldapconfig = new LDAPConfig();
//    }
}
