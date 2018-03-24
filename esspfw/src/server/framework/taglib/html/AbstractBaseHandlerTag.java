package server.framework.taglib.html;

import java.util.Locale;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import server.framework.taglib.util.TagUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import server.framework.taglib.util.Constants;

public abstract class AbstractBaseHandlerTag extends BodyTagSupport
{

    protected static MessageResources messages = MessageResources.getMessageResources(Constants.MESSAGE_CONFIG);
    protected Logger log;
    protected static final Locale defaultLocale = Locale.getDefault();
    private String onclick;
    private String ondblclick;
    private String onmouseover;
    private String onmouseout;
    private String onmousemove;
    private String onmousedown;
    private String onmouseup;
    private String onkeydown;
    private String onkeyup;
    private String onkeypress;
    private String onselect;
    private String onchange;
    private String onblur;
    private String onfocus;
    private boolean disabled;
    private boolean readonly;
    private String styleClass;
    private String styleId;
    private String locale;

    public AbstractBaseHandlerTag()
    {
        log = Logger.getLogger(this.getClass());
        onclick = null;
        ondblclick = null;
        onmouseover = null;
        onmouseout = null;
        onmousemove = null;
        onmousedown = null;
        onmouseup = null;
        onkeydown = null;
        onkeyup = null;
        onkeypress = null;
        onselect = null;
        onchange = null;
        onblur = null;
        onfocus = null;
        disabled = false;
        readonly = false;
        styleClass = null;
        styleId = null;
        locale = "org.apache.struts.action.LOCALE";
    }

    public void setOnclick(String onClick)
    {
        onclick = onClick;
    }

    public String getOnclick()
    {
        return onclick;
    }

    public void setOndblclick(String onDblClick)
    {
        ondblclick = onDblClick;
    }

    public String getOndblclick()
    {
        return ondblclick;
    }

    public void setOnmousedown(String onMouseDown)
    {
        onmousedown = onMouseDown;
    }

    public String getOnmousedown()
    {
        return onmousedown;
    }

    public void setOnmouseup(String onMouseUp)
    {
        onmouseup = onMouseUp;
    }

    public String getOnmouseup()
    {
        return onmouseup;
    }

    public void setOnmousemove(String onMouseMove)
    {
        onmousemove = onMouseMove;
    }

    public String getOnmousemove()
    {
        return onmousemove;
    }

    public void setOnmouseover(String onMouseOver)
    {
        onmouseover = onMouseOver;
    }

    public String getOnmouseover()
    {
        return onmouseover;
    }

    public void setOnmouseout(String onMouseOut)
    {
        onmouseout = onMouseOut;
    }

    public String getOnmouseout()
    {
        return onmouseout;
    }

    public void setOnkeydown(String onKeyDown)
    {
        onkeydown = onKeyDown;
    }

    public String getOnkeydown()
    {
        return onkeydown;
    }

    public void setOnkeyup(String onKeyUp)
    {
        onkeyup = onKeyUp;
    }

    public String getOnkeyup()
    {
        return onkeyup;
    }

    public void setOnkeypress(String onKeyPress)
    {
        onkeypress = onKeyPress;
    }

    public String getOnkeypress()
    {
        return onkeypress;
    }

    public void setOnchange(String onChange)
    {
        onchange = onChange;
    }

    public String getOnchange()
    {
        return onchange;
    }

    public void setOnselect(String onSelect)
    {
        onselect = onSelect;
    }

    public String getOnselect()
    {
        return onselect;
    }

    public void setOnblur(String onBlur)
    {
        onblur = onBlur;
    }

    public String getOnblur()
    {
        return onblur;
    }

    public void setOnfocus(String onFocus)
    {
        onfocus = onFocus;
    }

    public String getOnfocus()
    {
        return onfocus;
    }

    public void setDisabled(boolean aDisabled)
    {
        disabled = aDisabled;
    }

    public boolean getDisabled()
    {
        return disabled;
    }

    public void setReadonly(boolean aReadonly)
    {
        readonly = aReadonly;
    }

    public boolean getReadonly()
    {
        return readonly;
    }

    public void setStyleClass(String aStyleClass)
    {
        styleClass = aStyleClass;
    }

    public String getStyleClass()
    {
        return styleClass;
    }

    public void setStyleId(String aStyleId)
    {
        styleId = aStyleId;
    }

    public String getStyleId()
    {
        return styleId;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String aLocale)
    {
        locale = aLocale;
    }

    public void release()
    {
        super.release();
        locale = "org.apache.struts.action.LOCALE";
        onclick = null;
        ondblclick = null;
        onmouseover = null;
        onmouseout = null;
        onmousemove = null;
        onmousedown = null;
        onmouseup = null;
        onkeydown = null;
        onkeyup = null;
        onkeypress = null;
        onselect = null;
        onchange = null;
        onblur = null;
        onfocus = null;
        disabled = false;
        readonly = false;
        styleClass = null;
        styleId = null;
    }

    protected String prepareStyles()
        throws JspException
    {
        StringBuffer styles = new StringBuffer();
        TagUtils.appendAttribute(styles, "class", getStyleClass());
        TagUtils.appendAttribute(styles, "id", getStyleId());
        return styles.toString();
    }

    protected String prepareEventHandlers()
        throws JspException
    {
        StringBuffer handlers = new StringBuffer(prepareStyles());
        prepareMouseEvents(handlers);
        prepareKeyEvents(handlers);
        prepareTextEvents(handlers);
        prepareFocusEvents(handlers);
        return handlers.toString();
    }

    protected void prepareMouseEvents(StringBuffer handlers)
    {
        TagUtils.appendAttribute(handlers, "onclick", getOnclick());
        TagUtils.appendAttribute(handlers, "ondblclick", getOndblclick());
        TagUtils.appendAttribute(handlers, "onmouseover", getOnmouseover());
        TagUtils.appendAttribute(handlers, "onmouseout", getOnmouseout());
        TagUtils.appendAttribute(handlers, "onmousemove", getOnmousemove());
        TagUtils.appendAttribute(handlers, "onmousedown", getOnmousedown());
        TagUtils.appendAttribute(handlers, "onmouseup", getOnmouseup());
    }

    protected void prepareKeyEvents(StringBuffer handlers)
    {
        TagUtils.appendAttribute(handlers, "onkeydown", getOnkeydown());
        TagUtils.appendAttribute(handlers, "onkeyup", getOnkeyup());
        TagUtils.appendAttribute(handlers, "onkeypress", getOnkeypress());
    }

    protected void prepareTextEvents(StringBuffer handlers)
    {
        TagUtils.appendAttribute(handlers, "onselect", getOnselect());
        TagUtils.appendAttribute(handlers, "onchange", getOnchange());
    }

    protected void prepareFocusEvents(StringBuffer handlers)
    {
        TagUtils.appendAttribute(handlers, "onblur", getOnblur());
        TagUtils.appendAttribute(handlers, "onfocus", getOnfocus());
        if(disabled)
            handlers.append(" disabled=\"disabled\"");
        if(readonly)
            handlers.append(" readonly=\"readonly\"");
    }
}
