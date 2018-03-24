package server.framework.taglib.util;

import java.io.*;

public class SelectOptionImpl
    implements ISelectOption, Serializable
{

    private String label;
    private String value;
    private String title;
    public SelectOptionImpl()
    {
        label = null;
        value = null;
        title = null;
    }

    public SelectOptionImpl(String inLabel, String inValue)
    {
        label = null;
        value = null;
        label = inLabel;
        value = inValue;

    }

    public SelectOptionImpl(String inLabel, String inValue,String inTitle)
    {
        label = null;
        value = null;
        title = null;
        label = inLabel;
        value = inValue;
        title = inTitle;
    }


    public String getLabel()
    {
        return label;
    }

    public String getValue()
    {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public void setLabel(String inLabel)
    {
        label = inLabel;
    }

    public void setValue(String inValue)
    {
        value = inValue;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
