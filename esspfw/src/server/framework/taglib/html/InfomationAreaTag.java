package server.framework.taglib.html;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.util.ResponseUtils;

public class InfomationAreaTag extends TextTag
{

    public InfomationAreaTag()
    {
    }

    public int doStartTag()
        throws JspException
    {
        initAttribute();
        String message = (String)pageContext.getRequest().getAttribute("INFOMATION_AREA");
        if(message == null || "".equals(message))
        {
            doWrite();
            return 0;
        } else
        {
            setValue(message);
            doWrite();
            return 2;
        }
    }

    protected void initAttribute()
    {
        property = "infomation_area";
        setStyleId("INFOMATION_AREA");
        setFieldtype("text");
        setReadonly(true);
        type = "text";
    }

    protected void doWrite()
        throws JspException
    {
        prepareFieldType();
        ResponseUtils.write(pageContext, getTagString());
        release();
    }

    public void release()
    {
        super.release();
    }
}
