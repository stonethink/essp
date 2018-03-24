package client.essp.common.view.ui;

import java.util.*;

import client.framework.common.*;

/**
 *
 * <p>Title: ΪApplet�ṩ������֧�֣���/resource/**��ȡ��Դ�ļ�</p>
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
     *�������е�Applet�й�����Ϣ���ļ���
     */
    private static final List<String> BASE = new ArrayList();
    static {
        BASE.add("resources.errorMessage"); //������Ϣ
        BASE.add("resources.timesheet"); //timesheetģ��
        BASE.add("resources.common"); //�ɹ��õĹ��ʻ���Ϣ
    }

    /**
     * �����Ѷ����ResourceBundles,��Locale����
     * KeyΪLocale��ValueΪList�з��ø�Locale������ResourceBundles
     */
    private static final Map<Locale,List> RESOURCE = new HashMap();
    public static String getMessage(String key) {
        return getMessage(key,Global.locale);
    }

    /**
     * �ȿ���local��ResourceBundles�Ƿ��ж��룬��û������룬
     * ������Locale������ResourceBundles����Key��Ӧ��Message
     * û�ж�Ӧ��Key��Message����ֱ�ӷ���Key
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
     * ��ΪResourceBundle�в�֧��Key���пո����԰�Key�����пո��滻���»����ٲ���
     * @param key String
     * @return String
     */
    private static String formatKey(String key) {
        return key.replace(" ","_");
    }

    /**
     * ����BASE��ָ������Load����ResourceBundle
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
