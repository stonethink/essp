package server.essp.common.ldap;

import java.util.*;

import javax.naming.directory.*;

import org.apache.log4j.*;
import c2s.essp.common.user.DtoUserBase;
import com.wits.util.ThreadVariant;
import c2s.essp.common.user.DtoUser;
import javax.naming.*;
import c2s.essp.common.user.DtoUserInfo;

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
public class LDAPUtil extends LDAPUtilBase {
    public final static String ATT_NAME_LOGIN_ID = "SAMAccountName";
    public final static String ATT_NAME_NAME = "name";
    public final static String ATT_NAME_MAIL = "mail";
    public final static String ATT_NAME_PHONE = "phone";
    public final static String ATT_NAME_FAX = "fax";
    public final static String ATT_NAME_CN = "cn";
    public final static String ATT_NAME_OU = "ou";
    public final static String ATT_NAME_MANAGER = "manager";
    public final static String ATT_NAME_MANAGED_BY = "managedBy";
    public final static String ATT_NAME_MEMBER = "member";
    public final static String ATT_NAME_MEMBEROF = "memberOf";
    public final static String ATT_NAME_DISTINGUISHED_NAME =
            "distinguishedName";
    public final static String ATT_NAME_DESCRIPTION = "description";

    public final static String RESTRICT_CLASS_PERSON = "(objectClass=person)";
    public final static String RESTRICT_CLASS_USER = "(objectClass=user)";
    public final static String RESTRICT_CLASS_OU = "(objectClass=organizationalUnit)";
    public final static String RESTRICT_NOT_COMPUTER = "(objectClass=computer)";
    public final static String RESTRICT_PERSON = "(&" +
                                                 RESTRICT_CLASS_PERSON +
                                                 "(!" +
                                                 RESTRICT_NOT_COMPUTER + "))";
    public final static String RESTRICT_OBS =
            "(objectClass=organizationalUnit)";
    
    public final static String RESTRICT_GROUP =
        "(objectClass=group)";

    public final static String RESULT_TYPE_USER_BASE = "userBase";
    public final static String RESULT_TYPE_USER_INFO = "userInfo";
    public final static String RESULT_TYPE_DISTINGUISHED_NAME = "disName";
    public final static String RESULT_TYPE_MAX_PWD_AGE = "maxPwdAge";
    public final static String RESULT_TYPE_LAST_PWD_SET = "pwdLastSet";


    static Category log = Category.getInstance("server");

    private static String[]
            userBaseAttributes = new String[] {
                                 ATT_NAME_LOGIN_ID,
                                 ATT_NAME_NAME};

    private static String[]
            userInfoAttributes = new String[] {
                                 ATT_NAME_LOGIN_ID,
                                 ATT_NAME_NAME,
                                 ATT_NAME_MAIL};
    
    private static String[] 
            ouAttributes = new String[] {
    							ATT_NAME_OU,
    							ATT_NAME_NAME, 
    							ATT_NAME_DISTINGUISHED_NAME};
    private static String[]
            distinguishedName = new String[] {
                                ATT_NAME_DISTINGUISHED_NAME};
    
    private static String[] 
              groupAttributes = new String[] {
    							ATT_NAME_CN,
      							ATT_NAME_MAIL,
      							ATT_NAME_MEMBER,
      							ATT_NAME_DISTINGUISHED_NAME};

    private static String[]
            managedBy = new String[] {
                               ATT_NAME_MANAGED_BY};

    private static String[] pwdAge = new String[] {
                                     RESULT_TYPE_MAX_PWD_AGE,
                                     RESULT_TYPE_LAST_PWD_SET};


    String userDomain;
    String userLoginId;
    String userPassword;
//    String userLdapId; //domain + "\\" + loginId

    String searchDomain;

    /**
     * 可以直接从当前线程中获取登录用户的信息，但其调用的Action必须是重载AbstractESSPAction
     */
    public LDAPUtil() {
        //使用调试用户登录AD
        DtoUser dtoUser = LDAPConfig.getDebugUserInfo();
        if (dtoUser == null) {
            //如果不是调试模式从Session中取
            ThreadVariant thread = ThreadVariant.getInstance();
            dtoUser = (DtoUser) thread.get(DtoUser.SESSION_LOGIN_USER);
        }
        if (dtoUser != null) {
            this.setUserDomain(dtoUser.getDomain());
            this.setUserLoginId(dtoUser.getUserLoginId());
            this.setUserPassword(dtoUser.getPassword());
        }
    }

    public LDAPUtil(String userDomain, String userLoginId, String userPassword) {
        this.setUserDomain(userDomain);
        this.setUserLoginId(userLoginId);
        this.setUserPassword(userPassword);
    }

    /**
     * 取得系统所设置的Domain List
     * @return List
     */
    public static List getDomainIdList() {
        return LDAPConfig.getDomainIdList();
    }

    /**
     * 对用户进行认证，提供认证联接的Domain有两种可选方式：
     * 1、连接登录用户其所属的Domain，进行验证；
     * 2、连接系统所设置的Default_Domain，进行验证
     * @param userDomain String
     * @param userLoginId String
     * @param userPassword String
     * @return boolean
     */
    public static boolean authenticate(String userDomain, String userLoginId,
                                       String userPassword) {
        return authByUserDomain(userDomain, userLoginId, userPassword);
    }

    /**
    * 依据用户LoginId 在所有域中查找用户信息
    * @param userLoginId String
    * @return DtoUserBase
    */
   public DtoUser findUser(String userLoginId) {
       return findUser(DOMAIN_ALL, userLoginId);
   }


    /**
     * 依据用户所属的Domain及LoginId查找用户信息
     * @param userDomain String
     * @param userLoginId String
     * @return DtoUserBase
     */
    public DtoUser findUser(String userDomain, String userLoginId) {
//        List result = searchUserBases(userDomain, userLoginId, null, null);

        LDAPPara para = LDAPConfig.getLDAPPara(userDomain);
        String cn = para.getLdapBaseDN();
        String filterLoginId = "(" + ATT_NAME_LOGIN_ID + "=" + userLoginId +
                               ")";
        String filter = "(&" + filterLoginId + RESTRICT_PERSON + ")";
        List result = searchUserByFilter(userDomain, cn, filter,
                                         RESULT_TYPE_USER_INFO);
        if (result == null || result.size() == 0) {
            return null;
        }
        //先只查找DtoUserInfo中的信息
        DtoUserInfo userBase = (DtoUserInfo) result.get(0);
        DtoUser dtoUser = new DtoUser();
        dtoUser.setUserType(DtoUserBase.USER_TYPE_EMPLOYEE);
        dtoUser.setDomain(userDomain);
        //返回AD中的Login_id   modified by lipeng.xu
        dtoUser.setUserLoginId(userBase.getUserLoginId());
        dtoUser.setUserName(userBase.getUserName());
        dtoUser.setEmail(userBase.getEmail());
        return dtoUser;
    }
    
    /**
     * 查询指定OU下的所有人员<DtoUserInfo>
     * @param domain
     * @param ouId
     * @param includeSubOu
     * @return List<DtoUserInfo>
     */
    public List findUserInOu(String domain, String ouId, boolean includeSubOu) {
		String cn = findOUDistName(domain, ouId);
	    if(cn == null || "".equals(cn)) {
	    	return null;
	    }
	    String filter = RESTRICT_PERSON;
	    return searchUserByFilter(userDomain, cn, filter,
	                                       RESULT_TYPE_USER_INFO, includeSubOu);
    }
    
    /**
     * 查询指定OU下的所有人员<DtoUserInfo>
     * @param domain
     * @param ouId
     * @param includeSubOu
     * @return List<DtoUserInfo>
     */
    public List findUserInGroup(String domain, String groupDisName) {
	    LDAPPara para = LDAPConfig.getLDAPPara(domain);
        String cn = para.getLdapBaseDN();
        String filterMemberOf = "(" + ATT_NAME_MEMBEROF + "=" + groupDisName + ")";
        String filter = "(&" + filterMemberOf + RESTRICT_PERSON + ")";
	    return searchUserByFilter(domain, cn, filter,
	                                       RESULT_TYPE_USER_INFO, true);
    }

    /**
     * 查找指定loginId用户的签核者
     *      AD的manager属性
     * @param userLoginId String
     * @return String
     */
    public DtoUser findManager(String userDomain, String userLoginId) {
        String filterLoginId = "(" + ATT_NAME_LOGIN_ID + "=" + userLoginId + ")";
        String filter = "(&" + filterLoginId + RESTRICT_PERSON + ")";

        LDAPPara para = LDAPConfig.getLDAPPara(userDomain);
        String cn = para.getLdapBaseDN();
        DirContext dirContext = connect(userDomain, getLdapLoginId(),
                                        getUserPassword());
        List searchList = new ArrayList();
        try {
            searchList = searchContextSub(dirContext, cn, filter,
                                          new String[] {ATT_NAME_MANAGER});
        } catch (NamingException ex) {
            log.debug(ex);
        } finally {
            this.disConnect(dirContext);
        }
        if (searchList != null && searchList.size() > 0) {
            Map resultRowMap = (Map) searchList.get(0);
            String managerDisName = rowMap2ManagerDisName(resultRowMap);
            if(managerDisName == null || managerDisName.equals("")) return null;
            return findUserByDisName(userDomain, managerDisName);

        } else {
            return null;
        }
    }

    /**
     * 依据用户所属的Domain及LoginId查找所属机构列表
     * @param userDomain String
     * @param userLoginId String
     * @return List
     */
    public List findOU(String userDomain, String userLoginId) {
        LDAPPara para = LDAPConfig.getLDAPPara(userDomain);
        String cn = para.getLdapBaseDN();
        DirContext dirContext = connect(userDomain, getLdapLoginId(),
                                        getUserPassword());
        String filterLoginId = "(" + ATT_NAME_LOGIN_ID + "=" + userLoginId +
                               ")";
        String filter = "(&" + filterLoginId + RESTRICT_PERSON + ")";
        List searchList = null;
        Map resultRowMap = null;
        try {
            searchList = searchContextSub(dirContext, cn, filter,
                                          distinguishedName);
        } catch (NamingException ex) {
            log.debug(ex);
        } finally {
            this.disConnect(dirContext);
        }
        if (searchList != null && searchList.size() > 0) {
            resultRowMap = (Map) searchList.get(0);
        }
        return rowMap2OUList(resultRowMap);
    }
    
    /**
     * 查询指定OU的distinguishedName属性
     * @param ouDomain
     * @param ouId
     * @return
     */
    public String findOUDistName(String ouDomain, String ouId) {
    	LDAPPara para = LDAPConfig.getLDAPPara(userDomain);
        String cn = para.getLdapBaseDN();
        DirContext dirContext = connect(userDomain, getLdapLoginId(),
                                        getUserPassword());
        String filterOU = "(" + ATT_NAME_OU + "=" + ouId + ")";
        String filter = "(&" + filterOU + RESTRICT_CLASS_OU + ")";
        List searchList = null;
        Map resultRowMap = null;
        try {
            searchList = searchContextSub(dirContext, cn, filter, ouAttributes);
        } catch (NamingException ex) {
            log.debug(ex);
        } finally {
            this.disConnect(dirContext);
        }
        String ouDistName = null;
        if (searchList != null && searchList.size() > 0) {
            resultRowMap = (Map) searchList.get(0);
            List rsAttValList = (List)resultRowMap
            		.get(ATT_NAME_DISTINGUISHED_NAME.toLowerCase());
            if(rsAttValList != null && rsAttValList.size() > 0) {
            	ouDistName = (String) rsAttValList.get(0);
            }
        }
        return ouDistName;
    }
    
    /**
     * 查询指定OU的子孙OU
     * 	以指定OU的distinguishedName作为baseDN查询下面的所有OU
     * @param ouDomain
     * @param ouId
     * @return
     */
    public List findChildrenOU(String ouDomain, String ouId) {
        String cn = findOUDistName(ouDomain, ouId);
        if(cn == null || "".equals(cn)) {
        	return null;
        }
        DirContext dirContext = connect(userDomain, getLdapLoginId(),
                                        getUserPassword());
        String filter = RESTRICT_CLASS_OU;
        List searchList = null;
        Map resultRowMap = null;
        try {
            searchList = searchContextSub(dirContext, cn, filter, ouAttributes);
        } catch (NamingException ex) {
            log.debug(ex);
        } finally {
            this.disConnect(dirContext);
        }
        if(searchList == null) {
        	return null;
        }
        List ouList = new ArrayList();
        for (int i = 0; i < searchList.size(); i ++) {
            resultRowMap = (Map) searchList.get(i);
            List rsAttValList = (List)resultRowMap
    		.get(ATT_NAME_OU.toLowerCase());
		    if(rsAttValList != null && rsAttValList.size() > 0) {
		    	ouList.add(rsAttValList.get(0));
		    }
        }
        return ouList;
    }

    /**
     * 依据用户所属的Domain及LoginId查找直属OU
     * @param userDomain String
     * @param userLoginId String
     * @return String
     */
    public String findDirectOU(String userDomain, String userLoginId) {
        List ouList = findOU(userDomain, userLoginId);
        if(ouList != null && ouList.size() > 0) {
            return (String) ouList.get(0);
        }
        return null;
    }

    /**
     * 依据用户所属的Domain及ouId查找其Manager的DistinguishedName
     * TODO：需要补充完整
     * @param ouDomain String
     * @param ouId String
     * @return String
     */
    public String findOUManagerDisName(String ouDomain, String ouId) {
        LDAPPara para = LDAPConfig.getLDAPPara(ouDomain);
        String cn = para.getLdapBaseDN();
        DirContext dirContext = connect(ouDomain, getLdapLoginId(),
                                        getUserPassword());
        String filterManager = "(" + ATT_NAME_OU + "=" + ouId +
                               ")";
        String filter = "(&" + filterManager + RESTRICT_OBS + ")";
        List searchList = null;
        Map resultRowMap = null;
        try {
            searchList = searchContextSub(dirContext, cn, filter,
                                          managedBy);
        } catch (NamingException ex) {
            log.debug(ex);
        } finally {
            this.disConnect(dirContext);
        }
        if (searchList != null && searchList.size() > 0) {
            resultRowMap = (Map) searchList.get(0);
            return rowMap2ManagedByDisName(resultRowMap);
        } else {
            return null;
        }
    }
    
    /**
     * 依据用户所属的Domain及common name查找所属机构列表
     * @param userDomain String
     * @param group String
     * @return List <DtoUserInfo>
     */
    public List findGroup(String userDomain, String group, boolean userGrp) {
        LDAPPara para = LDAPConfig.getLDAPPara(userDomain);
        String cn = para.getLdapBaseDN();
        DirContext dirContext = connect(userDomain, getLdapLoginId(),
                                        getUserPassword());
        String filterLoginId = "(" + ATT_NAME_CN + "=" + group +
                               "*)";
        String filter = "(&" + filterLoginId + RESTRICT_GROUP + ")";
        List searchList = null;
        Map resultRowMap = null;
        try {
            searchList = searchContextSub(dirContext, cn, filter,
            												groupAttributes);
        } catch (NamingException ex) {
            log.debug(ex);
        } finally {
            this.disConnect(dirContext);
        }
        return rowMap2GroupList(searchList, userGrp);
    }
    
    /**
     * 依据用户所属的LonginId查找DistinguishedName
     * TODO：需要补充完整
     * @param domain String
     * @param loginId String
     * @return String
     */
    public String findDisNameByLonginId(String domain, String loginId) {
        LDAPPara para = LDAPConfig.getLDAPPara(domain);
        String cn = para.getLdapBaseDN();
        DirContext dirContext = connect(domain, getLdapLoginId(),
                                        getUserPassword());
        String filterDisN = "(" + ATT_NAME_LOGIN_ID + "=" + loginId +
                               ")";
        String filter = "(&" + filterDisN + RESTRICT_PERSON + ")";
        List searchList = null;
        Map resultRowMap = null;
        try {
            searchList = searchContextSub(dirContext, cn, filter,
                                          distinguishedName);
        } catch (NamingException ex) {
            log.debug(ex);
        }finally {
            this.disConnect(dirContext);
        }

        if (searchList != null && searchList.size() > 0) {
            resultRowMap = (Map) searchList.get(0);
            return rowMap2DisName(resultRowMap);
        } else {
            return null;
        }
    }

    /**
     * 依据用户所属的LonginId查找DistinguishedName
     * TODO：需要补充完整
     * @param domain String
     * @param loginId String
     * @return String
     */
    public String getPwdAge(String domain, String loginId) {
        LDAPPara para = LDAPConfig.getLDAPPara(domain);
        String cn = para.getLdapBaseDN();
        DirContext dirContext = connect(domain, getLdapLoginId(),
                                        getUserPassword());
        String filterDisN = "(" + ATT_NAME_LOGIN_ID + "=" + loginId +
                               ")";
        String filter = "(&" + filterDisN + RESTRICT_PERSON + ")";
        List searchList = null;
        Map resultRowMap = null;
        try {
            searchList = searchContextSub(dirContext, cn, filter,
                                          pwdAge);
        } catch (NamingException ex) {
            log.debug(ex);
        }finally {
            this.disConnect(dirContext);
        }

        if (searchList != null && searchList.size() > 0) {
            resultRowMap = (Map) searchList.get(0);
            return rowMap2DisName(resultRowMap);
        } else {
            return null;
        }
    }



    /**
     * 依据用户所属的Domain及distinguishedName查找用户信息
     * TODO：需要补充完整
     * @param disName String
     * @return DtoUser
     */
    public DtoUser findUserByDisName(String disName) throws Exception {
        String userDomain;
        try {
            String subDisName = disName.substring(disName.indexOf("DC"));
            userDomain = subDisName.substring(3, subDisName.indexOf(","));
        } catch (Exception ex) {
            throw new Exception("Distinguish Name Error!");
        }
        return findUserByDisName(userDomain,disName);
    }

    /**
     * 依据用户所属的Domain及distinguishedName查找用户信息
     * TODO：需要补充完整
     * @param userDomain String
     * @param disName String
     * @return DtoUser
     */
    public DtoUser findUserByDisName(String userDomain, String disName) {
        LDAPPara para = LDAPConfig.getLDAPPara(userDomain);
        String cn = para.getLdapBaseDN();
        String filterLoginId = "(" + ATT_NAME_DISTINGUISHED_NAME + "=" +
                               disName +
                               ")";
        String filter = "(&" + filterLoginId + RESTRICT_PERSON + ")";
        List result = searchUserByFilter(userDomain, cn, filter,
                                         RESULT_TYPE_USER_INFO);
        if (result == null || result.size() == 0) {
            return null;
        }
        //先只查找DtoUserInfo中的信息
        DtoUserInfo userBase = (DtoUserInfo) result.get(0);
        DtoUser dtoUser = new DtoUser();
        dtoUser.setUserType(DtoUserBase.USER_TYPE_EMPLOYEE);
        dtoUser.setDomain(userDomain);
        dtoUser.setUserLoginId(userBase.getUserLoginId());
        dtoUser.setUserName(userBase.getUserName());
        dtoUser.setEmail(userBase.getEmail());
        return dtoUser;
    }

    /**
     * 依据用户所属的Domain及loginId查找用户直属上司的信息
     * TODO：需要补充完整
     * @param userDomain String
     * @param loginId String
     * @return DtoUser
     */
    public DtoUser findDirectlyManager(String userDomain, String loginId) {
        List ouList = findOU(userDomain,loginId);
        if (ouList == null || ouList.size() == 0) {
            return null;
        }
        String managerDisName = findOUManagerDisName(userDomain,ouList.get(0).toString());
        if(managerDisName != null) {
            return findUserByDisName(userDomain,managerDisName);
        } else {
            return null;
        }
    }

    /**
     * 查找用户基本信息，主要为了查找用户的姓名
     * 输入：查找条件为loginId/userName/mail，并且查找条件是按OR运算，
     *      connectDomain取Default_Domain
     * 输出：List {DtoUserBase}
     *
     * @param loginId String
     * @param userName String
     * @param mail String
     * @return List
     */
    public List searchUserBases(String loginId, String userName, String mail) {
        List list = null;
        String connectDomain = LDAPConfig.getDefaultDomainId();
        list = searchUserBases(connectDomain, loginId, userName, mail);
        return list;
    }

    /**
     * 查找用户基本信息，主要为了查找用户的姓名
     * 输入：查找条件为loginId/userName/mail，并且查找条件是按OR运算，
     *      connectDomain为所选择的Domain
     * 输出：List {DtoUserBase}
     *
     * @param connectDomain String
     * @param loginId String
     * @param userName String
     * @param mail String
     * @return List
     */
    public List searchUserBases(String connectDomain, String loginId,
                                String userName, String mail) {
        LDAPPara para = LDAPConfig.getLDAPPara(connectDomain);
        String cn = para.getLdapBaseDN();
        String filter = createFilterUser(loginId, userName, mail);
        return searchUserBasesByFilter(connectDomain, cn, filter);
    }

    /**
     * 查找用户基本信息，主要为了查找用户的LoginId
     * 输入：用户签核者loginId
     * 输出：List <DtoUserBase>
     *
     * @param connectDomain String
     * @param loginId String
     * @param userName String
     * @param mail String
     * @return List
     */
    public List searchUserBasesByManager(String connectDomain, String manager) {
        LDAPPara para = LDAPConfig.getLDAPPara(connectDomain);
        String cn = para.getLdapBaseDN();
        String managerDisName = findDisNameByLonginId(connectDomain, manager);
        String filter = "(" + ATT_NAME_MANAGER + "=" + managerDisName + ")";
        return searchUserBasesByFilter(connectDomain, cn, filter);
    }


    /**
     * 依据自定义条件查找用户清单
     * @param connectDomain String
     * @param cn String
     * @param filter String
     * @return List
     */
    public List searchUserBasesByFilter(String connectDomain, String cn,
                                        String filter) {
        return searchUserByFilter(connectDomain, cn, filter,
                                  RESULT_TYPE_USER_BASE);
    }

    public List searchUserByFilter(String connectDomain, String cn,
            String filter, String resultType) {
    	return searchUserByFilter(connectDomain, cn, filter, resultType, true);
    }

    public List searchUserByFilter(String connectDomain, String cn,
                                   String filter, String resultType, boolean subTree) {
        if (resultType == null || resultType.equals("")) {
            resultType = RESULT_TYPE_USER_BASE;
        }
        List listDtoUser = new ArrayList();
        //String connectDomain = LDAPConfig.getDefaultDomainId();

        DirContext dirContext = connect(connectDomain, getLdapLoginId(),
                                        getUserPassword());
        try {
            String[] userAtts = null;
            if (resultType == RESULT_TYPE_USER_INFO) {
                userAtts = userInfoAttributes;
            } else {
                userAtts = userBaseAttributes;
            }
            List resultList;
            if(subTree) {
            	resultList = searchContextSub(dirContext, cn, filter,
                                               userAtts);
            } else {
            	resultList = searchContextOne(dirContext, cn, filter,
                        userAtts);
            }

            if (resultList != null) {
                for (int i = 0; i < resultList.size(); i++) {
                    Map resultRowMap = (Map) resultList.get(i);

                    DtoUserBase dtoUser = null;
                    if (resultType == RESULT_TYPE_USER_INFO) {
                        dtoUser = rowMap2DtoUserInfo(resultRowMap);
                    } else {
                        dtoUser = rowMap2DtoUserBase(resultRowMap);
                    }
                    if (dtoUser != null) {
                        dtoUser.setDomain(connectDomain);
                        dtoUser.setUserType(DtoUserBase.USER_TYPE_EMPLOYEE);
                        listDtoUser.add(dtoUser);
                    }

                }
            }

//            list = searchContextSub(dirContext, cn, filter);
        } catch (NamingException ex) {
            log.error(ex);
        } finally {
            this.disConnect(dirContext);
        }


        return listDtoUser;

    }

    private static DtoUserBase rowMap2DtoUserBase(Map resultRowMap) {
        DtoUserBase dtoUser = new DtoUserBase();

        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.
                           get(ATT_NAME_LOGIN_ID.toLowerCase());
            //抛弃LoginId 为空的记录
            if (rsAttValList == null || rsAttValList.size() <= 0) {
                return null;
            }
            rsValStr = (String) rsAttValList.get(0);
            dtoUser.setUserLoginId(rsValStr);
        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.
                           get(ATT_NAME_NAME.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            dtoUser.setUserName(rsValStr);

        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        return dtoUser;
    }

    private static DtoUserInfo rowMap2DtoUserInfo(Map resultRowMap) {
        DtoUserInfo dtoUser = new DtoUserInfo();

        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.
                           get(ATT_NAME_LOGIN_ID.toLowerCase());
            //抛弃LoginId 为空的记录
            if (rsAttValList == null || rsAttValList.size() <= 0) {
                return null;
            }
            rsValStr = (String) rsAttValList.get(0);
            dtoUser.setUserLoginId(rsValStr);
        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.
                           get(ATT_NAME_NAME.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            dtoUser.setUserName(rsValStr);

        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.
                           get(ATT_NAME_MAIL.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            dtoUser.setEmail(rsValStr);

        } catch (Exception e) {
            //coudn't get attrubite's value
        }

        return dtoUser;
    }

    private static List rowMap2OUList(Map resultRowMap) {
        List resultList = new ArrayList();
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.get
                           (ATT_NAME_DISTINGUISHED_NAME.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            String[] distinguishArrary = rsValStr.split(",");
            for (int j = 0; j < distinguishArrary.length; j++) {
                if (distinguishArrary[j].startsWith("OU")) {
                    resultList.add(distinguishArrary[j].substring(3));
                }
            }
        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        return resultList;
    }
    
    private static List rowMap2GroupList(List searchList, boolean userGrp) {
        List resultList = new ArrayList();
        try {
            for (int j = 0; j < searchList.size(); j++) {
            	DtoUserInfo dto = rowMap2Group((Map)searchList.get(j), userGrp);
            	if(dto != null) {
            		resultList.add(dto);
            	}
            }
        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        return resultList;
    }
    
    private static DtoUserInfo rowMap2Group(Map resultRowMap, boolean userGrp) {
        DtoUserInfo dtoGroup = new DtoUserInfo();
        
        if(userGrp) {
	        try {
	            List rsAttValList = (List) resultRowMap.
	                           get(ATT_NAME_MEMBER.toLowerCase());
	            if(rsAttValList == null || rsAttValList.get(0) == null)
	            	return null;
	        } catch (Exception e) {
	            return null;
	        }
        }
        
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.
                           get(ATT_NAME_CN.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            dtoGroup.setUserName(rsValStr);

        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.
                           get(ATT_NAME_MAIL.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            dtoGroup.setEmail(rsValStr);

        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.get
                           (ATT_NAME_DISTINGUISHED_NAME.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            dtoGroup.setOrganization(rsValStr);
        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        return dtoGroup;
    }
    
    private static List rowMap2ChildrenOUList(Map resultRowMap) {
        List resultList = new ArrayList();
        try {
            String rsValStr = null;
            List rsAttValList = null;
            rsAttValList = (List) resultRowMap.get
                           (ATT_NAME_OU.toLowerCase());
            rsValStr = (String) rsAttValList.get(0);
            resultList.add(rsValStr);
        } catch (Exception e) {
            //coudn't get attrubite's value
        }
        return resultList;
    }

    private static String rowMap2ManagedByDisName(Map resultRowMap) {
        String rsValStr = null;
        List rsAttValList = null;
        if(resultRowMap.size()>0) {
            rsAttValList = (List) resultRowMap.get
                           (ATT_NAME_MANAGED_BY.toLowerCase());
        }
        if(rsAttValList!=null&&rsAttValList.size()>0) {
            rsValStr = (String) rsAttValList.get(0);
        }
        return rsValStr;
    }

    private static String rowMap2ManagerDisName(Map resultRowMap) {
        String rsValStr = null;
        List rsAttValList = null;
        if (resultRowMap.size() > 0) {
            rsAttValList = (List) resultRowMap.get
                           (ATT_NAME_MANAGER.toLowerCase());
        }
        if (rsAttValList != null && rsAttValList.size() > 0) {
            rsValStr = (String) rsAttValList.get(0);
        }
        return rsValStr;
    }


    private static String rowMap2DisName(Map resultRowMap) {
        String rsValStr = null;
        List rsAttValList = null;
        if(resultRowMap.size()>0) {
            rsAttValList = (List) resultRowMap.get
                           (ATT_NAME_DISTINGUISHED_NAME.toLowerCase());
        }
        if(rsAttValList!=null&&rsAttValList.size()>0) {
            rsValStr = (String) rsAttValList.get(0);
        }
        return rsValStr;
    }

    /**
     * 依据所输入的用户查询数据，生成LDAP中查询字串
     * @param loginId String
     * @param userName String
     * @param mail String
     * @return String
     */
    public static String createFilterUser(String loginId, String userName,
                                          String mail) {
        String filter = null;
        String[] fs = new String[3];

        //(&(sn=Geisel)(mail=*))
        if (loginId == null || loginId.length() == 0) {
            //fLoginId = "(SAMAccountName=*)";
            //(|(SAMAccountName=*)
        } else {
            fs[0] = "(" + ATT_NAME_LOGIN_ID + "=*" + loginId + "*)";
        }
        if (userName == null || userName.length() == 0) {
            //fUserName = "(name=*)";
            //(|(SAMAccountName=*)
        } else {
            fs[1] = "(" + ATT_NAME_NAME + "=*" + userName + "*)";
        }
        if (mail == null || mail.length() == 0) {
            //fMail = "(mail=*)";
            //(|(SAMAccountName=*)
        } else {
            fs[2] = "(" + ATT_NAME_MAIL + "=*" + mail + "*)";
        }

        for (int i = 0; i < fs.length; i++) {
            if (!(fs[i] == null || fs[i].length() == 0)) {
                if (filter == null || filter.length() == 0) {
                    filter = fs[i];
                } else {
                    filter = "(|" + filter + fs[i] + ")";
                }
            }
        }
        //限制为人
        filter = "(&" + filter + RESTRICT_PERSON +  ")";
        log.debug(filter);
        return filter;
    }

    /**
     * 连接当前系统所设置Default_Domain，对用户进行认证
     * @param userDomain String
     * @param userLoginId String
     * @param userPassword String
     * @return boolean
     */
    private static boolean authByDefaultDomain(String userLoginId,
                                               String userPassword) {
        return authByUserDomain(LDAPConfig.getDefaultDomainId(),
                                userLoginId,
                                userPassword);
    }

    /**
     * 连接用户所属的Domain，对用户进行认证
     * @param userDomain String
     * @param userLoginId String
     * @param userPassword String
     * @return boolean
     */
    private static boolean authByUserDomain(String userDomain,
                                            String userLoginId,
                                            String userPassword) {
        DirContext dirContext = null;
        try {
            String connectDoamin = userDomain;
            //登录连接的Domain采用user所关联的Domain即，如wh的用户在tp之essp系统则仍登录wh's AD
            if (userDomain == null || userDomain.length() == 0) {
                connectDoamin = LDAPConfig.getDefaultDomainId();
            }

            String ldapLoginId = userDomain + "\\" + userLoginId;
            dirContext = connect(connectDoamin, ldapLoginId, userPassword);
            DtoUserBase user = new DtoUserBase();
            user.setDomain(userDomain);
            user.setUserLoginId(userLoginId);
            return true;
        } finally {
            disConnect(dirContext);
        }
    }

    /**
     * 列出当前连接Domain下相应CN之所有用户清单
     * @param connectDomain String
     * @param cn String
     * @return List
     */
    public List searchUsersByCN(String connectDomain, String cn,
                                String resultType) {
        List list = null;
//        String connectDomain = LDAPConfig.getDefaultDomainId();
//        LDAPPara para = LDAPConfig.getLDAPPara(connectDomain);
//        String basecn = para.getLdapBaseDN();
//        String cn = "OU=dlwits," + basecn;
        String filterLoginId = "(" + ATT_NAME_LOGIN_ID + "=*" + ")";
        String filter = "(&" + filterLoginId + RESTRICT_PERSON + ")";
        list = searchUserByFilter(connectDomain, cn, filter, resultType);
        return list;
    }


    public static void main(String[] args) {
        String userDomain = "wh";
        String userLoginId = "WH0302008";
        String userPassword = "stone.shi";
        List list = null;
        System.out.println("Begin");

        boolean bRtn = true;

        LDAPUtil ldapUtil = new LDAPUtil(userDomain, userLoginId, userPassword);

//        list = ldapUtil.searchUserBases("tp", "", "xiao", "stone");
        String cn =
                "OU=BJ,DC=wh,DC=wistronits,DC=com";

        list = ldapUtil.findUserInGroup("wh", "CN=WW112,OU=WW112,OU=WW110,OU=WW100,OU=WW000,OU=WH,DC=wh,DC=wistronits,DC=com");
        for (int i = 0; i < list.size(); i++) {
        	DtoUserInfo dtoUser = (DtoUserInfo) list.get(i);
            String str = dtoUser.getUserName()
                         + "\n" + dtoUser.getEmail()
                         + "\n" + dtoUser.getOrganization();
            System.out.println(str);
        }
        if(true) {
        	return;
        }
//        List resultList = ldapUtil.findOU("tp", "E0008");
//        String manager = ldapUtil.findOUManagerDisName("tp",
//                resultList.get(0).toString());
//            try {
//                ldapUtil.authByDefaultDomain("wh0607014","test");
//                DtoUserInfo dtoUser = ldapUtil.findUserByDisName(
//                        "CN=MingNiu(牛铭),OU=ShangHai,DC=wh,DC=wistronits,DC=com");
//                System.out.println(dtoUser.getEmail());
//            } catch (Exception ex) {
//                ex.printStackTrace();
//
//            }
        try {
        
        } catch (Exception e) {
        	e.printStackTrace();
        }
        while(true) {
        	Date begin = new Date();
        	ldapUtil = new LDAPUtil(userDomain, userLoginId, userPassword);
        	DtoUser user = ldapUtil.findUser("tp", "TP9603002");
        	Date end = new Date();
            System.out.println("find user: " + user.getUserName() + " at " + begin.toLocaleString());
            System.out.println("\t use Time: " + (end.getTime() -  begin.getTime()) + " ms");
            try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
//
//        System.out.println("End");
    }

    public String getUserDomain() {
        return userDomain;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserDomain(String domain) {
        this.userDomain = domain;
    }

    public void setUserLoginId(String loginId) {
        this.userLoginId = loginId;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    public String getLdapLoginId() {
        return (this.getUserDomain() + "\\" + this.getUserLoginId());
    }

}
