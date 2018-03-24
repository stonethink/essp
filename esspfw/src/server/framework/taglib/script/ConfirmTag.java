package server.framework.taglib.script;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import server.framework.taglib.util.SystemException;
import server.framework.taglib.util.Constants;
import server.framework.taglib.util.Confirmation;
import org.apache.struts.util.RequestUtils;

public class ConfirmTag extends AbstractBaseScriptTag
{

    private static final String FUNCTION_NAME = "doConfirmSubmit()";

    public ConfirmTag()
    {
    }

    public int doStartTag()
        throws JspException
    {
        Confirmation confirmation = (Confirmation)pageContext.getAttribute(Confirmation.BIND_KEY, 2);
        StringBuffer buf = new StringBuffer();
        if(confirmation != null)
        {
            String message = retrieveMessage(confirmation);
            String pathYes = confirmation.getPathYes();
            String pathNo = confirmation.getPathNo();
            render(buf, message, pathYes, pathNo);
        }
        doWrite("doConfirmSubmit()", buf);
        return 1;
    }

    private boolean hasValue(String str)
    {
        return str != null && str.length() != 0;
    }

    private String retrieveMessage(Confirmation confirmation)
        throws JspException
    {
        String id = confirmation.getMessageId();
        if(id == null || id.length() == 0)
        {
            String msg = "You should set valid message id when you use confirmation!";
            throw new SystemException(msg);
        } else
        {
            String replace[] = confirmation.getReplace();
            String message = RequestUtils.message(pageContext, null, null, id, replace);
            return message;
        }
    }

    private void render(StringBuffer buf, String message, String pathYes, String pathNo)
    {
        buf.append("var answer = confirm(\"");
        buf.append(message);
        buf.append("\")");
        buf.append(Constants.LINE_END);
        buf.append("if (answer) {");
        buf.append(Constants.LINE_END);
        if(hasValue(pathYes))
        {
            buf.append("doSubmitAfterConfirm(\"");
            buf.append(pathYes);
            buf.append("\");");
            buf.append(Constants.LINE_END);
        }
        buf.append("} else {");
        buf.append(Constants.LINE_END);
        if(hasValue(pathNo))
        {
            buf.append("doSubmitAfterConfirm(\"");
            buf.append(pathNo);
            buf.append("\");");
            buf.append(Constants.LINE_END);
        }
        buf.append("}");
        buf.append(Constants.LINE_END);
    }
}
