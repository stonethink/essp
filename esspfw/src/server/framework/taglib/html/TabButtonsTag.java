package server.framework.taglib.html;

import org.apache.struts.util.ResponseUtils;
import server.framework.taglib.util.TagUtils;
import javax.servlet.jsp.JspException;

public class TabButtonsTag extends AbstractBaseHandlerTag {
    public TabButtonsTag() {
        super();
    }

    public int doStartTag() throws JspException {
        StringBuffer results = new StringBuffer(
                "<td height=\"23\"  ID=\"t3\"  class=\"tdright\">");
        ResponseUtils.write(pageContext, results.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        StringBuffer results = new StringBuffer(
                "</td>");
        ResponseUtils.write(pageContext, results.toString());
        return 1;

    }
}
