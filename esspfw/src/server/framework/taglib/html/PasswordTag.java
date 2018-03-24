package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;
import org.apache.struts.util.ResponseUtils;
import server.framework.taglib.util.Global;

public class PasswordTag extends AbstractBaseControlerTag
{
    private static final String PREFIX_INPUT = "<input";
    private static final String LF = "\n";
    protected String name;
    protected String maxlength;
    protected String type;
    protected final String DISPLAY_ATTRIBUTE_PASSWORD = "Password";
    protected final String DISPLAY_ATTRIBUTE_ERROR = "Err";

    public String getName()
    {
        return name;
    }

    public void setName(String string)
    {
        name = string;
    }

    public String getMaxlength()
    {
        return maxlength;
    }

    public void setMaxlength(String string)
    {
        maxlength = string;
    }

    public PasswordTag()
    {
        name = null;
        maxlength = null;
        type = "password";
    }

    protected void doCheck()
        throws JspException
    {
        super.doCheck();
    }

    public int doStartTag()
        throws JspException
    {
        super.doStartTag();
        doCheck();
        property = name;
        addReqProperty(property);
        doSetValue();
        getClass();
        doSetStyleClass("Password");
        prepareEventScript();
        doWrite();
        return 0;
    }

    private void doSetValue()
    {
        if(value == null)
            value = "";
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

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendAttribute(sbuf, "type", type);
        TagUtils.appendAttribute(sbuf, "maxlength", maxlength);
    }

    private void doWrite()
        throws JspException
    {
        StringBuffer results = new StringBuffer("<input");
        prepareAttribute(results);
        results.append(TagUtils.getElementClose());
        log.debug("HTML:" + results.toString());
        ResponseUtils.write(pageContext, results.toString());
        release();
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
        type = "password";
    }
}
