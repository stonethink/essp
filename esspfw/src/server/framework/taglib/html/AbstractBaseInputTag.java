package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;


public abstract class AbstractBaseInputTag extends AbstractBaseControlerTag
{
    protected String maxlength;
    protected String fmt;
    protected String fieldtype;
    protected String defaultvalue;

    public AbstractBaseInputTag()
    {
        maxlength = null;
        fmt = null;
        fieldtype = null;
        defaultvalue = "true";
    }

    public String getMaxlength()
    {
        return maxlength;
    }

    public void setMaxlength(String aMaxlength)
    {
        maxlength = aMaxlength;
    }

    public String getFmt()
    {
        return fmt;
    }

    public void setFmt(String string)
    {
        fmt = string;
    }

    public String getFieldtype()
    {
        return fieldtype;
    }

    public void setFieldtype(String string)
    {
        fieldtype = string;
    }

    public String getDefaultvalue()
    {
        return defaultvalue;
    }

    public void setDefaultvalue(String string)
    {
        defaultvalue = string;
    }

    public int doStartTag()
        throws JspException
    {
        return 1;
    }

    public int doEndTag()
        throws JspException
    {
        return 6;
    }

    protected void doCheck()
        throws JspException
    {
        super.doCheck();
        String message = null;
        if(!"true".equals(defaultvalue) && !"false".equals(defaultvalue))
        {
            message = AbstractBaseHandlerTag.messages.getMessage("value.error", "defaultvalue", "[true]or[false]");
            throw new JspException(message);
        }
        if(("number".equals(fieldtype) || "money".equals(fieldtype)) && fmt == null)
        {
            message = AbstractBaseHandlerTag.messages.getMessage("fieldtype.need", property, "fmt");
            throw new JspException(message);
        } else
        {
            return;
        }
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendControlerAttribute(sbuf, "fieldtype", fieldtype);
        TagUtils.appendControlerAttribute(sbuf, "fmt", fmt);
    }

    public void release()
    {
        super.release();
        maxlength = null;
        fmt = null;
        defaultvalue = "true";
        fieldtype = null;
    }

}
