package client.framework.common;

import java.awt.Font;

/**
 * 这是一个常量类。如果一个应用中需要使用本应用专用的常量，那么请不要在这个类中增加常量，
 * 请先从这个常量类派生一个常量子类，然后在这个常量子类中增加应用自己的常量。切记切记!!!
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
    /**
     * define table cell property
     */
    public static final String EDITABLE = "Editable";
    public static final String UNEDITABLE = "Uneditable";

    /**
     * define Date Component Type
     */
    public static final String DATE_YYYYMMDD = "YYYYMMDD";
    public static final String DATE_HHMMSS = "HHMMSS";
    public static final String DATE_HHMM = "HHMM";
    public static final String DATE_YYYY = "YYYY";
    public static final String DATE_YYYYMM = "YYYYMM";
    public static final String DATE_MM = "MM";
    public static final String DATE_DD = "DD";
    public static final String DATE_MMDD = "MMDD";
    public static final String DATE_YYMMDD = "YYMMDD";
    public static final String DATE_YYMM = "YYMM";

    public static final Font DEFAULT_BUTTON_FONT=new Font("宋体", 0, 13);
    public static final Font DEFAULT_TEXTFIELD_FONT=new Font("宋体", 0, 12);

    /**
     * Confirm Dialog Setting
     */
    public static final int CANCEL=-1;
    public static final int ALWAYS_CANCEL=-2;
    public static final int OK=1;
    public static final int ALWAYS_OK=2;

    public static final String ACTION_FORM_KEY="actionForm";
    /**
     * 成功信息画面在struts-config.xml中对应的ID
     */
    public static final String PAGE_SUCCESS   = "success";
    /**
     * 错误信息画面在struts-config.xml中对应的ID
     */
    public static final String PAGE_FAILURE   = "failure";
}
