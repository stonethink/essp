package server.framework.taglib.logic;

import javax.servlet.jsp.tagext.*;
import server.framework.taglib.util.TagUtils;
import javax.servlet.jsp.JspException;

public class NotIETag extends BodyTagSupport {
    public NotIETag() {
        super();
    }

    public int doStartTag() throws JspException {
        if(TagUtils.isIE(pageContext)) {
            return BodyTagSupport.SKIP_BODY;
        } else {
            return 1;
        }
    }

    public int doEndTag() throws JspException {
        return 1;
    }
}
