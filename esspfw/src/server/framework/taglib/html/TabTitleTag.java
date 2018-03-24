package server.framework.taglib.html;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.struts.util.ResponseUtils;

public class TabTitleTag extends AbstractBaseHandlerTag {
    private String selected = "";
    private String title="";
    private String width="";

    public TabTitleTag() {
        selected="";
        title="";
    }

    public int doStartTag() throws JspException {
        TabTitlesTag titlesTag=null;
        /**
         * add title count
         */
        Tag tag = this.getParent();
        while (tag != null && !(tag instanceof TabTitlesTag)) {
            tag = tag.getParent();
        }
        if (tag == null) {
            throw new JspException("ERROR: Can't find TabTitles tag");
        }
        titlesTag=(TabTitlesTag)tag;
        boolean isSelected=false;
        if(selected!=null && selected.trim().equalsIgnoreCase("true")) {
        	isSelected=true;
        }
        String tagStr=titlesTag.addTitle(isSelected,this.getWidth());
        if(title!=null) {
        	tagStr=tagStr+title;
        }
        ResponseUtils.write(pageContext, tagStr);
        return 1;
    }

    public int doEndTag() throws JspException {
    	StringBuffer results = new StringBuffer("");
    	results.append("</div></td>");
    	ResponseUtils.write(pageContext, results.toString());
        return 1;
    }

    public String getSelected() {
        return selected;
    }

    public String getTitle() {
        return title;
    }

  public String getWidth() {
    return width;
  }

  public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  public void setWidth(String width) {
    this.width = width;
  }

  public void release() {
        super.release();
        selected="";
        title="";
    }
}
