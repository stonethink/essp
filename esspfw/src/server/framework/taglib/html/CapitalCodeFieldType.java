package server.framework.taglib.html;


public class CapitalCodeFieldType extends AbstractFieldType
{

    public CapitalCodeFieldType()
    {
    }

    public String getMaxLength(String fmt)
    {
        return null;
    }

    public String getStyleClass(String req, String sreq, boolean readonly, boolean errorFlg)
    {
        StringBuffer sbuf = new StringBuffer(" CapitalText");
        renderCommons(sbuf, req, sreq, readonly, errorFlg);
        return sbuf.toString();
    }

    public String doFormat(String value, String defaultvalue, String fmt)
    {
        if(value == null || "".equals(value))
            return "";
        else
            return value.toUpperCase();
    }
}
