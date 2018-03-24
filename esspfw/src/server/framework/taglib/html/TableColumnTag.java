package server.framework.taglib.html;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.JspException;
import org.apache.struts.util.ResponseUtils;
import java.util.Map;

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
public class TableColumnTag extends BodyTagSupport {
  private String onclick = "";
  private String styleId = "";
  private String width = "";
  private String ondblclick = "";
  private String align = "";
  private String toolTip = "";

  public TableColumnTag() {
    super();
  }

  public int doStartTag() throws JspException {
    StringBuffer buf = new StringBuffer();
    buf.append("<TD");
    if (styleId != null && !styleId.trim().equals("")) {
      buf.append(" class=\"" + styleId + "\"");
    }
    if (onclick != null && !onclick.trim().equals("")) {
      buf.append(" onclick=\"" + onclick + "\"");
    }

    if (id != null && !id.equals("")) {
      buf.append(" id=\"" + id + "\"");
    }
    if (ondblclick != null && !ondblclick.trim().equals("")) {
      buf.append(" ondblclick=\"" + ondblclick + "\"");
    }

    Tag tag = this.getParent().getParent().getParent();
    while(tag!=null && !(tag instanceof TableTag)) {
        tag = tag.getParent();
    }
    if(tag==null) {
    	throw new JspException("ERROR:Can't find TableTag");
    }
    if (tag!=null && tag instanceof TableTag) {
      TableTag tableTag = (TableTag) tag;
      Map tableContext = tableTag.getTableContext();
      if (tableContext != null) {
        Map widthMap = (Map) tableContext.get(TableTag.WIDTH_MAP);
        if (widthMap != null) {
          int columnCount = widthMap.size();
          int columnPointer = 1;
          String sColumnPointer = (String) tableContext.get(TableTag.COLUMN_POINTER);
          if (sColumnPointer != null && !sColumnPointer.trim().equals("")) {
            try {
              columnPointer = Integer.parseInt(sColumnPointer);
              if (columnPointer < 1) {
                columnPointer = 1;
                sColumnPointer = "1";
              } else {
//                columnPointer++;
                columnPointer = (columnPointer % columnCount) + 1;
                //System.out.println("columnPointer: " + columnPointer );
              }
            } catch (Exception ex) {
            }
          } else {
            columnPointer = 1;
          }

          if (columnPointer <= columnCount) {
            width = (String) widthMap.get(columnPointer + "");
            tableContext.put(TableTag.COLUMN_POINTER, columnPointer + "");
          } else {
            width = "";
            tableContext.put(TableTag.COLUMN_POINTER, columnPointer + "");
          }
        }
      }
    }

    if (width != null && !width.trim().equals("")) {
      buf.append(" width=\"" + width + "\"");
  }

  if (align != null && !align.trim().equals("")) {
      buf.append(" align=\"" + align + "\"");
  }

  if (toolTip != null && !toolTip.trim().equals("")) {
      buf.append(" title=\"" + toolTip + "\"");
  }



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
    align  = "";
    toolTip  = "";
    //System.out.println( "TableColumnTag: release " );
  }


  public String getStyleId() {
    return styleId;
  }

  public void setStyleId(String styleId) {
    this.styleId = styleId;
  }

  public void setOnclick(String onclick) {
    this.onclick = onclick;
  }

  public String getOnclick() {
    return onclick;
  }

  public String getOndblclick() {
    return ondblclick;
  }

    public String getAlign() {
        return align;
    }

    public String getToolTip() {
        return toolTip;
    }

    public void setOndblclick(String ondblclick) {
    this.ondblclick = ondblclick;
  }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }
}
