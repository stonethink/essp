package server.framework.taglib.util;

public interface ISelectOption
{

    public abstract String getLabel();

    public abstract String getValue();

    public abstract String getTitle();

    public abstract void setLabel(String s);

    public abstract void setValue(String s);

    public abstract void setTitle(String s);
}
