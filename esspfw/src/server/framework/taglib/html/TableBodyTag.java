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
public class TableBodyTag extends BodyTagSupport {
    private String styleId = "";
    private String height = "";
    protected int rowCount = 0;
    protected String uuid="";
    protected java.util.List idList=new java.util.ArrayList();

    public TableBodyTag() {
        super();
    }

    public int doStartTag() throws JspException {
        if(!TagUtils.isEmpty(id)) {
            uuid=id;
        } else {
            java.util.Date nowDate=new java.util.Date();
            uuid=nowDate.getTime()+"";
            rowCount = 0;
        }
        idList.clear();

        StringBuffer buf = new StringBuffer();
        if (height == null || height.trim().equals("")) {
            height = "100px";
        }
        buf.append("<TR vAlign=top><TD><DIV style=\"HEIGHT: " + height +
                   "; OVERFLOW: auto\"><TABLE");
        if (styleId != null && !styleId.trim().equals("")) {
            buf.append(" class=\"" + styleId + "\"");
        }
        if (id != null && !id.equals("")) {
            buf.append(" id=\"" + id + "\"");
        }
        buf.append("><TBODY>");
        writeScript(buf);
        /**
         *
         */

        ResponseUtils.write(pageContext, buf.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        StringBuffer buf = new StringBuffer();
        buf.append("</TBODY></TABLE></DIV></TD></TR>");
        ResponseUtils.write(pageContext, buf.toString());
        release();
        return 1;
    }


    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    private void writeScript(StringBuffer buf) {
        buf.append("\n");
        buf.append("<script language='javaScript'>\n");
        buf.append("var oldColor"+uuid+"=null;");
        buf.append("var oldObj"+uuid+"=null;");
        buf.append("function changeRowColor"+uuid+"(obj) {\n");
        buf.append("  if(oldObj"+uuid+"!=null) {\n");
        buf.append("  	oldObj"+uuid+".style.backgroundColor=oldColor"+uuid+";\n");
        buf.append("  }\n");
        buf.append("  oldColor"+uuid+"=obj.style.backgroundColor;\n");
        buf.append("  oldObj"+uuid+"=obj;\n");
        buf.append("  obj.style.backgroundColor=SelectedRow_COLOR;\n");
        buf.append("}\n");
        buf.append("</script>\n");
    }


    public void release() {
        super.release();
        styleId = "";
        height = "";
        rowCount=0;
        uuid="";
        idList.clear();
    }
}
