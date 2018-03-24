package server.framework.taglib.script;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import server.framework.taglib.util.Constants;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

public abstract class AbstractBaseScriptTag extends TagSupport
{

    protected static MessageResources messages = MessageResources.getMessageResources(Constants.MESSAGE_CONFIG);
    private static final String HTML_BEGIN_COMMENT = "<!-- Begin ";
    private static final String HTML_END_COMMENT = "//End --> ";

    public AbstractBaseScriptTag()
    {
    }

    private void addScriptStart(StringBuffer start)
    {
        start.append(Constants.LINE_END);
        start.append("<script type=\"text/javascript\"");
        start.append(" language=\"Javascript1.1\"");
        start.append(">");
        start.append(Constants.LINE_END);
        start.append("<!-- Begin ");
        start.append(Constants.LINE_END);
    }

    private void addScriptEnd(StringBuffer end)
    {
        end.append("//End --> ");
        end.append(Constants.LINE_END);
        end.append("</script>");
        end.append(Constants.LINE_END);
        end.append(Constants.LINE_END);
    }

    protected void doWrite(String name, StringBuffer script)
        throws JspException
    {
        StringBuffer function = new StringBuffer();
        addScriptStart(function);
        function.append(Constants.LINE_END);
        function.append("function ");
        function.append(name);
        function.append(" {");
        function.append(Constants.LINE_END);
        function.append(script);
        function.append(" }");
        function.append(Constants.LINE_END);
        addScriptEnd(function);
        ResponseUtils.write(pageContext, function.toString());
    }

}
