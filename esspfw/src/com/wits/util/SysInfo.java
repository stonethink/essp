package com.wits.util;

import java.lang.System;
import java.util.Properties;
import java.util.Hashtable;
import java.util.Enumeration;

public class SysInfo {
    //���JVM��õ�ϵͳ����
    static private Properties _property;

    //Main function
    public static void main(String[] args) {
        getSystemProperty();
        Hashtable hashKey;
        hashKey = new Hashtable();
        //��ϵͳ��Ϣ�Ĺؼ��ֺͱ���ŵ�hashtable
        hashKey.put("java.home", "Java��װĿ¼          ");
        hashKey.put("java.class.path", "װ�����·��          ");
        hashKey.put("java.specification.version", "Java API �淶�İ汾   ");
        hashKey.put("java.specification.vendor", "Java API �淶�ĳ���   ");
        hashKey.put("java.specification.name", "Java API �淶������   ");
        hashKey.put("java.version", "Java API ʵ�ֵİ汾   ");
        hashKey.put("java.vendor", "Java API ʵ�ֵĳ���   ");
        hashKey.put("java.vendor.url", "Java API �淶���̵�URL");
        hashKey.put("java.vm.specification.version", "Java������淶�İ汾  ");
        hashKey.put("java.vm.specification.vendor", "Java������淶�ĳ���  ");
        hashKey.put("java.vm.specification.name", "Java������淶������  ");
        hashKey.put("java.vm.version", "Java�����ʵ�ֵİ汾  ");
        hashKey.put("java.vm.vendor", "Java�����ʵ�ֵĳ���  ");
        hashKey.put("java.vm.name", "Java�����ʵ�ֵ�����  ");
        hashKey.put("java.class.version", "Java���ļ���ʽ�İ汾  ");
        hashKey.put("os.name", "��������ϵͳ������    ");
        hashKey.put("os.arch", "��������ϵͳ����ϵ�ṹ");
        hashKey.put("os.version", "��������ϵͳ�İ汾    ");
        hashKey.put("file.separator", "ƽ̨Ŀ¼�ķָ���      ");
        hashKey.put("path.separator", "ƽ̨·���ķָ���      ");
        hashKey.put("line.separator", "ƽ̨�ı��еķָ���    ");
        hashKey.put("user.name", "��ǰ�û����ʻ�����    ");
        hashKey.put("user.home", "��ǰ�û��ĸ�Ŀ¼      ");
        hashKey.put("user.dir", "��ǰ����Ŀ¼          ");
        Enumeration enumVar;
        String propertyKey;
        enumVar = hashKey.keys();
        while (enumVar.hasMoreElements()) {
            propertyKey = (String)enumVar.nextElement();
            System.out.println((String)hashKey.get(propertyKey) + ":" + _property.getProperty(propertyKey));
        }
    }

    /**
     * ���ϵͳ�����б�
     * @return Properties
     */
    static public Properties getSystemProperty() {
        _property = System.getProperties();
        return _property;
    }
}
