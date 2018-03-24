package server.framework.taglib.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Robin.zhang
 * @version 1.0
 */
public interface SelectContentInterface {
    //选择框option中属性的值(即value)
    public String getValue();

    //选择框option中显示的内容
    public String getShow();

    public String getUp();

    public int getId();
}
