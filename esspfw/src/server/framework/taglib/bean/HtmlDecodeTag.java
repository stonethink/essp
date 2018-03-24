package server.framework.taglib.bean;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

public class HtmlDecodeTag extends BodyTagSupport {
    private String name;
    private String property;
    private String scope;

    public HtmlDecodeTag() {
        super();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setScope(String scope) {
        this.scope = scope;
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
            } else {
                if(value instanceof String) {
                    str=(String)value;
                } else {
                    str=value.toString();
                }

                str=str.replaceAll("&amp;","&");
                str=str.replaceAll("&quto;","\"");
                str=str.replaceAll("&lt;(.*)&gt;","<$1>");
                str=str.replaceAll("&nbsp;"," ");
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
        scope=null;
    }

    public static void main(String[] args) {
        String str="abc&lt;b&gt;test";
        str=str.replaceAll("&lt;(.*)&gt;","<$1>");
        System.out.println(str);
    }
}
