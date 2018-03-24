package server.framework.taglib.html;

import server.framework.taglib.util.TagUtils;
import javax.servlet.jsp.JspException;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;
import java.util.Map;
import server.framework.taglib.util.Global;

public class CheckboxTag extends AbstractBaseControlerTag {
    protected String name;
    protected String beanName;
    protected String scope;
    protected String uncheckedValue;
    protected String checkedValue;
    protected String defaultValue;
    protected String checked;
    protected String type;
    protected final String DISPLAY_ATTRIBUTE_CHECKBOX = "Checkbox";
    protected final String DISPLAY_ATTRIBUTE_ERROR = "Err";

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

    public String getCheckedValue() {
        return checkedValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getUncheckedValue() {
        return uncheckedValue;
    }

    public void setName(String string)
    {
        name = string;
    }

    public void setCheckedValue(String checkedValue) {
        this.checkedValue = checkedValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setUncheckedValue(String uncheckedValue) {
        this.uncheckedValue = uncheckedValue;
    }

    public CheckboxTag()
    {
        name = null;
        beanName = null;
        scope = null;
        uncheckedValue=null;
        checkedValue=null;
        defaultValue=null;
        type = "checkbox";
        checked=null;
    }

    public int doStartTag()
        throws JspException
    {
        return 2;
    }

    public int doEndTag()
        throws JspException
    {
        property = name;
        doFocusControl();
        findErrorMsg();
        getClass();
        doSetStyleClass("Checkbox");
        doInitializeCheckbox();
        prepareEventScript();
        return doWrite();
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
        Object currValue = null;
        if(bean != null) {
            if (bean instanceof Map) {
                currValue = ((Map) bean).get(property);
            } else {
                try {
                    currValue = RequestUtils.lookup(pageContext, beanName,
                            property,
                            scope);
                } catch (Exception ex) {
                    if (Global.debug) {
                        log.info(ex);
                    }
                }
            }
        }
        if(currValue!=null) {
            if(currValue instanceof String) {
                value=(String)currValue;
            } else {
                value=currValue.toString();
            }
        }

        if(value!=null &&
           !value.equals(checkedValue) &&
           !value.equals(uncheckedValue)) {
            throw new JspException("value=["+value+"], not equal ["+checkedValue+"] or ["+uncheckedValue+"]");
        }

        if(value==null && defaultValue!=null) {
            if(defaultValue.equals(checkedValue)) {
                checked="checked";
                value=checkedValue;
            } else if(defaultValue.equals(uncheckedValue)) {
                checked="";
                value=uncheckedValue;
            } else {
                throw new JspException("defaultValue=["+value+"], not equal ["+checkedValue+"] or ["+uncheckedValue+"]");
            }
        }

        if(value!=null && value.equals(checkedValue)) {
            checked="checked";
        }

        setOnclick("if(this.checked) { this.value='"+checkedValue+"'; } else { this.value='"+uncheckedValue+"'; }");
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendAttribute(sbuf, "type", type);

        if(checked!=null && checked.trim().equalsIgnoreCase("checked")) {
            sbuf.append(" checked");
        }
        TagUtils.appendAttribute(sbuf, "onclick", getOnclick());
        //log.debug(sbuf.toString());
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

    public void release()
    {
        super.release();
        name = null;
        beanName = null;
        scope = null;
        uncheckedValue=null;
        checkedValue=null;
        defaultValue=null;
        type = "checkbox";
        checked=null;
    }
}
