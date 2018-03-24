package server.framework.taglib.logic;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;

public class IsIETag extends BodyTagSupport {
    public IsIETag() {
        super();
    }

    public int doStartTag() throws JspException {
        if(!TagUtils.isIE(pageContext)) {
            return BodyTagSupport.SKIP_BODY;
        } else {
            return 1;
        }
    }

    public int doEndTag() throws JspException {
        return 1;
    }

}
