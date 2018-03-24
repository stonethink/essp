package client.framework.common;

import java.awt.Font;

/**
 * ����һ�������ࡣ���һ��Ӧ������Ҫʹ�ñ�Ӧ��ר�õĳ�������ô�벻Ҫ������������ӳ�����
 * ���ȴ��������������һ���������࣬Ȼ���������������������Ӧ���Լ��ĳ������м��м�!!!
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

    public static final Font DEFAULT_BUTTON_FONT=new Font("����", 0, 13);
    public static final Font DEFAULT_TEXTFIELD_FONT=new Font("����", 0, 12);

    /**
     * Confirm Dialog Setting
     */
    public static final int CANCEL=-1;
    public static final int ALWAYS_CANCEL=-2;
    public static final int OK=1;
    public static final int ALWAYS_OK=2;

    public static final String ACTION_FORM_KEY="actionForm";
    /**
     * �ɹ���Ϣ������struts-config.xml�ж�Ӧ��ID
     */
    public static final String PAGE_SUCCESS   = "success";
    /**
     * ������Ϣ������struts-config.xml�ж�Ӧ��ID
     */
    public static final String PAGE_FAILURE   = "failure";
}
