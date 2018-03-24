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
   * Controller全局URL
   */
  public static String defaultControllerURL = "http://localhost:8080/essp/Controller";

  /**
   * 当前Web应用的根目录
   */
  public static String appRoot="http://localhost:8080/essp";

  /**
   * 全局Applet实例
   */
  public static Applet applet = null;
  /**
   * 用户ID
   */
  public static String userId = "";
  /**
   * 用户名
   */
  public static String userName = "";
  public static String userType = "";

  /**调试模式设置*/
  public static boolean debug = false;

  /**
   * 今天日期
   */
  public static String todayDateStr = "";
  public static String todayDatePattern = "yyyyMMdd";
  public static Date todayDate = new Date();
  //默认时区设置为北京时区
  public static final String DEFAULT_TIMEZONE = "Asia/Shanghai";
  public static String timeZoneID = DEFAULT_TIMEZONE;
  //区域
  public static Locale locale = Locale.getDefault();
  /**从日历中选择的日期*/
  public static Calendar selDateFromCalendar = Calendar.getInstance(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
}
