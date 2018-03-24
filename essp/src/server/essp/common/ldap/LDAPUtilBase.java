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
            //����ȱʡ��Domain
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
        // ����Ϊ��
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.common.parameter.empty",
                                        msglog.toString());
        }

        // ����Ϊ��
        if (attMap == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attMap NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // Ϊ�գ����˳�
        if (attMap.isEmpty()) {
            return;
        }

        // ȡ���е�����key
        Set keySet = attMap.keySet();
        Iterator keyIterator = keySet.iterator();
        Attributes attrs = new BasicAttributes();
        // �������е�����key
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

        // ����Ϊ��
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.common.parameter.empty",
                                        msglog.toString());
        }
        // ɾ��һ����Context
        try {
            context.destroySubcontext(cn);
        } catch (NamingException ex) {
            throw new BusinessException("error.system.ldap", ex);
        }
    }

    /**
     * ���ݵ�ǰ������DirContext ������Context
     * @param context ���Ӻ��DirContext
     * @param cn   ԭContext������
     * @param newCn �µ�Context����
     * @throws NamingException
     * @throws BaseException
     */
    public static void reNameContext(DirContext context, String cn,
                                     String newCn) throws BusinessException {

        // ����Ϊ��
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (StringUtils.isEmpty(newCn)) {
            String[] args = {
                            "newCn"};
            // ��ӡ������־
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
     * �ڵ�ǰ���ӵ�DirContext ָ����Context ���һ�� / �������
     * @param context ���ӵ�DirContext
     * @param cn    ָ����Context
     * @param attMap Map ���� List ,keyΪ�������ƣ�
     * value ����ֵ, ��Ϊ��ֵʱ����ΪList,��Ϊ��ֵʱ��ΪString����
     * @throws BaseException
     * @throws NamingException
     */
    public static void addAttributes(DirContext context, String cn, Map attMap) throws
            BusinessException {

        // ����Ϊ��
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (attMap == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attMap NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }
        // ����Ϊ��
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // Ϊ�գ��˳�
        if (attMap.isEmpty()) {
            return;
        }

        // ȡ���е�����key
        Set keySet = attMap.keySet();
        Iterator keyIterator = keySet.iterator();
        Attributes attrs = new BasicAttributes();
        // �������е�����key
        while (keyIterator.hasNext()) {

            // ȡ��һ������
            String key = (String) keyIterator.next();
            Attribute att = null;
            Object valueObj = attMap.get(key);
            // �ж���������
            if (valueObj instanceof String) {
                // Ϊ�ַ�����Ϊ��ֵ����
                att = new BasicAttribute(key, valueObj);
            } else if (valueObj instanceof List) {
                // ΪList ,Ϊ��ֵ����
                att = new BasicAttribute(key);
                List valueList = (List) valueObj;
                // �����ֵ����
                for (int i = 0; i < valueList.size(); i++) {
                    att.add(valueList.get(i));
                }
            }
            // ����
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
     * �ڵ�ǰ������DirContext ɾ�� ָ��Context �µ� һ�� / �������
     * @param context ���Ӻ��DirContext
     * @param cn ָ��Context������
     * @param attList ����Ҫɾ�������Ե�����,ΪList����
     * @throws BaseException
     * @throws NamingException
     */
    public static void deleteAttributes(DirContext context, String cn,
                                        List attList) throws BusinessException {
        // ����Ϊ��
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (attList == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attList NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }
        // ����Ϊ��
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // Ϊ�գ��˳�
        if (attList.isEmpty()) {
            return;
        }

        Attributes attrs = new BasicAttributes();

        for (int i = 0; i < attList.size(); i++) {
            Attribute att = null;
            att = new BasicAttribute((String) attList.get(i));
            // ����
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
     * �ڵ�ǰ���ӵ�DirContext �޸�ָ��Context�µ�һ�� �� �������
     * @param context ���ӵ�DirContext
     * @param cn ָ��Context�µ�����
     * @param attMap ����List keyΪ�������ƣ�������Ϊ��ֵʱ
     * value Ϊ������ֵ��List,Ϊ��ֵʱ��Ϊ������ֵ��String����
     * @throws BaseException
     * @throws NamingException
     */
    public static void modifyAttributes(DirContext context, String cn,
                                        Map attMap) throws
            BusinessException {

        // ����Ϊ��
        if (context == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter context NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // ����Ϊ��
        if (attMap == null) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter attMap NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }
        // ����Ϊ��
        if (StringUtils.isEmpty(cn)) {
            StringBuffer msglog = new StringBuffer(
                    "empty invoke parameter cn NULL ");
            log.error(msglog.toString());
            throw new BusinessException("error.system.ldap", msglog.toString());
        }

        // Ϊ�գ��˳�
        if (attMap.isEmpty()) {
            return;
        }
        // ȡ���е�����key
        Set keySet = attMap.keySet();
        Iterator keyIterator = keySet.iterator();
        Attributes attrs = new BasicAttributes();
        // �������е�����key
        while (keyIterator.hasNext()) {
            // ȡ��һ������
            String key = (String) keyIterator.next();
            Attribute att = null;
            Object valueObj = attMap.get(key);

            if (valueObj instanceof List) {
                // ΪList ,Ϊ��ֵ����
                att = new BasicAttribute(key);
                List valueList = (List) valueObj;
                // �����ֵ����
                for (int i = 0; i < valueList.size(); i++) {
                    att.add(valueList.get(i));
                }
            } else if (valueObj instanceof String) {
                att = new BasicAttribute(key, valueObj);
            }
            // ����
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
     * ��ȡ���ӵ�DirContext��ָ��Context�µ�ָ������
     * @param context ���ӵ�DirContext
     * @param cn��ָ��Context������
     * @param attNameList Ҫȡ�����Ե�����List
     * @return Map����List ,key Ϊ���Ե�����,������ֵΪ��ֵʱ��ValueΪList���ͣ�
     * ����value ΪString ����
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
                /////////////////////////////////////////// ���·�������̫�� ////////////////////////////////
                //            for (int i = 0; i < attNameList.size(); i++) {
                //               stTemp[i] = (String) attNameList.get(i);
                //            }
                //            results = context.getAttributes(cn,
                //                                    stTemp);
                ///////////////////////////////////////////////////////////////////////////////////////////
                // �Ƚϸ����ܵ�List תΪ ����ķ���
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
                    // ����������Ե���������ֵ
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                            attValList.add(obj1);
                        }
                        attValList.add(vals.nextElement());
                    }
                    // ������Ϊ��ֵ��ʱ����Ϊ�ַ���
                    // ������Ϊ��ֵ��ʱ����Ϊ������ֵ���List
                    if (attValList != null) {
                        attsMap.put(attrId, attValList);
                        // ���
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
     * �ڵ�ǰ���ӵ�DirContext ��ȡָ��Context�µ�ָ���������Ƶ���������ֵ��һ������ֵ��
     * @param context ���ӵ�DirContext
     * @param cn��ָ��Context��cn��
     * @param attName ��������
     * @return ���ذ�������ֵ��List ע�⣬������ֻ��һ��ֵʱ�����ص�List����Ϊ1��������
     * �Ƕ�ֵ����ʱ������List����Ϊ����ֵ����Ŀ
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
     * ��ȡ��ɫ�������Ϣ
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
     * ������������ָ��CN��Context�µ�һ����������
     * @param context �����˵�DirContext
     * @param cn Ҫ��ѯ��BaseCN����
     * @param filter Ҫ��ѯ�Ĺ����ַ���
     * @return ���ϲ�ѯ�����List
     * @throws NamingException
     */
    public static List searchContextOne(DirContext context, String cn,
                                        String filter) throws
            NamingException {
        List resultList = new ArrayList();
        Map resultRowMap = null;
        List attValList = null;
        String attValStr = null;
        // ʵ����һ��������
        SearchControls constraints = new SearchControls();
        // ������������������Χ
        constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        // �ڻ�Ŀ¼����������ΪEnv.MY_FILTER���������� ע�⣺���ﷵ���ǵ����е���Ŀ����
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // ��ӡ��Ŀ��ʶ����(DN)�������е���������ֵ
        while (results != null && results.hasMoreElements()) {
            // ȡһ����Ŀ
            SearchResult si = (SearchResult) results.next();

            // ��ȡ��Ŀ���������Լ���
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                String attrId = null;
                // һ������
                resultRowMap = new HashMap();
                // ��ӡ��������
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {
                    // ��ȡһ������
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
                    // ����������Ե���������ֵ
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                            attValList.add(obj1);
                        }
                        attValList.add(vals.nextElement());
                    }
                    // ������Ϊ��ֵ��ʱ����Ϊ�ַ���
                    // ������Ϊ��ֵ��ʱ����Ϊ������ֵ���List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // ���
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
     * ������������ָ��CN��Context�µ������µ���������
     * @param context �����˵�DirContext
     * @param cn Ҫ��ѯ��BaseCN����
     * @param filter Ҫ��ѯ�Ĺ����ַ���
     * @return ���ϲ�ѯ�����List
     * @throws NamingException
     */
    public static List searchContextSub(DirContext context, String cn,
                                        String filter) throws
            NamingException {
        List resultList = new ArrayList();
        Map resultRowMap = null;
        List attValList = null;
        // ʵ����һ��������
        SearchControls constraints = new SearchControls();
        // ������������������Χ
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // �ڻ�Ŀ¼����������ΪEnv.MY_FILTER���������� ע�⣺���ﷵ���ǵ����е���Ŀ����
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // ��ӡ��Ŀ��ʶ����(DN)�������е���������ֵ
        while (results != null && results.hasMoreElements()) {
            // ȡһ����Ŀ
            SearchResult si = (SearchResult) results.next();

            // ��ȡ��Ŀ���������Լ���
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                String attrId = null;
                // һ������
                resultRowMap = new HashMap();
                // ��ӡ��������ֵ
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {
                    // ��ȡһ������
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
                    // ����������Ե���������ֵ
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                            attValList.add(obj1);
                        }
                        attValList.add(vals.nextElement());
                    }
                    // ������Ϊ��ֵ��ʱ����Ϊ�ַ���
                    // ������Ϊ��ֵ��ʱ����Ϊ������ֵ���List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // ���
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
     * ����ָ��CN��Context�µ������µ�ָ������
     * @param context DirContext
     * @param cn String
     * @param filter String
     * @param returnedAtts String[] ������������
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
        // ʵ����һ��������
        SearchControls constraints = new SearchControls();
        // ������������������Χ
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // String[] returnedAtts = {"uniquemember"};
        constraints.setReturningAttributes(returnedAtts);
        // ��Ŀ
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // �������е���Ŀ
//        while (results != null && results.hasMore()) {
        while (results != null && results.hasMoreElements()) {
            // ȡһ����Ŀ
            SearchResult si = (SearchResult) results.next();
            resultRowMap = new HashMap();
            // ��ȡ��Ŀ��ָ�����ص�����
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                // ������������ֵ
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {

                    // ��ȡһ������
                    Attribute attr = (Attribute) ae.next();
                    attrId = attr.getID().toLowerCase();
                    //System.out.println(attrId);
                    Enumeration vals = attr.getAll();
                    if (vals == null) {
                        continue;
                    }
                    // ����������Ե���������ֵ
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                        }
                        Object obj = vals.nextElement();
                        attValList.add(obj);
                        //System.out.println(obj);
                    }
                    // ������Ϊ��ֵ��ʱ����Ϊ�ַ���
                    // ������Ϊ��ֵ��ʱ����Ϊ������ֵ���List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // ���
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
     * ����ָ��CN��Context�µ�һ��ָ������
     * @param context DirContext
     * @param cn String
     * @param filter String
     * @param returnedAtts String[] ������������
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
        // ʵ����һ��������
        SearchControls constraints = new SearchControls();
        // ������������������Χ
        constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        // String[] returnedAtts = {"uniquemember"};
        constraints.setReturningAttributes(returnedAtts);
        // ��Ŀ
        NamingEnumeration results
                = context.search(cn, filter, constraints);

        // �������е���Ŀ
//        while (results != null && results.hasMore()) {
        while (results != null && results.hasMoreElements()) {
            // ȡһ����Ŀ
            SearchResult si = (SearchResult) results.next();
            resultRowMap = new HashMap();
            // ��ȡ��Ŀ��ָ�����ص�����
            Attributes attrs = si.getAttributes();
            if (attrs != null) {
                // ������������ֵ
                for (NamingEnumeration ae = attrs.getAll();
                                            ae.hasMoreElements(); ) {

                    // ��ȡһ������
                    Attribute attr = (Attribute) ae.next();
                    attrId = attr.getID().toLowerCase();
                    //System.out.println(attrId);
                    Enumeration vals = attr.getAll();
                    if (vals == null) {
                        continue;
                    }
                    // ����������Ե���������ֵ
                    while (vals.hasMoreElements()) {
                        if (attValList == null) {
                            attValList = new ArrayList();
                        }
                        Object obj = vals.nextElement();
                        attValList.add(obj);
                        //System.out.println(obj);
                    }
                    // ������Ϊ��ֵ��ʱ����Ϊ�ַ���
                    // ������Ϊ��ֵ��ʱ����Ϊ������ֵ���List
                    if (attValList != null) {
                        resultRowMap.put(attrId, attValList);
                        // ���
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
     * �ڵ�ǰ������DirContext ɾ�� ָ��Context �µ� һ���������������������
     * @param context ���Ӻ��DirContext
     * @param cn ָ��Context������
     * @param attList ����Ҫɾ�������Ե�����
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
