package server.framework.taglib.html;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.log4j.*;
import org.apache.struts.util.*;
import server.framework.taglib.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class HiddenTag extends BodyTagSupport {
    protected static MessageResources messages = MessageResources.getMessageResources(Constants.MESSAGE_CONFIG);
    protected Logger log=Logger.getLogger(this.getClass());;
    protected static final Locale defaultLocale = Locale.getDefault();
    private String beanName;
    private String name;
    private String property;
    private String value;
    private String scope;

    public HiddenTag() {
        beanName = "";
        property = "";
        value = "";
        name= "";
        scope = null;
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

    public String getBeanName() {
        return beanName;
    }

    public String getValue() {
        return value;
    }

    public String getProperty() {
        return property;
    }

    public String getScope() {
        return scope;
    }

  public String getName() {
    return name;
  }

  public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

  public void setName(String name) {
    this.name = name;
  }

  public void release() {
        super.release();
        beanName = "";
        property = "";
        value = "";
        scope=null;
        name= "";
    }

    public int doStartTag() throws JspException {
        super.doStartTag();

        if(name==null || name.trim().equals("")) {
          name=this.property;
        }

        /**
         * check beanName
         */
        String propertyValue="";
        try {
            if (beanName != null && !beanName.trim().equals("")) {
                propertyValue = "";
                if (RequestUtils.lookup(pageContext, beanName, scope) != null) {
                    Object pvalue = RequestUtils.lookup(pageContext, beanName, name,
                                                        scope);
                    if (pvalue != null) {
                        propertyValue = pvalue.toString();
                    }
                }
            }

            if(propertyValue!=null && !propertyValue.trim().equals("")) {
                value=propertyValue;
            } else {
				value="";
            }
        } catch(Exception e) {
            if(Global.debug) {
                log.info(e);
            }
        }

        if(value==null) {
            value="";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<input type=\"hidden\" ");
        sb.append("name=\"" + name + "\" ");
        if (value != null) {
            sb.append("value=\"" + value + "\">");
        } else {
            sb.append("value=\"\">");
        }
        JspWriter writer = pageContext.getOut();
        try {
            writer.write(sb.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return 1;
    }

    public int doEndTag() throws JspException {
		release();
        return 1;
    }

  private void jbInit() throws Exception {
  }

}
