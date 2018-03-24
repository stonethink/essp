package server.framework.taglib.html;

import javax.servlet.jsp.*;

import org.apache.struts.util.*;
import server.framework.taglib.util.*;

public class TabPanelTag extends AbstractBaseHandlerTag {
    private String tabindex;
    protected int selectIndex = 1;
    private String width;
    private String height;

    public int doStartTag() throws JspException {
        if (this.getId() == null) {
            this.setId("");
        }
        StringBuffer results = new StringBuffer("<table");
        TagUtils.appendAttribute(results, "id", getId());
        TagUtils.appendAttribute(results, "style",
            "table-layout:fixed;word-wrap:break-word;word-break:break-all;");
        TagUtils.appendAttribute(results, "CELLPADDING", "0");
        TagUtils.appendAttribute(results, "CELLSPACING", "0");
        if (this.width == null || this.width.trim().equals("")) {
            this.width = "98%";
        }

        TagUtils.appendAttribute(results, "width", this.width);
        TagUtils.appendAttribute(results, "height", this.height);
        TagUtils.appendAttribute(results, "align", "center");
        TagUtils.appendAttribute(results, "onchange", this.getOnchange());
        TagUtils.appendAttribute(results, "tabindex", tabindex);
        results.append(" ><tr>");
        ResponseUtils.write(pageContext, results.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        release();
        return 1;
    }

    public void release() {

    }

    public void setTabindex(String tabIndex) {
        this.tabindex = tabIndex;
    }

    public String getTabindex() {
        return tabindex;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }
}
