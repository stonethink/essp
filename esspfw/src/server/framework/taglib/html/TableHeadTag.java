package server.framework.taglib.html;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.*;
import server.framework.taglib.util.TagUtils;

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
public class TableHeadTag extends BodyTagSupport {
  private String styleId="";
  private String height="";
  private String width="";

  public TableHeadTag() {
    super();
  }

  public int doStartTag() throws JspException {
    StringBuffer buf = new StringBuffer();
    buf.append("<TR><TD><TABLE");
    Tag tag=this.getParent();
    while(tag!=null && !(tag instanceof TableTag)) {
    	tag=tag.getParent();
    }
    if(tag==null) {
    	throw new JspException("ERROR:Can't find TableTag");
    }

    if(tag instanceof TableTag) {
      TableTag tableTag=(TableTag)tag;
      String parentStyleId=tableTag.getStyleId();
      buf.append(" class=\""+parentStyleId+"\"");
    }
    buf.append("><TBODY><TR");
    TagUtils.appendAttribute(buf,"height",this.getHeight());
    TagUtils.appendAttribute(buf,"width",this.getWidth());
    TagUtils.appendAttribute(buf,"class",this.getStyleId());
    buf.append(">");
    ResponseUtils.write(pageContext, buf.toString());
    return 1;
  }

  public int doEndTag() throws JspException {
    StringBuffer buf = new StringBuffer();
    buf.append("</TR></TBODY></TABLE></TD></TR>");
    ResponseUtils.write(pageContext, buf.toString());
    release();
    return 1;
}


//add by xh
public void release() {
    super.release();
    styleId = "";
    height="";
    width="";
}



  public String getStyleId() {
    return styleId;
  }

  public String getHeight() {
    return height;
  }

  public String getWidth() {
    return width;
  }

  public void setStyleId(String styleId) {
    this.styleId = styleId;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public void setWidth(String width) {
    this.width = width;
  }
}
