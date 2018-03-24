package server.framework.taglib.html;



public class AlphaFieldType extends AbstractFieldType
{

    public AlphaFieldType()
    {
    }

    public String getMaxLength(String fmt)
    {
        return null;
    }

    public String getStyleClass(String req, String sreq, boolean readonly, boolean errorFlg)
    {
        StringBuffer sbuf = new StringBuffer(" Atext");
        renderCommons(sbuf, req, sreq, readonly, errorFlg);
        return sbuf.toString();
    }

    public String doFormat(String value, String defaultvalue, String fmt)
    {
        return value;
    }
}
