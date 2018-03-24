package server.framework.taglib.html;

import javax.servlet.jsp.*;

import org.apache.struts.util.*;
import server.framework.taglib.util.*;

public class SelectTag extends AbstractBaseFieldTag {
    private String match;
    private String name;
    private String beanName;
    private String matchedLabel;
    private String size;
    private String defaultIndex;
    private String defaultValue;
    private String defaultTitle;

    public SelectTag() {
        match = null;
        name = null;
        beanName = "org.apache.struts.taglib.html.BEAN";
        defaultIndex = null;
        defaultValue = null;
        defaultTitle = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String string) {
        name = string;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getMatchedLabel() {
        return matchedLabel;
    }

    public String getSize() {
        return size;
    }

    public String getDefaultIndex() {
        return defaultIndex;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setBeanName(String string) {
        beanName = string;
    }

    public void setMatchedLabel(String matchedLabel) {
        this.matchedLabel = matchedLabel;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDefaultIndex(String defaultIndex) {
        this.defaultIndex = defaultIndex;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }


    public void doCheck() throws JspException {
        if (!"true".equals(req) && !"false".equals(req)) {
            String message = AbstractBaseHandlerTag.messages.getMessage(
                    "value.error", "req", "[true]or[false]");
            log.error(message);
            throw new JspException(message);
        }
        if (!"true".equals(sreq) && !"false".equals(sreq)) {
            String message = AbstractBaseHandlerTag.messages.getMessage(
                    "value.error", "sreq", "[true]or[false]");
            log.error(message);
            throw new JspException(message);
        }
        if ("true".equals(req) && "true".equals(sreq)) {
            String message = AbstractBaseHandlerTag.messages.getMessage(
                    "exclusive.error", "[req] and [sreq]");
            log.error(message);
            throw new JspException(message);
        }
        if (name == null) {
            String message = AbstractBaseHandlerTag.messages.getMessage(
                    "fieldtype.need", new String[] {
                    "select Tag", "\"name\""
            });
            log.error(message);
            throw new JspException(message);
        }
        if (getStyleId() == null) {
            String message = AbstractBaseHandlerTag.messages.getMessage(
                    "fieldtype.need", new String[] {
                    "select Tag", "\"styleId\""
            });
            log.error(message);
            throw new JspException(message);
        } else {
            return;
        }
    }

    public int doStartTag() throws JspException {
        //log.debug("doStartTag:START");
        property = name;
        super.doStartTag();
        StringBuffer buf = new StringBuffer();

        if (this.getReadonly() == true) {

//            String sId=this.getStyleId();
//            if(sId!=null && !sId.trim().equals("")) {
//                sId=sId+sId;
//                //this.setId(sId);
//                this.setStyleId(sId);
//            }
            buf.append("<input");
            TagUtils.appendControlerAttribute(buf, "type", "text");
            TagUtils.appendAttribute(buf, "name", name + name);
            calculateMatchValue();
            //TagUtils.appendAttribute(buf, "value", match);
            TagUtils.appendControlerAttribute(buf, "type", "text");
            //renderCommonsStyleClass();
            TagUtils.appendControlerAttribute(buf, "class",
                                              " Xtext Nreq Display");
            TagUtils.appendControlerAttribute(buf, "next", next);
            TagUtils.appendControlerAttribute(buf, "prev", prev);
            TagUtils.appendControlerAttribute(buf, "msg", msg);
            TagUtils.appendControlerAttribute(buf, "req", req);
            TagUtils.appendControlerAttribute(buf, "sreq", sreq);
            TagUtils.appendControlerAttribute(buf, "fieldtype", "text");
            TagUtils.appendControlerAttribute(buf, "fielderrorflg", "");
            TagUtils.appendControlerAttribute(buf, "fieldmsg", "");
            TagUtils.appendControlerAttribute(buf, "fmt", "");
            TagUtils.appendControlerAttribute(buf, "defaultvalue", "true");
            TagUtils.appendControlerAttribute(buf, "readonly", "readonly");
            prepareEventScript();
            buf.append(prepareEventHandlers());
            //buf.append("/>");
            ResponseUtils.write(pageContext, buf.toString());
            pageContext.setAttribute(Constants.SELECT_KEY, this);
            //return this.SKIP_BODY;
            return 1;
        } else {

            buf.append("<select");
            TagUtils.appendAttribute(buf, "name", name);
            renderCommonsStyleClass();
            TagUtils.appendControlerAttribute(buf, "next", next);
            TagUtils.appendControlerAttribute(buf, "prev", prev);
            TagUtils.appendControlerAttribute(buf, "msg", msg);
            TagUtils.appendControlerAttribute(buf, "req", req);
            TagUtils.appendControlerAttribute(buf, "sreq", sreq);
            TagUtils.appendControlerAttribute(buf, "size", size);
            prepareEventScript();
            selectEventScript();
            buf.append(prepareEventHandlers());
            buf.append(">");
            buf.append(Constants.LINE_END);
            ResponseUtils.write(pageContext, buf.toString());
            pageContext.setAttribute(Constants.SELECT_KEY, this);
            calculateMatchValue();
            //log.debug("doStartTag:END");
            return 1;
        }
    }

    public int doEndTag() throws JspException {
        StringBuffer buf = new StringBuffer();
        if (this.getReadonly() == true) {

            TagUtils.appendAttribute(buf, "value", this.getMatchedLabel());
            buf.append(">");
            String sId=this.getStyleId();
            if(sId!=null && !sId.trim().equals("")) {
                sId=sId+"_hidden";
            }
            buf.append("<input type=\"hidden\"");
            TagUtils.appendAttribute(buf, "name", name);
            if (match == null || match.equals("")) {
                match = "";
            }
            TagUtils.appendAttribute(buf, "value", match);
            TagUtils.appendAttribute(buf, "id", sId);
            buf.append(">");
//            buf.append("<div style=\"background: #FFFACD;border:" +
//                    "1px solid Black;font-size: 12px;position: "+
//                    "absolute;  z-index: 1; visibility: hidden;\" id=\"layer1\"></div>");

        } else {
            //log.debug("doEndTag:START");
//            StringBuffer buf = new StringBuffer();
            buf.append("</select>");
            buf.append("<div style=\"background: infobackground;border:" +
                       "1px solid Black;font-family: Arial, Helvetica, sans-serif;"+
                       "font-size: 10px; font-style: normal; position:" +
                       "absolute;  visibility:hidden; z-index: 3;\""+
                       " id='select'></div>");

//            ResponseUtils.write(pageContext, buf.toString());
//            pageContext.removeAttribute(Constants.SELECT_KEY);
//            release();
//            return 6;
        }


        ResponseUtils.write(pageContext, buf.toString());
        pageContext.removeAttribute(Constants.SELECT_KEY);
        release();
        return 6;

    }

    public void release() {
        super.release();
        name = null;
        match = null;
        matchedLabel = "";
        defaultIndex = null;
        defaultValue = null;
        defaultTitle = null;
    }

    public boolean isMatched(String value) {
        if (match == null || value == null) {
            return false;
        }
        return match.equals(value);
    }

    private void calculateMatchValue() throws JspException {
        if (value != null) {
            match = value;
        } else {
            Object selected = null;
            try {
                selected = RequestUtils.lookup(pageContext, beanName, name, null);
            } catch (Exception e) {
                if(Global.debug) {
                    log.info(e);
                }
            }
            if (selected == null) {
                return;
            }
            match = selected.toString();
        }
        //log.debug("selectedValue:" + match);
    }

    protected void renderCommonsStyleClass() {
        StringBuffer sbuf = new StringBuffer("Selectbox ");
        if (getStyleClass() != null) {
            sbuf.append(getStyleClass());
        }
        if (req != null && "true".equals(req)) {
            sbuf.append(" Req");
        } else
        if (sreq != null && "true".equals(sreq)) {
            sbuf.append(" Sreq");
        } else {
            sbuf.append(" Nreq");
        }
        setStyleClass(sbuf.toString());
    }

    private void prepareEventScript() {

        if(getOnblur() == null) {
            if(Global.isIE) {
                setOnblur("doBlur();");
            } else {
                setOnblur("doBlur(this);");
            }
        } else {
            String oldBlur=getOnblur();
            if (Global.isIE) {
                setOnblur("doBlur();"+oldBlur);
            } else {
                setOnblur("doBlur(this);"+oldBlur);
            }
        }

        if(getOnfocus() == null) {
            if(Global.isIE) {
                setOnfocus("doFocus();");
            } else {
                setOnfocus("doFocus(this);");
            }
        } else {
            String oldOnfocus=getOnfocus();
            if (Global.isIE) {
                //如果设定为不要doFocus的话，就不添加这个事件
                //modified by:Robin.zhang
                if(getOnfocus().equals("NO_DOFOCUS")){
                    setOnfocus("");
                }else{
                    setOnfocus("doFocus();" + oldOnfocus);
                }
            } else {
                if(getOnfocus().equals("NO_DOFOCUS")){
                    setOnfocus("");
                }else{
                    setOnfocus("doFocus(this);" + oldOnfocus);
                }
            }
        }

    }
    //////////////////////test  wenjun.yang
    public void selectEventScript(){

        if (getOnmouseover() == null) {
            if (Global.isIE) {
                setOnmouseover("showTip(this,'select',event)");
            }
        } else {
            String oldMouseover = getOnmouseover();
            if (Global.isIE) {
                setOnmouseover("showTip(this,'select',event);" + oldMouseover);
            }
        }
        if (getOnmouseout() == null) {
            if (Global.isIE) {
                setOnmouseout("CloseTip('select');");
            }
        } else {
            String oldMouseout = getOnmouseout();
            if (Global.isIE) {
                setOnmouseout("CloseTip('select');" + oldMouseout);
            }
        }


    }
}
