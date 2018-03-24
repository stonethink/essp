package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import org.apache.struts.util.ResponseUtils;
import javax.servlet.jsp.tagext.Tag;
import server.framework.taglib.util.TagUtils;

public class TabContentTag extends AbstractBaseHandlerTag {
    public int doStartTag() throws JspException {
        Tag tag = this.getParent();
        while (tag != null && !(tag instanceof TabContentsTag)) {
            tag = tag.getParent();
        }
        if (tag == null) {
            throw new JspException("ERROR: Can't find TabContents tag");
        }
        TabContentsTag tabContents = (TabContentsTag) tag;
        tabContents.index++;

        String currID = tabContents.index+"";
        StringBuffer results = new StringBuffer("");
        String visibleStr="visible";

        tag = this.getParent();
        while (tag != null && !(tag instanceof TabPanelTag)) {
            tag = tag.getParent();
        }
        if (tag == null) {
            throw new JspException("ERROR: Can't find TabPanelTag tag");
        }
        TabPanelTag tabPanel = (TabPanelTag) tag;

        if(tabPanel.selectIndex==tabContents.index) {
                        visibleStr="visible";
        } else {
                        visibleStr="hidden";
        }

        String layerId="";
        if(tabPanel.getId()!=null && !tabPanel.getId().trim().equals("")) {
            layerId=tabPanel.getId()+"_Layer"+currID;
        } else {
            layerId="Layer"+currID;
        }

        results.append("<div id=\"" + layerId +
                       "\" style=\"position:absolute;  " +
                       "z-index:1; visibility: "+visibleStr+";width=100%; height=93%\" >");

        //delete by xh
//        results.append("<table  valign=\"top\" align=\"center\" width=\"98%\" " +
//                       "height=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\">");
//        results.append("<tr><td width=\"100%\"");
//        TagUtils.appendAttribute(results,"class",this.getStyleClass());
//        results.append(">");
        results.append("<p align=\"center\">");
        ResponseUtils.write(pageContext, results.toString());
        return 1;
    }

    public int doEndTag() throws JspException {
        StringBuffer results = new StringBuffer("");
//        results.append("</td></tr></table></div>");
        results.append("</div>");
        ResponseUtils.write(pageContext, results.toString());
        return 1;
    }
}
