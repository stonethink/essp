package server.framework.taglib.html;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import server.framework.taglib.util.SystemException;
import server.framework.taglib.util.TagUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.util.*;

public class MultiboxTag extends AbstractBaseControlerTag
{
    protected String text;
    protected String beanName;
    protected String scope;
    protected String name;
    protected String checked;
    protected String type;
    protected final String DISPLAY_ATTRIBUTE_MULTIBOX = "Checkbox";
    protected final String DISPLAY_ATTRIBUTE_ERROR = "Err";
    
    public String getText()
    {
        return text;
    }

    public void setText(String string)
    {
        text = string;
    }

    public String getBeanName()
    {
        return beanName;
    }

    public void setBeanName(String string)
    {
        beanName = string;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String string)
    {
        scope = string;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String string)
    {
        name = string;
    }

    public MultiboxTag()
    {
        text = null;
        beanName = null;
        scope = null;
        name = null;
        checked = null;
        type = "checkbox";
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
            if(!"".equals(value))
                text = value;
        }
        return 0;
    }

    public int doEndTag()
        throws JspException
    {
        property = name;
        doSetValue();
        doFocusControl();
        findErrorMsg();
        getClass();
        doSetStyleClass("Checkbox");
        doInitializeCheckbox();
        prepareEventScript();
        return doWrite();
    }

    private void doSetValue()
        throws JspException
    {
        if(value == null && text != null)
            value = text;
        if(value == null || value.trim().length() < 1)
        {
            String message = null;
            message = AbstractBaseHandlerTag.messages.getMessage("value.body.error", "value", "Body part");
            log.error(message);
            throw new JspException(message);
        } else
        {
            return;
        }
    }

    public void doSetStyleClass(String displayAttribute)
    {
        String styleclass = getStyleClass();
        if(styleclass == null || "".equals(styleclass))
            styleclass = displayAttribute;
        if(isMsg())
            styleclass = styleclass + " " + "Err";
        setStyleClass(styleclass);
    }

    private void doInitializeCheckbox()
        throws JspException
    {
        Object bean = RequestUtils.lookup(pageContext, beanName, scope);
        if(bean == null)
            return;
        String values[] = null;
        String message = null;
        try
        {
            values = BeanUtils.getArrayProperty(bean, name);
            if(values == null)
                values = new String[0];
        }
        catch(IllegalAccessException e)
        {
            message = AbstractBaseHandlerTag.messages.getMessage("lookup.error", beanName, name);
            throw new SystemException(message, e);
        }
        catch(InvocationTargetException e)
        {
            message = AbstractBaseHandlerTag.messages.getMessage("lookup.error", beanName, name);
            throw new SystemException(message, e);
        }
        catch(NoSuchMethodException e)
        {
            message = AbstractBaseHandlerTag.messages.getMessage("lookup.error", beanName, name);
            throw new SystemException(message, e);
        }
        for(int i = 0; i < values.length; i++)
            if(value.equals(values[i]))
            {
                checked = "checked";
                return;
            }

    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendAttribute(sbuf, "type", type);
        TagUtils.appendAttribute(sbuf, "checked", checked);
        log.debug(sbuf.toString());
    }

    private int doWrite()
        throws JspException
    {
        StringBuffer results = new StringBuffer("<input");
        prepareAttribute(results);
        results.append(TagUtils.getElementClose());
        ResponseUtils.write(pageContext, results.toString());
        release();
        return 6;
    }

    private void prepareEventScript()
    {
        if(getOnblur() == null)
            setOnblur("doBlur();");
        if(getOnfocus() == null)
            setOnfocus("doFocus();");
    }

    public void release()
    {
        super.release();
        beanName = null;
        checked = null;
        name = null;
        scope = null;
        text = null;
        type = "checkbox";
    }
}
