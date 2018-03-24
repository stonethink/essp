package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import org.apache.struts.util.ResponseUtils;

public class TabContentsTag extends AbstractBaseHandlerTag {
    protected int index=0;
    public int doStartTag() throws JspException {
        index=0;
        StringBuffer results = new StringBuffer("</tr></table>");
        ResponseUtils.write(pageContext, results.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        return 1;
    }
}
