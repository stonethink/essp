package server.framework.taglib.html;

import server.framework.taglib.util.FormatUtils;


public class TimehhmmssFieldType extends AbstractFieldType
{

    private static final String MAX_LENGTH = "8";

    public TimehhmmssFieldType()
    {
    }

    public String getMaxLength(String fmt)
    {
        return "8";
    }

    public String getStyleClass(String req, String sreq, boolean readonly, boolean errorFlg)
    {
        StringBuffer sbuf = new StringBuffer(" Ttext");
        renderCommons(sbuf, req, sreq, readonly, errorFlg);
        return sbuf.toString();
    }

    public String doFormat(String value, String defaultvalue, String fmt)
    {
        return FormatUtils.formatTimehhmmss(value);
    }
}
