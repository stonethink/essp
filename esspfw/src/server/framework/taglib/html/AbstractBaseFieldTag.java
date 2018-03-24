package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;

public abstract class AbstractBaseFieldTag extends AbstractBaseInputTag
{
    protected boolean redisplay;
    protected String type;

    public AbstractBaseFieldTag()
    {
        redisplay = true;
        type = null;
    }

    public boolean getRedisplay()
    {
        return redisplay;
    }

    public void setRedisplay(boolean aRedisplay)
    {
        redisplay = aRedisplay;
    }

    public int doStartTag()
        throws JspException
    {
        doCheck();
        addReqProperty(property);
        findErrorMsg();
        doFocusControl();
        return 1;
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        super.prepareAttribute(sbuf);
        TagUtils.appendAttribute(sbuf, "type", type);
        TagUtils.appendAttribute(sbuf, "maxlength", maxlength);
        TagUtils.appendControlerAttribute(sbuf, "fielderrorflg", "");
        TagUtils.appendControlerAttribute(sbuf, "fieldmsg", "");
        log.debug(sbuf.toString());
    }

    public void release()
    {
        super.release();
        redisplay = true;
        type = null;
    }
}
