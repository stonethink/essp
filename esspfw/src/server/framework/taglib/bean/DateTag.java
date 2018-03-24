package server.framework.taglib.bean;

import java.text.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: YeryStudios</p>
 *
 * @author yery
 * @version 1.0
 */
public class DateTag extends BodyTagSupport {
    private String name;
    private String property;
    private String fmt;
    private String scope;

    public DateTag() {
        name=null;
        property=null;
        fmt=null;
        scope=null;
    }

    public int doStartTag() throws JspException {
        String str="";
        if(RequestUtils.lookup(pageContext, name, scope) != null) {
            Object value=RequestUtils.lookup(pageContext, name, scope);
            if(property!=null && !property.trim().equals("")) {
                value = RequestUtils.lookup(pageContext, name, property,
                        scope);
            }
            if(value==null) {
                str="";
            } else  if(value instanceof Date) {
                if(fmt==null || fmt.trim().equals("")) {
                    fmt="yyyy/MM/dd";
                }
                SimpleDateFormat sdf=new SimpleDateFormat(fmt);
                str=sdf.format(value,new StringBuffer(),new FieldPosition(0)).toString();
            } else {
                str=value.toString();
            }
        }
        ResponseUtils.write(pageContext, str);
        return 1;
    }

    public int doEndTag() throws JspException {
        release();
        return 1;
    }

    public void release() {
        name=null;
        property=null;
        fmt=null;
        scope=null;

    }

    public String getFmt() {
        return fmt;
    }

    public String getName() {
        return name;
    }

    public String getProperty() {
        return property;
    }

    public String getScope() {
        return scope;
    }

    public void setFmt(String fmt) {
        this.fmt = fmt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
