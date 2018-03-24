package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;

public class FooterButtonTag extends ButtonTag
{

    protected String function_key;
    protected final String DISPLAY_ATTRIBUTE_FOODERBUTTON = "FooterButton";

    public String getFunction_key()
    {
        return function_key;
    }

    public void setFunction_key(String string)
    {
        function_key = string;
    }

    public FooterButtonTag()
    {
        function_key = null;
        type = "button";
    }

    public int doEndTag()
        throws JspException
    {
        doCheck();
        doDisplayLabel();
        getClass();
        doSetStyleClass("FooterButton");
        return doWrite();
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendAttribute(sbuf, "id", "PF" + function_key);
        TagUtils.appendControlerAttribute(sbuf, "scriptstr", getOnclick());
        log.debug(sbuf.toString());
    }

    protected void doCheck()
        throws JspException
    {
        super.doCheck();
        String message = null;
        int key = 0;
        try
        {
            key = Integer.parseInt(function_key);
        }
        catch(NumberFormatException e)
        {
            throw new JspException(e);
        }
        if(1 > key || key > 24)
        {
            message = AbstractBaseHandlerTag.messages.getMessage("value.error", "function_key", "[1]-[24]");
            log.error(message);
            throw new JspException(message);
        } else
        {
            return;
        }
    }

    public void release()
    {
        super.release();
        function_key = null;
    }
}
