package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;
import org.apache.struts.util.*;

public class RadioButtonTag extends AbstractBaseControlerTag
{
    private static final String PREFIX_INPUT = "<input";
    private static final String PREFIX_LABEL = "<label";
    private static final String SUFFIX_LABEL = "</label>";
    private static final String LF = "\n";
    private static final String CHECKED = " checked";
    protected String text;
    protected String beanName;
    protected String idName;
    protected String name;
    protected String scope;
    protected String label;
    protected String labelId;
    protected String labelClass;
    protected String checked;
    protected String type;
    protected String labelType;
    protected final String DISPLAY_ATTRIBUTE_RADIO = "Radio";
    protected final String DISPLAY_ATTRIBUTE_LABEL = "RadioLabel";
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

    public String getIdName()
    {
        return idName;
    }

    public void setIdName(String string)
    {
        idName = string;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String string)
    {
        name = string;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String string)
    {
        scope = string;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String string)
    {
        label = string;
    }

    public String getLabelId()
    {
        return labelId;
    }

    public void setLabelId(String string)
    {
        labelId = string;
    }

    public String getLabelClass()
    {
        return labelClass;
    }

    public void setLabelClass(String string)
    {
        labelClass = string;
    }

    public RadioButtonTag()
    {
        text = null;
        beanName = null;
        idName = null;
        name = null;
        scope = null;
        label = null;
        labelId = null;
        labelClass = null;
        checked = null;
        type = "radio";
        labelType = "label";
    }

    protected void doCheck()
        throws JspException
    {
        super.doCheck();
        String message = null;
    }

    public int doStartTag()
        throws JspException
    {
        doCheck();
        text = null;
        return 2;
    }

    public int doAfterBody()
        throws JspException
    {
        if(bodyContent != null)
        {
            String labelValue = bodyContent.getString().trim();
            if(!"".equals(labelValue))
                text = labelValue;
        }
        return 0;
    }

    public int doEndTag()
        throws JspException
    {
        property = name;
        doSetLabel();
        doSetValue();
        doFocusControl();
        findErrorMsg();
        getClass();
        doSetStyleClass("Radio");
        getClass();
        doSetLabelClass("RadioLabel");
        prepareEventScript();
        doInitializeRadioButton();
        return doWrite();
    }

    private void doSetLabel()
        throws JspException
    {
        if(label == null && text != null)
            label = text;
        else
        if(label != null)
        {
            Object propertyObj = RequestUtils.lookup(pageContext, idName, label, scope);
            if(propertyObj == null)
                propertyObj = "";
            if(propertyObj instanceof String)
            {
                label = (String)propertyObj;
            } else
            {
                String message = null;
                message = AbstractBaseHandlerTag.messages.getMessage("taglib.error", "value", "idName property");
                log.error(message);
                throw new JspException(message);
            }
        }
        if(label == null || label.trim().length() < 1)
        {
            text = null;
            label = null;
            labelId = null;
            labelClass = null;
        } else
        if(labelId == null || labelId.trim().length() < 1)
        {
            String message = null;
            message = AbstractBaseHandlerTag.messages.getMessage("value.error", "labelId", "label");
            log.error(message);
            throw new JspException(message);
        }
    }

    private void doSetValue()
        throws JspException
    {
        if(idName != null && idName.length() != 0)
        {
            Object propertyObj = RequestUtils.lookup(pageContext, idName, value, scope);
            if(propertyObj == null)
                propertyObj = "";
            if(propertyObj instanceof String)
            {
                value = (String)propertyObj;
            } else
            {
                String message = null;
                message = AbstractBaseHandlerTag.messages.getMessage("taglib.error", "value", "idName property");
                log.error(message);
                throw new JspException(message);
            }
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

    public void doSetLabelClass(String displayAttribute)
    {
        if(labelClass == null || "".equals(labelClass))
            labelClass = displayAttribute;
    }

    private void doInitializeRadioButton()
        throws JspException
    {
        Object beanObj = RequestUtils.lookup(pageContext, beanName, property, scope);
        if(beanObj != null)
        {
            Object propertyObj = RequestUtils.lookup(pageContext, beanName, property, scope);
            if(propertyObj != null)
                if(propertyObj instanceof String)
                {
                    if(value.equals((String)propertyObj))
                    {
                        checked = " checked";
                        return;
                    }
                } else
                {
                    String message = null;
                    message = AbstractBaseHandlerTag.messages.getMessage("taglib.error", "value", "beanName property");
                    log.error(message);
                    throw new JspException(message);
                }
        }
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendAttribute(sbuf, "type", type);
        appendProperty(sbuf, checked);
    }

    protected void createLabelTag(StringBuffer results)
    {
        if(label != null && label.length() > 0)
        {
            results.append("<label");
            TagUtils.appendAttribute(results, "id", labelId);
            TagUtils.appendAttribute(results, "class", labelClass);
            TagUtils.appendAttribute(results, "type", labelType);
            results.append(">");
            results.append(label);
            results.append("</label>");
        }
    }

    private void appendProperty(StringBuffer sbuf, String pProperty)
    {
        if(pProperty != null && pProperty.length() > 0)
        {
            sbuf.append(" ");
            sbuf.append(pProperty);
        }
    }

    private int doWrite()
        throws JspException
    {
        StringBuffer results = new StringBuffer("<input");
        prepareAttribute(results);
        results.append(TagUtils.getElementClose());
        createLabelTag(results);
        log.debug(results.toString());
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
        name = null;
        idName = null;
        checked = null;
        scope = null;
        text = null;
        label = null;
        labelId = null;
        type = "radio";
        labelType = "label";
    }
}
