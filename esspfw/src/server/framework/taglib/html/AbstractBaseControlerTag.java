package server.framework.taglib.html;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import server.framework.taglib.util.TagUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.*;

public abstract class AbstractBaseControlerTag extends AbstractBaseHandlerTag
{
    protected String msg;
    protected String next;
    protected String prev;
    protected String outnext;
    protected String outprev;
    protected String req;
    protected String sreq;
    protected String property;
    protected String value;

    public AbstractBaseControlerTag()
    {
        msg = null;
        next = null;
        prev = null;
        outnext = null;
        outprev = null;
        req = "false";
        sreq = "false";
        property = null;
        value = null;
    }

    public String getNext()
    {
        return next;
    }

    public void setNext(String string)
    {
        next = string;
    }

    public String getPrev()
    {
        return prev;
    }

    public void setPrev(String string)
    {
        prev = string;
    }

    public String getOutnext()
    {
        return outnext;
    }

    public void setOutnext(String string)
    {
        outnext = string;
    }

    public String getOutprev()
    {
        return outprev;
    }

    public void setOutprev(String string)
    {
        outprev = string;
    }

    public String getReq()
    {
        return req;
    }

    public void setReq(String string)
    {
        req = string;
    }

    public String getSreq()
    {
        return sreq;
    }

    public void setSreq(String string)
    {
        sreq = string;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String aProperty)
    {
        property = aProperty;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String aValue)
    {
        value = aValue;
    }

    protected void doCheck()
        throws JspException
    {
        String message = null;
        if(!"true".equals(req) && !"false".equals(req))
        {
            message = AbstractBaseHandlerTag.messages.getMessage("value.error", "req", "[true]or[false]");
            log.error(message);
            throw new JspException(message);
        }
        if(!"true".equals(sreq) && !"false".equals(sreq))
        {
            message = AbstractBaseHandlerTag.messages.getMessage("value.error", "sreq", "[true]or[false]");
            log.error(message);
            throw new JspException(message);
        }
        if("true".equals(req) && "true".equals(sreq))
        {
            message = AbstractBaseHandlerTag.messages.getMessage("exclusive.error", "[req or sreq]");
            log.error(message);
            throw new JspException(message);
        } else
        {
            return;
        }
    }

    protected void prepareAttribute(StringBuffer sbuf)
        throws JspException
    {
        TagUtils.appendControlerAttribute(sbuf, "name", property);
        TagUtils.appendControlerAttribute(sbuf, "value", ResponseUtils.filter(value));
        TagUtils.appendControlerAttribute(sbuf, "next", next);
        TagUtils.appendControlerAttribute(sbuf, "prev", prev);
        TagUtils.appendControlerAttribute(sbuf, "msg", msg);
        TagUtils.appendControlerAttribute(sbuf, "req", req);
        TagUtils.appendControlerAttribute(sbuf, "sreq", sreq);
        sbuf.append(prepareEventHandlers());
        log.debug(sbuf.toString());
    }

    protected void addReqProperty(String aProperty)
    {
        if("true".equals(req))
        {
            ArrayList array = null;
            Object obj = pageContext.getAttribute("REQ_PROPERTIES");
            if(obj != null)
                array = (ArrayList)obj;
            else
                array = new ArrayList();
            if(array.contains(aProperty))
                return;
            array.add(aProperty);
            pageContext.setAttribute("REQ_PROPERTIES", array);
        }
    }

    protected void doFocusControl()
    {
        Object index = pageContext.getAttribute("ITERATE_INDEX");
        if(index != null)
        {
            log.debug(index.toString());
            setOutPrevValue((Integer)index);
            setOutNextValue((Integer)index);
        }
    }

    private void setOutPrevValue(Integer index)
    {
        if(outprev != null)
        {
            Integer max = (Integer)pageContext.getAttribute("MAX_LENGTH");
            if(max == null)
            {
                log.debug("cannot evaluate max:" + max);
                return;
            }
            int maxValue = max.intValue();
            if(maxValue <= 0)
            {
                log.debug("cannot evaluate max:" + max);
                return;
            }
            int mod = (index.intValue() + 1) % maxValue;
            if(mod == 1)
                prev = outprev;
        }
    }

    private void setOutNextValue(Integer index)
    {
        log.debug("setOutNextValue outNext:" + outnext);
        if(outnext != null)
        {
            Integer max = (Integer)pageContext.getAttribute("MAX_LENGTH");
            if(max == null)
            {
                log.debug("cannot evaluate max:" + max);
                return;
            }
            int maxValue = max.intValue();
            if(maxValue <= 0)
            {
                log.debug("cannot evaluate max:" + max);
                return;
            }
            int mod = (index.intValue() + 1) % maxValue;
            if(mod == 0)
                next = outnext;
        }
    }

    protected void findErrorMsg()
        throws JspException
    {
        ActionErrors errors = null;
        try
        {
            errors = RequestUtils.getActionErrors(pageContext, "org.apache.struts.action.ERROR");
        }
        catch(JspException e)
        {
            RequestUtils.saveException(pageContext, e);
            throw e;
        }
        if(errors == null || errors.isEmpty())
            return;
        Iterator reports = null;
        Object index = pageContext.getAttribute("ITERATE_INDEX");
        if(index != null)
        {
            StringBuffer buf = new StringBuffer(property);
            buf.append("+");
            buf.append(index.toString());
            reports = errors.get(buf.toString());
            if(!reports.hasNext())
            {
                buf.delete(0, buf.length());
                buf.append(property);
                buf.append(index.toString());
                reports = errors.get(buf.toString());
                if(reports.hasNext())
                    log.info("You can set error index with '+'. ex) prop+5.");
            }
        } else
        {
            reports = errors.get(property);
        }
        if(!reports.hasNext())
        {
            return;
        } else
        {
            ActionError report = (ActionError)reports.next();
            msg = RequestUtils.message(pageContext, null, "org.apache.struts.action.LOCALE", report.getKey(), report.getValues());
            return;
        }
    }

    protected boolean isMsg()
    {
        return msg != null;
    }

    public void release()
    {
        super.release();
        next = null;
        prev = null;
        property = null;
        value = null;
        outnext = null;
        outprev = null;
        req = "false";
        msg = null;
    }
}
