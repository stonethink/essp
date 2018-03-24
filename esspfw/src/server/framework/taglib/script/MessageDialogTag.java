package server.framework.taglib.script;

import java.util.Iterator;
import javax.servlet.jsp.JspException;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.RequestUtils;

public class MessageDialogTag extends AbstractBaseScriptTag
{

    protected static final String FUNCTION_NAME = "doMessageDialog()";

    public MessageDialogTag()
    {
    }

    public int doStartTag()
        throws JspException
    {
        ActionMessages actionMessages = RequestUtils.getActionMessages(pageContext, "org.apache.struts.action.ACTION_MESSAGE");
        StringBuffer msgBuffer = new StringBuffer();
        for(Iterator iter = actionMessages.get(); iter.hasNext();)
        {
            ActionMessage actionMessage = (ActionMessage)iter.next();
            String key = actionMessage.getKey();
            Object values[] = actionMessage.getValues();
            String message = RequestUtils.message(pageContext, null, null, key, values);
            if(message != null && message.length() != 0)
                msgBuffer.append(message + "\\n");
        }

        StringBuffer functionBuffer = new StringBuffer();
        if(msgBuffer.length() != 0)
        {
            functionBuffer.append("alert('");
            functionBuffer.append(msgBuffer);
            functionBuffer.append("');");
        }
        doWrite("doMessageDialog()", functionBuffer);
        return 1;
    }

    public void release()
    {
        super.release();
    }
}
