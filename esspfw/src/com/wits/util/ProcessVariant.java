package com.wits.util;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * 进程全局共享变量区
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
public class ProcessVariant {
    private static HashMap variantMap = new HashMap();
    private static HashMap listenerMap = new HashMap();

    public static void set(String key, Object obj) {
        variantMap.put(key, obj);
    }

    public static Object get(String key) {
        return variantMap.get(key);
    }

    public static void clear() {
        variantMap.clear();
    }

    public static Set entrySet() {
        return variantMap.entrySet();
    }

    public static Set keySet() {
        return variantMap.keySet();
    }

    public static boolean containsKey(Object key) {
        return variantMap.containsKey(key);
    }

    public static boolean containsValue(Object value) {
        return variantMap.containsValue(value);
    }

    public static void addDataListener(String key, IVariantListener listener) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            listeners.add(listener);
        } else {
            List listeners = new ArrayList();
            listeners.add(listener);
            listenerMap.put(key, listeners);
        }
    }

    public static IVariantListener removeDataListener(String key, IVariantListener listener) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            listeners.remove(listener);
            return listener;
        }
        return null;
    }

    public static List getDataListener(String key) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            return listeners;
        }
        return null;
    }

    public static void clearDataListener(String key) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            listeners.clear();
        }
    }

    public static void fireDataChange(String key) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            if(listeners!=null && listeners.size()>0) {
                for (int i = 0; i < listeners.size(); i++) {
                    IVariantListener listener=(IVariantListener)listeners.get(i);
                    Object data=variantMap.get(key);
                    listener.dataChanged(key,data);
                }
            }
        }

    }

    public static void fireDataChange(String key,IVariantListener filter) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            if(listeners!=null && listeners.size()>0) {
                for (int i = 0; i < listeners.size(); i++) {
                    IVariantListener listener=(IVariantListener)listeners.get(i);
                    if(listener!=null && !listener.equals(filter)) {
                        Object data = variantMap.get(key);
                        listener.dataChanged(key,data);
                    } else if(listener!=null && filter==null) {
                        Object data = variantMap.get(key);
                        listener.dataChanged(key,data);
                    }
                }
            }
        }
    }

    public static void fireDataChange(String key,List filters) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            if(listeners!=null && listeners.size()>0) {
                for (int i = 0; i < listeners.size(); i++) {
                    IVariantListener listener=(IVariantListener)listeners.get(i);
                    if(listener!=null) {
                        if(filters!=null && !filters.contains(listener)) {
                            Object data = variantMap.get(key);
                            listener.dataChanged(key,data);
                        } else if(filters==null) {
                            Object data = variantMap.get(key);
                            listener.dataChanged(key,data);
                        }
                    }
                }
            }
        }
    }

}
