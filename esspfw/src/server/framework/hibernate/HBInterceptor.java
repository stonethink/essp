package server.framework.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import com.wits.util.ThreadVariant;
import net.sf.hibernate.CallbackException;
import net.sf.hibernate.Interceptor;
import net.sf.hibernate.type.Type;
import org.apache.log4j.Category;

/**
 * <p>Title: essp hibernate Interceptor </p>
 *
 * <p>Description: �ڴ����Hibernate�Գ־û���ִ�и��ֶ���ʱ����Ҫִ�еĲ��� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class HBInterceptor implements Interceptor {
    static Category log = Category.getInstance("server");

    /**
     * �������ݼ���ǵ��õķ���
     *     �־û�����仯ʱҪִ�еĲ���
     * @param object Object
     * @param id Serializable
     * @param currentState Object[]     ���º������ֵ
     * @param previousState Object[]    ����ǰ������ֵ
     * @param propertyName String[]     ���Ե���������
     * @param types Type[]              ���Ե���������
     * @return int[]
     */
    public int[] findDirty(Object object, Serializable id,
                           Object[] currentState, Object[] previousState,
                           String[] propertyName, Type[] types) {
        return null;
    }

    /**
     * ��ʼ���־û�����ʱ���õķ���
     *     ��ȡ�־û�����ʱҪִ�еĲ���
     * @param _class Class
     * @param serializable Serializable
     * @return Object
     * @throws CallbackException
     */
    public Object instantiate(Class _class, Serializable serializable) throws
            CallbackException {
        return null;
    }

    /**
     * isUnsaved
     *
     * @param object Object
     * @return Boolean
     */
    public Boolean isUnsaved(Object object) {
        return null;
    }

    /**
     * ��ɾ��ǰ���õķ���
     *    ɾ��ʱҪִ�еĲ���
     * @param object Object
     * @param id Serializable
     * @param state Object[]          ����ֵ
     * @param propertyName String[]   ���Ե���������
     * @param types Type[]            ���Ե���������
     * @throws CallbackException
     */
    public void onDelete(Object object, Serializable id,
                         Object[] state, String[] propertyName,
                         Type[] types) throws CallbackException {
    }

    /**
     * ��FlushDirtyǰ���õķ���
     *   ����ʱҪִ�еĲ���
     * @param object Object
     * @param id Serializable
     * @param currentState Object[]     ���º������ֵ
     * @param previousState Object[]    ����ǰ������ֵ
     * @param propertyName String[]     ���Ե���������
     * @param types Type[]              ���Ե���������
     * @return boolean
     * @throws CallbackException
     */
    public boolean onFlushDirty(Object object, Serializable id,
                                Object[] currentState, Object[] previousState,
                                String[] propertyName, Type[] types) throws
            CallbackException {
        for(int i = 0; i < propertyName.length; i ++) {
            if("rst".equals(propertyName[i]) 
            		&& (currentState[i] == null 
            				|| currentState[i].equals(""))) {
                currentState[i] = "N";
            } else if("rut".equals(propertyName[i])) {
                currentState[i] = new Date();
            } else if("ruu".equals(propertyName[i])) {
                currentState[i] = getLoginId();
            } else if("rct".equals(propertyName[i]) && previousState != null) {
            	currentState[i] = previousState[i];
            } else if("rcu".equals(propertyName[i]) && previousState != null) {
            	currentState[i] = previousState[i];
            }
        }

        return true;
    }

    /**
     * ���غ���õķ���
     *
     * @param object Object
     * @param id Serializable
     * @param state Object[]            ����ֵ
     * @param propertyName String[]     ���Ե���������
     * @param types Type[]              ���Ե���������
     * @return boolean
     * @throws CallbackException
     */
    public boolean onLoad(Object object, Serializable id,
                          Object[] state, String[] propertyName,
                          Type[] types) throws CallbackException {
        return false;
    }

    /**
     * �ڱ���ǰ���õķ���
     *    ����ʱҪִ�еĲ���
     * @param object Object
     * @param id Serializable
     * @param state Object[]             ����ֵ
     * @param propertyName String[]      ���Ե���������
     * @param types Type[]               ���Ե���������
     * @return boolean
     * @throws CallbackException
     */
    public boolean onSave(Object object, Serializable id,
                          Object[] state, String[] propertyName,
                          Type[] types) throws CallbackException {
        for(int i = 0; i < propertyName.length; i ++) {
            if("rst".equals(propertyName[i])
            		&& (state[i] == null 
            				|| state[i].equals(""))) {
                state[i] = "N";
            } else if("rct".equals(propertyName[i])) {
                state[i] = new Date();
            } else if("rut".equals(propertyName[i])) {
                state[i] = new Date();
            } else if("rcu".equals(propertyName[i])) {
                state[i] = getLoginId();
            } else if("ruu".equals(propertyName[i])) {
                state[i] = getLoginId();
            }
        }
        return true;
    }

    /**
     * ��Flush����õķ���
     *
     * @param iterator Iterator
     * @throws CallbackException
     */
    public void postFlush(Iterator iterator) throws CallbackException {
    }

    /**
     * ��Flushǰ���õķ���
     * @param iterator Iterator
     * @throws CallbackException
     */
    public void preFlush(Iterator iterator) throws CallbackException {
    }

    private static String getLoginId() {
        String loginId = "";
        try {
            ThreadVariant thread = ThreadVariant.getInstance();
            loginId = (String) thread.get("LOGIN_ID");
        } catch (Exception e) {
            log.warn(e);
        }
        return loginId;
    }


}
