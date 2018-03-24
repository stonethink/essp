package client.framework.common;

import java.applet.Applet;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Global {
  /**
   * Controllerȫ��URL
   */
  public static String defaultControllerURL = "http://localhost:8080/essp/Controller";

  /**
   * ��ǰWebӦ�õĸ�Ŀ¼
   */
  public static String appRoot="http://localhost:8080/essp";

  /**
   * ȫ��Appletʵ��
   */
  public static Applet applet = null;
  /**
   * �û�ID
   */
  public static String userId = "";
  /**
   * �û���
   */
  public static String userName = "";
  public static String userType = "";

  /**����ģʽ����*/
  public static boolean debug = false;

  /**
   * ��������
   */
  public static String todayDateStr = "";
  public static String todayDatePattern = "yyyyMMdd";
  public static Date todayDate = new Date();
  //Ĭ��ʱ������Ϊ����ʱ��
  public static final String DEFAULT_TIMEZONE = "Asia/Shanghai";
  public static String timeZoneID = DEFAULT_TIMEZONE;
  //����
  public static Locale locale = Locale.getDefault();
  /**��������ѡ�������*/
  public static Calendar selDateFromCalendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
}
