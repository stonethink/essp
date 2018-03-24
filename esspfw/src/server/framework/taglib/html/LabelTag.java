package server.framework.taglib.html;

import java.io.IOException;
import javax.servlet.jsp.*;
import org.apache.struts.util.MessageResources;
import server.framework.taglib.util.Constants;

public class LabelTag extends AbstractBaseControlerTag
{

    private String fieldtype;

    public String getFieldtype()
    {
        return fieldtype;
    }

    public void setFieldtype(String string)
    {
        fieldtype = string;
    }

    public LabelTag()
    {
        fieldtype = null;
    }

    public int doStartTag()
        throws JspException
    {
        super.doStartTag();
        if(fieldtype == null)
        {
            MessageResources mr = MessageResources.getMessageResources(Constants.MESSAGE_CONFIG);
            String message = mr.getMessage("fieldtype.need", new String[] {
                "label Tag", "\"fieldtype\""
            });
            throw new JspException(message);
        }
        String styleClass = getStyleClass();
        if(styleClass == null)
            if(fieldtype.equals("normal"))
                super.setStyleClass("normalLabel");
            else
            if(fieldtype.equals("reference"))
                super.setStyleClass("referenceLabel");
            else
            if(fieldtype.equals("comment"))
            {
                super.setStyleClass("commentLabel");
            } else
            {
                MessageResources mr = MessageResources.getMessageResources(Constants.MESSAGE_CONFIG);
                String message = mr.getMessage("value.error", new String[] {
                    "\"normal\" or \"reference\" or \"comment\"", "fieldtype"
                });
                throw new JspException(message);
            }
        StringBuffer sb = new StringBuffer();
        sb.append("<label");
        sb.append(super.prepareStyles());
        sb.append(">");
        JspWriter writer = pageContext.getOut();
        try
        {
            writer.write(sb.toString());
        }
        catch(IOException e)
        {
            throw new JspException(e);
        }
        return 1;
    }

    public int doEndTag()
        throws JspException
    {
        super.doEndTag();
        JspWriter writer = pageContext.getOut();
        try
        {
            writer.write("</label>");
        }
        catch(IOException e)
        {
            throw new JspException(e);
        }
        release();
        return 6;
    }

    public void release()
    {
        super.release();
        fieldtype = null;
    }
}
