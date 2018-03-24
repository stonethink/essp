package com.wits.util;

import java.lang.System;
import java.util.Properties;
import java.util.Hashtable;
import java.util.Enumeration;

public class SysInfo {
    //存放JVM获得的系统属性
    static private Properties _property;

    //Main function
    public static void main(String[] args) {
        getSystemProperty();
        Hashtable hashKey;
        hashKey = new Hashtable();
        //将系统信息的关键字和标题放到hashtable
        hashKey.put("java.home", "Java安装目录          ");
        hashKey.put("java.class.path", "装载类的路径          ");
        hashKey.put("java.specification.version", "Java API 规范的版本   ");
        hashKey.put("java.specification.vendor", "Java API 规范的厂商   ");
        hashKey.put("java.specification.name", "Java API 规范的名称   ");
        hashKey.put("java.version", "Java API 实现的版本   ");
        hashKey.put("java.vendor", "Java API 实现的厂商   ");
        hashKey.put("java.vendor.url", "Java API 规范厂商的URL");
        hashKey.put("java.vm.specification.version", "Java虚拟机规范的版本  ");
        hashKey.put("java.vm.specification.vendor", "Java虚拟机规范的厂商  ");
        hashKey.put("java.vm.specification.name", "Java虚拟机规范的名称  ");
        hashKey.put("java.vm.version", "Java虚拟机实现的版本  ");
        hashKey.put("java.vm.vendor", "Java虚拟机实现的厂商  ");
        hashKey.put("java.vm.name", "Java虚拟机实现的名称  ");
        hashKey.put("java.class.version", "Java类文件格式的版本  ");
        hashKey.put("os.name", "主机操作系统的名称    ");
        hashKey.put("os.arch", "主机操作系统的体系结构");
        hashKey.put("os.version", "主机操作系统的版本    ");
        hashKey.put("file.separator", "平台目录的分隔符      ");
        hashKey.put("path.separator", "平台路径的分隔符      ");
        hashKey.put("line.separator", "平台文本行的分隔符    ");
        hashKey.put("user.name", "当前用户的帐户名称    ");
        hashKey.put("user.home", "当前用户的根目录      ");
        hashKey.put("user.dir", "当前工作目录          ");
        Enumeration enumVar;
        String propertyKey;
        enumVar = hashKey.keys();
        while (enumVar.hasMoreElements()) {
            propertyKey = (String)enumVar.nextElement();
            System.out.println((String)hashKey.get(propertyKey) + ":" + _property.getProperty(propertyKey));
        }
    }

    /**
     * 获得系统属性列表
     * @return Properties
     */
    static public Properties getSystemProperty() {
        _property = System.getProperties();
        return _property;
    }
}
