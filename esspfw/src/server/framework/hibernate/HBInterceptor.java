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
 * <p>Description: 在此添加Hibernate对持久化类执行各种动作时，需要执行的操作 </p>
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
     * 在脏数据检测是调用的方法
     *     持久话对象变化时要执行的操作
     * @param object Object
     * @param id Serializable
     * @param currentState Object[]     更新后的属性值
     * @param previousState Object[]    更新前的属性值
     * @param propertyName String[]     属性的名称数组
     * @param types Type[]              属性的类型数组
     * @return int[]
     */
    public int[] findDirty(Object object, Serializable id,
                           Object[] currentState, Object[] previousState,
                           String[] propertyName, Type[] types) {
        return null;
    }

    /**
     * 初始化持久化对象时调用的方法
     *     获取持久化对象时要执行的操作
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
     * 在删除前调用的方法
     *    删除时要执行的操作
     * @param object Object
     * @param id Serializable
     * @param state Object[]          属性值
     * @param propertyName String[]   属性的名称数组
     * @param types Type[]            属性的类型数组
     * @throws CallbackException
     */
    public void onDelete(Object object, Serializable id,
                         Object[] state, String[] propertyName,
                         Type[] types) throws CallbackException {
    }

    /**
     * 在FlushDirty前调用的方法
     *   更新时要执行的操作
     * @param object Object
     * @param id Serializable
     * @param currentState Object[]     更新后的属性值
     * @param previousState Object[]    更新前的属性值
     * @param propertyName String[]     属性的名称数组
     * @param types Type[]              属性的类型数组
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
     * 加载后调用的方法
     *
     * @param object Object
     * @param id Serializable
     * @param state Object[]            属性值
     * @param propertyName String[]     属性的名称数组
     * @param types Type[]              属性的类型数组
     * @return boolean
     * @throws CallbackException
     */
    public boolean onLoad(Object object, Serializable id,
                          Object[] state, String[] propertyName,
                          Type[] types) throws CallbackException {
        return false;
    }

    /**
     * 在保存前调用的方法
     *    新增时要执行的操作
     * @param object Object
     * @param id Serializable
     * @param state Object[]             属性值
     * @param propertyName String[]      属性的名称数组
     * @param types Type[]               属性的类型数组
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
     * 在Flush后调用的方法
     *
     * @param iterator Iterator
     * @throws CallbackException
     */
    public void postFlush(Iterator iterator) throws CallbackException {
    }

    /**
     * 在Flush前调用的方法
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
