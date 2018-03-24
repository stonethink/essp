package com.wits.util;

import java.util.*;

/**
 * <p> ��ȡ�����ļ���������Ϣ�������ļ����ļ�����ʽ��fileName.properties���ļ����ݸ�ʽ��stringKey=value�� </p>
 * @version 1.0
 * @date 2003/03/06
 * @author Stone
 */
public class Config {
    private Hashtable ht = new Hashtable();

    /**
     *���췽������ȡ�����ļ������ݡ�
     *@param String configFileName�������ļ����ļ�����ע�⣬������.properties�����֡�
     */
    public Config() {
        _Config("config");
    }

    public Config(String configName) {
        _Config(configName);
    }

    private void _Config(String configFileName) {
        try {
            ResourceBundle resources = ResourceBundle.getBundle(configFileName, Locale.getDefault());
            Enumeration enumVar = resources.getKeys();
            while (enumVar.hasMoreElements()) {
                String key = (String)enumVar.nextElement();
                String value = resources.getString(key).trim();
                ht.put(key, value);
            }
        } catch (MissingResourceException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     *ȡ�������ü���ֵ�ķ�������ȡ�������ü���ֵ�����ݡ�
     *@param String key�������ļ��ļ�����
     *@return String value��Ϊ�ü���ֵ��
     */
    public String getValue(String key) {
        return (String)ht.get(key);
    }

    public Object getObject(String key) {
        return ht.get(key);
    }

    public void setValue(String key,Object value) {
      ht.put(key,value);
    }

   /**
     *ȡ���������ļ������ݡ�
     *@return Hashtable��Ϊ�������ļ���ȫ�����ݵ�hash��
     */
    public Hashtable getConfig() {
        return ht;
    }

    public static void main(java.lang.String[] args) {
        String configName;
        String key;
        String value;
        Hashtable mHt;
        Config mCfg;
        System.out.println("Begin load Config");
        //mCfg = new Config("config");
        mCfg = new Config();
        key = "DB.Username";
        value = mCfg.getValue(key);
        System.out.println("key=" + key + ";value=" + value);
        mHt = mCfg.getConfig();
        Enumeration enumVar = mHt.keys();
        while (enumVar.hasMoreElements()) {
            key = (String)enumVar.nextElement();
            value = (String)mHt.get(key);
            System.out.println("key=" + key + ";value=" + value);
        }
    }
}
