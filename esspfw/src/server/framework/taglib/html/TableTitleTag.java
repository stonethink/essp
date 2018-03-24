package server.framework.taglib.html;

import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

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
public class TableTitleTag extends BodyTagSupport {
    private String styleId = "";
    private String width = "";
    private String onclick;
    private String ondblclick;
    private String onmouseover;
    private String onmouseout;
    private String onmousemove;
    private String onmousedown;
    private String onmouseup;
    private String onkeydown;
    private String onkeyup;
    private String onkeypress;
    private String align;
    private String valign;
    private String toolTip;

    public TableTitleTag() {
        super();
    }

    public int doStartTag() throws JspException {
        Tag tag = this.getParent();
        while(tag!=null && !(tag  instanceof TableTag)) {
        	tag = tag.getParent();
        }
        if(tag==null) {
        	throw new JspException("ERROR:Can't find TableTag");
        }

        if (tag instanceof TableTag) {
            TableTag tableTag = (TableTag) tag;
            if (width != null && !width.trim().equals("")) {
                Map tableContext = tableTag.getTableContext();
                if (tableContext != null) {
                    Map widthMap = (Map) tableContext.get(TableTag.WIDTH_MAP);
                    if (widthMap == null) {
                        widthMap = new HashMap();
                    }
                    int widthId = widthMap.size() + 1;
                    widthMap.put(widthId + "", width);
                    tableContext.put(TableTag.WIDTH_MAP, widthMap);
                    //System.out.println("put width: " + widthId + " --> " + width );
                }
            }else{
                //System.out.println( "\nTableTitleTag: Not set property -- width. " );
            }
        }

        StringBuffer buf = new StringBuffer();
        buf.append("<TD");
        TagUtils.appendAttribute(buf, "class", styleId);
        TagUtils.appendAttribute(buf, "width", width);
        TagUtils.appendAttribute(buf, "id", id);
        TagUtils.appendAttribute(buf, "onclick", onclick);
        TagUtils.appendAttribute(buf, "ondblclick", ondblclick);
        TagUtils.appendAttribute(buf, "onmouseover", onmouseover);
        TagUtils.appendAttribute(buf, "onmouseout", onmouseout);
        TagUtils.appendAttribute(buf, "onmousemove", onmousemove);
        TagUtils.appendAttribute(buf, "onmousedown", onmousedown);
        TagUtils.appendAttribute(buf, "onmouseup", onmouseup);
        TagUtils.appendAttribute(buf, "onkeydown", onkeydown);
        TagUtils.appendAttribute(buf, "onkeypress", onkeypress);
        TagUtils.appendAttribute(buf, "align", align);
        TagUtils.appendAttribute(buf, "title", toolTip);

        buf.append(">");
        ResponseUtils.write(pageContext, buf.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        StringBuffer buf = new StringBuffer();
        buf.append("</TD>");
        ResponseUtils.write(pageContext, buf.toString());
        release();
        return 1;
    }

    //add by xh
    public void release() {
      super.release();
      styleId     = "";
      width       = "";
      onclick     = "";
      ondblclick  = "";
      onmouseover = "";
      onmouseout  = "";
      onmousemove = "";
      onmousedown = "";
      onmouseup   = "";
      onkeydown   = "";
      onkeyup     = "";
      onkeypress  = "";
      align  = "";
    //System.out.println( "TableTitleTag: release " );
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setValign(String valign) {
		this.valign=valign;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public String getWidth() {
        return width;
    }

    public String getOnclick() {
        return onclick;
    }

    public String getOndblclick() {
        return ondblclick;
    }

    public String getOnkeydown() {
        return onkeydown;
    }

    public String getOnkeypress() {
        return onkeypress;
    }

    public String getOnkeyup() {
        return onkeyup;
    }

    public String getOnmousedown() {
        return onmousedown;
    }

    public String getOnmousemove() {
        return onmousemove;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public String getOnmouseup() {
        return onmouseup;
    }

    public String getAlign() {
        return align;
    }

    public String getValign() {
		return valign;
    }

    public String getToolTip() {
        return toolTip;
    }
}
