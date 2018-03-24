package server.framework.taglib.logic;

import java.lang.reflect.Array;
import java.util.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import server.framework.taglib.util.SystemException;
import server.framework.taglib.util.Constants;
import server.framework.taglib.util.TagUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.*;

public class IterateTag extends BodyTagSupport
{
    protected static MessageResources messages = MessageResources.getMessageResources(Constants.MESSAGE_CONFIG);
    private static Logger log;
    protected Iterator iterator;
    protected int lengthCount;
    protected int lengthValue;
    protected int offsetValue;
    protected boolean started;
    protected int childHeightValue;
    protected int childTopValue;
    protected int maxDisplayValue;
    protected int heightValue;
    protected int widthValue;
    protected static final int SCROLL_WIDTH = 16;
    protected static final String CHILD_DIV_ID = "CHILD_DIV_ID";
    protected Object collection;
    protected String id;
    protected String length;
    protected String name;
    protected String offset;
    protected String property;
    protected String scope;
    protected String type;
    protected boolean scroll;
    protected String ctrlName;
    protected String rowHeight;
    protected String maxDisplay;
    protected String width;
    static Class class$jp$co$tis$cocube$framework$web$taglib$logic$IterateTag; /* synthetic field */

    public IterateTag()
    {
        iterator = null;
        lengthCount = 0;
        lengthValue = 0;
        offsetValue = 0;
        started = false;
        childHeightValue = 0;
        childTopValue = 0;
        maxDisplayValue = 0;
        heightValue = 0;
        widthValue = 0;
        collection = null;
        id = null;
        length = null;
        name = null;
        offset = null;
        property = null;
        scope = null;
        type = null;
        scroll = true;
        ctrlName = null;
        rowHeight = "0";
        maxDisplay = "0";
        width = "0";
        log=Logger.getLogger(this.getClass());
    }

    public Object getCollection()
    {
        return collection;
    }

    public void setCollection(Object aCollection)
    {
        collection = aCollection;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String aId)
    {
        id = aId;
    }

    public String getLength()
    {
        return length;
    }

    public void setLength(String aLength)
    {
        length = aLength;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String aName)
    {
        name = aName;
    }

    public String getOffset()
    {
        return offset;
    }

    public void setOffset(String aOffset)
    {
        offset = aOffset;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String aProperty)
    {
        property = aProperty;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String aScope)
    {
        scope = aScope;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String aType)
    {
        type = aType;
    }

    public boolean isScroll()
    {
        return scroll;
    }

    public void setScroll(boolean b)
    {
        scroll = b;
    }

    public String getCtrlName()
    {
        return ctrlName;
    }

    public void setCtrlName(String string)
    {
        ctrlName = string;
    }

    public String getRowHeight()
    {
        return rowHeight;
    }

    public void setRowHeight(String string)
    {
        rowHeight = string;
    }

    public String getMaxDisplay()
    {
        return maxDisplay;
    }

    public void setMaxDisplay(String string)
    {
        maxDisplay = string;
    }

    public String getWidth()
    {
        return width;
    }

    public void setWidth(String string)
    {
        width = string;
    }

    public int doStartTag()
        throws JspException
    {
        prepareIterator();
        prepareOffsetValue();
        prepareLengthValue();
        if(iterator != null)
        {
            for(int i = 0; i < offsetValue; i++)
                if(iterator.hasNext())
                    iterator.next();

            prepareNextElement();
        }
        lengthCount++;
        started = true;
        init();
        pageContext.setAttribute("ITERATE_INDEX", new Integer(getScreenIndex()));
        pageContext.setAttribute("MAX_LENGTH", new Integer(lengthValue));
        ResponseUtils.write(pageContext, getDivTagParents());
        return 2;
    }

    public int doAfterBody()
        throws JspException
    {
        if(bodyContent != null)
        {
            ResponseUtils.writePrevious(pageContext, getDivTagChild());
            bodyContent.clearBody();
        }
        if(lengthCount >= lengthValue)
            return 0;
        if(iterator != null)
            prepareNextElement();
        lengthCount++;
        pageContext.setAttribute("ITERATE_INDEX", new Integer(getScreenIndex()));
        addTopValue();
        return 2;
    }

    public int doEndTag()
        throws JspException
    {
        started = false;
        iterator = null;
        StringBuffer end = new StringBuffer("</DIV>");
        end.append(Constants.LINE_END);
        ResponseUtils.write(pageContext, end.toString());
        pageContext.removeAttribute("ITERATE_INDEX");
        pageContext.removeAttribute("MAX_LENGTH");
        release();
        return 6;
    }

    protected void prepareIterator()
        throws JspException
    {
        Object workCollection = collection;
        if(workCollection == null)
        {
            Object bean = RequestUtils.lookup(pageContext, name, scope);
            if(bean == null)
                return;
            workCollection = RequestUtils.lookup(pageContext, name, property, scope);
            if(workCollection == null)
                return;
        }
        if(workCollection.getClass().isArray())
            try
            {
                iterator = Arrays.asList((Object[])workCollection).iterator();
            }
            catch(ClassCastException e)
            {
                int len = Array.getLength(workCollection);
                ArrayList c = new ArrayList(len);
                for(int i = 0; i < len; i++)
                    c.add(Array.get(workCollection, i));

                iterator = c.iterator();
            }
        else
        if(workCollection instanceof Collection)
            iterator = ((Collection)workCollection).iterator();
        else
        if(workCollection instanceof Iterator)
            iterator = (Iterator)workCollection;
        else
        if(workCollection instanceof Map)
            iterator = ((Map)workCollection).entrySet().iterator();
        else
        if(workCollection instanceof Enumeration)
        {
            iterator = IteratorUtils.asIterator((Enumeration)workCollection);
        } else
        {
            JspException e = new JspException(messages.getMessage("iterate.iterator"));
            RequestUtils.saveException(pageContext, e);
            throw e;
        }
    }

    protected void prepareOffsetValue()
        throws JspException
    {
        if(offset == null)
        {
            if(log.isDebugEnabled())
                log.debug("offset value is null");
            offsetValue = 0;
        } else
        {
            try
            {
                offsetValue = Integer.parseInt(offset);
            }
            catch(NumberFormatException e)
            {
                if(log.isDebugEnabled())
                    log.debug("offset value is not a number:[" + offset + "]");
                Integer offsetObject = (Integer)RequestUtils.lookup(pageContext, offset, null);
                if(offsetObject == null)
                {
                    if(log.isDebugEnabled())
                        log.debug("offsetObject is null");
                    offsetValue = 0;
                } else
                {
                    if(log.isDebugEnabled())
                        log.debug("offsetObject is not null");
                    offsetValue = offsetObject.intValue();
                }
            }
        }
        if(offsetValue < 0)
        {
            if(log.isDebugEnabled())
                log.debug("offsetObject is minus");
            offsetValue = 0;
        }
    }

    protected void prepareLengthValue()
        throws JspException
    {
        if(length == null)
            throw new JspException("The attribute 'length' should be set.");
        try
        {
            lengthValue = Integer.parseInt(length);
        }
        catch(NumberFormatException e)
        {
            Integer lengthObject = (Integer)RequestUtils.lookup(pageContext, length, null);
            if(lengthObject == null)
                throw new JspException("Cannot find length value with key[" + length + "] from pageContext.");
            lengthValue = lengthObject.intValue();
        }
        if(lengthValue < 0)
        {
            throw new JspException("length value was [" + lengthValue + "]. it should have positive value.");
        } else
        {
            lengthCount = 0;
            return;
        }
    }

    protected void prepareNextElement()
    {
        if(iterator.hasNext())
        {
            Object element = iterator.next();
            pageContext.setAttribute(id, element);
        } else
        {
            pageContext.removeAttribute(id);
        }
    }

    protected String getDivTagChild()
    {
        StringBuffer body = new StringBuffer("<DIV");
        TagUtils.appendControlerAttribute(body, "id", "CHILD_DIV_ID");
        body.append(" style=\"");
        body.append("WIDTH:");
        body.append(widthValue);
        body.append("px;");
        body.append(" ");
        body.append("HEIGHT:");
        body.append(rowHeight);
        body.append("px;");
        body.append(" ");
        body.append("LEFT:0px;");
        body.append(" ");
        body.append("TOP:");
        body.append(childTopValue);
        body.append("px;");
        body.append("\" >");
        body.append(Constants.LINE_END);
        body.append(bodyContent.getString());
        body.append(Constants.LINE_END);
        body.append("</DIV>");
        body.append(Constants.LINE_END);
        return body.toString();
    }

    protected String getDivTagParents()
    {
        StringBuffer div = new StringBuffer("<DIV");
        TagUtils.appendControlerAttribute(div, "id", ctrlName);
        div.append(" style=\"");
        div.append("WIDTH:");
        if(scroll)
            div.append(widthValue + 16);
        else
            div.append(widthValue);
        div.append("px;");
        div.append(" ");
        div.append("HEIGHT:");
        div.append(heightValue);
        div.append("px;");
        if(scroll)
        {
            div.append(" ");
            div.append("overflow-y:scroll;");
        }
        div.append("\" >");
        div.append(Constants.LINE_END);
        return div.toString();
    }

    public void release()
    {
        super.release();
        iterator = null;
        lengthCount = 0;
        lengthValue = 0;
        offsetValue = 0;
        started = false;
        childHeightValue = 0;
        childTopValue = 0;
        maxDisplayValue = 0;
        heightValue = 0;
        widthValue = 0;
        collection = null;
        id = null;
        length = null;
        name = null;
        offset = null;
        property = null;
        scope = null;
        type = null;
        scroll = true;
        ctrlName = null;
        rowHeight = "0";
        maxDisplay = "0";
        width = "0";
    }

    private void init()
        throws JspException
    {
        prepareChildHeightValue();
        prepareMaxDisplayValue();
        prepareWidthValue();
        prepareHeightValue();
    }

    private void prepareWidthValue()
    {
        widthValue = Integer.parseInt(width);
    }

    private void addTopValue()
    {
        childTopValue += childHeightValue;
    }

    private void prepareMaxDisplayValue()
        throws JspException
    {
        if(maxDisplay == null)
            throw new SystemException("attribute 'maxDisplay' should be set.");
        try
        {
            maxDisplayValue = Integer.parseInt(maxDisplay);
        }
        catch(NumberFormatException e)
        {
            if(log.isDebugEnabled())
                log.debug("cannot parse maxDisplay value[" + maxDisplay + "]");
            Integer maxDisplayObject = (Integer)RequestUtils.lookup(pageContext, maxDisplay, null);
            if(maxDisplayObject == null)
                throw new SystemException("Cannot find maxLength value with key[" + maxDisplay + "] from pageContext.");
            maxDisplayValue = maxDisplayObject.intValue();
        }
        if(maxDisplayValue < 0)
            throw new SystemException("maxDisplay was [" + maxDisplayValue + "]. it should have positive value.");
        else
            return;
    }

    private void prepareHeightValue()
    {
        heightValue = childHeightValue * maxDisplayValue;
    }

    private void prepareChildHeightValue()
    {
        childHeightValue = Integer.parseInt(rowHeight);
    }

    public int getIndex()
    {
        if(started)
            return (offsetValue + lengthCount) - 1;
        else
            return 0;
    }

    public int getScreenIndex()
    {
        if(started)
            return lengthCount - 1;
        else
            return 0;
    }
}
