package client.essp.common.view.ui;

import java.util.*;

import client.framework.common.*;

/**
 *
 * <p>Title: 为Applet提供多语言支持，从/resource/**读取资源文件</p>
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
public class MessageUtil {
    /**
     *包括所有的Applet中国际信息的文件名
     */
    private static final List<String> BASE = new ArrayList();
    static {
        BASE.add("resources.errorMessage"); //错误信息
        BASE.add("resources.timesheet"); //timesheet模块
        BASE.add("resources.common"); //可共用的国际化信息
    }

    /**
     * 所有已读入的ResourceBundles,按Locale分类
     * Key为Locale，Value为List中放置该Locale下所有ResourceBundles
     */
    private static final Map<Locale,List> RESOURCE = new HashMap();
    public static String getMessage(String key) {
        return getMessage(key,Global.locale);
    }

    /**
     * 先看该local下ResourceBundles是否有读入，若没有则读入，
     * 遍历该Locale下所与ResourceBundles查找Key对应的Message
     * 没有对应的Key的Message，则直接返回Key
     * @param key String
     * @param local Locale
     * @return String
     */
    public static String getMessage(String key, Locale local) {
        if(key == null || "".equals(key)){
            return "";
        }
        String formattedKey = formatKey(key);
        if (!RESOURCE.containsKey(local)) {
            loadResourceBundles(local);
        }
        String msg = getMessageFromCache(formattedKey,local);
        if(msg == null || "".equals(msg)){
            return key;
        }
        return msg;
    }

    /**
     * 因为ResourceBundle中不支持Key中有空格，所以把Key中所有空格替换成下划线再查找
     * @param key String
     * @return String
     */
    private static String formatKey(String key) {
        return key.replace(" ","_");
    }

    /**
     * 根据BASE中指定名称Load所有ResourceBundle
     * @param local Locale
     */
    private static void loadResourceBundles(Locale locale) {
        List<ResourceBundle> resources = resources = new ArrayList();
        for (String base: BASE) {
            try{
                ResourceBundle resource = ResourceBundle.getBundle(base, locale);
                resources.add(resource);
            }catch(Throwable tx){
                System.out.println("ERROR:" + tx.getMessage());
            }
        }
        RESOURCE.put(locale, resources);
    }

    private static String getMessageFromCache(String key,Locale locale) {
        List<ResourceBundle> resources =  (List) RESOURCE.get(locale);
        if(resources == null || resources.isEmpty()){
            return "";
        }

        for(ResourceBundle resource: resources){
            try{
                String msg = resource.getString(key);
                if(msg != null){
                    return msg;
                }
            }catch(Throwable tx){
//                System.out.println("WARN:" + tx.getMessage());
//                return "";
            }
        }
        return "";
    }

    public static void main(String[] args){
//        Global.locale = new Locale("zh");
        System.out.println(MessageUtil.getMessage("error.system"));

//        Config cfg = new Config("resources.errorMessage");
//        System.out.println(cfg.getValue("error.system"));
    }
}
