package server.framework.taglib.util;

import javax.servlet.http.HttpServletRequest;

public class Confirmation
{

    public static final String BIND_KEY=Confirmation.class.getName();
    private String messageId;
    private String replace[];
    private String pathYes;
    private String pathNo;
    static Class class$jp$co$tis$cocube$framework$web$util$Confirmation; /* synthetic field */

    public Confirmation()
    {
    }

    public void save(HttpServletRequest request)
    {
        if(request == null)
        {
            throw new SystemException("illegal argument request:" + request);
        } else
        {
            request.setAttribute(BIND_KEY, this);
            return;
        }
    }

    public String getMessageId()
    {
        return messageId;
    }

    public String[] getReplace()
    {
        return replace;
    }

    public String getPathNo()
    {
        return pathNo;
    }

    public String getPathYes()
    {
        return pathYes;
    }

    public void setMessageId(String str)
    {
        messageId = str;
    }

    public void setMessageId(String id, String strs[])
    {
        messageId = id;
        replace = strs;
    }

    public void setPathNo(String str)
    {
        pathNo = str;
    }

    public void setPathYes(String str)
    {
        pathYes = str;
    }
}
