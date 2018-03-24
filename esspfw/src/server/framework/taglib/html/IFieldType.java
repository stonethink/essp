package server.framework.taglib.html;


public interface IFieldType
{

    public abstract String getMaxLength(String s);

    public abstract String getStyleClass(String s, String s1, boolean flag, boolean flag1);

    public abstract String doFormat(String s, String s1, String s2);
}
