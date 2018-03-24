package server.framework.taglib.html;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import server.framework.taglib.util.AuthenticationException;

public class JspGuardTag extends BodyTagSupport
{

    private String contextKey;

    public JspGuardTag()
    {
    }

    public int doStartTag()
        throws JspException
    {
        String key = "CONTEXT_KEY";
        if(contextKey != null && contextKey.length() != 0)
            key = contextKey;
        HttpSession session = pageContext.getSession();
        if(session == null)
            throw new AuthenticationException("Session is not detected.");
        Object userContext = session.getAttribute(key);
        if(userContext == null)
            throw new AuthenticationException("Illegal JSP access detected. Cannot find userContext from session with key [" + key + "]");
        else
            return 6;
    }

    public String getContextKey()
    {
        return contextKey;
    }

    public void setContextKey(String str)
    {
        contextKey = str;
    }

    public void release()
    {
        super.release();
        contextKey = null;
    }
}
