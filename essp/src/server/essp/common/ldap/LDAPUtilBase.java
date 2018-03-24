package server.essp.common.ldap;

import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;

import org.apache.log4j.*;
import server.framework.common.BusinessException;
import org.apache.commons.lang.StringUtils;

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
public class LDAPUtilBase {
    static Category log = Category.getInstance("server");
    private final static String LDAP_VERSION = "3";
    public final static String DOMAIN_ALL = "all";

    public LDAPUtilBase() {
    }

//    DirContext dirContext;

    public static DirContext connect(String connectDomain,
                                     String loginId,
                                     String passwd) throws
            BusinessException {
        log.debug("Connect begin,connectDomain=" + connectDomain + ";user=" +
                  loginId + ";Time=" + System.currentTimeMillis());

        try {
            //联接缺省的Domain
            LDAPPara para = LDAPConfig.getLDAPPara(connectDomain);

            Hashtable env = new Hashtable(11);
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, para.getLdapUrl());
            env.put("java.naming.ldap.version", LDAP_VERSION);
            env.put(Context.SECURITY_AUTHENTICATION, para.getLdapConnectFlag());

            env.put(Context.SECURITY_PRINCIPAL, loginId);
            env.put(Context.SECURITY_CREDENTIALS, passwd);

            DirContext ctx = new InitialDirContext(env);
            log.debug("Connect sucessed,connectDomain=" + connectDomain +
                      ";user=" + loginId + ";Time=" + System.currentTimeMillis());
            return ctx;
        } catch (NamingException ex) {
            ex.printStackTrace();
            String errorMsg = "Connect failed,connectDomain=" + connectDomain +
                              ";user=" +
                              loginId + ";Time=" + System.currentTimeMillis() +
                              ".\n" + parseLoginException(ex);
            log.debug(errorMsg);
            log.error(ex.getMessage());
            throw new BusinessException("error.system.ldap", errorMsg, ex);
        }
    }

    public static void disConnect(DirContext dirContext) {
        try {
            if (dirContext != null) {
                dirContext.close();
            }
        } catch (Exception ex) {
            log.error("not close DirContext", ex);
        }
    }

    public static void addContext(DirContext context, String cn, Map attMap) throws
            BusinessException {
        // 参数为空
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.common.parameter.empty",
                                        msglog.toString());
        }

        // 参数为空
        if (attMap == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attMap NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 为空，则退出
        if (attMap.isEmpty()) {
            return;
        }

        // 取所有的属性key
        Set keySet = attMap.keySet();
        Iterator keyIterator = keySet.iterator();
        Attributes attrs = new BasicAttributes();
        // 迭代所有的属性key
        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            Attribute att = null;
            Object valueObj = attMap.get(key);
            if (valueObj instanceof String) {
                att = new BasicAttribute(key, valueObj);
            } else if (valueObj instanceof List) {
                att = new BasicAttribute(key);
                List valueList = (List) valueObj;
                for (int i = 0; i < valueList.size(); i++) {
                    att.add(valueList.get(i));
                }
            } else {
                att = new BasicAttribute(key, valueObj);
            }
            attrs.put(att);
        }
        try {
            context.createSubcontext(cn, attrs);
        } catch (NamingException ex) {
            throw new BusinessException("error.system.ldap", ex);
        }
    }

    public static void deleteContext(DirContext context, String cn) throws
            BusinessException {

        // 参数为空
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.common.parameter.empty",
                                        msglog.toString());
        }
        // 删除一个子Context
        try {
            context.destroySubcontext(cn);
        } catch (NamingException ex) {
            throw new BusinessException("error.system.ldap", ex);
        }
    }

    /**
     * 根据当前的连接DirContext 重命名Context
     * @param context 连接后的DirContext
     * @param cn   原Context的名称
     * @param newCn 新的Context名称
     * @throws NamingException
     * @throws BaseException
     */
    public static void reNameContext(DirContext context, String cn,
                                     String newCn) throws BusinessException {

        // 参数为空
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (StringUtils.isEmpty(newCn)) {
            String[] args = {
                            "newCn"};
            // 打印错误日志
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter newCn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }
        try {
            context.rename(cn, newCn);
        } catch (NamingException ex) {
            throw new BusinessException("error.system.ldap", ex);
        }
        // context.close();
    }

    /**
     * 在当前连接的DirContext 指定的Context 添加一个 / 多个属性
     * @param context 连接的DirContext
     * @param cn    指定的Context
     * @param attMap Map 包含 List ,key为属性名称，
     * value 属性值, 当为多值时，存为List,当为单值时，为String类型
     * @throws BaseException
     * @throws NamingException
     */
    public static void addAttributes(DirContext context, String cn, Map attMap) throws
            BusinessException {

        // 参数为空
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (attMap == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attMap NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }
        // 参数为空
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 为空，退出
        if (attMap.isEmpty()) {
            return;
        }

        // 取所有的属性key
        Set keySet = attMap.keySet();
        Iterator keyIterator = keySet.iterator();
        Attributes attrs = new BasicAttributes();
        // 迭代所有的属性key
        while (keyIterator.hasNext()) {

            // 取下一个属性
            String key = (String) keyIterator.next();
            Attribute att = null;
            Object valueObj = attMap.get(key);
            // 判断属性类型
            if (valueObj instanceof String) {
                // 为字符串，为单值属性
                att = new BasicAttribute(key, valueObj);
            } else if (valueObj instanceof List) {
                // 为List ,为多值属性
                att = new BasicAttribute(key);
                List valueList = (List) valueObj;
                // 加入多值属性
                for (int i = 0; i < valueList.size(); i++) {
                    att.add(valueList.get(i));
                }
            }
            // 加入
            attrs.put(att);
        }

        try {
            context.modifyAttributes(cn, DirContext.ADD_ATTRIBUTE, attrs);
        } catch (NamingException ex) {
            throw new BusinessException("error.system.ldap", ex);
        }
        // context.close();
    }

    /**
     * 在当前的连接DirContext 删除 指定Context 下的 一个 / 多个属性
     * @param context 连接后的DirContext
     * @param cn 指定Context的名称
     * @param attList 包含要删除的属性的名称,为List类型
     * @throws BaseException
     * @throws NamingException
     */
    public static void deleteAttributes(DirContext context, String cn,
                                        List attList) throws BusinessException {
        // 参数为空
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (attList == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attList NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }
        // 参数为空
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 为空，退出
        if (attList.isEmpty()) {
            return;
        }

        Attributes attrs = new BasicAttributes();

        for (int i = 0; i < attList.size(); i++) {
            Attribute att = null;
            att = new BasicAttribute((String) attList.get(i));
            // 加入
            attrs.put(att);
        }
        try {
            context.modifyAttributes(cn, DirContext.REMOVE_ATTRIBUTE, attrs);
        } catch (NamingException ex) {
            throw new BusinessException("error.system.ldap", ex);
        }
        // context.close();
    }


    /**
     * 在当前连接的DirContext 修改指定Context下的一个 或 多个属性
     * @param context 连接的DirContext
     * @param cn 指定Context下的名字
     * @param attMap 包含List key为属性名称，当属性为多值时
     * value 为包含多值的List,为单值时，为包含单值的String类型
     * @throws BaseException
     * @throws NamingException
     */
    public static void modifyAttributes(DirContext context, String cn,
                                        Map attMap) throws
            BusinessException {

        // 参数为空
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 参数为空
        if (attMap == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attMap NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }
        // 参数为空
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // 为空，退出
        if (attMap.isEmpty()) {
            return;
        }
        // 取所有的属性key
        Set keySet = attMap.keySet();
        Iterator keyIterator = keySet.iterator();
        Attributes attrs = new BasicAttributes();
        // 迭代所有的属性key
        while (keyIterator.hasNext()) {
            // 取下一个属笥
            String key = (String) keyIterator.next();
            Attribute att = null;
            Object valueObj = attMap.get(key);

            if (valueObj instanceof List) {
                // 为List ,为多值属性
                att = new BasicAttribute(key);
                List valueList = (List) valueObj;
                // 加入多值属性
                for (int i = 0; i < valueList.size(); i++) {
                    att.add(valueList.get(i));
                }
            } else if (valueObj instanceof String) {
                att = new BasicAttribute(key, valueObj);
            }
            // 加入
            attrs.put(att);
        }
        try {
            context.modifyAttributes(cn, DirContext.REPLACE_ATTRIBUTE, attrs);
        } catch (NamingException ex) {
            throw new BusinessException("error.system.ldap", ex);
        }
        // context.close();
    }

//
    /**
     * 获取连接的DirContext中指定Context下的指定属性
     * @param context 连接的DirContext
     * @param cn　指定Context的名称
     * @param attNameList 要取的属性的名称List
     * @return Map包含List ,key 为属性的名称,当属性值为多值时，Value为List类型，
     * 否则，value 为String 类型
     * @throws NamingException
     */
    public static Map getAttributes(DirContext context, String cn,
                                    List attNameList) throws NamingException {
        Map attsMap = new HashMap();
        Attributes results = null;
        List attValList = null;
        String attrId = null;

        if (attNameList == null) {
            results = context.getAttributes(cn);
        } else {
            if (!attNameList.isEmpty()) {
                // results = context.getAttributes(cn);
                String[] stTemp = new String[attNameList.size()];
                /////////////////////////////////////////// 以下方法性能太低 ////////////////////////////////
                //            for (int i = 0; i < attNameList.size(); i++) {
                //               stTemp[i] = (String) attNameList.get(i);
                //            }
                //            results = context.getAttributes(cn,
                //                                    stTemp);
                ///////////////////////////////////////////////////////////////////////////////////////////
                // 比较高性能的List 转为 数组的方法
                results = context.getAttributes(cn, (String[])
                                                (attNameList.toArray(stTemp)));
            }
        }
        for (int i = 0; i < attNameList.size(); i++) {
            Attribute attr = results.get((String) attNameList.get(i));
            attrId = (String) attNameList.get(i);
            if (attr != null) {
                if (attr.size() > 0) {
                    NamingEnumeration vals = attr.getAll();
                    if (vals == null) {
                        continue;
                    }
                    Object obj1 = vals.nextElement();
                    if (obj1 == null) {
                        continue;
                    }
                    // 迭代这个属性的所有属性值
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                            attValList.add(obj1);
                        }
                        attValList.add(vals.nextElement());
                    }
                    // 当属性为单值域时，存为字符串
                    // 当属性为多值域时，存为包含多值域的List
                    if (attValList != null) {
                        attsMap.put(attrId, attValList);
                        // 清空
                        attValList = null;
                    } else {
                        attsMap.put(attrId, obj1);
                    }
                }
            }
        }
        // context.close();
        return attsMap;
    }

    /**
     * 在当前连接的DirContext 获取指定Context下的指定属性名称的所有属性值（一个或多个值）
     * @param context 连接的DirContext
     * @param cn　指定Context的cn名
     * @param attName 属性名称
     * @return 返回包括属性值的List 注意，当属性只有一个值时，返回的List长度为1，当属性
     * 是多值属性时，返回List长度为属性值的数目
     * @throws NamingException
     */
    public static List getAttributeValues(DirContext context, String cn,
                                          String attName) throws
            NamingException {
        List attValList = new ArrayList();
        List attNameList = new ArrayList();
        attNameList.add(attName);
        Map attMap = null;
        attMap = getAttributes(context, cn, attNameList);

        if (attMap != null) {
            Object attValObj = attMap.get(attName);
            if (attValObj instanceof String) {
                attValList.add((String) attValObj);
            } else if (attValObj instanceof List) {
                attValList = ((List) attValObj);
            }
        }
        // context.close();
        return attValList;
    }

    /**
     * 获取角色的相关信息
     * @param context DirContext
     * @param cn String
     * @param attName String
     * @return String
     * @throws NamingException
     */
    public static String getRoleAttributeValues(DirContext context, String cn,
                                                String attName) throws
            NamingException {
        String result = "";
        List attNameList = new ArrayList();
        attNameList.add(attName);
        Map attMap = null;
        attMap = getAttributes(context, cn, attNameList);

        if (attMap != null) {
            Object attValObj = attMap.get(attName);
            result = (String) attValObj;
        }
        return result;
    }

    /**
     * 根据条件查找指定CN的Context下的一层所有属性
     * @param context 连接了的DirContext
     * @param cn 要查询的BaseCN名称
     * @param filter 要查询的过滤字符串
     * @return 符合查询结果的List
     * @throws NamingException
     */
    public static List searchContextOne(DirContext context, String cn,
                                        String filter) throws
            NamingException {
        List resultList = new ArrayList();
        Map resultRowMap = null;
        List attValList = null;
        String attValStr = null;
        // 实例化一个搜索器
        SearchControls constraints = new SearchControls();
        // 设置搜索器的搜索范围
        constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        // 在基目录中搜索条件为Env.MY_FILTER的所有属性 注意：这里返回是的所有的条目集合
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // 打印条目的识别名(DN)及其所有的属性名，值
        while (results != null && results.hasMoreElements()) {
            // 取一个条目
            SearchResult si = (SearchResult) results.next();

            // 获取条目的所有属性集合
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                String attrId = null;
                // 一行数据
                resultRowMap = new HashMap();
                // 打印所有属性
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {
                    // 获取一个属性
                    Attribute attr = (Attribute) ae.next();
                    attrId = attr.getID();
                    Enumeration vals = attr.getAll();
                    if (vals == null) {
                        continue;
                    }
                    Object obj1 = vals.nextElement();
                    if (obj1 == null) {
                        continue;
                    }
                    // 迭代这个属性的所有属性值
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                            attValList.add(obj1);
                        }
                        attValList.add(vals.nextElement());
                    }
                    // 当属性为单值域时，存为字符串
                    // 当属性为多值域时，存为包含多值域的List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // 清空
                        attValList = null;
                    } else {
                        resultRowMap.put(attrId, obj1);
                    }

                }
            }
            resultList.add(resultRowMap);
        }
        return resultList;
    }

    /**
     * 根所条件查找指定CN的Context下的子树下的所有属性
     * @param context 连接了的DirContext
     * @param cn 要查询的BaseCN名称
     * @param filter 要查询的过滤字符串
     * @return 符合查询结果的List
     * @throws NamingException
     */
    public static List searchContextSub(DirContext context, String cn,
                                        String filter) throws
            NamingException {
        List resultList = new ArrayList();
        Map resultRowMap = null;
        List attValList = null;
        // 实例化一个搜索器
        SearchControls constraints = new SearchControls();
        // 设置搜索器的搜索范围
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 在基目录中搜索条件为Env.MY_FILTER的所有属性 注意：这里返回是的所有的条目集合
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // 打印条目的识别名(DN)及其所有的属性名，值
        while (results != null && results.hasMoreElements()) {
            // 取一个条目
            SearchResult si = (SearchResult) results.next();

            // 获取条目的所有属性集合
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                String attrId = null;
                // 一行数据
                resultRowMap = new HashMap();
                // 打印所有属性值
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {
                    // 获取一个属性
                    Attribute attr = (Attribute) ae.next();
                    attrId = attr.getID();
                    Enumeration vals = attr.getAll();
                    if (vals == null) {
                        continue;
                    }
                    Object obj1 = vals.nextElement();
                    if (obj1 == null) {
                        continue;
                    }
                    // 迭代这个属性的所有属性值
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                            attValList.add(obj1);
                        }
                        attValList.add(vals.nextElement());
                    }
                    // 当属性为单值域时，存为字符串
                    // 当属性为多值域时，存为包含多值域的List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // 清空
                        attValList = null;
                    } else {
                        resultRowMap.put(attrId, obj1);
                    }
                }
            }
            resultList.add(resultRowMap);
        }
        return resultList;
    }

    /**
     * 查找指定CN的Context下的子树下的指定属性
     * @param context DirContext
     * @param cn String
     * @param filter String
     * @param returnedAtts String[] 属性名字数组
     * @return List
     * @throws NamingException
     */
    public static List searchContextSub(DirContext context, String cn,
                                        String filter, String[] returnedAtts) throws
            NamingException {
        List resultList = new ArrayList();
        String attrId = null; //
        List attValList = null; //
        Map resultRowMap = null; //
        // 实例化一个搜索器
        SearchControls constraints = new SearchControls();
        // 设置搜索器的搜索范围
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // String[] returnedAtts = {"uniquemember"};
        constraints.setReturningAttributes(returnedAtts);
        // 条目
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // 迭代所有的条目
//        while (results != null && results.hasMore()) {
        while (results != null && results.hasMoreElements()) {
            // 取一个条目
            SearchResult si = (SearchResult) results.next();
            resultRowMap = new HashMap();
            // 获取条目的指定返回的属性
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                // 迭代所有属性值
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {

                    // 获取一个属性
                    Attribute attr = (Attribute) ae.next();
                    attrId = attr.getID().toLowerCase();
                    //System.out.println(attrId);
                    Enumeration vals = attr.getAll();
                    if (vals == null) {
                        continue;
                    }
                    // 迭代这个属性的所有属性值
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                        }
                        Object obj = vals.nextElement();
                        attValList.add(obj);
                        //System.out.println(obj);
                    }
                    // 当属性为单值域时，存为字符串
                    // 当属性为多值域时，存为包含多值域的List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // 清空
                        attValList = null;
                    }
                }
            }
            resultList.add(resultRowMap);
        }
        //results.
        results.close();
        return resultList;
    }

    /**
     * 查找指定CN的Context下的一层指定属性
     * @param context DirContext
     * @param cn String
     * @param filter String
     * @param returnedAtts String[] 属性名字数组
     * @return List
     * @throws NamingException
     */
    public static List searchContextOne(DirContext context, String cn,
                                        String filter, String[] returnedAtts) throws
            NamingException {
    	List resultList = new ArrayList();
        String attrId = null; //
        List attValList = null; //
        Map resultRowMap = null; //
        // 实例化一个搜索器
        SearchControls constraints = new SearchControls();
        // 设置搜索器的搜索范围
        constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        // String[] returnedAtts = {"uniquemember"};
        constraints.setReturningAttributes(returnedAtts);
        // 条目
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // 迭代所有的条目
//        while (results != null && results.hasMore()) {
        while (results != null && results.hasMoreElements()) {
            // 取一个条目
            SearchResult si = (SearchResult) results.next();
            resultRowMap = new HashMap();
            // 获取条目的指定返回的属性
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                // 迭代所有属性值
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {

                    // 获取一个属性
                    Attribute attr = (Attribute) ae.next();
                    attrId = attr.getID().toLowerCase();
                    //System.out.println(attrId);
                    Enumeration vals = attr.getAll();
                    if (vals == null) {
                        continue;
                    }
                    // 迭代这个属性的所有属性值
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                        }
                        Object obj = vals.nextElement();
                        attValList.add(obj);
                        //System.out.println(obj);
                    }
                    // 当属性为单值域时，存为字符串
                    // 当属性为多值域时，存为包含多值域的List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // 清空
                        attValList = null;
                    }
                }
            }
            resultList.add(resultRowMap);
        }
        //results.
        results.close();
        return resultList;
    }

    /**
     * 在当前的连接DirContext 删除 指定Context 下的 一个属性里面包含的子属性
     * @param context 连接后的DirContext
     * @param cn 指定Context的名称
     * @param attList 包含要删除的属性的名称
     * @throws BaseException
     * @throws NamingException
     */
    public static void deleteInAttributes(DirContext ctx, String userDN,
                                          List attList, String flag) throws
            NamingException {
        if (attList == null || attList.size() == 0) {
            return;
        } else {
            int size = attList.size();
            ModificationItem[] mods = new ModificationItem[size];
            for (int i = 0; i < size; i++) {
                Attribute att = null;
                mods[i] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                                               new BasicAttribute(
                        flag, (String) attList.get(i)));
            }
            ctx.modifyAttributes(userDN, mods);
        }
    }

    /**
     * parseLoginException
     * @param e NamingException
     * @return String
     */
    public static String parseLoginException(NamingException e) {
        if (e == null) {
            return "null exception";
        }
        String msgStr = e.getMessage();
        if (msgStr == null) {
            return "empty message";
        }
        if (msgStr.indexOf("LDAP: error code 49") < 0) {
            return msgStr;
        }
        if (msgStr.indexOf("AcceptSecurityContext error, data 525") > 0) {
            return "user not found";
        } else if (msgStr.indexOf("AcceptSecurityContext error, data 52e") > 0) {
            return "invalid password";
        } else if (msgStr.indexOf("AcceptSecurityContext error, data 530") > 0) {
            return "not permitted to logon at this time";
        } else if (msgStr.indexOf("AcceptSecurityContext error, data 532") > 0) {
            return "password expired";
        } else if (msgStr.indexOf("AcceptSecurityContext error, data 533") > 0) {
            return "account disabled";
        } else if (msgStr.indexOf("AcceptSecurityContext error, data 701") > 0) {
            return "account expired";
        } else if (msgStr.indexOf("AcceptSecurityContext error, data 773") > 0) {
            return "user must reset password";
        } else {
            return msgStr;
        }
    }

}
