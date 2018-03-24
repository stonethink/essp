package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.ResponseUtils;
import server.framework.taglib.util.Global;

public class ButtonTag extends AbstractBaseControlerTag
{
    private String label;
    protected String text;
    protected String message;
    protected String name;
    protected String reqcheck;
    protected String path;
    protected String type;
    protected final String DISPLAY_ATTRIBUTE_BUTTON = "Button";
    protected Logger log;

    public String getText()
    {
        return text;
    }

    public void setText(String string)
    {
        text = string;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String string)
    {
        message = string;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String string)
    {
        name = string;
    }

    public String getReqcheck()
    {
        return reqcheck;
    }

    public void setReqcheck(String string)
    {
        reqcheck = string;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String string)
    {
        path = string;
    }

    public ButtonTag()
    {
        label = null;
        text = null;
        message = null;
        name = null;
        reqcheck = "false";
        path = null;
        type = "button";
        log=Logger.getLogger(this.getClass());
    }

    public int doStartTag()
        throws JspException
    {
        text = null;
        return 2;
    }

    public int doAfterBody()
        throws JspException
    {
        if(bodyContent != null)
        {
            String value = bodyContent.getString().trim();
            if(value.length() > 0)
                text = value;
        }
        return 0;
    }

    public int doEndTag()
        throws JspException
    {
        property = name;
        doCheck();
        doDisplayLabel();
        doFocusControl();
        getClass();
        doSetStyleClass("Button");
        prepareEventScript();
        return doWrite();
    }

    protected void doCheck()
        throws JspException
    {
        String errorMessage = null;
        if(!"true".equalsIgnoreCase(reqcheck) && !"false".equalsIgnoreCase(reqcheck))
        {
            errorMessage = AbstractBaseHandlerTag.messages.getMessage("value.error", "reqcheck", "[true]or[false]");
            log.error(errorMessage);
            throw new JspException(errorMessage);
        }
        if(("doSubmit(this)".equals(getOnclick()) || "doSubmit(this);".equals(getOnclick())) && (path == null || "".equals(path)))
        {
            errorMessage = AbstractBaseHandlerTag.messages.getMessage("fieldtype.need", "submit", "path");
            log.error(errorMessage);
            throw new JspException(errorMessage);
        } else
        {
            return;
        }
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendAttribute(sbuf, "type", type);
        TagUtils.appendControlerAttribute(sbuf, "message", message);
        TagUtils.appendControlerAttribute(sbuf, "reqcheck", reqcheck);
        TagUtils.appendControlerAttribute(sbuf, "path", path);
        log.debug(sbuf.toString());
    }

    public void doDisplayLabel()
    {
        label = value;
        if(label == null && text != null)
            label = text.trim();
        if(label == null || "".equals(label))
            label = "Click";
        setValue(label);
    }

    public void doSetStyleClass(String displayAttribute)
    {
        String styleclass = getStyleClass();
        if(styleclass == null || "".equals(styleclass))
            styleclass = displayAttribute;
        setStyleClass(styleclass);
    }

    private void prepareEventScript()
    {
        if(getOnblur() == null) {
            if(Global.isIE) {
                setOnblur("doBlur();");
            } else {
                setOnblur("doBlur(this);");
            }

        }
        if(getOnfocus() == null) {
            if(Global.isIE) {
                setOnfocus("doFocus();");
            } else {
                setOnfocus("doFocus(this);");
            }

        }
    }

    public int doWrite()
        throws JspException
    {
        StringBuffer results = new StringBuffer("<input");
        prepareAttribute(results);
        results.append(TagUtils.getElementClose());
        ResponseUtils.write(pageContext, results.toString());
        release();
        return 6;
    }

    public void release()
    {
        super.release();
        label = null;
        message = null;
        text = null;
        type = "button";
        name = null;
        reqcheck = "false";
        path = null;
    }
}
