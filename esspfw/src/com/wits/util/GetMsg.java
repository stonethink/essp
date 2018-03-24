package com.wits.util;


/**
 * <p> ˵����
 *         �ṩ����MessageResources���壬ȡ�ö�Ӧ����Ϣ
 * </p>
 */
public class GetMsg {
//    private static Config cfg = new Config("MessageResources"); ///---del
  private static Config cfg = new Config("msg");

  /**
   *ȡ�������ü���ֵ�ķ�������ȡ�������ü���ֵ�����ݡ�
   *@param String key�������ļ��ļ�����
   *@return String value��Ϊ�ü���ֵ��
   */
  public static String getMsg(String key) {
    //��ȡMessageResources������Ϣ��
    //Config cfg = new Config("MessageResources");
    return (String) cfg.getValue(key);
  }

  /**
   *ȡ�������ü���ֵ�ķ�������ȡ�������ü���ֵ�����ݡ�
   *@param String key�������ļ��ļ�����
   *@return String value��Ϊ�ü���ֵ��
   */
  public static String getMsg(String resources, String key) {
    //��ȡMessageResources������Ϣ��
    Config cofig = new Config(resources);
    return (String) cofig.getValue(key);
  }

  public static void main(java.lang.String[] args) {
    String key;
    String value;
    String outValue;
    key = "T0001";
    value = BytesConverter.asc2gb(GetMsg.getMsg(key));

    System.out.println("key=" + key + ";value=" + value);

    key = "00000000";
    value = GetMsg.getMsg("msg", key);
    outValue = BytesConverter.asc2gb(value);
    //outValue = BytesConverter.convert(value,"GB2312");

    System.out.println("key=" + key + ";value=" + value + ";outValue=" +
                       outValue);

    key = "9999999";
//    value = GetMsg.getMsg("MessageResources", key);
    value = GetMsg.getMsg("msg", key);
    outValue = BytesConverter.asc2gb(value);
    //outValue = BytesConverter.asc2gb("");
    //outValue = BytesConverter.convert(value,"GB2312");

    System.out.println("key=" + key + ";value=" + value + ";outValue=" +
                       outValue);

  }

}
