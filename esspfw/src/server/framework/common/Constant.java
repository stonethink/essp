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
   * �ɹ���Ϣ������struts-config.xml�ж�Ӧ��ID
   */
  public static final String PAGE_SUCCESS = "success";
  /**
   * ������Ϣ������struts-config.xml�ж�Ӧ��ID
   */
  public static final String PAGE_FAILURE = "failure";
  public static final String PAGE_LIST="list";
  public static final String PAGE_INSERT="insert";
  public static final String PAGE_UPDATE="update";
  public static final String PAGE_SELECT="select";
  public static final String PAGE_DELETE="delete";

  /**
   * ���ݿ��¼״̬��1�������ã�0����������
   */
  public static final String RST_NORMAL = "N";
  public static final String RST_DELETE = "X";

  public static boolean IGNORE_ENCODING=true;
}
