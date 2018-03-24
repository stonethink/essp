package server.essp.common.ldap;

import org.apache.log4j.Category;
import com.wits.util.Config;
import java.util.HashMap;
import java.util.ArrayList;
import com.wits.util.StringUtil;
import java.util.List;
import c2s.essp.common.user.DtoUser;

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
public class LDAPConfig {
    static Category log = Category.getInstance("server");
    private static Config cfg = new Config("ldap");
    private static HashMap domainConfigs = new HashMap();
    private static String defaultDomainId = null;
    private static List domainIdList;
    private static boolean debugFlag = true;
//    private static String debugUserId;
//    private static String debugUserPsw;
//    private static String debugUserDomain;
    private static DtoUser debugUserInfo = null;

    public LDAPConfig() {
    }

    public static String getDefaultDomainId() {
        if (defaultDomainId == null) {
            defaultDomainId = cfg.getValue("Default_Domain");
            if (defaultDomainId == null || defaultDomainId.length() == 0) {
                defaultDomainId = "tp";
            }
        }
        return defaultDomainId;
    }

    private static void getDebugUserId() {
        String debugUserId = cfg.getValue("Debug_User_Id");
        if (debugUserId == null || debugUserId.length() == 0) {
            debugFlag = false;
        } else {
            debugUserInfo.setUserLoginId(debugUserId);
        }
    }

    private static void getDebugUserPsw() {
        String debugUserPsw = cfg.getValue("Debug_User_Psw");
        if (debugUserPsw == null || debugUserPsw.length() == 0) {
            debugFlag = false;
        } else {
            debugUserInfo.setPassword(debugUserPsw);
        }
    }

    private static void getDebugUserDomain() {
        String debugUserDomain = cfg.getValue("Debug_User_Domain");
        if (debugUserDomain == null || debugUserDomain.length() == 0) {
            debugFlag = false;
        } else {
            debugUserInfo.setDomain(debugUserDomain);
        }
    }

    public static DtoUser getDebugUserInfo() {
        if (debugUserInfo == null) {
            debugUserInfo = new DtoUser();
            getDebugUserId();
            getDebugUserPsw();
            getDebugUserDomain();
        }
        if (debugFlag) {
            return debugUserInfo;
        } else {
            return null;
        }
    }


    public static List getDomainIdList() {
        if (domainIdList == null) {
            domainIdList = new ArrayList();
            String listStr = cfg.getValue("All_Domains");
            if (listStr != null) {
                String[] sp = StringUtil.split(listStr, ",");
                for (int i = 0; i < sp.length; i++) {
                    String dftDefaultDomainId = getDefaultDomainId();
                    if (dftDefaultDomainId.equals(sp[i])) {
                        domainIdList.add(0, sp[i]);
                    } else {
                        domainIdList.add(sp[i]);
                    }
                }
            }
        }

        return domainIdList;
    }

    public static LDAPPara getLDAPPara() {
        LDAPPara para = getLDAPPara("");
        return para;
    }

    public static LDAPPara getLDAPPara(String key) {
        LDAPPara para = new LDAPPara();
        String tmpKey = null;
        String ldapKey = null;
        if (key == null || key.length() == 0) {
            tmpKey = getDefaultDomainId();
            ldapKey = getDefaultDomainId();
        } else {
            tmpKey = key + ".";
            ldapKey = key;
        }

        if (domainConfigs.containsKey(ldapKey)) {
            para = (LDAPPara) domainConfigs.get(ldapKey);
        } else {
            para = getPara(tmpKey);
            domainConfigs.put(para.getDomain(), para);
        }

        return para;
    }

    private static LDAPPara getPara(String tmpKey) {
        /*
         tp.Domain = tp
         tp.Site = tp,jp,sh
         tp.LDAP_URL = ldap://10.1.1.1:389/
         tp.LDAP_Base_DN = "DC=wistronits,DC=com"
         tp.LDAP_Connect_Flag = simple
         */
        LDAPPara para = new LDAPPara();
        para.setDomain(cfg.getValue(tmpKey + "Domain"));
        String sitesStr = cfg.getValue(tmpKey + "Site");
        if (sitesStr == null) {
            para.setSites(null);
        } else {
            String[] sp = StringUtil.split(sitesStr, ",");
            ArrayList siteList = new ArrayList();
            for (int i = 0; i < sp.length; i++) {
                siteList.add(sp[i]);
            }
            para.setSites(siteList);
        }
        para.setLdapUrl(cfg.getValue(tmpKey + "LDAP_URL"));
        para.setLdapBaseDN(cfg.getValue(tmpKey + "LDAP_Base_DN"));
        para.setLdapConnectFlag(cfg.getValue(tmpKey + "LDAP_Connect_Flag"));

        return para;
    }

    public static void main(String[] args) {
        LDAPConfig ldapconfig = new LDAPConfig();
    }
}
