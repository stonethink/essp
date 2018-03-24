package com.wits.util;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ThreadVariant extends HashMap {
    HashMap listenerMap = new HashMap();

    private static ThreadLocal thread = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return new ThreadVariant();
        }
    };

    protected ThreadVariant() {
        super();
    }

    public static ThreadVariant getInstance() {
        return (ThreadVariant) thread.get();
    }

    public void set(String key, Object obj) {
        this.put(key, obj);
    }

    public void addDataListener(String key, IVariantListener listener) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            listeners.add(listener);
        } else {
            List listeners = new ArrayList();
            listeners.add(listener);
            listenerMap.put(key, listeners);
        }
    }

    public IVariantListener removeDataListener(String key, IVariantListener listener) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            listeners.remove(listener);
            return listener;
        }
        return null;
    }

    public List getDataListener(String key) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            return listeners;
        }
        return null;
    }

    public void clearDataListener(String key) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            listeners.clear();
        }
    }

    public void fireDataChange(String key) {
        if (listenerMap.containsKey(key)) {
            List listeners = (List) listenerMap.get(key);
            if(listeners!=null && listeners.size()>0) {
                for (int i = 0; i < listeners.size(); i++) {
                    IVariantListener listener=(IVariantListener)listeners.get(i);
                    Object data=get(key);
                    listener.dataChanged(key,data);
                }
            }
        }

    }
}
