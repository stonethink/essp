package server.framework.common;

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
public class Constant {

  public static final String ACTION_FORM_KEY = "actionForm";
  public static final String VIEW_BEAN_KEY = "webVo";

  /**
   * 成功信息画面在struts-config.xml中对应的ID
   */
  public static final String PAGE_SUCCESS = "success";
  /**
   * 错误信息画面在struts-config.xml中对应的ID
   */
  public static final String PAGE_FAILURE = "failure";
  public static final String PAGE_LIST="list";
  public static final String PAGE_INSERT="insert";
  public static final String PAGE_UPDATE="update";
  public static final String PAGE_SELECT="select";
  public static final String PAGE_DELETE="delete";

  /**
   * 数据库记录状态，1——可用，0——不可用
   */
  public static final String RST_NORMAL = "N";
  public static final String RST_DELETE = "X";

  public static boolean IGNORE_ENCODING=true;
}
