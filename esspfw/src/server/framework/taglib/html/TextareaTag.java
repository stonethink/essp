package server.framework.taglib.html;

import javax.servlet.jsp.*;

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
public class TextareaTag extends AbstractBaseControlerTag {
    protected String beanName;
    protected String scope;
    protected String name;
    protected String cols;
    protected String rows;
    protected String maxlength;

    public String getBeanName() {
        return beanName;
    }

    public String getName() {
        return name;
    }

    public String getScope() {
        return scope;
    }

    public String getCols() {
        return cols;
    }

    public String getRows() {
        return rows;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public int doStartTag() throws JspException {
        property = name;
        if (this.getStyleClass() == null ||
            this.getStyleClass().trim().equals("")) {
          if(this.getReq()==null ||
             this.getReq().trim().equals("") ||
             this.getReq().trim().equalsIgnoreCase("false")) {
            this.setStyleClass(" Xtext Nreq");
          } else {
            this.setStyleClass(" Xtext Req");
          }
        }

        if(this.getReadonly()) {
          this.setStyleClass(this.getStyleClass()+" Display");
        }
        addReqProperty(property);
        super.doStartTag();

        //if (RequestUtils.lookup(pageContext, beanName, scope) == null) {
        doWrite();
        return 1;
        //   return 0;
        //}
        // Object value = RequestUtils.lookup(pageContext, beanName, property, scope);
        // if (value == null) {
        //   doWrite();
        //   return 0;
        // } else {
        //   setValue(value.toString());
        //   doWrite();
        //   return 2;
        //}
    }

    public int doEndTag() throws JspException {
        ResponseUtils.write(pageContext, "</textarea>");
        return 1;
    }

    protected void doWrite() throws JspException {
        prepareEventScript();
        StringBuffer buffer = new StringBuffer();
        buffer.append(getTagString());

        if (beanName != null && !beanName.trim().equals("")) {
            if (TagUtils.lookup(pageContext, beanName, scope) != null) {
                Object value = TagUtils.lookup(pageContext, beanName,
                        property, scope);
                if (value != null) {
                    if (this.getBodyContent() != null) {
                        this.getBodyContent().clearBody();
                    }
                    buffer.append(value.toString());
                }
            }
        } else if (value != null && !value.trim().equals("")) {
            if (this.getBodyContent() != null) {
                this.getBodyContent().clearBody();
            }

            buffer.append(value.toString());
        }

        ResponseUtils.write(pageContext, buffer.toString());
        release();
    }

    private void prepareEventScript() {
        if (getOnblur() == null) {
            setOnblur("doBlur();");
        }
        if (getOnfocus() == null) {
            setOnfocus("doFocus();");
        }
    }

    protected void prepareAttribute(StringBuffer sbuf) throws JspException {
        TagUtils.appendControlerAttribute(sbuf, "name", property);
        TagUtils.appendControlerAttribute(sbuf, "next", next);
        TagUtils.appendControlerAttribute(sbuf, "prev", prev);
        TagUtils.appendControlerAttribute(sbuf, "msg", msg);
        TagUtils.appendControlerAttribute(sbuf, "req", req);
        TagUtils.appendControlerAttribute(sbuf, "sreq", sreq);
        if (cols != null && !cols.trim().equals("")) {
            TagUtils.appendControlerAttribute(sbuf, "cols", cols);
        }
        if (rows != null && !rows.trim().equals("")) {
            TagUtils.appendControlerAttribute(sbuf, "rows", rows);
        }
        if (maxlength != null && !maxlength.trim().equals("")) {
            TagUtils.appendControlerAttribute(sbuf, "maxlength", maxlength);
        }

        sbuf.append(prepareEventHandlers());
        log.debug(sbuf.toString());
    }

    protected String getTagString() throws JspException {
        StringBuffer results = new StringBuffer("<textarea");
        prepareAttribute(results);
        results.append(" >");
        return results.toString();
    }

    public void release() {
        super.release();
        beanName = "";
        scope = "";
        name = "";
        value = "";
    }
}
