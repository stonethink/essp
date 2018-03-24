package server.framework.taglib.html;

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
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TableTag extends BodyTagSupport {
  public final static String WIDTH_MAP="WIDTH_MAP";
  public final static String COLUMN_POINTER="COLUMN_POINTER";
  private String styleId="";
  private Map tableContext=new HashMap();

  public TableTag() {
    super();
  }

  public int doStartTag() throws JspException {
    StringBuffer buf = new StringBuffer();
    buf.append("<TABLE");
    if(styleId!=null && !styleId.trim().equals("")) {
      buf.append(" class=\""+styleId+"\"");
    }
    if(id!=null && !id.equals("")) {
      buf.append(" id=\""+id+"\"");
    }

    buf.append(">");
    buf.append("<TBODY>");
    ResponseUtils.write(pageContext, buf.toString());
    return 1;
  }

  public int doEndTag() throws JspException {
    StringBuffer buf = new StringBuffer();
    buf.append("</TBODY></TABLE>");
    ResponseUtils.write(pageContext, buf.toString());
    release();
    return 1;
}

//add by xh
public void release() {
  super.release();
  styleId     = "";
  tableContext.clear();
    //System.out.println( "TableTag: release " );
}


  public String getStyleId() {
    return styleId;
  }

  public Map getTableContext() {
    return tableContext;
  }

  public void setStyleId(String styleId) {
    this.styleId = styleId;
  }

  public void setTableContext(Map tableContext) {
    this.tableContext = tableContext;
  }
}
