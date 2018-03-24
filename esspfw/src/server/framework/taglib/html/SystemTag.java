package server.framework.taglib.html;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import server.framework.taglib.util.Global;
import javax.servlet.http.HttpServletRequest;
import server.framework.taglib.util.TagUtils;

public class SystemTag extends BodyTagSupport {
    private String debug;

    public SystemTag() {
        super();
        debug="false";
    }

    public int doStartTag() throws JspException {
        super.doStartTag();
        if(debug!=null && debug.trim().equalsIgnoreCase("true")) {
            Global.debug=true;
        } else {
            Global.debug=false;
        }
        if(TagUtils.isIE(pageContext)) {
            Global.isIE=true;
        } else {
            Global.isIE=false;
        }

        return 1;
    }

    public int doEndTag() throws JspException {
        return 1;
    }


    public void release() {
        super.release();
        debug="false";
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }
}
